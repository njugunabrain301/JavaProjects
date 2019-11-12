/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abac;

import java.sql.Time;

/**
 *
 * @author Brian John
 */
public class Environment {
    private String name;
    private String operatingHours;
    
    public Environment(String name, String operatingHours){
        this.name = name.trim();
        this.operatingHours = operatingHours.trim();
    }
    
    public String getOH(){
        return operatingHours;
    }
    
    public String getName(){
        return name;
    }
    
    public String toString(){
        return "Environment name: "+name+", Operating hours: "+operatingHours;
    }
    
    public boolean isWithinOH(String operatingHours){
        boolean isTrue = true;
        
        //Environment validation
        
        return isTrue;
    }
}
