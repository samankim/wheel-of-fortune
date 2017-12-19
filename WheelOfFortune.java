/*
 * File: WheelOfFortune.java
 * ------------------
 * Name: Samantha Kim and Thomas Jiang
 * Section Leader: Alex Mallery
 * 
 * The WheelOfFortune class implements the popular TV show game Wheel of Fortune.
 */

import acm.program.*;
import acm.util.*;
import java.io.*;
import java.util.*;
import acm.graphics.*;
import java.awt.*;
import java.awt.event.*;



public class WheelOfFortune extends ConsoleProgram {

	/** Intro animation pause */
	private static final double INTRO_PAUSE = 23;
	/** Cost of Vowel */
	private static final int VOWEL_COST = 250;
	
	/** Pause time for board letter display animation */
	private static final double PAUSE = 15;
	
	/** Prize for solving puzzle */
	private static final int PUZZLE_PRIZE = 1000;
	
	/** Puzzle theme */
	private static final String THEME = "Disney";

	
	
	/* Method: init */
	/**
	 * Initiates the program and adds the canvas before running. Displays the title screen until enter is pressed.
	 */
	public void init() {
    	canvas = new WheelOfFortuneCanvas();
    	add(canvas);
		canvas.addIntro();
		animateIntro();
		canvas.addPressEnter();
		readLine("Press Enter to start.");
    }
	
	/* Method: animateIntro */
	/** Animates the Intro text such that it bounces on the screen */
	private void animateIntro() {
		double vy = 0;
		int numBounces = 0; 
		while (true) {
			vy += 1;
			canvas.moveIntro(0, vy);
			if (canvas.isIntroBottomHalf()) {
				vy = -vy * 0.8;
				canvas.moveIntro(0, vy);
				numBounces++;
			}
			if (numBounces == 4) { // The intro text "bounces" 4 times.
				break;
			}
			pause(INTRO_PAUSE);
		}
	}
	
	/* Method: run */
	/**
	 * Runs the Wheel of Fortune game.
	 */
	public void run() {
		canvas.reset();
		resetVariables();
		introduction();
		println("");
		int response = readInt("Would you like to see the rules? Press 1 for yes and 2 for no.");
		if (response == 1) {
			displayRules();
		}
		println("");
		addPlayerProfiles();
		choosePhrase();
		hidePhrase();
		canvas.setLetterBlocks(phrase);
		while (! blankPhrase.equals(phrase)) { // Runs loop as long as the phrase has not been guessed correctly.
			for (int i = 1; i < 4; i++) {
				switch(i) {
					case 1: currentPlayer = playerOne;
							break;
					case 2: currentPlayer = playerTwo;
							break;
					case 3: currentPlayer = playerThree;
							break;
				}
			playerTurn(currentPlayer);
			if (blankPhrase.equals(phrase)) break;
			}
    	}
		println("" + currentPlayer.getName() + " guessed the phrase: " + phrase);
		findWinner();
		int playAgain = readInt("Want to play again? Press 1 for yes and 2 for no. "); // Prompts the user to play again
		if (playAgain == 1) {
			println("");
			run(); // Restarts the game
		}
	}
	
	/* Method: resetVariables */
	/** Resets instance variables to their starting values. */
	private void resetVariables() {
		phrase = "";
	    blankPhrase = "";
		playerOne = null;
		playerTwo = null;
		playerThree = null;
		playerGuesses = "";
		currentPlayer = null;
		isCurrentPlayerTurn = false;
	}
	
	/* Method: introduction */
	/** Welcome messages */
	private void introduction() {
		println("Welcome to Wheel of Fortune!");
		println("Today's theme is: " + THEME + "!");
	}
	
	/* Method: displayRules */
	/** Displays the rules of Wheel of Fortune */
	private void displayRules() {
		println("");
		println("Rules to Play: (Press enter to continue)");
		readLine("");
		println("The object of the game is to earn the most money before someone guesses the phrase displayed on the board.");
		println("Each turn, a player has the option of spinning the wheel, buying a vowel, or trying to solve the puzzle.");
		println("");
		readLine("");
		println("If the player chooses to spin, the wheel spins until it lands on a wedge.");
		println("If the wheel lands on the the bankrupt wedge, the player's balance is set to zero and the turn ends.");
		println("Otherwise, the player can then guess a consonant. If the guess is correct, the letter is displayed on the board, and the player's balance increases by the wedge value.");
		println("If the guess is incorrect, the player's turn ends, and it is now the next player's turn.");
		println("");
		readLine("");
		println("If the player chooses to buy a vowel, the player's balance decreases by the cost to buy a vowel ($250), and the player can then pick a vowel to guess.");
		println("If the player guesses correctly, the letter is displayed on the board. There is no reward for guessing vowels.");
		println("If the player guesses incorrectly, the player's turn ends, and it is now the next player's turn.");
		println("");
		readLine("");
		println("If the player chooses to solve the puzzle, the player must type the phrase they believe is hidden on the board.");
		println("If the player guesses incorrectly, the turn ends.");
		println("If the player guesses correctly, the player earns the puzzle prize ($1000) and the game ends.");
		println("");
		readLine("");
		println("Whoever has the most money at the end of the game wins!");
		println("");
	}
	
