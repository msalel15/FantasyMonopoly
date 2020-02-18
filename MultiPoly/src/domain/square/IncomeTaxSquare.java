package domain.square;

import domain.MultiPolyController;

public class IncomeTaxSquare extends Square {

	int payMoney;

	public IncomeTaxSquare(int id, String name, Boolean isBuyable) {
		super(id, name, isBuyable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actForSquare(MultiPolyController controller) {
		// TODO Auto-generated method stub

		// Pay %10 or 200

		if ((controller.getCurrentPlayer().getMoney() / 10) < 200) {
			payMoney = controller.getCurrentPlayer().getMoney() / 10;
			controller.getCurrentPlayer().transaction(payMoney);

		} else {
			payMoney = 200;
			controller.getCurrentPlayer().transaction(payMoney);
		}

		controller.notifyObservers("UpdatePlayerInfo", null, null);
		controller.getGameFlow().add(controller.getCurrentPlayer().getName() + " has payed " + payMoney);
		controller.getBoard().updatePoolMoney(-payMoney);
	}

}