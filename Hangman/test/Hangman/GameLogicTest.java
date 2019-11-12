
package Hangman;

import javax.swing.JLabel;
import javax.swing.JTextField;
import linked_data_structures.SinglyLinkedList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class GameLogicTest {

    /**
     * Test of getGuess method, of class GameLogic.
     */
    @Test
    public void testGetGuess() {
        System.out.println("getGuess");
        JTextField field = new JTextField();
        field.setText("Hello");
        GameLogic instance = new GameLogic();
        int expResult = 0;
        int result = instance.getGuess(field);
        assertEquals(expResult, result);
    }

    /**
     * Test of hint method, of class GameLogic.
     */
    @Test
    public void testHint() throws Exception {
        System.out.println("hint");
        JTextField field = new JTextField();
        field.setText("Hello");
        GameLogic instance = new GameLogic();
        int expResult = -10;
        instance.letterLabels = new SinglyLinkedList();
        instance.letters = new SinglyLinkedList();
        instance.getGuess(field);
        int result = instance.hint();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of scores method, of class GameLogic.
     */
    @Test
    public void testScores() throws Exception {
        System.out.println("scores");
        GameLogic instance = new GameLogic();
        instance.scores();
        assertEquals("","");
    }

    /**
     * Test of rules method, of class GameLogic.
     */
    @Test
    public void testRules() {
        System.out.println("rules");
        GameLogic instance = new GameLogic();
        instance.rules();
        assertEquals("","");
    }
}
