package domain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import domain.card.RollThreeCard;
import domain.elements.Piece;
import domain.elements.Player;

public class LoadOperator extends Operator {

	private BufferedReader br;

	// Constructor
	public LoadOperator(GameState state) {
		super(state);
	}

	@Override
	public void operate() {
		updateName();
		readGeneral();
	}

	private void readGeneral() {
		try {
			int lineNumber = 1;
			String line;
			br = new BufferedReader(new FileReader(directoryGeneral + state.getGameName() + generalFileName));
			ArrayList<Player> pl = new ArrayList<Player>();
			while ((line = br.readLine()) != null) {
				if (lineNumber < 4) {
					readGeneralLines(line, lineNumber);
					lineNumber++;
				} else {
					readPlayers(pl, line);
					state.setpList(pl);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Either directory or game name invalid");

		} finally {

			try {
				if (br != null)
					br.close();

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	private void readGeneralLines(String line, int lineNum) {
		switch (lineNum) {
		case 1:
			state.setPlayerNumber(Integer.parseInt(line));
			break;
		case 2:
			state.setCurrentPlayerId(Integer.parseInt(line));
			break;
		case 3:
			state.setPoolMoney(Integer.parseInt(line));
		}

	}

	private void readPlayers(ArrayList<Player> pl, String line) {
		String[] arr = line.split("-");
		int id = Integer.parseInt(arr[0]);
		String name = arr[1];
		int location = Integer.parseInt(arr[2]);
		boolean isInJail = Boolean.parseBoolean(arr[3]);
		int turnInJail = Integer.parseInt(arr[4]);
		boolean isReverse = Boolean.parseBoolean(arr[5]);
		int money = Integer.parseInt(arr[6]);
		int roll3 = Integer.parseInt(arr[7]);
		// Check is there any violation
		Player newPlayer = new Player(name, new Piece(state.getController().getBoard().getSquareByID(location)));
		newPlayer.setInJail(isInJail);
		newPlayer.setTurnInJail(turnInJail);
		newPlayer.setReverse(isReverse);
		newPlayer.setMoney(money);
		handleRoll3(newPlayer, roll3);

		addSquaresToList(newPlayer, line);
		if (id >= state.getPlayerNumber())
			System.out.println("Player number does not match");
		pl.add(newPlayer);

	}

	private void handleRoll3(Player pl, int val) {
		ArrayList<Integer> roll3list = new ArrayList<>();
		int a = val / 100;
		int b = (val - a * 100) / 10;
		int c = (val - a * 100 - b * 10);
		roll3list.add(a);
		roll3list.add(b);
		roll3list.add(c);

		RollThreeCard current = new RollThreeCard();
		current.setRollThreeCard(roll3list);
		pl.addToRoll3List(current);
	}

	private void addSquaresToList(Player pl, String line) {
		String[] arr = line.split(sep);
		int len = arr.length;
		for (int i = 8; i < len; i++) {
			pl.addProperty(state.getController().getBoard().getSquareByID(Integer.parseInt(arr[i])));
		}
	}

}
