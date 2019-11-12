/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abac;

import java.util.ArrayList;

/**
 *
 * @author Brian John
 */
public class Permission {
    
    private String name;
    private ArrayList<Attribute> attributes;
    
    public Permission(String name){
        this.name = name.trim();
        attributes = new ArrayList();
    }
    
    public String getName(){
        return this.name;
    }
    
    public void addAttribute(Attribute attribute){
        this.attributes.add(attribute);
    }
    
    public String toString(){
        return "Permission name: "+name;
    }
}
