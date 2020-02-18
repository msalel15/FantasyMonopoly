package domain.elements;

import java.util.ArrayList;

import domain.MultiPolyController;
import domain.card.Card;
import domain.card.RollThreeCard;
import domain.square.Square;

public class Player {

	// Characteristic Properties of the Player
	private String name;
	private Piece piece;
	
	private Boolean isBot;

	protected boolean isInJail;
	private int turnInJail;

	// Current Direction
	private boolean isReverse;

	// Current Money
	private int money;

	// Data
	private ArrayList<Square> propertyList;

	private ArrayList<Card> cardList;
	private ArrayList<RollThreeCard> roll3Cards;

	private static final int startingMoney = 3200; // Starting Money - as constant -

	public Player(String name, Piece figure) {
		this.name = name;
		this.piece = figure;
		
		isBot = false;

		isReverse = false; // initial direction
		money = startingMoney; // initial money

		propertyList = new ArrayList<>();
		cardList = new ArrayList<>();
		roll3Cards = new ArrayList<>();
		isInJail = false;
		resetTurnInJail();
	}

 public void incTurnInJail() {
		turnInJail++;
	}
	// Setters & Getters
	public String getName() {
		return name;
	}

	public Piece getPiece() {
		return piece;
	}


	public boolean isReverse() {
		return isReverse;
	}


	public int getMoney() {
		return money;
	}


	public ArrayList<RollThreeCard> getRoll3Cards() {
		return roll3Cards;
	}
	
	public Boolean isBot() {
		return isBot;
	}

	public void setIsBot(Boolean isBot) {
		this.isBot = isBot;
	}

	// Helper Methods

	public void transaction(int money) {
		this.money += money;
	}


	public void changeDirection() {
		this.isReverse = !isReverse;
	}
	
	public void resetDirection() {
		this.isReverse = false;
	}


	public void addToRoll3List(RollThreeCard r3Card) {
		roll3Cards.add(r3Card);
	}


	public void removeFromRoll3List(RollThreeCard r3Card) {
		roll3Cards.remove(r3Card);

	}

	public void addProperty(Square sq) {
		propertyList.add(sq);
	}
	
	
	public boolean isInJail() {
		return isInJail;
	}

	public void setInJail(boolean isInJail) {
		this.isInJail = isInJail;
	}

	public int getTurnInJail() {
		return turnInJail;
	}

	public void resetTurnInJail() {
		this.turnInJail = 0;
	}


	public boolean repOk() {
		if (piece != null && name != null && propertyList != null && cardList != null && roll3Cards != null) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Player [name=" + name + ", piece=" + piece + ", isReverse=" + isReverse + ", money=" + money
				+ ", propertyList=" + propertyList + ", cardList=" + cardList + ", roll3Cards=" + roll3Cards + "]";
	}


	public ArrayList<Square> getPropertyList() {
		return propertyList;
	}
	
	public void playYourTurn(MultiPolyController multiPolyController) {
		
	}

	public void setTurnInJail(int turnInJail) {
		this.turnInJail = turnInJail;
	}

	public void setReverse(boolean isReverse) {
		this.isReverse = isReverse;
	}

	public void setMoney(int money) {
		this.money = money;
	}

}
