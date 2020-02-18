package domain.elements;

import java.awt.Color;
import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import domain.MultiPolyController;
import domain.card.Card;
import domain.card.ChanceGoJail;
import domain.card.ChanceHolidayBonus;
import domain.card.ChanceHurricaneLandfall;
import domain.card.ComPayHost;
import domain.card.ComTornadoHits;
import domain.square.RailroadSquare;
import domain.square.RealEstateSquare;
import domain.square.Square;
import domain.square.UtilitySquare;

/**
 * Typical board is an arrayList of squares
 * 
 * This class is a container that holds squares, cards and figures informations
 * and provides a number of procedures that are necessary for monopoly logic to
 * work.
 */

public class Board {

	// Data
	private ArrayList<Square> squares;
	private ArrayList<Card> cards;
	private ArrayList<Piece> pieces;
	private HashMap<Color, Integer> colorMap;

	// Pool Money Amount
	private int poolMoney;

	// Game pos
	private boolean isNewGame;

	public Board() {
		this.squares = new ArrayList<>();
		this.cards = new ArrayList<>();
		this.pieces = new ArrayList<>();
		colorMap = new HashMap<Color, Integer>();

		isNewGame = true;
		poolMoney = 0;
	}

	/////////////////
	// For squares //
	/////////////////
	public void initializeSquares(MultiPolyController controller) {
		BufferedReader breader = null;

		String def = System.getProperty("user.dir") + "/components/squareinfo/";
		String append = "def-sq.txt";
		if (!isNewGame)
			append = controller.getGameName() + "-sq.txt";

		try {
			String line;
			breader = new BufferedReader(new FileReader(def + append));
			while ((line = breader.readLine()) != null) {
				initializeSquare(line, controller);
			}
		} catch (IOException e) {
			e.printStackTrace();

		} finally {

			try {
				if (breader != null)
					breader.close();

				System.out.println("Data Read");

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		isNewGame = true;

	}

	public void initializeSquare(String line, MultiPolyController controller) {

		Square sq = SquareFactory.getInstance().createSquare(line, controller);
		addToSquareList(sq);
	}

	/**
	 * adds specified square to square list
	 * 
	 * @param sq represents Square object that is being added to square list
	 *           (squares)
	 * 
	 */
	// REQUIRES : squares is not null (initialized already)
	// EFFECTS: Adds sq object to the squares
	// MODIFIES : squares

	public void addToSquareList(Square sq) {
		squares.add(sq);
	}

	public ArrayList<Square> getSquares() {
		return squares;
	}

	/**
	 * returns square that is specified by parameter name
	 * 
	 * @param name name of the specified Square object
	 * @return domain.square.Square or null (if specified Square could not be found)
	 */
	// REQUIRES : squares(array list) is not null (already initialized)
	// REQUIRES : name is a valid String
	// EFFECTS: If there exists a Square with the given name in squares, that square
	// will be returned.
	// MODIFIES : nothing

	public Square getSquareByName(String name) {
		for (int i = 0; i < squares.size(); i++) {
			Square sq = squares.get(i);
			if (sq.getName().equals(name)) {
				return sq;
			}
		}
		return null;
	}

	/**
	 * returns square that is specified by parameter id
	 * 
	 * @param id integer id of the square
	 * @return domain.square.Square or null
	 */
	// REQUIRES : squares is not null (already initialized)
	// REQUIRES : id as a valid integer
	// EFFECTS: If there exists a Square with the given id in squares, returns that
	// square.
	// MODIFIES : nothing

	public Square getSquareByID(int id) {
		for (int i = 0; i < squares.size(); i++) {
			Square sq = squares.get(i);
			if (sq.getId() == id) {
				return sq;
			}
		}
		return null;
	}

	public void setSquares(ArrayList<Square> squares) {
		this.squares = squares;
	}

	///////////////
	// For cards //
	///////////////

	public void initializeCards() {

		Card card = new ChanceGoJail();

		addToCardList(card);
		Card card1 = new ChanceHolidayBonus();
		addToCardList(card1);
		Card card2 = new ChanceHurricaneLandfall();
		addToCardList(card2);
		Card card3 = new ComPayHost();
		addToCardList(card3);
		Card card4 = new ComTornadoHits();
		addToCardList(card4);
	}

	public void addToCardList(Card card) {
		cards.add(card);
	}

	public ArrayList<Card> getCards() {
		return cards;
	}

	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}

	/////////////////
	// For figures //
	/////////////////
	public ArrayList<Piece> getPieces() {
		return pieces;
	}

	///////////////////
	// Board Methods //
	///////////////////
	/**
	 * enables specified Player to buy specified Square
	 * 
	 * @param player represents Player object that acts buying
	 * @param sq     represents Square object that is being purchased
	 * @return boolean success of the purchase
	 * 
	 * @exception IllegalArgumentException if Square is not purchasable
	 */
	// REQUIRES : player and sq are not null.
	// MODIFIES : player and sq (bonds both params to each other)
	// EFFECTS: If sq is not owned and player has enough money to buy sq this
	// methods decrease player's money by sq's price, sets sq's owner as player and
	// adds sq to the player'spropertyList

	public boolean buyPlace(Player player, Square sq) {
		if (sq instanceof RealEstateSquare) {
			return buyRealEstate(player, (RealEstateSquare) sq);
		} else if (sq instanceof UtilitySquare) {
			return buyUtility(player, (UtilitySquare) sq);
		} else if (sq instanceof RailroadSquare) {
			return buyRailRoad(player, (RailroadSquare) sq);
		} else {
			System.out.println("Buy Failed. You cannot buy a square that is not a property");
			return false;
		}
	}

	/**
	 * enables player to purchase specified RealEstateSquare
	 * 
	 * @param player specified Player object that is acting for purchase
	 * @param sq     specified RealEstateSquare square that will be purchased
	 * 
	 */
	// REQUIRES : player and sq are not null. Sq must not be owned.
	// MODIFIES : player and sq (bonds both param to each other)
	// EFFECTS: If sq is not owned and player has enough money to buy sq this
	// methods decrease player's money by sq' s price, sets sq's owner as player and
	// adds sq to the player'spropertyList

	public boolean buyRealEstate(Player player, RealEstateSquare sq) {
		if (sq.isOwned()) {
			System.out.println("Buy Failed. Already has an owner.");
			return false;
		} else {
			int price = sq.getPrice();
			int playerMoney = player.getMoney();
			if (playerMoney - price >= 0) {
				player.transaction(-price);
				sq.setOwner(player);
				player.addProperty(sq);
				return true;
			} else {
				System.out.println("Buy Failed. Not enough money.");
				return false;
			}
		}
	}

	// There is not a limit on number of owners
	/**
	 * enables specified Player to buy specified UtilitySquare
	 * 
	 * @param player represents Player object that acts buying
	 * @param sq     represents UtilitySquare object that is being purchased
	 * 
	 */
	// REQUIRES : player and sq are not null.
	// MODIFIES : player and sq (bonds both param to each other)
	// EFFECTS: If sq is not owned and player has enough money to buy sq this
	// methods decrease player's money by sq' s price, sets sq's owner as* player
	// and adds sq to the player'spropertyList*/

	public boolean buyUtility(Player player, UtilitySquare sq) {
		int price = sq.getPrice();
		int playerMoney = player.getMoney();
		if (playerMoney - price >= 0) {
			player.transaction(-price);
			player.addProperty(sq);
			return true;
		} else {
			System.out.println("Buy Failed. Not enough money.");
			return false;
		}
	}

	/**
	 * enables specified Player to buy specified RailroadSquare
	 * 
	 * @param player represents Player object that acts buying
	 * @param sq     represents RailroadSquare object that is being purchased
	 * 
	 */
	// REQUIRES : player and sq are not null.
	// MODIFIES : player and sq (bonds both param to each other)
	// EFFECTS: If sq is not owned and player has enough money to buy sq this
	// methods decrease player's money by sq's price, sets sq's owner as player and
	// adds sq to the player's propertyList

	public boolean buyRailRoad(Player player, RailroadSquare sq) {
		int price = sq.getPrice();
		int playerMoney = player.getMoney();
		if (playerMoney - price >= 0) {
			player.transaction(-price);
			sq.setOwner(player);
			player.addProperty(sq);
			return true;
		} else {
			System.out.println("Buy Failed. Not enough money.");
			return false;
		}
	}

	public int getPoolMoney() {
		return poolMoney;
	}

	public void setPoolMoney(int money) {
		poolMoney = money;
	}

	/**
	 * adds specified money to pool
	 * 
	 * @param money represents money
	 * 
	 */

	public void updatePoolMoney(int money) {
		// REQUIRES : poolMoney and money not null and valid integers
		// MODIFIES : poolMoney
		// EFFECTS: increments poolMoney by money
		poolMoney += money;

	}

	public boolean repOk() {
		if ((squares != null) && (squares.size() >= 0))
			return true;
		return false;
	}

	@Override
	public String toString() {

		String info = "";

		for (Square sq : squares) {
			info += "Sqaure ID: " + sq.getId();
			info += " Name: " + sq.getName() + "\n";
		}

		return "MultiPolyBoard [" + info + "]";
	}

	public boolean upgradeSquare(Player player, Square sq) {
		if (sq instanceof RealEstateSquare) {
			return upgradeRealEstate(player, (RealEstateSquare) sq);
		} else if (sq instanceof RailroadSquare) {
			return upgradeRailRoad(player, (RailroadSquare) sq);
		} else {
			System.out.println("Upgrade failed");
			return false;
		}
	}

	public boolean upgradeRailRoad(Player player, RailroadSquare sq) {
		if (player.equals(sq.getOwner())) {
			if (!sq.isUpgraded()) {
				if (player.getMoney() >= sq.getUpgradeCost()) {
					player.transaction(-sq.getUpgradeCost());
					sq.setUpgraded(true);
					System.out.println(sq.getName() + " upgraded!");
					return true;
				} else {
					System.out.println("You do not have enough money to upgrade " + sq.getName());
				}
			} else {
				System.out.println("This railroad is already upgraded");
			}
		} else {
			System.out.println("You do not own this property.");
		}
		return false;
	}

	public boolean upgradeRealEstate(Player player, RealEstateSquare sq) {
		int nextLevel = sq.getLevel() + 1;
		if (player.equals(sq.getOwner())) {
			if (player.getMoney() >= sq.getBuildCost()) {
				if (hasMajority(player, sq.getColor()) && nextLevel < 6
						|| hasMonopoly(player, sq.getColor()) && nextLevel <= 6) {
					player.transaction(-sq.getBuildCost());
					sq.increaseLevel();
					System.out.println(sq.getName() + " upgraded!");
					return true;
				} else {
					System.out.println("You cannot upgrade this property");
				}

			} else {
				System.out.println("You do not have enough money to upgrade " + sq.getName());
			}
		} else {
			System.out.println("You do not own this property");
		}

		return false;
	}

	public int getColorAmount(Color key) {
		return colorMap.get(key);
	}

	public void addToColorMap(Color key) {
		if (colorMap.containsKey(key)) {
			colorMap.put(key, colorMap.get(key) + 1);
		} else {
			colorMap.put(key, 0);
		}
	}

	/**
	 *
	 * @param player
	 * @param color
	 * @return player's properties in a given color group
	 */
	public ArrayList<RealEstateSquare> getRealEstateByColor(Player player, Color color) {
		ArrayList<RealEstateSquare> relist = new ArrayList<RealEstateSquare>();
		for (Square property : player.getPropertyList()) {
			if (property instanceof RealEstateSquare) {
				if (((RealEstateSquare) property).getColor() == color) {
					relist.add((RealEstateSquare) property);
				}
			}
		}
		return relist;
	}

	public boolean hasMajority(Player player, Color color) {
		ArrayList<RealEstateSquare> relist = getRealEstateByColor(player, color);
		int numEstate = getColorAmount(color);
		if (numEstate - relist.size() <= 1) {
			return true;
		}
		return false;
	}

	public boolean hasMonopoly(Player player, Color color) {
		ArrayList<RealEstateSquare> relist = getRealEstateByColor(player, color);
		int numEstate = getColorAmount(color);
		if (numEstate == relist.size()) {
			return true;
		}
		return false;
	}

	public RealEstateSquare getCheapestProperty(ArrayList<RealEstateSquare> list) {
		if (list.size() >= 0) {
			ArrayList<RealEstateSquare> newList = list;
			newList.sort(Comparator.comparing(RealEstateSquare::getPrice));
			return newList.get(0);
		} else {
			return null;
		}
	}

	public RealEstateSquare getMostExpProperty(ArrayList<RealEstateSquare> list) {
		if (list.size() >= 0) {
			ArrayList<RealEstateSquare> newList = list;
			newList.sort(Comparator.comparing(RealEstateSquare::getPrice));
			return newList.get(newList.size() - 1);
		} else {
			return null;
		}
	}

	public boolean isNewGame() {
		return isNewGame;
	}

	public void setNewGame(boolean isNewGame) {
		this.isNewGame = isNewGame;
	}
}
