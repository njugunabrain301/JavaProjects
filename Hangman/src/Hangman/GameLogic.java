
package Hangman;

import java.awt.Shape;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import linked_data_structures.DoublyLinkedList;
import linked_data_structures.SinglyLinkedList;

/**
 *
 * @author 
 */
public class GameLogic {
    
    SinglyLinkedList letterLabels;
    SinglyLinkedList letters;
    SinglyLinkedList guessed;
    SinglyLinkedList words;
    int mistakes;
    int correctGuesses;
    String correct = "";
    Player player;
    String guessedL = "";
    ArrayList<Shape> shapes = new ArrayList();
    
    /**
     *
     * @param field
     * @return
     */
    public int getGuess(JTextField field){
        String text = field.getText();
        field.setText("");
        if (text.length() != 1) {
            JOptionPane.showMessageDialog(null, "Enter a single character");
        } else {
            try {
                return processGuess(text.charAt(0));
            } catch (Exception ex) {
                Logger.getLogger(Hangman.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return 0;
    }
    
    /**
     *
     * @param c
     * @return
     * @throws Exception
     */
    public int processGuess(char c) throws Exception{
        boolean found = false;
        correct+=c;
        for(int i = 0; i < letters.getLength(); i++){
            if(c == (char)letters.getElementAt(i)){
                found = true;
                ((JLabel)letterLabels.getElementAt(i)).setText(c+"");
                correctGuesses++;
                if(correctGuesses == letters.getLength()){
                    player.addGamesPlayed();player.addWins();
                    return 1;
                }
            }
        }
        if(!found){
            guessed.add(c);
            mistakes++;
            guessedL+=" '"+c+"'";
        }
        if(mistakes >= 6){
            player.addGamesPlayed();
            return 2;
        }
        return -1;
    }
    
    public void scores() throws FileNotFoundException{
        JFrame scores = new JFrame("Score Board");
        scores.setSize(400, 500);
        DoublyLinkedList scoresp = FileIO.getScoreBoard();
        JPanel panel = new JPanel();
        panel.setLayout(null);
        JScrollPane scroll = new JScrollPane(panel);
        int x = 5, y = 5;
        JLabel titleName = new JLabel("Name");
        titleName.setBounds(x, y, 130, 20);
        JLabel TGamesPlayed = new JLabel("Games Played");
        TGamesPlayed.setBounds(x+130, y, 130, 20);
        JLabel TWins = new JLabel("Wins");
        TWins.setBounds(x+260, y, 130, 20);
        panel.add(titleName);panel.add(TGamesPlayed);panel.add(TWins);
        for(int i = 0; i < scoresp.getLength(); i++){
            y+=15;
            Player pl = (Player) scoresp.getElementAt(i);
            JLabel nm = new JLabel(pl.getName());
            nm.setBounds(x, y, 130, 20);
            JLabel played = new JLabel(pl.getGamesPlayed()+"");
            played.setBounds(x+130, y, 130, 20);
            JLabel won = new JLabel(pl.getWins()+"");
            won.setBounds(x+260, y, 130, 20);
            panel.add(nm);panel.add(played);panel.add(won);
        }
        scores.add(panel);
        scores.setVisible(true);
        scores.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
    
    public int hint() throws Exception{
        Random rand = new Random();
        if(letters.getLength() == 0) return -10;
        int index = rand.nextInt(letters.getLength());
        char c = (char) letters.getElementAt(index);
        while(correct.contains(c+"")){
            index++;
            if(index == letters.getLength())
                index = 0;
            c = (char) letters.getElementAt(index);
        }
        return processGuess(c);
    }
    
    public void rules(){
        String string = "1. Guess letters until you have filled out all the blank spaces in the given word\n2. You fail when you make 6 wrong guesses\n3. You can utilize one hint to show you one or more letters";
        JOptionPane.showMessageDialog(null, string,"Rules",JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void exit() throws Exception{
        FileIO.serialize(Manager.scores, words);
        System.exit(0);
    }
}
