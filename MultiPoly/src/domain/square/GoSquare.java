package domain.square;

import domain.MultiPolyController;

public class GoSquare extends Square implements MoneyGiver {

	private static final int salary = 200;

	public GoSquare(int id, String name, Boolean isBuyable) {
		super(id, name, isBuyable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actForSquare(MultiPolyController controller) {
		// TODO Auto-generated method stub
		controller.getCurrentPlayer().transaction(salary);
		controller.notifyObservers("UpdatePlayerInfo", null, null);
	}

	@Override
	public void receiveMoney(MultiPolyController controller) {
		// TODO Auto-generated method stub
		controller.getCurrentPlayer().transaction(salary);
		controller.notifyObservers("UpdatePlayerInfo", null, null);

	}

}
