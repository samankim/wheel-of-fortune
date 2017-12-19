/*
 * File: HangmanLexicon.java
 * -------------------------
 * Name: Samantha Kim and Thomas Jiang
 * Section Leader: Alex Mallery
 * 
 * This class opens the lexicon to be used with the Hangman class.
 */

import acm.program.*;
import acm.util.*;
import java.io.*;
import java.util.*;

public class HangmanLexicon{

/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		wordList = new ArrayList<String>();
    	try {
    		BufferedReader rd = new BufferedReader(new FileReader("HangmanLexicon.txt"));
    		while (true) {
    			String line = rd.readLine();
    			if (line == null) break;
    			wordList.add(line);
    			
    		}
    		rd.close();
    		
    	} catch (IOException ex) {
    		throw new ErrorException(ex);
    	}
		return wordList.size();
	}

/** Returns the word at the specified index. */
	public String getWord(int index) {
		String word = wordList.get(index);
		return word;
		
	}
	private ArrayList<String> wordList;
}  
