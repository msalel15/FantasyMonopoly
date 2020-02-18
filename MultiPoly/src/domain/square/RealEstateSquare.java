package domain.square;

import java.awt.Color;

import java.util.ArrayList;

import domain.MultiPolyController;
import domain.elements.Player;

public class RealEstateSquare extends Square {

	// Group Color
	private Color color;

	// Money Related Characteristics
	private int price;
	private int level;
	private int buildCost; // will be used later in order to leveling up
	private ArrayList<Integer> rent;

	// Owner of the Square
	private Player owner;

	private static final int maxLevel = 6;

	public RealEstateSquare(int id, String name, Boolean isBuyable, Player owner, Color color, int price,
			ArrayList<Integer> rent, int buildCost, int level) {
		super(id, name, isBuyable);
		this.owner = owner;
		this.color = color;
		this.price = price;
		this.buildCost = buildCost;
		this.rent = rent;
		this.level = level;
	}

	// Helper Methods
	public void increaseLevel() {
		if (level <= maxLevel)
			level++;
	}
	public void decreaseLevel() { 
		if (level > 0) 
			level--; 
		System.out.println("cannot decrease the level more"); //change to gameflow 
		 
	}

	public boolean isOwned() {
		return owner != null;
	}

	// Getters
	public Color getColor() {
		return color;
	}

	public int getPrice() {
		return price;
	}

	public int getLevel() {
		return level;
	}

	public ArrayList<Integer> getRent() {
		return rent;
	}

	public Player getOwner() {
		return owner;
	}
	
	public int getBuildCost() {
		return buildCost;
	}

	// Setting owner of the square
	public void setOwner(Player owner) {
		this.owner = owner;
		super.setIsBuyable(false);
	}

	@Override
	public void actForSquare(MultiPolyController controller) {

	}


}
