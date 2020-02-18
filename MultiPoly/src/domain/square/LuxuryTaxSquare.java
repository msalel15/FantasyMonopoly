package domain.square;

import domain.MultiPolyController;

public class LuxuryTaxSquare extends Square {

	private static final int payMoney = 75;

	public LuxuryTaxSquare(int id, String name, Boolean isBuyable) {
		super(id, name, isBuyable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actForSquare(MultiPolyController controller) {
		// TODO Auto-generated method stub
		controller.getCurrentPlayer().transaction(-payMoney);
		controller.getBoard().updatePoolMoney(payMoney);
		controller.getGameFlow().add(controller.getCurrentPlayer().getName() + " lost $" + payMoney);
		controller.notifyObservers("UpdatePlayerInfo", null, null);
	}

}
