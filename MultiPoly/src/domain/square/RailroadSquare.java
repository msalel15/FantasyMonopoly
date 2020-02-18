package domain.square;

import domain.MultiPolyController;
import domain.elements.Player;

public class RailroadSquare extends Square {

	private int price;
	private boolean upgraded;
	private Player owner;

	private static final int upgradeCost = 100; // cost of building a train depot

	private static final int rentPerRailroad = 25; // for every railroad player has, double this value

	public RailroadSquare(int id, String name, Boolean isBuyable, Player owner, int price, boolean upgraded) {
		super(id, name, isBuyable);
		this.owner = owner;
		this.price = price;
		this.upgraded = upgraded;
	}

	@Override
	public void actForSquare(MultiPolyController controller) {
		// TODO Auto-generated method stub

	}

	public int getUpgradeCost() {
		return upgradeCost;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public boolean isUpgraded() {
		return upgraded;
	}

	public void setUpgraded(boolean upgraded) {
		this.upgraded = upgraded;
	}

	public boolean isOwned() {
		return owner != null;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
		super.setIsBuyable(false);
	}

}
