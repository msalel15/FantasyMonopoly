package domain.bot;

import java.util.Random;

import domain.MultiPolyController;

public class BusGoesRandomly implements IBusStrategy{

	Random rand = new Random();
	
	@Override
	public void botBusMove(Bot bot, MultiPolyController controller) {
		// TODO Auto-generated method stub
		int id = rand.nextInt(120);
		controller.setSquareIdFromPlayer(id);
		controller.getMovementHandle().moveWithBusIcon();
	}

}
