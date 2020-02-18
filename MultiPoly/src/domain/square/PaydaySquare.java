package domain.square;

import domain.MultiPolyController;

public class PaydaySquare extends Square implements MoneyGiver {

	private static final int passWithOdd = 300;
	private static final int passwithEven = 400;

	public PaydaySquare(int id, String name, Boolean isBuyable) {
		super(id, name, isBuyable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actForSquare(MultiPolyController controller) {
		// TODO Auto-generated method stub
		receiveMoney(controller);
	}

	@Override
	public void receiveMoney(MultiPolyController controller) {
		// TODO Auto-generated method stub
		int diceSum = 0;
		for (int i = 0; i < controller.getDiceValues().size(); i++) {
			if (controller.getDiceValues().get(i) instanceof Integer) {
				diceSum += controller.getDiceValues().get(i);
			}
		}

		if (diceSum % 2 == 0) {
			controller.getCurrentPlayer().transaction(passwithEven);
		} else {
			controller.getCurrentPlayer().transaction(passWithOdd);
		}

	}

}
