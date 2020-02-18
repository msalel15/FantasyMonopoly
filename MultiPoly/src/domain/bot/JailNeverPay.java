package domain.bot;

import domain.MultiPolyController;

public class JailNeverPay implements IBotInJailStrategy{
	
	@Override
	public void botInJail(Bot bot, MultiPolyController controller) {
		// TODO Auto-generated method stub
		
		controller.throwAndMove();
		
	}

}
