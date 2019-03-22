/*
 * To change this license Sheader, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spellcheckersuggestions;

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
 * @author 
 */
public class SpellCheckerSuggestions {
    
    private static ArrayList wordList = new ArrayList();
    private static ArrayList suggestions = new ArrayList();
    private static ArrayList correctSpellings = new ArrayList();
    
    public static void main(String[] args) {
        readDictionary();
        
        File file;
        String input = "";
        try {
            input = args[0];
        } catch (Exception exe) {
        }
        
        boolean okay = false;
        do {
            //Incase no file is entered at command line
            if (input.isEmpty()) {
                System.out.println("No file entered in command line. Enter a file name or path");
                Scanner scanner = new Scanner(System.in);
                input = scanner.nextLine();
            }
            file = new File(input);

            //Check if the file entered exists
            if (file.exists()) {
                okay = true;
            } else {
                System.out.println("File is not accessible.\nEnter another file");
                input = "";
            }
        } while (!okay);

        try {
            Scanner fileScanner = new Scanner(file);
            
            //Go through each word in the file provided and compare with the words in the dictionary
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
            Logger.getLogger(SpellCheckerSuggestions.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Done checking the file");
        saveCorrectSpellings();
    }
    
    //Find similar words
    public static ArrayList soundEx(String word, ArrayList dictionary){
        suggestions = new ArrayList();
        
        
        for(int i = 0; i < dictionary.size(); i++){
            String wordSyntax = getVowelSyntax(word);
            String dictionaryWord = (String)dictionary.get(i);
            String dictionaryWordSyntax = getVowelSyntax(dictionaryWord.toLowerCase());
            
            if(wordSyntax.equalsIgnoreCase(dictionaryWordSyntax)){
                suggestions.add(dictionaryWord);
            }
            if(suggestions.size() > 10)
                break;
        }
        
        for(int i = 0; i < dictionary.size(); i++){
            String wordSyntax = getWordSyntax(word);
            String dictionaryWord = (String)dictionary.get(i);
            String dictionaryWordSyntax = getWordSyntax(dictionaryWord.toLowerCase());
            
            if(wordSyntax.equalsIgnoreCase(dictionaryWordSyntax)){
                suggestions.add(dictionaryWord);
            }
            if(suggestions.size() > 10)
                break;
        }
        return suggestions;
    }
    //CHeck if the only difference is in vowels
    public static String getVowelSyntax(String word){
       char[] wordArray = word.toCharArray();
        char[] vowels = {'a','e','i','o','u'};
        
        for(int i = 1; i < wordArray.length; i++){
            boolean found = false;
            for(int j = 0; j < 5; j++){
                if(wordArray[i] == vowels[j]){
                    found = true;
                    break;
                }  
            }
            
            if(found)
                wordArray[i] = '_';
        }
        
        String wordSyntax = "";
        for(int i = 0; i < wordArray.length; i++)
            wordSyntax += wordArray[i];
        
        return wordSyntax;
    }
    
    //Use full soundEx to get similar words
    public static String getWordSyntax(String word){
        char[] wordArray = word.toCharArray();
        char[] vowels = {'a','e','i','o','u'};
        
        for(int i = 1; i < wordArray.length; i++){
            
            switch (wordArray[i]) {

                case 'B':
                case 'F':
                case 'P':
                case 'V':
                    wordArray[i] = '1';
                    break;

                case 'C':
                case 'G':
                case 'J':
                case 'K':
                case 'Q':
                case 'S':
                case 'X':
                case 'Z':
                    wordArray[i] = '2';
                    break;

                case 'D':
                case 'T':
                    wordArray[i] = '3';
                    break;

                case 'L':
                    wordArray[i] = '4';
                    break;

                case 'M':
                case 'N':
                    wordArray[i] = '5';
                    break;

                case 'R':
                    wordArray[i] = '6';
                    break;

                default:
                    wordArray[i] = '0';
                    break;
            }
          
        }
        String wordSyntax = "";
        for(int i = 0; i < wordArray.length; i++)
            wordSyntax += wordArray[i];
        
        return wordSyntax;
    }
    
    //Read words from dictionary
    public static void readDictionary() {
        File file1 = new File("dictionary.txt");
        File file2 = new File("AddedWords.txt");
        try {
            Scanner scan = new Scanner(file1);
            while (scan.hasNext()) {
                String line = scan.nextLine();
                wordList.add(line.toLowerCase());
            }
            if (file2.exists()) {
                scan = new Scanner(file2);
                while (scan.hasNext()) {
                    String line = scan.nextLine();
                    wordList.add(line.toLowerCase());
                }
                scan.close();
            }
            scan.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SpellCheckerSuggestions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Check input entered if it is in the dictionary list
    public static void checkInput(String word) {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        
        if (!wordList.contains(word.toLowerCase())) {
            suggestions = soundEx(word.toLowerCase(), wordList);

            System.out.println("Could not find the word '"+word+"' in the dictionary");
            System.out.println("0: Enter replacement");
            System.out.println("1: Rewrite the word");
            ArrayList options = new ArrayList();
            options.add("0");
            options.add("1");
            if (suggestions.size() == 0) {
                System.out.println("No suggestions found");
            } else {
                for (int j = 0; j < suggestions.size(); j++) {
                    System.out.println((j + 2) + ": " + (String) suggestions.get(j));
                    options.add("" + (j + 2) + "");
                }
            }

            boolean acceptable = false;

            do {
                input = scanner.nextLine();
                if (input.length() > 2) {
                    System.out.println("Enter a single character as answer");
                } else if (options.contains(input)) {
                    acceptable = true;
                }
            } while (!acceptable);

            handleInput(input, word);
        }else 
            correctSpellings.add(word);
        
    }
    
    //Incase no match is found, prompt user to either reenter or choose from a list
    private static String handleInput(String input, String givenWord) {
        String word = input;

        if (input.equals("0") || input.equals("1")) {
            takeInput();
        } else {
            
            int option = Integer.parseInt(input);
            word = (String)suggestions.get((option-2));
            correctSpellings.add(word);
            
        }
        return word;
    }
    
    //Write out the correct spellings to a file
    public static void saveCorrectSpellings(){
        File file = new File("CorrectlySpelled.txt");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for(int i = 0; i < correctSpellings.size(); i++){
                writer.write((String)correctSpellings.get(i));
                writer.newLine();
            }
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(SpellCheckerSuggestions.class.getName()).log(Level.SEVERE, null, ex);
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
}
