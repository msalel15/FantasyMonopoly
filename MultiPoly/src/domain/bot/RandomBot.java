package domain.bot;

import domain.MultiPolyController;
import domain.elements.Piece;

public class RandomBot extends Bot {

	public RandomBot(String name, Piece figure) {
		super(name, figure);
		// TODO Auto-generated constructor stub
		setBuyStrategy(new BuyRandomly());
		setBusStrategy(new BusGoesRandomly());
		setJailStrategy(new JailRandomlyDecide());
		setUpgradeStrategy(new UpgradeRandomHouse());
		setDestructionStrategy(new DestructRandomHouses());
	}

	

}
