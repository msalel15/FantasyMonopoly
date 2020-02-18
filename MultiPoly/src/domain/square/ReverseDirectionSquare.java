package domain.square;

import domain.MultiPolyController;

public class ReverseDirectionSquare extends Square {

	public ReverseDirectionSquare(int id, String name, Boolean isBuyable) {
		super(id, name, isBuyable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actForSquare(MultiPolyController controller) {
		controller.getCurrentPlayer().changeDirection();
	}

}
