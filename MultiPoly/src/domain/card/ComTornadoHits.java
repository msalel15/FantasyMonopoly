package domain.card;

import domain.MultiPolyController;
import domain.square.RealEstateSquare;

public class ComTornadoHits extends Card {

	public ComTornadoHits() {
		super(5, "Tornado Hits!", true, "Community Chest");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actForCard(MultiPolyController controller) {
		// TODO Auto-generated method stub
		for(int i=0; i<controller.getCurrentPlayer().getPropertyList().size(); i++) {
			RealEstateSquare resq =	(RealEstateSquare) controller.getCurrentPlayer().getPropertyList().get(i);
			if(resq.getLevel()>0) resq.decreaseLevel();
			}
	}

}