	/* Method: choosePhrase */
	/** Chooses a random phrase from the lexicon. Variable phrase is not visible to user. */
    private void choosePhrase() {
    	lexicon = new WheelOfFortuneLexicon();
    	phrase = lexicon.getPhrase(rgen.nextInt(0, lexicon.getPhraseCount() - 1));
    }
    
	/* Method: introduction */
	/** Forms a new phrase in which each letter of the phrase is replaced with a space. Variable blankPhrase is visible to user. */
    private void hidePhrase() {	
    	blankPhrase = "";
    	for (int i = 0; i < phrase.length(); i++) {
    		blankPhrase += " ";
    	}
    }
    
    /* Method: addPlayerProfiles */
	/** Creates three new players with the user-inputted names and creates the player boxes on the canvas. */
	private void addPlayerProfiles() { //Creates new players
		playerOne = new Player(readLine("Player 1's name: "));
		playerTwo = new Player(readLine("Player 2's name: "));
		playerThree = new Player(readLine("Player 3's name: "));
		canvas.addPlayerBoxes(playerOne.getName(), playerTwo.getName(), playerThree.getName());
	}
	
	/* Method: playerTurn */
	/** Runs through a single player's turn until the player's turn ends. */
	private void playerTurn(Player currentPlayer) {
		isCurrentPlayerTurn = true;
		println("");
		while (isCurrentPlayerTurn) {
			println("" + currentPlayer.getName() + "'s turn.");
			int option = readInt("Press 1 to spin, 2 to buy a vowel, and 3 to attempt to solve. ");
			
			println("");
			
			switch(option) {
			case 1: spinWheel();
					break;
			case 2: buyVowel();
					break;
			case 3: solvePhrase();
					break;
			}
			
			println("");
			
			if (blankPhrase.equals(phrase)) break; // If the player guessed the phrase correctly, the turn ends.
			
		}
	}
	
	/* Method: spinWheel */
	/** Runs through the spin wheel option. */
	private void spinWheel() {
		println("" + currentPlayer.getName() + " spins the wheel.");
		canvas.spinWheel(); // Calls the wheel spinning animation
		if (canvas.getWedgeValue() == -1) {
			currentPlayer.setBalance(0);
			println("BANKRUPT");
			canvas.updateBalance(currentPlayer.getBalance(), currentPlayerNumber());
			isCurrentPlayerTurn = false; //Ends player's turn if wheel lands on Bankrupt
			return;
		}
		currentPlayer.changeBalance(canvas.getWedgeValue()); // Increases the player's balance as if correct consonant was guessed. Player cannot see this.
		guessConsonant();
	}
	
	/* Method: buyVowel */
	/** Runs through the vowel buying option */
	private void buyVowel() {
		if (currentPlayer.getBalance() >= VOWEL_COST) {
			currentPlayer.changeBalance(-VOWEL_COST); // Charges the player the cost of the vowel.
			canvas.updateBalance(currentPlayer.getBalance(), currentPlayerNumber());
			while(true) { // Prompts user to enter a letter until a vowel is entered.
				String guess = readLine("Enter vowel: ");
				if (isLegalGuess(guess) && isVowel(guess)) {
					trackPlayerGuess(guess);
					return;
				} else if (guess.length() == 1 && ! isVowel(guess)){
					println("Please enter a vowel.");
				}
			}
		} else {
			println("You cannot afford to buy a vowel."); // If player's balance is too low, does not let the player buy a vowel.
		}
	}
	
	/* Method: isVowel */
	/** Determines if the inputted letter is a vowel. 
	 *  @return true if vowel, otherwise false.
	 */
	private boolean isVowel(String guess) {
    	char guessch = Character.toUpperCase(guess.charAt(0));
		return guessch == 'A' || guessch == 'E' || guessch == 'I' || guessch == 'O' || guessch == 'U';
	}
	
