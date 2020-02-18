package domain.bot;

import domain.MultiPolyController;

public class BuyAlways implements IBuyStrategy {

	@Override
	public void buy(Bot bot, MultiPolyController controller) {
		// TODO Auto-generated method stub
		controller.buy();
	}

}
