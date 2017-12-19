/*
 * File: Hangman.java
 * ------------------
 * Name: Samantha Kim and Thomas Jiang
 * Section Leader: Alex Mallery
 * 
 * The Hangman class implements the game of Hangman, in which a user attempts to guess a word letter by letter
 * until the word is guessed, or the figure of a hanging man is completely drawn.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;

public class Hangman extends ConsoleProgram {

	private static final int NUM_GUESSES = 8;
	
	/* Method: init */
	/**
	 * Initiates the program and adds the canvas before running.
	 */
    public void init() {
    	canvas = new HangmanCanvas();
    	add(canvas);
    }
	
	/* Method: run */
	/**
	 * Runs the Hangman game.
	 */
	public void run() {
		canvas.reset();
		chooseWord();
		hideWord();
		println("Welcome to Hangman!");
		guessesLeft = NUM_GUESSES;
		while (guessesLeft > 0) {
			println("The word now looks like this: " + blankWord);
			println("You have " + guessesLeft + " guesses left.");
			String guess = readLine("Your guess: ");
			if (isLegalGuess(guess)) {
		    	trackUserGuess(guess);
				if (blankWord.equals(word)) { // If user guesses all the letters in the word, user won. Ends the game.
					println("You guessed the word: " + word);
					println("You win.");
					break;
				}
	    	}
    	} 
		if (! blankWord.equals(word)) { // If there are no guesses left and user has not correctly guessed the word, user has lost.
    		println("You're completely hung.");
    		println("The word was: " + word);
    		println("You lose.");
    	}
    }
   
	/* Method: chooseWord */
	/**
	 * Picks a random word from the specified lexicon in the class HangmanLexicon. Variable word is not visible to user.
	 */
    private void chooseWord() {
    	lexicon = new HangmanLexicon();
    	word = lexicon.getWord(rgen.nextInt(0, lexicon.getWordCount() - 1));
    }
    
	/* Method: hideWord */
	/**
	 * Forms a new word in which each letter of the word is replaced with a dash. Variable blankWord is visible to user.
	 */
    private void hideWord() {	
    	blankWord = "";
    	for (int i = 0; i < word.length(); i++) {
    		blankWord += "-";
    	}
		canvas.displayWord(blankWord);
    }
    
    
	/* Method: trackUserGuess */
	/**
	 * Replaces the dash in the hidden word with the guessed letter and displays the updated hidden word on the canvas
	 * if user guesses correctly. Otherwise, ends the round, displays the incorrect letter on the canvas and decrements 
	 * the number of guesses the user has left.
	 * @param guess	Word entry inputted by user
	 */
    private void trackUserGuess(String guess) {
    	char guessch = Character.toUpperCase(guess.charAt(0)); //
    	for (int i = 0; i < word.length(); i++) {
    		char currentch = word.charAt(i);
    		if (guessch == currentch) {
    			blankWord = blankWord.substring(0, i) + guessch + blankWord.substring(i + 1);
    		}
    	}
    	if (blankWord.indexOf(guessch) != -1) {
    		println("That guess is correct");
    		canvas.displayWord(blankWord);
    	} else {
    		println("There are no " + guessch + "'s in the word.");
    		canvas.noteIncorrectGuess(guessch);
    		guessesLeft --; // Only decrements guessesLeft if the user guesses incorrectly.
    	}
    }
    
    
	/* Method: isLegalGuess */
	/**
	 * Determines if the user input is legal in the game. If not legal, displays a message telling user why the entry
	 * was not accepted.
	 * @return true if the guess is allowed. Otherwise, false.
	 */
    private boolean isLegalGuess (String guess) {
		if (guess.equals("")) {
			println("Please enter a letter.");
			return false;
    	} else if (guess.length() > 1 || ! Character.isLetter(guess.charAt(0))) {
    		println("That guess is illegal. Try again.");
    		return false;
    	} else {
    		return true;
    	}
    }

    
    /* Word user attempts to guess */
    private String word;
    
    /* Word replaced by dashes seen by user */
    private String blankWord;
    
    /* Number of guesses the user has remaining */
    private int guessesLeft;
    
    /* Generates a random number */
    private RandomGenerator rgen = RandomGenerator.getInstance();
    
    /* List of possible words */
    private HangmanLexicon lexicon;
    
    /* Graphics display */
    private HangmanCanvas canvas;
    
}