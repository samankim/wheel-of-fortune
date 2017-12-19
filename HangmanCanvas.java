/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {

	/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;
	private static final int DISPLAY_SPACE = 40;
	
	/* Method: reset */
	/** Resets the display so that only the scaffold appears */
	public void reset() {
		removeAll();
		addScaffold();
	}
	
	/* Method: addScaffold */
	/** Creates the scaffold. */
	private void addScaffold() {
		double scaffoldX = getWidth() / 2 - BEAM_LENGTH;
		scaffoldY = getHeight() / 2 - SCAFFOLD_HEIGHT / 2;
		GLine scaffold = new GLine(scaffoldX, scaffoldY, scaffoldX, scaffoldY + SCAFFOLD_HEIGHT);
		add(scaffold);
		GLine beam = new GLine(scaffoldX, scaffoldY, scaffoldX + BEAM_LENGTH, scaffoldY);
		add(beam);
		ropeX = scaffoldX + BEAM_LENGTH;
		GLine rope = new GLine(ropeX, scaffoldY, ropeX, scaffoldY + ROPE_LENGTH);
		add(rope);
	}
	
	/* Method: displayWord */
	/**
	 * Updates the word on the screen to correspond to the current
	 * state of the game.  The argument string shows what letters have
	 * been guessed so far; unguessed letters are indicated by hyphens.
	 */
	public void displayWord(String word) {
		if (displayWord == null) {
			initDisplayWord(word);
		}
		displayWord.setLabel(word);
	}
	
	/* Method: initDisplayWord */
	/** 
	 * Displays the word passed into the canvas by the Hangman console.
	 * @param word	Word specified by Hangman console to be displayed.
	 */
	private void initDisplayWord(String word) {
		displayWord = new GLabel (word);
		displayWord.setFont("Courier New-24");
		double x = (getWidth() / 2) - displayWord.getWidth() / 2;
		double y = scaffoldY + SCAFFOLD_HEIGHT + DISPLAY_SPACE;
		displayWord.setLocation(x, y);
		add(displayWord);
	}

	/* Method: noteIncorrectGuess */
	/**
	 * Updates the display to correspond to an incorrect guess by the
	 * user.  Calling this method causes the next body part to appear
	 * on the scaffold and adds the letter to the list of incorrect
	 * guesses that appears at the bottom of the window.
	 */
	public void noteIncorrectGuess(char letter) {
		if (incorrectGuessDisplay == null) {
			incorrectGuesses = "";
			initGuessDisplay(incorrectGuesses);
			bodyPartsPresent = 0;
		}
		incorrectGuesses += letter;
		incorrectGuessDisplay.setLabel(incorrectGuesses);
		centerGuessDisplay();
		addNextBodyPart();
	}
	
	/* Method: initGuessDisplay */
	/**
	 * Sets up the display of incorrect guesses on the canvas.
	 */
	private void initGuessDisplay(String result) {
		incorrectGuessDisplay = new GLabel (result);
		centerGuessDisplay();
		add(incorrectGuessDisplay);
	}
	
	/* Method: centerGuessDisplay */
	/**
	 * Re-centers the incorrect guess display every time a new letter is added.
	 */
	private void centerGuessDisplay() {
		double x = getWidth() / 2 - incorrectGuessDisplay.getWidth() / 2;
		double y = displayWord.getY() + DISPLAY_SPACE / 2;
		incorrectGuessDisplay.setLocation(x, y);
	}
	
	/* Method: addNextBodyPart */
	/**
	 * Keeps track of which body part to add next and adds it to the canvas.
	 */
	private void addNextBodyPart() {
		switch (bodyPartsPresent) {
			case 0: addHead();
					bodyPartsPresent++; //Increments the count of the number of body parts displayed on the canvas. 
					break;
			case 1: addBody();
					bodyPartsPresent++;
					break;
			case 2: addLeftArm();
					bodyPartsPresent++;
					break;
			case 3: addRightArm();
					bodyPartsPresent++;
					break;
			case 4: addLeftLeg();
					bodyPartsPresent++;
					break;
			case 5: addRightLeg();
					bodyPartsPresent++;
					break;
			case 6: addLeftFoot();
					bodyPartsPresent++;
					break;
			case 7: addRightFoot();
					bodyPartsPresent++;
					break;
		}
	}
	
	/* Method: addHead */
	/**
	 * Creates and adds the head of the hanging man.
	 */
	private void addHead() {
		headX = ropeX - HEAD_RADIUS;
		headY = scaffoldY + ROPE_LENGTH;
		GOval head = new GOval(headX, headY, HEAD_RADIUS * 2, HEAD_RADIUS * 2);
		add(head);
	}
	
	/* Method: addBody */
	/**
	 * Creates and adds the body of the hanging man.
	 */
	private void addBody() {
		bodyX = getWidth() / 2;
		bodyY = headY + (HEAD_RADIUS * 2);
		GLine body = new GLine(bodyX, bodyY, bodyX, bodyY + BODY_LENGTH);
		add(body);
	}
	
	/* Method: addLeftArm */
	/**
	 * Creates and adds the left arm of the hanging man.
	 */
	private void addLeftArm() {
		double x = bodyX - UPPER_ARM_LENGTH;
		double y = bodyY + ARM_OFFSET_FROM_HEAD;
		addUpperArm(x, y);
		addLowerArm(x, y);
	}
	
	/* Method: addRightArm */
	/**
	 * Creates and adds the right arm of the hanging man.
	 */
	private void addRightArm() {
		double x = bodyX;
		double y = bodyY + ARM_OFFSET_FROM_HEAD;
		addUpperArm(x, y);
		x += UPPER_ARM_LENGTH;
		addLowerArm(x, y);
	}
	
	/* Method: addUpperArm */
	/**
	 * Creates and adds the upper arm of the hanging man.
	 */
	private void addUpperArm(double x, double y) {
		GLine upperArm = new GLine (x, y, x + UPPER_ARM_LENGTH, y);
		add(upperArm);
	}
	
	/* Method: addLowerArm */
	/**
	 * Creates and adds the lower arm of the hanging man.
	 */
	private void addLowerArm(double x, double y) {
		GLine lowerArm = new GLine(x, y, x, y + LOWER_ARM_LENGTH);
		add(lowerArm);
	}
	
	/* Method: addLeftLeg */
	/**
	 * Creates and adds the left leg and hip of the hanging man.
	 */
	private void addLeftLeg() {
		double x = bodyX - HIP_WIDTH;
		double y = bodyY + BODY_LENGTH;
		addHip(x, y);
		addLeg(x, y);
	}
	
	/* Method: addRightLeg */
	/**
	 * Creates and adds the right leg and hip of the hanging man.
	 */
	private void addRightLeg() {
		double x = bodyX;
		double y = bodyY + BODY_LENGTH;
		addHip(x, y);
		x += HIP_WIDTH;
		addLeg(x, y);
	}
	
	/* Method: addHip */
	/**
	 * Creates and adds the hip of the hanging man that extends out of the body.
	 */
	private void addHip(double x, double y) {
		GLine hip = new GLine(x, y, x + HIP_WIDTH, y);
		add(hip);
	}
	
	/* Method: addLeg */
	/**
	 * Creates and adds the leg of the hanging man that extends from the hip.
	 */
	private void addLeg(double x, double y) {
		GLine leg = new GLine(x, y, x, y + LEG_LENGTH);
		add(leg);
	}	
	
	/* Method: addLeftFoot */
	/**
	 * Creates and adds the left foot of the hanging man.
	 */
	private void addLeftFoot() {
		double x = bodyX - (HIP_WIDTH + FOOT_LENGTH);
		addFoot(x);
	}
	
	/* Method: addRightFoot */
	/**
	 * Creates and adds the right foot of the hanging man.
	 */
	private void addRightFoot() {
		double x = bodyX + HIP_WIDTH;
		addFoot(x);
	}
	
	/* Method: addFoot */
	/**
	 * Creates and adds a foot to the hanging man.
	 */
	private void addFoot(double x) {
		double y = bodyY + BODY_LENGTH + LEG_LENGTH;
		GLine foot = new GLine (x, y, x + FOOT_LENGTH, y);
		add(foot);
	}	


	/* Displays the hidden word. Changes when user guesses a new correct letter. */
	private GLabel displayWord;
	
	/* List of the incorrect letters guessed by the user. */
	private String incorrectGuesses;
	
	/* Displays the string of incorrect letters guessed by the user. */
	private GLabel incorrectGuessDisplay;
	
	
	/* Number of body parts displayed on the canvas. Must be instance variable to keep track of the number 
	 * of body parts displayed in between method calls.
	 */
	private int bodyPartsPresent;
	
	/* Y coordinate of the top of the scaffold. */
	private double scaffoldY;
	
	/* X coordinate of the rope. */
	private double ropeX;
	
	/* Coordinates of the head */
	private double headX;
	private double headY;
	
	/*Coordinates of the body */
	private double bodyX;
	private double bodyY;
}
