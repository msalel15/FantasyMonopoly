package domain.bot;

import java.util.Random;

import domain.MultiPolyController;

public class JailRandomlyDecide implements IBotInJailStrategy{

	Random rand = new Random();
	
	@Override
	public void botInJail(Bot bot, MultiPolyController controller) {
		// TODO Auto-generated method stub
		if(rand.nextBoolean()) {
			controller.payPenalty();
		}else {
			controller.throwAndMove();
		}
		
	}

}
