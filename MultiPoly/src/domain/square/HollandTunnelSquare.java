package domain.square;

import domain.MultiPolyController;

public class HollandTunnelSquare extends Square {

	public HollandTunnelSquare(int id, String name, Boolean isBuyable) {
		super(id, name, isBuyable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actForSquare(MultiPolyController controller) {
		// TODO Auto-generated method stub
		if (super.getId() == 42) {
			/*
			 * 
			 * controller.notifyObservers("HollandTunnel", currentPos, nextPos);
			 * 
			 */
			controller.notifyObservers("HollandTunnel", controller.getPlayers().indexOf(controller.getCurrentPlayer()), 102);
			controller.setCurrentPlayerLoc(controller.getBoard().getSquares().get(102));
			controller.getGameFlow()
					.add(controller.getCurrentPlayer().getName() + " appeared on other enterance of the tunnel");
		} else {
			controller.notifyObservers("HollandTunnel", controller.getPlayers().indexOf(controller.getCurrentPlayer()), 42);
			controller.setCurrentPlayerLoc(controller.getBoard().getSquares().get(42));
			controller.getGameFlow()
					.add(controller.getCurrentPlayer().getName() + " appeared on other enterance of the tunnel");
		}

	}

}
