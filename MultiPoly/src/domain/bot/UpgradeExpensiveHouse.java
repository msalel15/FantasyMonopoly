package domain.bot;

import java.awt.Color;
import java.util.ArrayList;

import domain.MultiPolyController;
import domain.square.RealEstateSquare;
import domain.square.Square;

public class UpgradeExpensiveHouse implements IUpgradeBuildingStrategy{

	public void upgradeHouses(Bot bot, MultiPolyController controller) {

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

		RealEstateSquare sq = controller.getBoard().getMostExpProperty(majority);

		controller.getBoard().upgradeSquare(controller.getCurrentPlayer(), sq);

	}

}
