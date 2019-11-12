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
public class AttributeAssignment {
    private Attribute attribute;
    private Entity entity;
    
    public AttributeAssignment(Attribute attribute, Entity entity){
        this.attribute = attribute;
        this.entity = entity;
    }
    
    public Attribute getAttribute(){
        return this.attribute;
    }
    
    public Entity getEntity(){
        return this.entity;
    }
    
     public String toString(){
        return "Attribute assignment: ["+attribute.toString()+" assigned to "+entity.toString()+"]";
    }
}
