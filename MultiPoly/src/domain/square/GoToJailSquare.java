package domain.square;

import domain.MultiPolyController;

public class GoToJailSquare extends Square {

	public GoToJailSquare(int id, String name, Boolean isBuyable) {
		super(id, name, isBuyable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actForSquare(MultiPolyController controller) {
		controller.setCautionMoneyPayed(false);
		controller.getCurrentPlayer().setInJail(true);
		controller.setCurrentPlayerLoc(controller.getBoard().getSquares().get(86));
		controller.getGameFlow()
				.add(controller.getCurrentPlayer().getName() + " arrested! The trial will begin in the next turn.");
		controller.notifyObservers("GoToJail", controller.getPlayers().indexOf(controller.getCurrentPlayer()), 86);

	}

}
