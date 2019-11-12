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
class Resource {
    private String name;
    private String value;
    
    public Resource(String name, String value){
        this.name = name.trim();
        this.value = value.trim();
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getValue(){
        return this.value;
    }
    
    public String toString(){
        return "Resource name: "+name+", Resource value: "+value+"";
    }
}
