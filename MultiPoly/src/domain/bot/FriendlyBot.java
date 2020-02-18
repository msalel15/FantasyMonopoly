package domain.bot;

import domain.MultiPolyController;
import domain.elements.Piece;

public class FriendlyBot extends Bot {

	public FriendlyBot(String name, Piece figure) {
		super(name, figure);
		// TODO Auto-generated constructor stub
		setBuyStrategy(new BuyConsideringMoney());
		setBusStrategy(new BusGoesRandomly());
		setJailStrategy(new JailPayPenalty());
		setUpgradeStrategy(new UpgradeRandomHouse());
		setDestructionStrategy(new DestructCheapHouses());
	}

	public void playYourTurn(MultiPolyController controller) {
		System.out.println("RandomBot is playing");
		controller.throwAndMove();
		this.performBuy(controller);
	}
}
