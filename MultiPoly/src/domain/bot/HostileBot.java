package domain.bot;

import domain.MultiPolyController;
import domain.elements.Piece;

public class HostileBot extends Bot{

	public HostileBot(String name, Piece figure) {
		super(name, figure);
		// TODO Auto-generated constructor stub
		setBuyStrategy(new BuyAlways());
		setBusStrategy(new BusGoesExpensiveColorGroup());
		setJailStrategy(new JailNeverPay());
		setUpgradeStrategy(new UpgradeExpensiveHouse());
		setDestructionStrategy(new DestructExpensiveHouses());
	}

	public void playYourTurn(MultiPolyController controller) {
		System.out.println("RandomBot is playing");
		controller.throwAndMove();
		this.performBuy(controller);
	}

}
