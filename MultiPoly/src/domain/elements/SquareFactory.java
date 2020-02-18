package domain.elements;
//This class is in elements package, since this class is called by Board class many times.

import java.awt.Color;

import java.util.ArrayList;
import java.util.HashMap;

import domain.square.*;

import domain.MultiPolyController;


public class SquareFactory {

	private static SquareFactory instance;

	/**
	 * responsible for returning this Factory in singleton manner
	 * 
	 * 
	 * @return domain.elements.SquareFactory(this)
	 */
	// REQUIRES: nothing
	// EFFECTS: if instance is null (not initialized before) instance will be
	// initialized and be ready for crreating squares
	// MODIFIES: instance object if not initialized

	public static SquareFactory getInstance() {
		if (instance == null)
			instance = new SquareFactory();
		return instance;
	}

	private SquareFactory() {
	}

	/**
	 * This method is used to create varied Squares with respect to given data.
	 * 
	 * @param line contains proper data for Squares
	 * @return domain.square.Square subclasses (RealEstateSquare, RailroadSquare,
	 *         UtilitySquare, ChanceSquare, CommunityChestSquare, GoToJailSquare,
	 *         BirthdayGiftSquare, SubwaySquare, HollandTunnelSquare,
	 *         FreeParkingSquare, RollThreeSquare, LuxuryTaxSquare, IncomeTaxSquare,
	 *         JailSquare, TaxRefundSquare, ReverseDirectionSquare, BonusSquare,
	 *         PaydaySquare, GoSquare)
	 * @exception IllegalArgumentException if data length is not sufficient
	 * @exception NumberFormatException    if id type is not valid (not an integer)
	 * @exception RuntimeException         if Square type unsupported
	 */
	// REQUIRES: line must have specified number of different data that represents
	// each Square type, data must be valid for being read and sufficient to
	// represent Square's.
	// EFFECTS: returns specified subclasses of Square with respect to given data
	// (param line)
	// MODIFIES: nothing

	public Square createSquare(String line, MultiPolyController controller) {

		String lineArr[] = line.split("-");
		// type(0) id(1) isBuyable(2) owner(3) name(4) group#(5) price(6) rents(7-13)
		// buildcost(14)

		// Common for All Squares
		String type = lineArr[0];
		int id = Integer.parseInt(lineArr[1]);

		boolean isBuyable = Boolean.parseBoolean(lineArr[2]);

		String name;
		Player owner;
		int price;
		int buildCost;
		int level;

		switch (type) {
		case "realestate":
			name = lineArr[4];
			owner = findPlayer(lineArr[3], controller);
			Color color = getGroupColor(Integer.parseInt(lineArr[5]));
			controller.mapColor(color);
			price = Integer.parseInt(lineArr[6]);

			ArrayList<Integer> rent = new ArrayList<>();
			for (int i = 0; i < 7; i++) {
				rent.add(Integer.parseInt(lineArr[i + 7])); // Rent info at location 7 to 13 as referred above
			}

			buildCost = Integer.parseInt(lineArr[14]);
			level = Integer.parseInt(lineArr[15]);

			return new RealEstateSquare(id, name, isBuyable, owner, color, price, rent, buildCost, level);

		case "railroad":
			name = lineArr[4];
			owner = findPlayer(lineArr[3], controller);
			price = Integer.parseInt(lineArr[5]);
			boolean upgrade = Boolean.parseBoolean(lineArr[6]);

			return new RailroadSquare(id, name, isBuyable, owner, price, upgrade);

		case "utility":
			name = lineArr[4];
			owner = findPlayer(lineArr[3], controller);
			return new UtilitySquare(id, name, isBuyable, owner);

		case "chance":
			return new ChanceSquare(id, "Chance", isBuyable);

		case "community":
			return new CommunityChestSquare(id, "Community Chest", isBuyable);

		case "gojail":
			return new GoToJailSquare(id, "Go Jail", isBuyable);

		case "birthday":
			return new BirthdayGiftSquare(id, "Birthday", isBuyable);

		case "subway":
			return new SubwaySquare(id, "Subway", isBuyable);

		case "holland":
			return new HollandTunnelSquare(id, "Holland Tunnel", isBuyable);

		case "freepark":
			return new FreeParkingSquare(id, "Free Parking", isBuyable);

		case "roll3":
			return new RollThreeSquare(id, "Roll Three", isBuyable);

		case "luxtax":
			return new LuxuryTaxSquare(id, "Luxury Tax", isBuyable);

		case "incomeTax":
			return new IncomeTaxSquare(id, "Income Tax", isBuyable);

		case "jail":
			return new JailSquare(id, "Jail", isBuyable);

		case "taxref":
			return new TaxRefundSquare(id, "Tax Refund", isBuyable);

		case "reverse":
			return new ReverseDirectionSquare(id, "Reverse Direction", isBuyable);

		case "bonus":
			return new BonusSquare(id, "Bonus", isBuyable);

		case "payday":
			return new PaydaySquare(id, "PayDay", isBuyable);

		case "go":
			return new GoSquare(id, "Go", isBuyable);

		default:
			return null;

		}

	}

	private Player findPlayer(String name, MultiPolyController controller) {
		Player player = null;
		if (name != null) {
			ArrayList<Player> players = controller.getPlayers();
			for (Player p : players) {
				if (p.getName().equals(name)) {
					return p;
				}
			}
		}
		return player;
	}

	private Color getGroupColor(int groupNumber) {
		switch (groupNumber) {
		// outter groups
		case 1:
			return new Color(128, 0, 51); // purple
		case 2:
			return new Color(170, 136, 2); // mustard
		case 3:
			return new Color(255, 179, 129); // pinkish orange
		case 4:
			return new Color(130, 0, 0); // dark red
		case 5:
			return new Color(255, 168, 170); // light pink
		case 6:
			return new Color(130, 255, 129); // lime
		case 7:
			return new Color(255, 230, 130); // light yellow
		case 8:
			return new Color(1, 127, 103); // dark green

		// middle groups
		case 9:
			return new Color(213, 0, 0); // red
		case 10:
			return new Color(254, 203, 0); // dark yellow
		case 11:
			return new Color(8, 134, 50); // green
		case 12:
			return new Color(40, 78, 159); // dark blue
		case 13:
			return new Color(135, 165, 215); // eggplant
		case 14:
			return new Color(86, 13, 59); // light blue
		case 15:
			return new Color(239, 56, 120); // pink
		case 16:
			return new Color(245, 128, 35); // orange

		// inner groups
		case 17:
			return new Color(128, 128, 128); // grey
		case 18:
			return new Color(170, 68, 0); // coffee
		case 19:
			return new Color(255, 255, 255); // white
		case 20:
			return new Color(0, 0, 0); // black

		default:
			return new Color(127, 3, 49);

		}
	}

	public boolean repOk() {
		return (instance != null) && (instance instanceof SquareFactory);

	}

	@Override
	public String toString() {
		return "This is a Square Factory that is implemented in a Singleton Manner. Access is only granted for encapsulated instance of this class.";
	}

}
