package domain;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import domain.bot.Bot;
import domain.bot.RandomBot;
import domain.card.RollThreeCard;
import domain.elements.Board;
import domain.elements.Piece;
import domain.elements.Player;
import domain.square.RailroadSquare;
import domain.square.RealEstateSquare;
import domain.square.Square;
import domain.square.UtilitySquare;
import domain.square.JailSquare;

public class MultiPolyController implements Observable {

	// Data Storage
	private ArrayList<Player> players; // player list
	private ArrayList<String> playerInfo; // general player info (assets)
	private ArrayList<String> gameFlow; // information about game flow
	private ArrayList<Observer> observers; // observer list

	// Current Info
	private Player currentPlayer;
	private ArrayList<Integer> roll3dice;
	private int sqIdFromPlayer;

	private boolean cautionMoneyPayed;

	// Square Container
	private Board board; // container for squares and cards (Creator Pattern)

	// Constant for starting location
	private static final int startLoc = 76;
	private MovementHandler movementHandle;

	// Save & Load Action
	private GameState state;

	// About Save & Load
	private String gameName;

	private static MultiPolyController instance;

	public static MultiPolyController getInstance() {

		if (instance == null) {
			instance = new MultiPolyController();
		}

		return instance;
	}

	// Constructor
	private MultiPolyController() {

		state = new GameState(this);
		players = new ArrayList<>();
		playerInfo = new ArrayList<>();
		gameFlow = new ArrayList<>();
		observers = new ArrayList<>();

		currentPlayer = null;

		board = new Board();
		board.initializeSquares(this);
		board.initializeCards();
		instance = this;
		addPlayers();

		initializeRollThreeCard();
		updatePlayerInfo();
		movementHandle = new MovementHandler(this);

	}

	public ArrayList<Integer> getRoll3Dice() {
		roll3dice = movementHandle.getDices().roll3ThrowAndGet();
		return roll3dice;

	}

	public int getSquareIdFromPlayer() {
		return sqIdFromPlayer;
	}

	public void setSquareIdFromPlayer(int id) {
		sqIdFromPlayer = id;
	}
	// In-class - private - methods

	// Adds player(s)
	public void addPlayers() {
		// Get starting - go - square
		Square goSquare = board.getSquares().get(startLoc);

		// Initiate 5 default players
		Player msl = new Player("msl", new Piece(goSquare));
		Player pelin = new RandomBot("pelin", new Piece(goSquare));
		Player okan = new RandomBot("okan", new Piece(goSquare));
		Player melis = new Player("melis", new Piece(goSquare));
		Player yusa = new Player("yusa", new Piece(goSquare));

		// Add players to the list
		players.add(msl);
		players.add(pelin);
		players.add(melis);
//		players.add(okan);
//		players.add(yusa);

		// Get current player
		currentPlayer = players.get(0);
	}

	// Initializes rollThree Cards (from beginning)
	private void initializeRollThreeCard() {
		for (Player player : players) {
			player.addToRoll3List(new RollThreeCard());
		}
	}

	// Updates Player Info (Access from controller relatives)
	public void updatePlayerInfo() {
		playerInfo = new ArrayList<>();
		for (int i = 0; i < players.size(); i++) {
			playerInfo.add("" + players.get(i).getName() + ": " + players.get(i).getMoney());
		}
	}

	// Publicly accessible methods

	/**
	 * enables die throw and determining movement type
	 * 
	 * 
	 * 
	 */
	// REQUIRES : roll method must work properly and return a ThrowDice object
	// EFFECTS : calls proper move methods with respect to the result of the roll,
	// changes Player's current location and enables acting for specified Square
	// MODIFIES : Player's current position and abilities (via calling move methods)

	public void throwAndMove() {
		movementHandle.throwAndMove();

	}

	// Enables purchase
	/**
	 * enables player to purchase a square if it is buyable
	 * 
	 * 
	 * 
	 * @exception IllegalArgumentException if Square is not valid for being
	 *                                     purchased /
	 * 
	 */
	// REQUIRES : player must be already initialized and square must be buyable
	// EFFECTS : updates gameFlow, and enables Player to purchase at that Square
	// MODIFIES : Board conditions (via updating Square condition), and Player
	// attributes (i.e. money,propertyList)

	// TODO
	public void buy() {
		Square sq = currentPlayer.getPiece().getLocation();
		System.out.println(sq.getName());
		if (board.buyPlace(currentPlayer, sq)) {
			gameFlow.add("" + currentPlayer.getName() + " bought " + sq.getName());
			notifyObservers("UpdatePlayerInfo", null, null);

		} else {
			System.out.println("Square is not valid for being purchased");
		}

	}

	/**
	 * enables player to use a card
	 * 
	 * @param id represents card id
	 * 
	 * @exception IllegalArgumentException if specified id is not valid (neither 11
	 *                                     nor 31)
	 */
	// REQUIRES : valid id (either 11 or 31) board must not be null (already
	// initialized)
	// EFFECTS : enables to use specified card (additional abilities and changes on
	// Player), gameFlow will be updated as a result of this action
	// MODIFIES : gameFlow and Player attributes (i.e. money)

