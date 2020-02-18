package domain.bot;

import domain.MultiPolyController;

import java.util.Random;

public class BuyRandomly implements IBuyStrategy {

	Random rand = new Random();

	@Override
	public void buy(Bot bot, MultiPolyController controller) {
		// TODO Auto-generated method stub
		controller.buy();
	/*	if(rand.nextBoolean()) {
			controller.buy();
		}
		*/
	}

}
