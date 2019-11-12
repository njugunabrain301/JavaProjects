
package Hangman;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import linked_data_structures.DoublyLinkedList;


public class Manager {
    static Player player;
    static DoublyLinkedList scores;
    public static void main(String[] args) throws FileNotFoundException {
        scores = FileIO.getScoreBoard();
        getPlayer();
    }
    
    public static void getPlayer(){
        JFrame frame = new JFrame("Welcome");
        frame.setSize(400, 150);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.cyan);
        frame.add(panel);
        JLabel label = new JLabel("Select existing name or Enter a new name");
        label.setBounds(10, 10, 300, 20);
        panel.add(label);
        JComboBox box = new JComboBox();
        box.setBounds(10, 30, 130, 20);
        for(int i = 0; i < scores.getLength(); i++){
            Player p = (Player) scores.getElementAt(i);
            box.addItem(p.getName());
        }
        panel.add(box);
        JTextField field = new JTextField();
        field.setBounds(145, 30, 130, 20);
        panel.add(field);
        JButton button = new JButton("Start");
        button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                openNext(field, box, frame);
            }
            
        });
        button.setBounds(290, 30, 80, 20);
        panel.add(button);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void openNext(JTextField field, JComboBox box, JFrame frame) {
        String txt = field.getText();
        String com = (String) box.getSelectedItem();
        String use = "";
        if (!txt.isEmpty()) {
            use = txt;
            player = new Player();
            player.setName(use);
        } else if (!com.isEmpty()) {
            use = com;
        }
        System.out.println(use);
        if (use.isEmpty()) {
            return;
        } else {
            boolean found = false;
            for (int i = 0; i < scores.getLength(); i++) {
                Player p = (Player) scores.getElementAt(i);
                if (p.getName().equals(use)) {
                    player = p;
                    found = true;
                    break;
                }
            }
            if (!found) {
                scores.add(player);
            }
        }

        try {
            frame.setVisible(false);
            Hangman hm = new Hangman(FileIO.readWords(), player);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
