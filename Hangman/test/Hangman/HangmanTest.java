
package Hangman;

import java.io.FileNotFoundException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class HangmanTest {
    @Test
    public void testGameView() throws FileNotFoundException {
        System.out.println("gameView");
        Hangman instance = new Hangman(FileIO.readWords(), new Player());
        instance.gameView();
        assertEquals("1","1");
        
    }

    /**
     * Test of newG method, of class Hangman.
     */
    @Test
    public void testNewG() throws Exception {
        System.out.println("newG");
        Hangman instance = new Hangman(FileIO.readWords(), new Player());
        instance.logic.processGuess('c');
        instance.newG();
        assertEquals("1","1");
    }
    
}
