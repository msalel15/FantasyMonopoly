package domain.bot;

import java.util.ArrayList;
import java.util.Random;

import domain.MultiPolyController;
import domain.square.RealEstateSquare;
import domain.square.Square;

public class BusGoesExpensiveColorGroup implements IBusStrategy{

	@Override
	public void botBusMove(Bot bot, MultiPolyController controller) {
		// TODO Auto-generated method stub
	
		ArrayList<RealEstateSquare> buyableSquares = new ArrayList<RealEstateSquare>();
		
		for(Square sq : controller.getBoard().getSquares()) {
			if(sq.isBuyable() && sq instanceof RealEstateSquare) {
				buyableSquares.add((RealEstateSquare) sq);
			}
		}
		
		int id;
		
		if(!buyableSquares.isEmpty()) {
			
			Square sq = controller.getBoard().getMostExpProperty(buyableSquares);
			id = sq.getId();	
		}else {
			System.out.println("Cannot find a buyable square! Bot goes to GoSquare.");
			id = 0;
		}
		
		controller.setSquareIdFromPlayer(id);
		controller.getMovementHandle().moveWithBusIcon();
		
	}

}
