package domain.square;

import domain.MultiPolyController;

public class TaxRefundSquare extends Square {

	public TaxRefundSquare(int id, String name, Boolean isBuyable) {
		super(id, name, isBuyable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actForSquare(MultiPolyController controller) {
		// TODO Auto-generated method stub
		int earnedMoney = controller.getBoard().getPoolMoney() / 2;
		controller.getCurrentPlayer().transaction(earnedMoney);
		controller.notifyObservers("UpdatePlayerInfo", null, null);
		controller.getBoard().updatePoolMoney(-earnedMoney);
	}

}
