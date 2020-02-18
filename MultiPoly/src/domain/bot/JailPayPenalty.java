package domain.bot;

import domain.MultiPolyController;

public class JailPayPenalty implements IBotInJailStrategy{

	@Override
	public void botInJail(Bot bot, MultiPolyController controller) {
		// TODO Auto-generated method stub
		
		controller.payPenalty();
		
	}

}
