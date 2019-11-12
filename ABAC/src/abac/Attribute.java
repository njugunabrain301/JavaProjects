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
public class Attribute {
    
    private String name;
    private String value;
    
    public Attribute(String name, String value){
        this.name = name.trim();
        this.value = value.trim();
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getValue(){
        return this.value;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setValue(String value){
        this.value = value;
    }
    
     public String toString(){
        return "Attribute name: "+name+", Attribute value: "+value;
    }
}
