
package Hangman;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import linked_data_structures.DoublyLinkedList;
import linked_data_structures.SinglyLinkedList;

public class Hangman {
    
    GameLogic logic = new GameLogic();
    JLabel guessedL;
    JFrame game = new JFrame("Hangman");
    MyPanel panel;
    
    public Hangman(SinglyLinkedList words, Player player){
        logic.words = words;
        logic.player = player;
        set();
    }
    
    public void set(){
        logic.letterLabels = new SinglyLinkedList();
        logic.letters = new SinglyLinkedList();
        logic.guessed = new SinglyLinkedList();
        logic.mistakes = 0;logic.correctGuesses = 0;
        logic.guessedL = "";
        panel = new MyPanel();
        guessedL = new JLabel();
        Random rand = new Random();
        int index = rand.nextInt(logic.words.getLength()-1);
        String str = (String) logic.words.getElementAt(index);
        logic.words.remove(index);
        System.out.println(str);
        for(int i = 0; i < str.length(); i++){
            logic.letters.add(str.charAt(i));
        }
        Line2D l1 = new Line2D.Float(200, 100, 200, 200);
        Line2D l2 = new Line2D.Float(170, 200, 230, 200);
        Line2D l3 = new Line2D.Float(200, 100, 240, 100);
        Line2D l4 = new Line2D.Float(240, 100, 240, 115);
        Ellipse2D head = new Ellipse2D.Float(233,115,15,15);
        Line2D l5 = new Line2D.Float(240,130, 240, 160);
        Line2D l6 = new Line2D.Float(220, 130, 240, 135);
        Line2D l7 = new Line2D.Float(260, 130, 240, 135);
        Line2D l8 = new Line2D.Float(220, 175, 240, 160);
        Line2D l9 = new Line2D.Float(260, 175, 240, 160);
        logic.shapes.add(l1);logic.shapes.add(l2);logic.shapes.add(l3);logic.shapes.add(l4);
        logic.shapes.add(head);logic.shapes.add(l5);logic.shapes.add(l6);logic.shapes.add(l7);
        logic.shapes.add(l8);logic.shapes.add(l9);
        gameView();
    }
    
    public void gameView(){
        game.add(panel);
        game.setSize(400, 300);
        panel.setLayout(null);
        int first = 40;
        int width = 15;
        JLabel name = new JLabel("Player: "+logic.player.getName()+"  Games Played: "+logic.player.getGamesPlayed()+"  Games Won: "+logic.player.getWins());
        name.setBackground(Color.BLACK);
        name.setForeground(Color.yellow);
        name.setOpaque(true);
        name.setBounds(3, 3, 378, 20);
        panel.add(name);
        panel.setBackground(Color.CYAN);
        for(int i = 0; i < logic.letters.getLength(); i++){
            JLabel l = new JLabel("_");
            l.setBounds(first+(width*i), 60, 12, 20);
            logic.letterLabels.add(l);
        }
        
        JLabel label = new JLabel("Guess the word");
        label.setBounds(first, 30, 200, 30);
        panel.add(label);
        
        JLabel label2 = new JLabel("Guessed Letters");
        label2.setBounds(first, 160, 200, 30);
        panel.add(label2);
        
        
        guessedL.setBounds(first, 190, 200, 30);
        panel.add(guessedL);
        
        JTextField field = new JTextField();
        field.setBounds(first, 100, 30, 30);
        
        JButton guess = new JButton("Guess");
        guess.setBounds(first + 40, 100, 100, 30);
        
        guess.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int ret = logic.getGuess(field);
                guessedL.setText(logic.guessedL);
                panel.repaint();
                guessMade(ret);
                
            }
        
        });
        
        panel.add(field);
        panel.add(guess);
        for(int i = 0; i < logic.letters.getLength(); i++){
            panel.add((JLabel)logic.letterLabels.getElementAt(i));
        }
        
        MenuBar menu = new MenuBar();
        Menu file = new Menu("File");
        MenuItem newG = new MenuItem("New Game");
        newG.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                newG();
            }
        });
        MenuItem scores = new MenuItem("Score Board");
        scores.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    logic.scores();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Hangman.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        MenuItem hint = new MenuItem("Hint");
        hint.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    guessMade(logic.hint());
                } catch (Exception ex) {
                    Logger.getLogger(Hangman.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        MenuItem rules = new MenuItem("Rules");
        rules.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                logic.rules();
            }
        });
        MenuItem exit = new MenuItem("Exit");
        exit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    logic.exit();
                } catch (Exception ex) {
                    Logger.getLogger(Hangman.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        file.add(newG);file.add(scores);file.add(hint);file.add(rules);file.add(exit);
        menu.add(file);
        game.setMenuBar(menu);
        panel.setVisible(true);
        game.setVisible(true);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void guessMade(int ret) {
        if (ret == 1) {
            ret = JOptionPane.showConfirmDialog(null, "You Win\nDo you want to play again", "Continue?", JOptionPane.YES_NO_OPTION);
        } else if (ret == 2) {
            ret = JOptionPane.showConfirmDialog(null, "You loose\nDo you want to play again", "Continue?", JOptionPane.YES_NO_OPTION);
        }
        if (ret == 0) {
            newG();
        } else if (ret == 1 || ret == 2) {
            try {
                FileIO.serialize(Manager.scores, logic.words);
            } catch (Exception ex) {
                Logger.getLogger(Hangman.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.exit(0);
        }
    }
    
    public void newG(){
        game.remove(panel);
        set();
    }

    class MyPanel extends JPanel{
    @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            for(int i = 0; i < (logic.mistakes + 4); i++)
                g2.draw(logic.shapes.get(i));
        }
    }
}

