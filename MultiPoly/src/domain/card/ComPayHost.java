package domain.card;

import domain.MultiPolyController;

public class ComPayHost extends Card {

	public ComPayHost() {
		super(4, "Pay Hospital Bills", true, "Community Chest");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actForCard(MultiPolyController controller) {
		System.out.println("pas host");
		controller.getCurrentPlayer().transaction(-100);
		controller.notifyObservers("UpdatePlayerInfo", null, null);
	}

}
