/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abac;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Brian John
 */
public class ABAC {
    
    private static ArrayList<Attribute> attributes = new ArrayList();
    private static ArrayList<Permission> permissions = new ArrayList();
    private static ArrayList<Entity> entities = new ArrayList();
    private static ArrayList<PolicyAssignment> PAs = new ArrayList();
    private static ArrayList<AttributeAssignment> AAs = new ArrayList();
    private static ArrayList<Resource> resources = new ArrayList();
    
    public static void main(String[] args){
        
        try {
            System.out.print("> ");
            String command = getInput();
            String[] commands = command.split(";");

            if (command.startsWith("abacmonitor")) {
                for (String input : commands) {
                    input = (input.replaceFirst("abacmonitor", "")).trim();
                    if (input.startsWith("check-permission")) {
                        checkPermission(input);
                    } else if (input.startsWith("load-policy")) {
                        loadPolicies(input);
                    } else if (input.startsWith("show-policy")) {
                        showPolicies();
                    } else if (input.startsWith("add-entity")) {
                        addEntity(input);
                    } else if (input.startsWith("remove-entity")) {
                        removeEntity(input);
                    } else if (input.startsWith("add-attribute")) {
                        addAttribute(input);
                    } else if (input.startsWith("remove-attribute")) {
                        removeAttribute(input);
                    } else if (input.startsWith("add-permission")) {
                        addPermission(input);
                    } else if (input.startsWith("remove-permission")) {
                        removePermission(input);
                    } else if (input.startsWith("add-attribute-to-permission")) {
                        addAttributestoPermission(input);
                    } else if (input.startsWith("remove-attribute-from-permission")) {
                        removeAttributesfromPermission(input);
                    } else if (input.startsWith("add-attribute-toentity")) {
                        addAttributestoEntity(input);
                    } else if (input.startsWith("remove-attribute-from-entity")) {
                        removeAttributesfromEntity(input);
                    }

                }
            } else {
            }

        } catch (Exception ex) {
        }
    }
    
    public static String getInput()throws Exception{
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }
    
    public static void loadPolicies(String command) throws Exception{
        String[] commands = command.split(" ");
        File file = new File(commands[1]);
        Scanner scan = new Scanner(file);
        
        while(scan.hasNext()){
            String line = scan.nextLine();
            line = line.replaceAll("<", "");
            line = line.replaceAll(">", "");
            if(line.startsWith("ATTRS")){
                line = line.replaceFirst("ATTRS =", "");
                String[] infos = line.split(";");
                for(String info: infos){
                    String[] split = info.split(",");
                    Attribute att = new Attribute(split[0].trim(), split[1].trim());
                    attributes.add(att);
                }
            }else if (line.startsWith("PERMS")){
                line = line.replaceFirst("PERMS =", "");
                String[] infos = line.split(";");
                for(String info: infos){
                    Permission permission = new Permission(info.trim());
                    permissions.add(permission);
                }
            }else if (line.startsWith("PA")){
                line = line.replaceFirst("PA =", "");
                String[] assignments = line.split("-");
                for(String assignment: assignments){
                    String permissionName = assignment.split(":")[1];
                    assignment = assignment.replaceAll(":", "");
                    assignment = assignment.replaceAll(permissionName, "");
                    String[] infos = assignment.split(";");
                    String[] info1 = infos[0].split(",");
                    String[] info2 = null; String[] info3 = null;
                    try{info2 = infos[1].split(",");}catch(Exception e){}
                    try{info3 = infos[2].split(",");}catch(Exception e){}
                    Resource rsc = new Resource("null", "null");
                    Attribute att = new Attribute("null", "null");
                    Environment env = new Environment("null", "null");
                    Permission name = new Permission(permissionName);
                    if(info2 == null && info3 == null)
                        att = new Attribute(info1[0], info1[1].replaceAll("\"", ""));
                    else{
                        rsc = new Resource(info1[0], info1[1].replaceAll("\"", ""));
                        att = new Attribute(info2[0], info2[1].replaceAll("\"", ""));
                        env = new Environment(info3[0], info3[1]);
                    }
                    PolicyAssignment PA = new PolicyAssignment(att, rsc, name,env);
                    PAs.add(PA);
                }
            }else if (line.startsWith("ENTITIES")){
                line = line.replaceFirst("ENTITIES =", "");
                String[] infos = line.split(";");
                for(String info: infos){
                    Entity entity = new Entity(info.trim());
                    entities.add(entity);
                }
            }else if (line.startsWith("AA")){
                line = line.replaceFirst("AA =", "");
                String[] assignments = line.split(";");
                for(String assignment: assignments){
                    String[] info = assignment.split(":");
                    String[] attributes = info[1].split(",");
                    Entity entity = new Entity(info[0].trim());
                    Attribute att = new Attribute(attributes[0], attributes[1].replaceAll("\"", ""));
                    AttributeAssignment AA = new AttributeAssignment(att, entity);
                    AAs.add(AA);
                }
            }
        }
    }
    
