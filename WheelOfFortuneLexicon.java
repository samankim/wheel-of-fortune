/*
 * File: WheelOfFortuneLexicon.java
 * -------------------------
 * Name: Samantha Kim and Thomas Jiang
 * Section Leader: Alex Mallery
 * 
 * This class opens the lexicon to be used with the Wheel of Fortune class.
 */

import acm.program.*;
import acm.util.*;
import java.io.*;
import java.util.*;

public class WheelOfFortuneLexicon{

/** Returns the number of phrases in the lexicon. */
	public int getPhraseCount() {
		phraseList = new ArrayList<String>();
    	try {
    		BufferedReader rd = new BufferedReader(new FileReader("Disney.txt"));
    		while (true) {
    			String line = rd.readLine();
    			if (line == null) break;
    			phraseList.add(line);
    			
    		}
    		rd.close();
    		
    	} catch (IOException ex) {
    		throw new ErrorException(ex);
    	}
		return phraseList.size();
	}

/** Returns the phrase at the specified index. */
	public String getPhrase(int index) {
		String phrase = phraseList.get(index);
		return phrase;
		
	}
	private ArrayList<String> phraseList;
}  
