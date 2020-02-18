package domain.square;

import domain.MultiPolyController;

public class BonusSquare extends Square implements MoneyGiver {

	private static final int landMoney = 300;
	private static final int passMoney = 250;

	public BonusSquare(int id, String name, Boolean isBuyable) {
		super(id, name, isBuyable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actForSquare(MultiPolyController controller) {
		// TODO Auto-generated method stub
		controller.getCurrentPlayer().transaction(landMoney);
		controller.notifyObservers("UpdatePlayerInfo", null, null);
	}

	@Override
	public void receiveMoney(MultiPolyController controller) {
		controller.getCurrentPlayer().transaction(passMoney);
		controller.notifyObservers("UpdatePlayerInfo", null, null);

	}

}
