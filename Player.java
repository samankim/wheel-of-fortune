


import acm.program.*;

public class Player extends ConsoleProgram {
	
	/* Method: getBalance */
	/** Returns balance of player */
	public int getBalance() {
		return balance;
	}
	
	/* Method: setBalance */
	/** Sets player's balance */
	public int setBalance(int newBalance) {
		balance = newBalance;
		return balance;	
	}
	/* Method: changeBalance */
	/** Changes the players balance by amount changeInBalance */
	public void changeBalance(int changeInBalance) {
		balance += changeInBalance;
	}
	/* Constructor: Player */
	
	public Player(String name) {
		playername = name;
	}
	/* Method: getName */
	/** Returns the name of the player */
	public String getName() {
		return playername;
	}
	/** private instance variables */
	private int balance;
	private String playername;
}

