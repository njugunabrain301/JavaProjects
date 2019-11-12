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
public class Entity {
    private String name;
    
    public Entity(String name){
        this.name = name;
    }
    
    public String getName(){
        return this.name;
    }
    
     public String toString(){
        return "Entity name: "+name;
    }
}
