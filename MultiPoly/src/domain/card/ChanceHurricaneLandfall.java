package domain.card;

import domain.MultiPolyController;
import domain.square.RealEstateSquare;

public class ChanceHurricaneLandfall extends Card {

	public ChanceHurricaneLandfall() {
		super(3, "Hurricane makes landfall!", true, "Chance");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actForCard(MultiPolyController controller) {
		// TODO Auto-generated method stub
		for(int i=0; i< controller.getPlayers().size();i++) {
			for(int j=0; j<controller.getCurrentPlayer().getPropertyList().size(); j++) {
				RealEstateSquare resq =	(RealEstateSquare) controller.getPlayers().get(i).getPropertyList().get(j);
				if(resq.getLevel()>0) resq.decreaseLevel();
				}
		}
	}

}
