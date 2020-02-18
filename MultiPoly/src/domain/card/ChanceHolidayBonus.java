package domain.card;

import domain.MultiPolyController;

public class ChanceHolidayBonus extends Card {

	public ChanceHolidayBonus() {
		super(2, "Holiday Bonus!", true, "Chance");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actForCard(MultiPolyController controller) {
		System.out.println("bonus");
		controller.getCurrentPlayer().transaction(100);
		controller.notifyObservers("UpdatePlayerInfo", null, null);
	}

}
