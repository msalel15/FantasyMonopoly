package domain.square;

import domain.MultiPolyController;

import domain.elements.Player;

public class UtilitySquare extends Square {

	private Player owner;

	private static final int price = 150; // Check this out

	public UtilitySquare(int id, String name, Boolean isBuyable, Player owner) {
		super(id, name, isBuyable);
		this.owner = owner;

		// TODO Auto-generated constructor stub
	}

	// Getters
	public int getPrice() {
		return price;
	}

	public boolean isOwned() {
		return owner != null;
	}

	public Player getOwner() {
		return owner;
	}

	// Setting owner of the square
	public void setOwner(Player owner) {
		this.owner = owner;
		super.setIsBuyable(false);
	}

	@Override
	public void actForSquare(MultiPolyController controller) {
		// TODO Auto-generated method stub

	}

}
