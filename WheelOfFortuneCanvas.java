/*
 * File: WheelOfFortuneCanvas.java
 * ------------------
 * Name: Samantha Kim and Thomas Jiang
 * Section Leader: Alex Mallery
 * 
 * This file keeps track of the Wheel of Fortune display.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class WheelOfFortuneCanvas extends GCanvas {
	
	/** Constants for alphabet letter display */
	private static final double LETTER_DISPLAY_X = 8;
	private static final double LETTER_DISPLAY_Y = 485;
	private static final double LETTER_SPACING = 3; 
	
	/** Constants for wedge legend */
	private static final double LEGEND_DIMENSIONS = 20;
	private static final double LEGEND_X_OFFSET = 10;
	private static final double LEGEND_Y_OFFSET = 265;
	private static final double LEGEND_GAP = 5;
	
	/** Constants for the wheel */
	private static final double WHEEL_DIMENSIONS = 200;
	private static final double WHEEL_X_OFFSET = 125;
	private static final double WHEEL_Y_OFFSET = 265;
	
	/** Number of players */
	private static final int NUM_PLAYERS = 3;
	
	/** Number of cash values including Bankrupt */
	private static final double NUM_CASH_TYPES = 6;

	/** Dimensions of game display board */
	private static final double BOARD_WIDTH = 377;
	private static final double BOARD_HEIGHT = 196;
	
	/** Constants for the player boxes */
	private static final double BOX_WIDTH = 101;
	private static final double BOX_HEIGHT = 20;
	private static final double BOX_GAP = 17;	
	private static final double BOX_X_OFFSET = 20;
	private static final double BOX_Y_OFFSET = BOARD_HEIGHT + 10;
	
	/** Constants for the letter boxes that appear on the game board */
	private static final double BLOCK_HEIGHT = 25;
	private static final double BLOCK_WIDTH = 18;
	private static final double BLOCK_GAP = (325 - BLOCK_WIDTH * 14)/ 13;
	private static final double BLOCK_X_OFFSET = 26 + (BLOCK_WIDTH + BLOCK_GAP); //Letters are never displayed in the first block space.
	private static final double BLOCK_Y_OFFSET = 64 - (BLOCK_HEIGHT + BLOCK_GAP);
	
	/* Pause for letter display animation */
	private static final double PAUSE = 15;
	
	/** Cash values of each wedge */
	private static final int CASH_VALUE_ONE = 100;
	private static final int CASH_VALUE_TWO = 200;
	private static final int CASH_VALUE_THREE = 300;
	private static final int CASH_VALUE_FOUR = 400;
	private static final int CASH_VALUE_FIVE = 500;
			
	
	// Width of canvas = 377
	// Height of canvas = 492
	
	/* Method: reset */
	/** Resets the display */
	public void reset() {
		removeAll();
		addGameSetup();	
	}
	
	/* Method: addIntro */
	/** Adds the intro text */
	public void addIntro() {
		intro = new GLabel("WELCOME TO WHEEL OF FORTUNE!");
		intro.setFont("SansSerif-BOLD-18");
		intro.setColor(Color.BLUE);
		intro.setLocation(377/2 - intro.getWidth()/2, 0);
		add(intro);
	}
	
	/* Method: moveIntro */
	/** Moves the intro text */
	public void moveIntro(double dx, double dy) {
		intro.move(dx, dy);
	}
	
	/* Boolean: isIntroBottomHalf */
	/** returns true if intro text is in bottom half */
	public boolean isIntroBottomHalf() {
		if (intro.getY() > getHeight()/2 + intro.getAscent()/2) {
			return true;
		} else {
			return false;
		}
	}
	
	
	/* Method: addPressEnter */
	/** Adds the label "Press Enter to start" */
	public void addPressEnter() {
		GLabel pressEnter = new GLabel("Press Enter to start!");
		pressEnter.setFont("Courier New-20");
		pressEnter.setLocation(getWidth() / 2 - pressEnter.getWidth() / 2, getHeight() / 2 + 20);
		add(pressEnter);
	}
	
	/* Method: addGameSetup */
	/** Adds game setup to the canvas */
	private void addGameSetup() { 
		addBoard();
		wheel = new Wheel(WHEEL_X_OFFSET, WHEEL_Y_OFFSET, WHEEL_DIMENSIONS);
		add(wheel);
		addLetterDisplay();
		addColorLegend();
	}

	/* Method: addBoard */
	/** Adds the game board display background image. */
	private void addBoard() {
		GImage boardTemplate = new GImage("board.jpg");
		boardTemplate.setSize(BOARD_WIDTH, BOARD_HEIGHT);
		boardTemplate.setLocation(getWidth() / 2 - boardTemplate.getWidth() / 2, 0);
		boardTemplate.sendBackward();
		add(boardTemplate);
	}

	/* Method: addPlayerBoxes */
	/** Adds the Player Boxes to the canvas including their name and current balance */
	public void addPlayerBoxes(String playerOneName, String playerTwoName, String playerThreeName) {
		double x = BOX_X_OFFSET;
		double y = BOX_Y_OFFSET;
		for (int i = 0; i < NUM_PLAYERS; i++) { // Generates each column of boxes for each player (Player 1, Player 2, Player 3)
			for (int j = 0; j < 2; j++) { // Generates each row within each player box (Name, balance)
				GRect playerBox = new GRect(x, y, BOX_WIDTH, BOX_HEIGHT);
				add(playerBox);
				y += BOX_HEIGHT; // Creates the next box below the first box
			}
			
			y = BOX_Y_OFFSET; // Resets y to BOX_Y_OFFSET
			String playerName = "";
			switch (i) { // 3 different cases for 3 different players.
				case 0: 
					balanceLabelOne = setBalanceLabel(0, x, y);
					add(balanceLabelOne);
					playerName = playerOneName;
					break;
				case 1:
					balanceLabelTwo = setBalanceLabel(0, x, y);
					add(balanceLabelTwo);
					playerName = playerTwoName;
					break;
				case 2:
					balanceLabelThree = setBalanceLabel(0, x, y);
					add(balanceLabelThree);
					playerName = playerThreeName;
					break;	
			}
			
			displayName(playerName, x, y);
			x += BOX_GAP + BOX_WIDTH; // Spaces each player box
		}
	}
	
	/* Method setBalanceLabel */
	/** 
	 * Creates the balance label with the given initial balance and coordinates.
	 * @return balance label*/
	private GLabel setBalanceLabel(int balance, double x, double y) {
		GLabel balanceLabel = new GLabel(Integer.toString(balance));
		x += BOX_WIDTH / 2 - balanceLabel.getWidth() / 2; // calibration to center of box
		y += BOX_HEIGHT + BOX_HEIGHT / 2 + balanceLabel.getAscent() / 2;
		balanceLabel.setLocation(x, y);
		return(balanceLabel);
	}	
	
	/* Method displayName */
	/** Creates the name label with the given player's name and coordinates. */
	private void displayName(String playerName, double x, double y) {
		GLabel nameLabel = new GLabel(playerName);
		x += BOX_WIDTH / 2 - nameLabel.getWidth() / 2;
		y += BOX_HEIGHT / 2 + nameLabel.getAscent() / 2; // calibration for center.
		nameLabel.setLocation(x, y);
		add(nameLabel);
	}	
	
	/* Method: setLetterBlocks */
	/** Adds the blank letter blocks to the game board. 
	 * @param	phrase	Random phrase chosen from lexicon
	 */
	public void setLetterBlocks(String phrase) {
		line1 = setFirstLine(phrase);
		int lettersPerLine = (12 - inset * 2);
		if (((double) phrase.length() / lettersPerLine) > 1) {
			line2 = setLine(phrase, lettersPerLine);
		} else {
			line2 = "";
		}
		
		if (((double) phrase.length() / lettersPerLine) > 2) {
			line3 = setLine(phrase, lettersPerLine);
		} else {
			line3 = "";
		}
		
		if (((double) phrase.length() / lettersPerLine) > 3) {
			line4 = setLine(phrase, lettersPerLine);
		} else {
			line4 = "";
		}
		
		double x = BLOCK_X_OFFSET + (inset * (BLOCK_WIDTH + BLOCK_GAP));
		double y = BLOCK_Y_OFFSET;
		if (line3.equals("") && line4.equals("")) { //If there are only 1 or 2 lines, display the first line in the second row of the game board.
			y += BLOCK_HEIGHT + BLOCK_GAP;
		}
		addLetterBlocks(line1, x, y);
		if (! line2.equals("")) {
			y += BLOCK_HEIGHT + BLOCK_GAP;
			addLetterBlocks(line2, x, y);
		}
		if (! line3.equals("")) {
			y += BLOCK_HEIGHT + BLOCK_GAP;
			addLetterBlocks(line3, x, y);
		}
		if (! line4.equals("")) {
			y += BLOCK_HEIGHT + BLOCK_GAP;
			addLetterBlocks(line4, x, y);
		}
	}
	
	/* Method: setFirstLine */
	/** Determines which letters will appear on the first line of the game board.
	 * @param	phrase	Random phrase chosen from lexicon
	 * @return	Part of phrase to be displayed in the first line
	 */
	private String setFirstLine(String phrase) {
		int endIndex = 12;
		if (phrase.length() < 12) {
			endIndex = phrase.length() - 1;
		}
		for (int i = endIndex; i > 0; i--) {
			char ch = phrase.charAt(i);
			if (ch == ' ') {
				inset = 6 - i / 2; //Number of blocks to inset the line so that the line is centered on the game board.
				index = i + 1;
				return phrase.substring(0, i);
			}
		}
		index = endIndex + 1;
		return phrase.substring(0, index);
	}
	
	/* Method: setLine */
	/** Determines which letters will appear on subsequent lines of the game board.
	 * @param	phrase			Random phrase chosen from lexicon
	 * @param 	lettersPerLine	Maximum number of letters in a line
	 * @return	Part of phrase to be displayed in the specified line
	 */
	private String setLine(String phrase, int lettersPerLine) {
		String line = phrase.substring(index);
		if (line.length() <= lettersPerLine) {
			return phrase.substring(index);
		} else {
			int endIndex = (index + lettersPerLine);
			for (int i = endIndex; i > index; i--) {
				char ch = phrase.charAt(i);
				if (ch == ' ') { //Ends the line at the last space before the maximum number of letters in a line.
					int initIndex = index;
					index = i + 1;
					return phrase.substring(initIndex, index);
				}
			}
			int initIndex = index;
			index = endIndex + 1;
			return phrase.substring(initIndex, index);
		}
	}
	
	/* Method: addLetterBlocks */
	/** Creates the letter blocks for each line.
	 * @param line		Part of phrase displayed in line
	 * @param numLine	Number of the line to be displayed
	 * @param x			initial x coordinate of the line
	 * @param y			y coordinate of the line
	 */
	private void addLetterBlocks(String line, double x, double y) {
		for (int i = 0; i < line.length(); i++) {
			char ch = line.charAt(i);
			if (Character.isLetter(ch)) {
				displayBlock(x, y);
			}
			x += (BLOCK_WIDTH + BLOCK_GAP);
		}
	}
	
	
	
	/* Method: displayBlock */
	/** Displays each individual letter block. 
	 */
	private void displayBlock(double x, double y) {
		GRect wordBlock = new GRect(x, y, BLOCK_WIDTH, BLOCK_HEIGHT);
		wordBlock.setFilled(true);
		wordBlock.setFillColor(Color.WHITE);
		wordBlock.sendToFront();
		add(wordBlock);
	}
	
	
	/* Method: displayLetters */
	/** Displays the letter that was guessed on the game board display 
	 * @param char	ch	character guessed by user
	 */
	public void displayLetters(char ch) {
		double x = BLOCK_X_OFFSET + (inset * (BLOCK_WIDTH + BLOCK_GAP));
		double y = BLOCK_Y_OFFSET;
		if (line3.equals("") && line4.equals("")) {
			y += BLOCK_HEIGHT + BLOCK_GAP;
		}		
		checkLine(ch, line1, x, y);
		y += BLOCK_HEIGHT + BLOCK_GAP;
		if (! line2.equals("")) checkLine(ch, line2, x, y);
		y += BLOCK_HEIGHT + BLOCK_GAP;
		if (! line3.equals("")) checkLine(ch, line3, x, y);
		y += BLOCK_HEIGHT + BLOCK_GAP;
		if (! line4.equals("")) checkLine(ch, line4, x, y);
	}
	
	/* Method: checkLine */
	/** Checks within a line to see if the letter is present and if so, displays it on the game board.
	 * @param	ch		character guessed by user
	 * @param	line	Part of the phrase displayed in the specified line
	 * @param	x		starting x coordinate of letter label
	 * @param	y		y coordinate of letter label
	 */
	private void checkLine(char ch, String line, double x, double y) {	
		int i = line.indexOf(ch);
		while (i != -1) {
			GLabel letter = new GLabel ("" + ch);
			letter.setFont("Courier New-BOLD-24");
			double newX = x + (i * (BLOCK_WIDTH + BLOCK_GAP)) + BLOCK_WIDTH / 2 - letter.getWidth() / 2;
			double newY = y + (BLOCK_HEIGHT / 2) + (letter.getAscent() / 2);
			letter.setLocation(newX, newY);
			add(letter);
			i = line.indexOf(ch, i + 1);
		}
	}
	
	
	/* Method: addLetterDisplay */
	/** Add a display of the letters of the alphabet that have not already been guessed by players. */
	private void addLetterDisplay() {
		char[] alphabet = new char[26];
		double letterDisplayX = LETTER_DISPLAY_X;
		for (int i = 0; i < alphabet.length; i++) {
			alphabet[i] = (char)(i + 'A');
			GLabel letterDisplay = new GLabel ("" + alphabet[i], letterDisplayX, LETTER_DISPLAY_Y); // Creates a new label for each letter in the alphabet
			letterDisplay.setFont("Courier New-18");
			add(letterDisplay);
			letterDisplayWidth = letterDisplay.getWidth();
			letterDisplayX += letterDisplayWidth + LETTER_SPACING;
		}
	}
	
	/* Method: removeLetter */
	/** Removes a letter from the letter display when it is guessed 
	 * @param	letter	The letter guessed by a player
	 */
	public void removeLetter(char letter) { 
		int i = letter - 'A';
		GObject removech = getElementAt(((letterDisplayWidth + LETTER_SPACING) * i + LETTER_DISPLAY_X), LETTER_DISPLAY_Y);
		remove(removech);
	}
	
	
	/* Method: addColorLegend */
	/** Creates a legend that shows the color and corresponding cash value of each wheel wedge. */
	private void addColorLegend() {
		String cashvalue = "";
		double y = LEGEND_Y_OFFSET;
		double x = LEGEND_X_OFFSET;
		Color cashValueColor = null;
		for (int i = 0; i < NUM_CASH_TYPES; i++) {
			switch (i) { // Sets the color and legend label
			case 0: cashValueColor = Color.RED;
					cashvalue = "100";
					break;
			case 1: cashValueColor = Color.ORANGE; 
					cashvalue = "200";
					break;
			case 2: cashValueColor = Color.YELLOW;
					cashvalue = "300";
					break;
			case 3: cashValueColor = Color.GREEN;
					cashvalue = "400";
					break;
			case 4: cashValueColor = Color.BLUE; 
					cashvalue = "500";
					break;
			case 5: cashValueColor = Color.BLACK;
					cashvalue = "BANKRUPT";
					break;
			}
			addLegendBlock(cashValueColor, x, y);
			addLegendLabel(cashvalue, x, y);
			y += LEGEND_DIMENSIONS + LEGEND_GAP;
		}
	}

	
	/* Method: addLegendBlock */
	/** Creates a legend block and its color 
	 * @param	cashValueColor	Color of the wedge
	 * @param	x				x coordinate of the legend block
	 * @param	y				y coordinate of the legend block
	 */
	private void addLegendBlock (Color cashValueColor, double x, double y) {
		GRect legendBlock = new GRect(LEGEND_DIMENSIONS, LEGEND_DIMENSIONS);
		legendBlock.setLocation(x, y);
		legendBlock.setFilled(true);
		legendBlock.setFillColor(cashValueColor);
		add(legendBlock);
	}
	
	/* Method: addLegendLabel */
	/** Creates the legend label (the cash value of the color). 
	 * @param	cashvalue	Cash value of the wedge
	 * @param	x			x coordinate of the legend label
	 * @param	y			y coordinate of the legend label
	 */
	private void addLegendLabel(String cashvalue, double x, double y) {
		GLabel legendLabel = new GLabel(cashvalue);
		legendLabel.setLocation(x + LEGEND_DIMENSIONS + LEGEND_GAP, y + legendLabel.getAscent());
		add(legendLabel);
	}

	
	
	/* Method: spinWheel */
	/** Calls the wheel spin animation */
	public void spinWheel() {
		wheel.spinWheel();
	}

	
	/* Method: getWedgeValue */
	/** 
	 * Gets the cash value of the wedge that the wheel stopper is pointing to when the 
	 * wheel stops spinning.
	 * @return	the cash value of the wedge after the wheel finishes spinning */
	public int getWedgeValue() {
		switch(wheel.checkWedge()) { // checks the color of the wheel in the wheel class and returns an integer.
			case 1: return CASH_VALUE_ONE;
			case 2: return CASH_VALUE_TWO;
			case 3: return CASH_VALUE_THREE;
			case 4: return CASH_VALUE_FOUR;
			case 5: return CASH_VALUE_FIVE;
			default: return -1;
		}
	}

	/* Method: updateBalance */
	/** Updates the balance display of the specified player */
	public void updateBalance(int changeBalance, int playerNumber) {
		switch(playerNumber) {
			case 1: balanceLabelOne.setLabel(Integer.toString(changeBalance));
					centerBalanceLabel(balanceLabelOne, playerNumber);
					break;
			case 2: balanceLabelTwo.setLabel(Integer.toString(changeBalance));
					centerBalanceLabel(balanceLabelTwo, playerNumber);
					break;
			case 3: balanceLabelThree.setLabel(Integer.toString(changeBalance));
					centerBalanceLabel(balanceLabelThree, playerNumber);
					break;
		}
	}

	/* Method: centerBalanceLabel */
	/** Centers the balance displayed in the Player Profile Boxes. */
	private void centerBalanceLabel(GLabel label, int i) {
		label.setLocation(BOX_X_OFFSET + (i-1)* (BOX_GAP + BOX_WIDTH) + BOX_WIDTH/2 - label.getWidth()/2, 
				BOARD_HEIGHT + 10 + BOX_HEIGHT + BOX_HEIGHT/2 + label.getAscent()/2);
	}
	

	/** Private instance variables */
	
	/* Intro screen label */
	private GLabel intro;	
	
	private Wheel wheel;
	
	/* Parts of the phrase separated into lines */
	private String line1;
	private String line2;
	private String line3;
	private String line4;
	
	/* Number of empty blocks before the first letter block in each line */
	private int inset;
	
	/* Index of either the end of the current line or beginning of the next line */
	private int index;
	
	/* Display the players' current balances */
	private GLabel balanceLabelOne;
	private GLabel balanceLabelTwo;
	private GLabel balanceLabelThree;
	
	/* Width of each letter in the alphabet display */
	private double letterDisplayWidth;
	
}
