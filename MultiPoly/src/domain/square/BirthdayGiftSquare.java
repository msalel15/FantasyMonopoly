package domain.square;

import java.util.ArrayList;

import domain.MultiPolyController;
import domain.elements.Player;

public class BirthdayGiftSquare extends Square {

	private static final int BirthdayGiftMoney = 100;

	public BirthdayGiftSquare(int id, String name, Boolean isBuyable) {
		super(id, name, isBuyable);
	}

	@Override
	public void actForSquare(MultiPolyController controller) {
		ArrayList<Player> players = controller.getPlayers();
		Player currentPlayer = controller.getCurrentPlayer();

		for (Player player : players) {
			if (player != currentPlayer) {
				transferMoney(player, currentPlayer, BirthdayGiftMoney);
			}
		}
		controller.getGameFlow().add("" + currentPlayer.getName() + " recieved $"
				+ (players.size() - 1) * BirthdayGiftMoney + " from other players.");

		controller.notifyObservers("UpdatePlayerInfo", null, null);
	}

	public void transferMoney(Player from, Player to, int amount) {

		if (from.getMoney() >= 0) {
			from.transaction(-amount);
			to.transaction(amount);
		} else {
			System.out.println(from + " has not enough money for money transfer");
		}
	}

}
