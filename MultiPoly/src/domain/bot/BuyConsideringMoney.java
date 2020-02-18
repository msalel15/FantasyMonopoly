package domain.bot;

import domain.MultiPolyController;


public class BuyConsideringMoney implements IBuyStrategy{

	private int boundary;
	
	public BuyConsideringMoney() {
		super();
		this.boundary = 3000;
	}

	@Override
	public void buy(Bot bot, MultiPolyController controller) {
		// TODO Auto-generated method stub
		if(bot.getMoney() > boundary) {
			controller.buy();
		}
		
	}

	public int getBoundary() {
		return boundary;
	}

	public void setBoundary(int boundary) {
		this.boundary = boundary;
	}
	
	
}
