/*
 * File: Wheel.java
 * ------------------
 * Name: Samantha Kim and Thomas Jiang
 * Section Leader: Alex Mallery
 * 
 * This class creates a new Wheel object and defines its associated methods.
 */

import acm.program.*;
import acm.util.*;

import java.awt.Color;
import java.io.*;
import java.util.*;
import acm.graphics.*;


public class Wheel extends GCompound{

	/* Constants for the wheel stopper */
	private static final double STOPPER_WIDTH = 10;
	private static final double STOPPER_HEIGHT = 20;
	private static final Color STOPPER_COLOR = Color.WHITE;
	
	/* Color of the center circle */
	private static final Color CENTER_COLOR = Color.GREEN;
	
	/* Constants for the wedges */
	private static final int NUM_WEDGES = 6;
	private static final Color WEDGE_ONE_COLOR = Color.RED;
	private static final Color WEDGE_TWO_COLOR = Color.ORANGE;
	private static final Color WEDGE_THREE_COLOR = Color.YELLOW;
	private static final Color WEDGE_FOUR_COLOR = Color.GREEN;
	private static final Color WEDGE_FIVE_COLOR = Color.BLUE;
	private static final Color WEDGE_SIX_COLOR = Color.BLACK;
	
	/* Constants for animation of the wheel spin */
	private static final double FRICTION = 1;
	private static final double PAUSE = 20;
	
	/* Creates a new Wheel object */
	public Wheel(double wheelX, double wheelY, double wheeldiam) {
		startAngle = 0;
		sweepAngle = 360 / NUM_WEDGES;
		
		wedgeOne = createWedge(wheelX, wheelY, wheeldiam, WEDGE_ONE_COLOR);
		add(wedgeOne);
		
		startAngle += sweepAngle;
		wedgeTwo = createWedge(wheelX, wheelY, wheeldiam, WEDGE_TWO_COLOR);
		add(wedgeTwo);
		
		startAngle += sweepAngle;
		wedgeThree = createWedge(wheelX, wheelY, wheeldiam, WEDGE_THREE_COLOR);
		add(wedgeThree);
		
		startAngle += sweepAngle;
		wedgeFour = createWedge(wheelX, wheelY, wheeldiam, WEDGE_FOUR_COLOR);
		add(wedgeFour);
		
		startAngle += sweepAngle;
		wedgeFive = createWedge(wheelX, wheelY, wheeldiam, WEDGE_FIVE_COLOR);
		add(wedgeFive);
		
		startAngle += sweepAngle;
		wedgeSix = createWedge(wheelX, wheelY, wheeldiam, WEDGE_SIX_COLOR);
		add(wedgeSix);
		
		double centerdiam = wheeldiam / 4;
		addCenter(centerdiam, centerdiam, wheelX + wheeldiam / 2 - centerdiam / 2, wheelY + wheeldiam / 2 - centerdiam / 2);
		addStopper(wheelX + wheeldiam/ 2 - STOPPER_WIDTH / 2, wheelY - STOPPER_HEIGHT / 2);

	}
	
	/* Method: createWedge */
	/** Creates one wedge in the wheel.
	 * @param	x			X coordinate of the bounding circle of the arc
	 * @param	y			Y coordinate of the bounding circle of the arc
	 * @param	diam		Diameter of the bounding circle of the arc
	 * @param	wedgeColor	Color of the wedge
	 * @return	New wedge with specified properties
	 */
	private GArc createWedge (double x, double y, double diam, Color wedgeColor) {
		GArc wedge = new GArc(x, y, diam, diam, startAngle, sweepAngle);
		wedge.setColor(wedgeColor);
		wedge.setFilled(true);
		wedge.setFillColor(wedgeColor);
		return wedge;
	}
	
	/* Method: addCenter */
	/** Adds the center circle.
	 * @param width		width of the circle
	 * @param height	height of the circle
	 * @param x			x coordinate of the circle
	 * @param y			y coordinate of the circle
	 */
	private void addCenter(double width, double height, double x, double y) {
		GOval center = new GOval(width, height);
		center.setLocation(x, y);
		center.setFilled(true);
		center.setFillColor(CENTER_COLOR);
		add(center);
	}
	
	/* Method: addCenter */
	/** Adds the wheel stopper.
	 * @param	x	x coordinate of the upper left corner of the stopper
	 * @param	y	y coordinate of the upper left corner of the stopper
	 */
	private void addStopper(double x, double y) {
		stopperX = x;
		stopperY = y;
		stopper = new GPolygon();
		stopper.addVertex(stopperX, stopperY);
		stopper.addEdge(STOPPER_WIDTH, 0);
		stopper.addEdge(-STOPPER_WIDTH / 2, STOPPER_HEIGHT);
		stopper.setFilled(true);
		stopper.setFillColor(STOPPER_COLOR);
		add(stopper);
	}
	
	/* Method: spinWheel */
	/** Animates the wheel such that it spins with a random initial speed and slows to a stop. */
	public void spinWheel() {
		startAngle = 0;
		double vspin = 0;
		double w = rgen.nextDouble(40, 70);
		while (w > 0) {
			wedgeOne.setStartAngle(startAngle + vspin);
			startAngle += sweepAngle;
			wedgeTwo.setStartAngle(startAngle + vspin);
			startAngle += sweepAngle;
			wedgeThree.setStartAngle(startAngle + vspin);
			startAngle += sweepAngle;
			wedgeFour.setStartAngle(startAngle + vspin);
			startAngle += sweepAngle;
			wedgeFive.setStartAngle(startAngle + vspin);
			startAngle += sweepAngle;
			wedgeSix.setStartAngle(startAngle + vspin);
			startAngle += sweepAngle;
			vspin -= w;
			w -= FRICTION;
			pause(PAUSE);
		}
	}
	
	/* Method: checkWedge */
	/** Checks the cash value of the wedge that that stopper lands on.
	 * @return Cash value of the wedge
	 */
	public int checkWedge() {
		GPoint stopperpt = new GPoint (stopperX + STOPPER_WIDTH / 2, stopperY + STOPPER_HEIGHT + 1.0);
		GObject landingWedge = getElementAt(stopperpt);
		if (landingWedge.getColor() == WEDGE_ONE_COLOR) {
			return 1;
		} else if (landingWedge.getColor() == WEDGE_TWO_COLOR) {
			return 2;
		} else if (landingWedge.getColor() == WEDGE_THREE_COLOR) {
			return 3;
		} else if (landingWedge.getColor() == WEDGE_FOUR_COLOR) {
			return 4;
		} else if (landingWedge.getColor() == WEDGE_FIVE_COLOR) {
			return 5;
		} else {
			return 6;
		}
	}
	
	/* Private instance variables */
	
	/* Angles of the wedges */
	private double startAngle;
	private double sweepAngle;
	
	private GArc wedgeOne;
	private GArc wedgeTwo;
	private GArc wedgeThree;
	private GArc wedgeFour;
	private GArc wedgeFive;
	private GArc wedgeSix;
	
	private GPolygon stopper;
	
	/* Coordinates of upper left vertex of stopper */
	private double stopperX;
	private double stopperY;
	
	
	private RandomGenerator rgen = RandomGenerator.getInstance();
}