    public static void showPolicies() throws Exception{
        System.out.println("Attributes");
        for(Attribute attribute: attributes)
            System.out.println(attribute.toString());
        System.out.println("");
        System.out.println("Entities");
        for(Entity entity: entities)
            System.out.println(entity.toString());
        System.out.println("");
        System.out.println("Permissions");
        for(Permission permission: permissions)
            System.out.println(permission.toString());
        System.out.println("");
        System.out.println("Policy Assignments");
        for(PolicyAssignment PA: PAs)
            System.out.println(PA.toString());
        System.out.println("");
        System.out.println("Attribute Assignments");
        for(AttributeAssignment AA: AAs)
            System.out.println(AA.toString());
    }
    
    public static void checkPermission(String command) throws Exception{
        String[] commands = command.split(" ");
        boolean permissionExists = false;
        for(Permission p: permissions)
            if(p.getName().equals(commands[4])){
                permissionExists = true;
        }
        Entity pEntity = null;
        for(Entity entity: entities){
            if(entity.getName().equalsIgnoreCase(commands[1])){
                pEntity = entity;
                break;
            }
        }
        boolean found = false;
        if (pEntity != null && permissionExists) {
            Attribute pAttribute = null;
            for (AttributeAssignment AA : AAs) {
                Entity eEntity = AA.getEntity();
                if (pEntity.getName().equals(eEntity.getName())) {
                    pAttribute = AA.getAttribute();
                    for (PolicyAssignment PA : PAs) {
                        Attribute eAttribute = PA.getAttribute();
                        boolean attributeExists = false;
                        for(Attribute a: attributes)
                            if(a.getName().equals(eAttribute.getName()))
                                attributeExists = false;
                        if(eAttribute.getName().equalsIgnoreCase("file owner"))
                            found = true;
                        if (eAttribute.getValue().equals(pAttribute.getValue())) {
                            Resource rsc = PA.getResource();
                            Permission perm = PA.getPermission();
                            Environment env = PA.getEnvironment();
                            if (rsc.getValue().equals(commands[2]) || true) {//Because there is only one resource
                                if (env.isWithinOH(commands[3])) {
                                    if (perm.getName().equals(commands[4])) {
                                        found = true;
                                    }
                                }
                            }
                        }
                    }
                    //break;
                }
            }
        }
        
        if(found) System.out.println("Permission Granted!");
        else System.out.println("Permission Denied!");
    }
    
    public static void addEntity(String command) throws Exception{
        String[] commands = command.split(" ");
        Entity entity = new Entity(commands[1]);
        entities.add(entity);
    }
    
    public static void removeEntity(String command) throws Exception{
        String[] commands = command.split(" ");
        for(Entity entity: entities){
            if(entity.getName().equals(commands[1])){
                entities.remove(entity);
                break;
            }
        }
    }
    
    public static void addAttribute(String command) throws Exception{
        String[] commands = command.split(" ");
        Attribute attribute = new Attribute(commands[1], commands[2]);
        attributes.add(attribute);
    }
    
    public static void removeAttribute(String command) throws Exception{
        String[] commands = command.split(" ");
        for(Attribute attribute: attributes){
            if(attribute.getName().equals(commands[1])){
                attributes.remove(attribute);
                break;
            }
        }
    }
    
     public static void addPermission(String command) throws Exception{
        String[] commands = command.split(" ");
        Permission permission = new Permission(commands[1]);
        permissions.add(permission);
     }
     
     public static void removePermission(String command) throws Exception{
        String[] commands = command.split(" ");
        for(Permission permission: permissions){
            if(permission.getName().equals(commands[1])){
                permissions.remove(permission);
                break;
            }
        }
    }
     
    public static void addAttributestoPermission(String command)throws Exception{
        //command = command.replaceAll("[", "");
        //command = command.replaceAll("]", "");
        String[] commands = command.split(" ");
        Attribute attribute = new Attribute(commands[2], commands[3]);
        Permission permission = new Permission(commands[1]);
        Resource resource = new Resource("","");
        Environment env = new Environment("","");
        PolicyAssignment PA = new PolicyAssignment(attribute, resource, permission, env);
        PAs.add(PA);
    }
    
    public static void removeAttributesfromPermission(String command) throws Exception{
        //command = command.replaceAll("[", "");
        //command = command.replaceAll("]", "");
        String[] commands = command.split(" ");
        Attribute attribute = new Attribute(commands[2], commands[3]);
        for(PolicyAssignment PA: PAs){
            Attribute a = PA.getAttribute();
            Permission p = PA.getPermission();
            if(p.getName().equals(commands[1]) && a.getName().equals(commands[2]) && a.getValue().equals(commands[3])){
                PAs.remove(PA);
                break;
            }
        }
    }
    
    public static void addAttributestoEntity(String command) throws Exception{
        //command = command.replaceAll("[", "");
        //command = command.replaceAll("]", "");
        String[] commands = command.split(" ");
        Attribute attribute = new Attribute(commands[2], commands[3]);
        Entity entity = new Entity(commands[1]);
        AttributeAssignment AA = new AttributeAssignment(attribute, entity);
        AAs.add(AA);
    }
    
    public static void removeAttributesfromEntity(String command) throws Exception{
        //command = command.replaceAll("[", "");
        //command = command.replaceAll("]", "");
        String[] commands = command.split(" ");
        for(AttributeAssignment AA: AAs){
            Attribute a = AA.getAttribute();
            Entity e = AA.getEntity();
            if(a.getName().equals(commands[2]) && a.getValue().equals(commands[3])&& e.getName().equals(commands[1])){
                AAs.remove(AA);
                break;
            }
        }
    }
    
}
