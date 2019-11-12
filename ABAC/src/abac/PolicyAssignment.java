/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abac;

/**
 *
 * @author Brian John
 */
public class PolicyAssignment {
    private Attribute attribute;
    private Resource resource;
    private Permission permission;
    private Environment environment;
    
    public PolicyAssignment(Attribute attribute, Resource resource, Permission permission, Environment environment){
        this.attribute = attribute;;
        this.resource = resource;
        this.permission = permission;
        this.environment = environment;
    }
    
    public Attribute getAttribute(){
        return attribute;
    }
    
    public Permission getPermission(){
        return permission;
    }
    
    public Resource getResource(){
        return resource;
    }
    
    public Environment getEnvironment(){
        return environment;
    }
    
    public String toString(){
        return "Policy assignment: ["+attribute.toString()+" assigned to "+resource+" permission "+permission.toString()+" conditions "+environment.toString()+"]";
    }
}