	public void useCard(int id) {
		// board.getCards().get(id).actForCard(this);

		board.getCards().get(id - 1).actForCard(this);
		gameFlow.add("" + currentPlayer.getName() + " used " + board.getCards().get(id - 1).getName());
		/*
		 * 
		 * if (id == 11) { board.getCards().get(0).actForCard(this); gameFlow.add("" +
		 * currentPlayer.getName() + " used " + board.getCards().get(0).getName()); }
		 * else if (id == 31) { gameFlow.add("" + currentPlayer.getName() + " used " +
		 * board.getCards().get(1).getName()); board.getCards().get(1).actForCard(this);
		 * }
		 */
	}

	// Enables ending the turn
	public void endTurn() {
		gameFlow.add(currentPlayer.getName() + " ended its turn");
		int index = players.indexOf(currentPlayer);
		if (index == players.size() - 1)
			gameFlow.clear();
		currentPlayer = players.get((index + 1) % players.size());
		gameFlow.add(currentPlayer.getName() + "'s turn");
		if (currentPlayer.isBot()) {

			((Bot) currentPlayer).playYourTurn();
		}
		movementHandle.getDices().resetNumDub();
		notifyObservers("EnableThrowDiceButton", null, null);
		notifyObservers("DisableEndTurnButton", null, null);

		if (currentPlayer.isInJail()) {
			notifyObservers("InJail", null, null);
		}
		autoSave();

	}

	// Overridden Methods (Observable Interface)