	/* Method: solvePhrase */
	/** Runs through the solving phrase option. */
	private void solvePhrase() {
		String guess = readLine("Enter your guess: ");
		guess = guess.toUpperCase();
		if (guess.equals(phrase)) {
			blankPhrase = phrase; //Fills in the remaining letters
			for (int i = 0; i < phrase.length(); i++) {
	    		char currentch = phrase.charAt(i);
	    		if (playerGuesses.indexOf(currentch) == -1) 
	    			canvas.displayLetters(currentch);
			}
			currentPlayer.changeBalance(PUZZLE_PRIZE); //Increases the player's balance by the puzzle prize value.
			canvas.updateBalance(currentPlayer.getBalance(), currentPlayerNumber());
		} else {
			println("That is not correct.");
			isCurrentPlayerTurn = false;
		}
	}
	
	/* Method: currentPlayerNumber */
	/** Determines the player number corresponding to the current player
	 *  @return The number of the current player
	 */
	private int currentPlayerNumber() {
    	if (currentPlayer == playerOne) {
    		return 1;
    	} else if (currentPlayer == playerTwo) {
    		return 2;
    	} else {
    		return 3;
    	}
    }
	
	/* Method: guessConsonant */
	/** Prompts the user to enter a letter until a consonant is entered. */
	private void guessConsonant() {
		while(true) {
			String guess = readLine("Your guess: ");
			if (isLegalGuess(guess) && ! isVowel(guess)) {
				trackPlayerGuess(guess);
				return;
			} else if (guess.length() == 1 && isVowel(guess)){
				println("Please enter a consonant.");
	    	}
		}
	}
	
    
	/* Method: trackPlayerGuess */
	/** 
	 *  Determines if the inputted letter is in the phrase. If so, displays the letter and the user's new balance.
	 * 	If not, ends the turn.
	 *  @param	guess	user inputted String
	 */
    private void trackPlayerGuess(String guess) {
    	char guessch = Character.toUpperCase(guess.charAt(0));
    	playerGuesses += guessch;
		for (int i = 0; i < phrase.length(); i++) {
    		char currentch = phrase.charAt(i);
    		if (guessch == currentch) {
    			blankPhrase = blankPhrase.substring(0, i) + guessch + blankPhrase.substring(i + 1); // Replaces the spaces with the user's guessed letter.
    			canvas.displayLetters(guessch);
    		}
		}
		if (blankPhrase.indexOf(guessch) != -1) {
			println("That guess is correct.");
			canvas.updateBalance(currentPlayer.getBalance(), currentPlayerNumber()); //Displays the player's new balance.
		} else {
			println("There are no " + guessch + "'s in the phrase.");
			if (! isVowel(guess)) currentPlayer.changeBalance(-canvas.getWedgeValue()); //Only decreases the player's balance to its former value if the guess was a consonant.
			isCurrentPlayerTurn = false;
		}
		canvas.removeLetter(guessch); //Removes the letter from the remaining letters of the alphabet on display.
    }
    
	/* Method: isLegalGuess */
	/**
	 * Determines if the user input is legal in the game. If not legal, displays a message telling user why the entry
	 * was not accepted.
	 * @param	guess	user inputted String
	 * @return true if the guess is allowed. Otherwise, false.
	 */
    private boolean isLegalGuess (String guess) {
		char guessch = Character.toUpperCase(guess.charAt(0));
		if (guess.length() != 1 || ! Character.isLetter(guessch)) { // Ensures entry is a single letter.
    		println("That guess is illegal. Try again.");
    		return false;
		} else if (playerGuesses.indexOf(guessch) != -1) { // Ensures the letter has not been guessed already.
    		println("The letter " + guessch + " has already been guessed.");
    		return false;
    	} else {
    		return true;
    	}
    }
    
	/* Method: findWinner */
	/** Determines which user has the highest balance and displays message stating who won. */
	private void findWinner() {
		if (playerOne.getBalance() > playerTwo.getBalance() && playerOne.getBalance() > playerThree.getBalance()) {
			println("" + playerOne.getName() +" wins!");
		} else if (playerTwo.getBalance() > playerOne.getBalance() && playerTwo.getBalance() > playerThree.getBalance()) {
			println("" + playerTwo.getName() +" wins!");
		} else if (playerThree.getBalance() > playerOne.getBalance() && playerThree.getBalance() > playerTwo.getBalance()) {
			println("" + playerThree.getName() +" wins!");
		} else {
			println("Tie game!");
		}
	}

	/* Private instance variables */
    private String phrase;
    private String blankPhrase = "";
    private RandomGenerator rgen = RandomGenerator.getInstance();
    private WheelOfFortuneLexicon lexicon;
    private WheelOfFortuneCanvas canvas;
	private Player playerOne;
	private Player playerTwo;
	private Player playerThree;
	private String playerGuesses = "";
	private Player currentPlayer;
	
	/* Whether or not the current player's turn should continue. */
	private boolean isCurrentPlayerTurn;

}