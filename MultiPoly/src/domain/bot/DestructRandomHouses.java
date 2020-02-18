package domain.bot;

import java.awt.Color;
import java.util.ArrayList;

import domain.MultiPolyController;
import domain.square.RealEstateSquare;
import domain.square.Square;

public class DestructRandomHouses implements IDestructionStrategy {

	@Override
	public void destructHouses(Bot bot, MultiPolyController controller) {
		// TODO Auto-generated method stub
		
		for(Square sq : controller.getCurrentPlayer().getPropertyList()) {
			if(sq instanceof RealEstateSquare) {
				Color color = ((RealEstateSquare) sq).getColor();
				if(controller.getBoard().hasMajority(controller.getCurrentPlayer(), color)){
					
					ArrayList<RealEstateSquare> majority = controller.getBoard().getRealEstateByColor(controller.getCurrentPlayer(), color);
					
					for(RealEstateSquare property : majority) {
						if(property.getLevel() <= 0) {
							property.decreaseLevel();
						}
					}
					
					break;
					
				}
			}
		}
		
	}

}
