
package Hangman;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Formatter;
import java.util.Scanner;
import linked_data_structures.DoublyLinkedList;
import linked_data_structures.SinglyLinkedList;

public class FileIO{
    
    public static DoublyLinkedList getScoreBoard() throws FileNotFoundException{
        DoublyLinkedList scoreboard = new DoublyLinkedList();
        File f = new File("scoreboard.txt");
        if(!f.exists()){
            return scoreboard;
        }
        Scanner sc = new Scanner(f);
        while(sc.hasNext()){
            String[] info = sc.nextLine().split(",");
            Player p = new Player();
            p.setName(info[0]);
            p.setGamesPlayed(Integer.parseInt(info[1]));
            p.setWins(Integer.parseInt(info[2]));
            scoreboard.add(p);
        }
        
        int[] order = new int[scoreboard.getLength()];
        for(int i = 0; i < order.length; i++)
            order[i] = i;
        
        for(int i = 0; i < order.length; i++){
            for(int j = 0; j+1 < order.length; j++){
                Player p1 = (Player) scoreboard.getElementAt(j);
                Player p2 = (Player) scoreboard.getElementAt(j+1);
                
                if(p1.getName().compareToIgnoreCase(p2.getName()) < 0){
                    int temp = order[j];
                    order[j] = order[j+1];
                    order[j+1] = temp;
                }
            }
        }
        
        DoublyLinkedList scoreboardSorted = new DoublyLinkedList();
        
        for(int i = 0; i < order.length; i++)
            scoreboardSorted.add(scoreboard.getElementAt(order[i]));
        
        return scoreboardSorted;
    }
    
    public static SinglyLinkedList readWords() throws FileNotFoundException{
        SinglyLinkedList words = new SinglyLinkedList();
        File f1 = new File("savedWords.txt");
        File f2 = new File("dictionary.txt");
        File file = null;
        if(f1.exists())
            file = f1;
        else
            file = f2;
        Scanner sc = new Scanner(file);
        while(sc.hasNext()){
            String word = sc.nextLine();
            words.add(word);
        }
        if(words.getLength() == 0){
            sc = new Scanner(f2);
            while(sc.hasNext()){
                String word = sc.nextLine();
                words.add(word);
            }
        }
        return words;
    }
    
    public static void serialize(DoublyLinkedList scores, SinglyLinkedList words) throws Exception{
        File f = new File("scoreboard.txt");
        Formatter x = new Formatter(f);x.close();
        BufferedWriter w = new BufferedWriter(new FileWriter(f));
        for(int i = 0; i < scores.getLength(); i++){
            Player p = (Player) scores.getElementAt(i);
            w.write(p.toString());w.newLine();
        }
        w.close();
        f = new File("savedWords.txt");
        x = new Formatter(f);x.close();
        w = new BufferedWriter(new FileWriter(f));
        for(int i = 0; i < words.getLength(); i++){
            String s = (String) words.getElementAt(i);
            w.write(s);w.newLine();
        }
        w.close();
    }
}
