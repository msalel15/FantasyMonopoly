package domain.card;

import domain.MultiPolyController;

public class ChanceGoJail extends Card {

	public ChanceGoJail() {
		super(1, "Go to Jail", true, "Chance");

	}

	@Override
	public void actForCard(MultiPolyController controller) {
		// TODO Auto-generated method stub
		System.out.println("go jail");
		controller.setCautionMoneyPayed(false);
		controller.getCurrentPlayer().setInJail(true); 
		controller.setCurrentPlayerLoc(controller.getBoard().getSquares().get(86)); 
		controller.getGameFlow().add(controller.getCurrentPlayer().getName()+" arrested! The trial will begin in the next turn.");
		controller.notifyObservers("GoToJail", controller.getPlayers().indexOf(controller.getCurrentPlayer()), 86);

	}

}
