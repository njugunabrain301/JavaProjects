
package Hangman;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerTest {

    /**
     * Test of getName method, of class Player.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Player instance = new Player();
        String expResult = "Player";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of setName method, of class Player.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "Player";
        Player instance = new Player();
        instance.setName(name);
        assertEquals(name, instance.getName());
    }

    /**
     * Test of getGamesPlayed method, of class Player.
     */
    @Test
    public void testGetGamesPlayed() {
        System.out.println("getGamesPlayed");
        Player instance = new Player();
        int expResult = 0;
        int result = instance.getGamesPlayed();
        assertEquals(expResult, result);
    }

    /**
     * Test of addGamesPlayed method, of class Player.
     */
    @Test
    public void testAddGamesPlayed() {
        System.out.println("addGamesPlayed");
        Player instance = new Player();
        instance.addGamesPlayed();
        assertEquals(1,instance.getGamesPlayed());
    }

    /**
     * Test of getWins method, of class Player.
     */
    @Test
    public void testGetWins() {
        System.out.println("getWins");
        Player instance = new Player();
        int expResult = 0;
        int result = instance.getWins();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of addWins method, of class Player.
     */
    @Test
    public void testAddWins() {
        System.out.println("addWins");
        Player instance = new Player();
        instance.addWins();
        assertEquals(1,instance.getWins());
    }

    /**
     * Test of toString method, of class Player.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Player instance = new Player();
        String expResult = "Player,0,0";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of setGamesPlayed method, of class Player.
     */
    @Test
    public void testSetGamesPlayed() {
        System.out.println("setGamesPlayed");
        int gamesPlayed = 0;
        Player instance = new Player();
        instance.setGamesPlayed(gamesPlayed);
        assertEquals(gamesPlayed, instance.getGamesPlayed());
    }

    /**
     * Test of setWins method, of class Player.
     */
    @Test
    public void testSetWins() {
        System.out.println("setWins");
        int wins = 0;
        Player instance = new Player();
        instance.setWins(wins);
        assertEquals(wins,instance.getWins());
    }
    
}
