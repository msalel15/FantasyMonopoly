package domain.square;

import domain.MultiPolyController;

public class JailSquare extends Square {
	int JailFreeMoney = -500; // temp var
	// find the value of it

	public JailSquare(int id, String name, Boolean isBuyable) {
		super(id, name, isBuyable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actForSquare(MultiPolyController controller) {
		
		System.out.println("ActForSquare Jail");

		if(controller.getCurrentPlayer().isInJail()) {
			System.out.println("In Jail");

			int numTurnInJail = controller.getCurrentPlayer().getTurnInJail();

			if (numTurnInJail == 0 || numTurnInJail == 1) {
				System.out.println("Stay in jail for " + (3 - numTurnInJail) + " turns");
					controller.getCurrentPlayer().incTurnInJail();
				
			} else if (numTurnInJail == 2) {
				controller.getCurrentPlayer().setInJail(false);
				controller.getCurrentPlayer().resetTurnInJail();
				controller.getGameFlow().add(controller.getCurrentPlayer().getName() + " has been acquitted! \n"
						+ controller.getCurrentPlayer().getName() + " free to go!");
				controller.notifyObservers("FreeFromJail", null, null);

			}

		}

	}

	public void payPenalty(MultiPolyController controller) {
		controller.getCurrentPlayer().transaction(JailFreeMoney);
		System.out.print(controller.getCurrentPlayer().getName() + " paid penalty.");
		controller.getCurrentPlayer().setInJail(false);
		controller.getCurrentPlayer().resetTurnInJail();
		controller.getGameFlow().add(controller.getCurrentPlayer().getName() + " has been acquitted! \n"
				+ controller.getCurrentPlayer().getName() + " free to go!");
		controller.notifyObservers("FreeFromJail", null, null);
		
	}

}