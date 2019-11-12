
package Hangman;

import java.io.File;
import linked_data_structures.DoublyLinkedList;
import linked_data_structures.SinglyLinkedList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class FileIOTest {

    /**
     * Test of getScoreBoard method, of class FileIO.
     */
    @Test
    public void testGetScoreBoard() throws Exception {
        System.out.println("getScoreBoard");
        DoublyLinkedList expResult = FileIO.getScoreBoard();
        DoublyLinkedList result = FileIO.getScoreBoard();
        assertEquals(expResult.getLength(), result.getLength());
        
    }

    /**
     * Test of readWords method, of class FileIO.
     */
    @Test
    public void testReadWords() throws Exception {
        System.out.println("readWords");
        SinglyLinkedList expResult = FileIO.readWords();
        SinglyLinkedList result = FileIO.readWords();
        assertEquals(expResult.getLength(), result.getLength());
    }

    /**
     * Test of serialize method, of class FileIO.
     */
    @Test
    public void testSerialize() throws Exception {
        System.out.println("serialize");
        DoublyLinkedList scores = new DoublyLinkedList();
        SinglyLinkedList words = new SinglyLinkedList();
        FileIO.serialize(scores, words);
        File f = new File("scoreboard.txt");
        File f2 = new File("savedWords.txt");
        if(f.exists() && f2.exists())
            assertEquals("","");
        else
            assertEquals("fail","");
    }
    
}
