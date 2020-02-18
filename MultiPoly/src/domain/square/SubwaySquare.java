package domain.square;

import domain.MultiPolyController;

public class SubwaySquare extends Square {

	public SubwaySquare(int id, String name, Boolean isBuyable) {
		super(id, name, isBuyable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actForSquare(MultiPolyController controller) {
		// TODO Auto-generated method stub
		controller.getMovementHandle().startBusIconProcess(); 
		controller.notifyObservers("moveWithSubway", null, null); 


	}

}
