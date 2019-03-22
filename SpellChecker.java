/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spellchecker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Brian John
 */
public class SpellChecker {

    /**
     * @param args the command line arguments
     */
    private static ArrayList wordList = new ArrayList();
    private static ArrayList suggestions = new ArrayList();
    
    private static ArrayList correctSpellings = new ArrayList();

    public static void main(String[] args) {
        readDictionary();

        System.out.println("No file entered in command line. Enter a file name or path");
        Scanner scanner = new Scanner(System.in);
        File file;
        String input = "";
        try {
            input = args[0];
        } catch (Exception exe) {
        }
        boolean okay = false;
        do {
            if (input.isEmpty()) {
                input = scanner.nextLine();
            }
            file = new File(input);

            if (file.exists()) {
                okay = true;
            } else {
                System.out.println("File is not accessible.\nEnter another file");
                input = "";
            }
        } while (!okay);

        try {
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNext()) {
                String sentence = fileScanner.nextLine();
                String[] words = sentence.split(" ");
                for (int i = 0; i < words.length; i++) {
                    String word = words[i].replaceAll(",", "");
                    word = word.replace(".", "");
                    word = word.replace("_", "");
                    word = word.replace("!", "");
                    word = word.replace("?", "");
                    checkInput(word);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SpellChecker.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Done checking the file");
        saveCorrectSpellings();
    }

    //Read words from dictionary
    public static void readDictionary() {
        File file1 = new File("words.txt");
        File file2 = new File("AddedWords.txt");
        try {
            Scanner scan = new Scanner(file1);
            while (scan.hasNext()) {
                String line = scan.nextLine();
                wordList.add(line.toLowerCase());
            }
            scan.close();
            if (file2.exists()) {
                scan = new Scanner(file2);
                while (scan.hasNext()) {
                    String line = scan.nextLine();
                    wordList.add(line.toLowerCase());
                }
                scan.close();
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(SpellChecker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Incase no match is found, prompt user to either reenter or choose from a list
    private static String handleInput(String input, String givenWord) {
        String word = input;

        if (input.equals("0")) {
            addWord(givenWord);
            correctSpellings.add(word);
        } else if (input.equals("1")) {
            takeInput();
        }
        return word;
    }

    public static void addWord(String word) {
        File f = new File("AddedWords.txt");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(f, true));
            writer.write(word);
            writer.newLine();
            writer.close();
            System.out.println("Added the word '" + word + "' to dictionary");
        } catch (IOException ex) {
            Logger.getLogger(SpellChecker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Take in input from the user
    public static String takeInput() {
        String input = "";
        Scanner scanner = new Scanner(System.in);
        boolean okay = false;
        
            System.out.println("Enter the word again");
            input = scanner.nextLine();
            checkInput(input);
        return input;
    }

    //Check input entered if it is in the dictionary list
    public static void checkInput(String word) {
        Scanner scanner = new Scanner(System.in);
        String input = "";

        if (!wordList.contains(word.toLowerCase())) {
            

            System.out.println("Could not find the word '" + word + "' in the dictionary");
            System.out.println("0: Add the word to dictionary");
            System.out.println("1: Rewrite the word");
           

            boolean acceptable = false;

            do {
                input = scanner.nextLine();
                if (input.equalsIgnoreCase("0") || input.equalsIgnoreCase("1")) {
                    acceptable = true;
                } else {
                    System.out.println("Enter a single character as answer ('0' or '1')");
                }

            } while (!acceptable);

            handleInput(input, word);
        } else {
            correctSpellings.add(word);
        }
    }
    
    //Write out the correct spellings to a file
    public static void saveCorrectSpellings() {
        File file = new File("CorrectlySpelled.txt");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            for (int i = 0; i < correctSpellings.size(); i++) {
                writer.write((String) correctSpellings.get(i));
                writer.newLine();
            }
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(SpellChecker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
