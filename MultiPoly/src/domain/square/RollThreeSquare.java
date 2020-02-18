package domain.square;

import java.util.ArrayList;

import domain.MultiPolyController;
import domain.card.RollThreeCard;
import domain.elements.Player;

public class RollThreeSquare extends Square {

	private static final int oneMatch = 50;
	private static final int twoMatch = 200;
	private static final int threeMatch = 1000;

	private boolean isRolled;

	public RollThreeSquare(int id, String name, boolean isBuyable) {
		super(id, name, isBuyable);

	}

	/**
	 * @param controller well-known Controller of the program
	 * 
	 */
	// requires: proper and working Controller object and already initialized lists
	// of controllers
	// effects: adds actions to the game flow and transacts specified money to the
	// player with respect to the result of the action, all results of the acting
	// will be mirrored to the each Player
	// modifies: controller fields i.e. gameFlow (via adding actions), money of
	// players

	@Override
	public void actForSquare(MultiPolyController controller) {

		ArrayList<Player> players = controller.getPlayers();

		Player currentPlayer = controller.getCurrentPlayer();

		// generate roll three cards for all players

		ArrayList<Integer> dices = controller.getRoll3Dice();

		controller.getGameFlow()
				.add("Roll Three Die is: " + dices.get(0) + " , " + dices.get(1) + " , " + dices.get(2));

		for (Player player : players) {

			player.addToRoll3List(new RollThreeCard());

		}

		// roll three regular dice
		controller.getGameFlow().add("----RollThreeCards----");

		for (Player player : players) {
			controller.getGameFlow().add(player.getName() + " :");

			int money = 0;
			int max = 0;
			RollThreeCard playerCard = null;

			for (RollThreeCard card : player.getRoll3Cards()) {
				controller.getGameFlow().add(" " + card.getRollThreeCard().get(0) + " , "
						+ card.getRollThreeCard().get(1) + " , " + card.getRollThreeCard().get(2));

				if (player == currentPlayer) {

					max = calculateMoney(true, dices, card.getRollThreeCard());
				} else {

					max = calculateMoney(false, dices, card.getRollThreeCard());

				}
				if (max >= money) {

					money = max;
					playerCard = card;
				}
			}
			player.transaction(money);
			controller.getGameFlow().add(player.getName() + " earned $" + money + " from rollThreeCard");
			if (playerCard != null) {
				player.removeFromRoll3List(playerCard);

			}

		}

		controller.notifyObservers("RollThree", dices, null);

	}

	/**
	 * calculates money with respect to matched roll3 cards
	 * 
	 * @param isCurrentPlayer checks for whether player matches with current player
	 * @param dices           contains dice values
	 * @param card            contains specified cards
	 * 
	 * @return int returns specified money value
	 */

	// requires: dices and card list must not be null
	// effects: updates money field with respect to match number via comparing
	// already owned cards with current cards
	// modifies: money value

	public int calculateMoney(boolean isCurrentPlayer, ArrayList<Integer> dices, ArrayList<Integer> card) {
		int numberOfMatches = 0;
		int money = 0;

		for (int i = 0; i < card.size(); i++) {
			if (dices.contains(card.get(i))) {
				numberOfMatches++;
			}

		}
		switch (numberOfMatches) {

		case 0:

			break;

		case 1:
			money = oneMatch;

			break;

		case 2:
			money = twoMatch;

			break;

		case 3:

			if (isCurrentPlayer) {

				money = 1500;

			} else {
				money = threeMatch;

			}

			break;

		default:

			break;

		}

		return money;
	}

	public boolean isRolled() {
		return isRolled;
	}

	public void setRolled(boolean isRolled) {
		this.isRolled = isRolled;
	}

	public boolean repOk() {
		return 0 < oneMatch && oneMatch < twoMatch && twoMatch < threeMatch;
	}

	@Override
	public String toString() {
		return super.toString() + "\nRollThreeSquare's only field is: \nisRolled = " + isRolled + "]";
	}

}
