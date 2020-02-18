package domain.bot;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import domain.MultiPolyController;
import domain.square.RealEstateSquare;
import domain.square.Square;

public class UpgradeRandomHouse implements IUpgradeBuildingStrategy{
	
	Random rand = new Random();

	@Override
	public void upgradeHouses(Bot bot, MultiPolyController controller) {
		// TODO Auto-generated method stub
		
		ArrayList<RealEstateSquare> majority = new ArrayList<RealEstateSquare>();
		
		for(Square sq : controller.getCurrentPlayer().getPropertyList()) {
			
			if(sq instanceof RealEstateSquare) {
				
				Color color = ((RealEstateSquare) sq).getColor();
				
				if(controller.getBoard().hasMajority(controller.getCurrentPlayer(), color)){
					
					for(RealEstateSquare realSq : controller.getBoard().getRealEstateByColor(controller.getCurrentPlayer(), color)) {
						majority.add(realSq);
					}
		
				}
			}
		}
		
		int id = rand.nextInt(majority.size());
		
		RealEstateSquare sq = (RealEstateSquare) controller.getBoard().getSquareByID(id);

		controller.getBoard().upgradeSquare(controller.getCurrentPlayer(), sq);
		
	}

}
