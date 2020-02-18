package domain.bot;

import java.awt.Color;
import java.util.ArrayList;

import domain.MultiPolyController;
import domain.square.RealEstateSquare;
import domain.square.Square;

public class DestructCheapHouses implements IDestructionStrategy{

	@Override
	public void destructHouses(Bot bot, MultiPolyController controller) {
		// TODO Auto-generated method stub

		Color color;
		ArrayList<RealEstateSquare> allMajority = new ArrayList<RealEstateSquare>();

		for(Square sq : controller.getCurrentPlayer().getPropertyList()) {
			if(sq instanceof RealEstateSquare) {
				color = ((RealEstateSquare) sq).getColor();

				if(controller.getBoard().hasMajority(controller.getCurrentPlayer(), color)){

					for(RealEstateSquare realSq : controller.getBoard().getRealEstateByColor(controller.getCurrentPlayer(), color)) {
						allMajority.add(realSq);
					}

				}
			}
		}

		if(!allMajority.isEmpty()) {
			RealEstateSquare realSq = controller.getBoard().getCheapestProperty(allMajority); 
			color = ((RealEstateSquare) realSq).getColor();

			ArrayList<RealEstateSquare> destructedHouses = controller.getBoard().getRealEstateByColor(controller.getCurrentPlayer(), color);

			if(!destructedHouses.isEmpty()) {
				for(RealEstateSquare property : destructedHouses) {
					if(property.getLevel() <= 0) {
						property.decreaseLevel();
					}
				}
			}

		}

	}

}