	@Override
	public void notifyObservers(String desc, Object obj1, Object obj2) {
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).update(desc, obj1, obj2);

		}

	}

	@Override
	public void addObserver(Observer o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
	}

	public ArrayList<Integer> getDiceValues() {
		ArrayList<Integer> diceValues = new ArrayList<Integer>();
		for (int i = 0; i < movementHandle.getDices().getDie().size(); i++) {
			diceValues.add(movementHandle.getDices().getDie().get(i).getValue());
		}
		return diceValues;
	}

	public ArrayList<Integer> getDiceJailValues() {

		ArrayList<Integer> diceJailValues = new ArrayList<Integer>();
		diceJailValues = getDiceValues();
		diceJailValues.set(2, 7);
		return diceJailValues;

	}

	// Getters

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public ArrayList<String> getPlayerInfo() {
		return playerInfo;
	}

	public ArrayList<String> getGameFlow() {
		return gameFlow;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public int getCurrentPlayerLocID() {
		return getCurrentPlayerLoc().getId();
	}

	public Square getCurrentPlayerLoc() {
		return currentPlayer.getPiece().getLocation();
	}

	public Board getBoard() {
		return board;
	}

	public MovementHandler getMovementHandle() {
		return movementHandle;
	}

	// Setter
	public boolean isCautionMoneyPayed() {
		return cautionMoneyPayed;
	}

	public void setCautionMoneyPayed(boolean cautionMoneyPayed) {
		this.cautionMoneyPayed = cautionMoneyPayed;
	}

	public void payPenalty() {
		((JailSquare) board.getSquareByID(86)).payPenalty(this);
	}

	public void setCurrentPlayerLoc(Square loc) {
		currentPlayer.getPiece().setLocation(loc);
	}

	public void pauseGame() {
		notifyObservers("PauseGame", null, null);
	}

	public void resumeGame() {
		notifyObservers("ResumeGame", null, null);
	}

	public void quitGame() {
		System.exit(0);
	}

	public ArrayList<Integer> getDiceValuesWhileInJail() {

		return movementHandle.getDiceValues();

	}

	public void showProperties() {
		ArrayList<String> properties = new ArrayList<String>();

		for (int i = 0; i < currentPlayer.getPropertyList().size(); i++) {
			properties.add(currentPlayer.getPropertyList().get(i).getId() + " "
					+ currentPlayer.getPropertyList().get(i).getName());

		}
		notifyObservers("ShowProperties", properties, null);
	}

	public void showTitleDeed(int parseInt) {
		ArrayList<String> titleDeedInfo = new ArrayList<String>();
		Square sq = board.getSquares().get(parseInt);
		if (sq instanceof RealEstateSquare) {
			RealEstateSquare rsq = (RealEstateSquare) sq;
			switch (rsq.getLevel()) {
			case 0:
				titleDeedInfo.add("There is no house at this square");
				break;
			case 1:
				titleDeedInfo.add("There is one house at this square");
				break;
			case 2:
				titleDeedInfo.add("There is two house at this square");
				break;
			case 3:
				titleDeedInfo.add("There is three house at this square");
				break;
			case 4:
				titleDeedInfo.add("There is four house at this square");
				break;
			case 5:
				titleDeedInfo.add("There is a hotel at this square");
				break;
			case 6:
				titleDeedInfo.add("There is a skyscraper at this square");
				break;

			}

		} else if (sq instanceof UtilitySquare) {

		} else if (sq instanceof RailroadSquare) {
			RailroadSquare rsq = (RailroadSquare) sq;
			if (rsq.isUpgraded())
				titleDeedInfo.add("This Railroad Upgraded");
			else
				titleDeedInfo.add("This Railroad is not Upgraded");
		}
		notifyObservers("showTitleDeed", parseInt, titleDeedInfo);

	}

	// TODO
	public void upgradeProperty() {
		Square sq = currentPlayer.getPiece().getLocation();
		System.out.println(sq.getName());
		if (board.upgradeSquare(currentPlayer, sq)) {
			gameFlow.add("" + currentPlayer.getName() + " upgraded " + sq.getName());
			notifyObservers("UpdatePlayerInfo", null, null);

		}

	}

	// Button Handler starts
	public void mrMonopolyBeforeActButtonHandler() {

		notifyObservers("DisableEndTurnButton", null, null);
		notifyObservers("DisableThrowDiceButton", null, null);

	}

	public void mrMonopolyAfterActButtonHandler() {

		notifyObservers("DisableMrMonopolyButton", null, null);
		if (movementHandle.getDices().isDouble()) {
			notifyObservers("EnableThrowDiceButton", null, null);
		} else {
			notifyObservers("EnableEndTurnButton", null, null);
		}

	}

	public void useless() {
		notifyObservers("EnablePropertiesButton", null, null);
	}

	public void busBeforeActButtonHandler() {

		notifyObservers("DisableEndTurnButton", null, null);
		notifyObservers("DisableThrowDiceButton", null, null);

	}

	// 0 for chance 1 for com
	public int randomCard(int cardType) {
		Random r = new Random();
		if (cardType == 0) {
			int cardIndex = r.nextInt(3) + 1;
			return cardIndex;
		} else if (cardType == 1) {
			int cardIndex = r.nextInt(2) + 4;
			return cardIndex;
		} else {
			System.out.println("Invalid input to cardType in randomCard Func");
			return -1;
		}
	}

	public void notifyForCards(int cardIndex) {
		notifyObservers("Card", cardIndex, null);
	}

	public void busAfterActButtonHandler() {

		notifyObservers("DisableBusButton", null, null);
		if (movementHandle.getDices().isDouble()) {
			notifyObservers("EnableThrowDiceButton", null, null);
		} else {
			notifyObservers("EnableEndTurnButton", null, null);
		}

	}

	public void regularMoveButtonHandler() {

		if (!(movementHandle.getDices().getNumDub() == 3) && movementHandle.getDices().isDouble()
				&& !(movementHandle.getDices().isSpecialDie())) {
			notifyObservers("EnableThrowDiceButton", null, null);
			notifyObservers("DisableEndTurnButton", null, null);
		} else {
			notifyObservers("DisableThrowDiceButton", null, null);
			notifyObservers("EnableEndTurnButton", null, null);
		}
	}

	// Specific use for tested method
	public boolean repOk() {
		return (board != null) && (gameFlow != null);
	}

	@Override
	public String toString() {
		return "MultiPolyController [players=" + players + ", playerInfo=" + playerInfo + ", gameFlow=" + gameFlow
				+ ", observers=" + observers + ", currentPlayer=" + currentPlayer + ", board=" + board + "]";
	}

	// Setters
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public void setMovementHandle(MovementHandler movementHandle) {
		this.movementHandle = movementHandle;
	}

	// Auto-Save Procedure
	private void autoSave() {
		state.updateGameState();
		state.saveGame();
	}

	public void load() {
		state.setGameName(gameName);
		state.loadGame();
		board = new Board();
		board.setNewGame(false);
		board.initializeSquares(this);
		board.initializeCards();
		board.setPoolMoney(state.getPoolMoney());
		updatePlayerInfo();
	}

	public void setState(GameState state) {
		this.state = state;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public void mapColor(Color color) {
		board.addToColorMap(color);
	}

	public void demolishColorGroup(MultiPolyController controller, int playerNum, int sqNum) {
		Square s = controller.getBoard().getSquareByID(sqNum);
		if (s instanceof RailroadSquare) {
			downgradeRailroad((RailroadSquare) s);
		} else if (s instanceof RealEstateSquare) {
			ArrayList<RealEstateSquare> list = controller.getBoard()
					.getRealEstateByColor(controller.getPlayers().get(playerNum), ((RealEstateSquare) s).getColor());
			downgradeRealEstate(list);
		} else {
			System.out.println("Square is not valid for tornado card");
		}
	}

	public void downgradeRealEstate(ArrayList<RealEstateSquare> list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getLevel() > 0) {
				list.get(i).decreaseLevel();
				System.out.println(list.get(i).getName() + " is downgraded");
			} else {
				System.out.println(list.get(i).getName() + " dows not have any buildings");
			}
		}
	}

	public void downgradeRailroad(RailroadSquare rsq) {
		if (rsq.isUpgraded()) {
			rsq.setUpgraded(false);
			System.out.println(rsq.getName() + " is downgraded");
		} else {
			System.out.println(rsq.getName() + " does not have any depots");
		}
	}
}
