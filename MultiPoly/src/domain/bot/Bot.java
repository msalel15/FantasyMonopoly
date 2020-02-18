package domain.bot;

import domain.MultiPolyController;
import domain.Observer;
import domain.elements.Piece;
import domain.elements.Player;

public class Bot extends Player implements Observer, Runnable {

	private IBuyStrategy buyStrategy;
	private IBusStrategy busStrategy;
	private IBotInJailStrategy jailStrategy;
	private IUpgradeBuildingStrategy upgradeStrategy;
	private IDestructionStrategy destructionStrategy;
	private boolean isMyTurn = false;
	private MultiPolyController controller;

	public Bot(String name, Piece figure) {
		super(name, figure);
		// TODO Auto-generated constructor stub
		setIsBot(true);
		controller = MultiPolyController.getInstance();
		controller.addObserver(this);
		(new Thread(this)).start();
		controller.addObserver(this);

	}

	public boolean isMyTurn() {
		return isMyTurn;
	}

	public void setMyTurn(boolean isMyTurn) {
		this.isMyTurn = isMyTurn;
	}

	
	public void playYourTurn() {
		playYourTurnHelper();
		while(controller.getMovementHandle().getDices().isDouble()&&!this.isInJail()) {
			playYourTurnHelper();
		}
		controller.endTurn();
	}
	
	
	public void playYourTurnHelper() {

		if (this.isInJail()) {
			this.performJailDecision(controller);
		} else {
			controller.throwAndMove();
			this.performBuy(controller);
			if (controller.getMovementHandle().getDices().isMrMonolopy()) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				controller.getMovementHandle().moveWithMrMonopoly();
				this.performBuy(controller);

			} else if (controller.getMovementHandle().getDices().isBusIcon()) {
				controller.notifyObservers("BotIsThinking", null, null);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.performBus(controller);
				this.performBuy(controller);

			}
			
		}

	}

	@Override
	public void update(String desc, Object obj1, Object obj2) {
		// TODO Auto-generated method stub

		if (isMyTurn) {

			switch (desc) {
			case "EnableThrowDiceButton":
				playYourTurn();
				break;
			
			case "Card":

				break;

			}
		}
	}

	public void performBuy(MultiPolyController controller) {
		buyStrategy.buy(this, controller);
	}

	public void performBus(MultiPolyController controller) {
		busStrategy.botBusMove(this, controller);
	}

	public void performJailDecision(MultiPolyController controller) {
		jailStrategy.botInJail(this, controller);
	}

	public void performUpgradeHouse(MultiPolyController controller) {
		upgradeStrategy.upgradeHouses(this, controller);
	}

	public void performDestructHouses(MultiPolyController controller) {
		destructionStrategy.destructHouses(this, controller);
	}

	// Getters and Setters

	public IBuyStrategy getBuyStrategy() {
		return buyStrategy;
	}

	public void setBuyStrategy(IBuyStrategy buyStrategy) {
		this.buyStrategy = buyStrategy;
	}

	public IBusStrategy getBusStrategy() {
		return busStrategy;
	}

	public void setBusStrategy(IBusStrategy busStrategy) {
		this.busStrategy = busStrategy;
	}

	public IBotInJailStrategy getJailStrategy() {
		return jailStrategy;
	}

	public void setJailStrategy(IBotInJailStrategy jailStrategy) {
		this.jailStrategy = jailStrategy;
	}

	public IUpgradeBuildingStrategy getUpgradeStrategy() {
		return upgradeStrategy;
	}

	public void setUpgradeStrategy(IUpgradeBuildingStrategy upgradeStrategy) {
		this.upgradeStrategy = upgradeStrategy;
	}

	public IDestructionStrategy getDestructionStrategy() {
		return destructionStrategy;
	}

	public void setDestructionStrategy(IDestructionStrategy destructionStrategy) {
		this.destructionStrategy = destructionStrategy;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
