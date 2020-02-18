package domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import domain.elements.Board;
import domain.elements.Player;
import domain.square.RailroadSquare;
import domain.square.RealEstateSquare;
import domain.square.Square;
import domain.square.UtilitySquare;

public class SaveOperator extends Operator {

	private PrintWriter pw;

	public SaveOperator(GameState state) {
		super(state);
	}

	@Override
	public void operate() {
		updateName();
		createFile();
	}

	private void createFile() {
		File dir = new File(directoryGeneral);
		if (!dir.exists()) {
			boolean isDirCreated = dir.mkdir();
			if (isDirCreated)
				System.out.println("Directory created successfully");
			else
				System.out.println("Failed to create directory");
		}

		try {
			pw = new PrintWriter(new FileWriter(directoryGeneral + state.getGameName() + generalFileName));
			constructGeneralFile(pw);
			pw.close();
		} catch (IOException e) {
			System.out.println("Specified path (" + directoryGeneral + ") does not exist.");
			e.printStackTrace();
		}

		try {
			pw = new PrintWriter(new FileWriter(directorySquare + state.getGameName() + squareFileName));
			writeSquares();
			pw.close();
		} catch (IOException e) {
			System.out.println("Specified path (" + directorySquare + ") does not exist.");
			e.printStackTrace();
		}

	}

	private void constructGeneralFile(PrintWriter pw) {
		// 1- Number of Players
		pw.println(state.getPlayerNumber());
		// 2- state Player Id
		pw.println(state.getCurrentPlayerId());
		// 3- Pool Money
		writePoolMoney();
		// 4- Player Data
		writePlayers();

	}

	private void writePlayers() {
		ArrayList<Player> pList = state.getpList();
		for (int i = 0; i < state.getPlayerNumber(); i++) {
			Player pl = pList.get(i);
			// id
			pw.print(i + sep);
			// name
			pw.print(pl.getName() + sep);
			// Piece (Square Id)
			pw.print(pl.getPiece().getLocation().getId() + sep);
			// isInJail
			pw.print(pl.isInJail() + sep);
			// turnInJail
			pw.print(pl.getTurnInJail() + sep);
			// isReverse
			pw.print(pl.isReverse() + sep);
			// money
			pw.print(pl.getMoney() + sep);
			// roll 3
			writeList(pl.getRoll3Cards().get(0).getRollThreeCard(), false);
			// isBot (not implemented yet)
			// pw.print(pl.isBot());
			pw.print(sep);
			writeList(pl.getPropertyList(), true);
			pw.println();
		}
	}

	private void writeList(ArrayList<?> list, boolean isSep) {
		for (int i = 0; i < list.size(); i++) {
			Object state;
			if (list.get(i) instanceof Square)
				state = ((Square) list.get(i)).getId();
			else
				state = list.get(i);
			if (isSep)
				pw.print(state + sep);
			else
				pw.print(state);
		}
	}

	private void writePoolMoney() {
		Board br = state.getController().getBoard();
		// pool Money
		pw.println(br.getPoolMoney());
	}

	private void writeSquares() {
		Board br = state.getController().getBoard();

		BufferedReader breader = null;

		int sqOrder = 0;
		try {

			String line;
			breader = new BufferedReader(
					new FileReader(System.getProperty("user.dir") + "/components/squareinfo/def-sq.txt"));
			while ((line = breader.readLine()) != null) {
				Square statesq = br.getSquareByID(sqOrder);
				printSquare(line, statesq);
				sqOrder++;
			}
		} catch (IOException e) {
			e.printStackTrace();

		} finally {

			try {
				if (breader != null)
					breader.close();

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		if (sqOrder != br.getSquares().size()) {
			System.out.println("Problematic transfer in saving, check the conditions");
			System.out.println(sqOrder+" vs "+br.getSquares().size());
			}

	}

	private void printSquare(String line, Square sq) {
		String[] arr = line.split(sep);
		String type = arr[0];
		String id = arr[1];

		pw.print(type + sep);
		pw.print(id + sep);
		pw.print(sq.isBuyable());

		Player owner;

		switch (type) {
		case "realestate":
			pw.print(sep);
			owner = ((RealEstateSquare) sq).getOwner();
			if (owner != null)
				pw.print(owner.getName() + sep);
			else
				pw.print(null + sep);

			pw.print(sq.getName() + sep);
			pw.print(arr[5] + sep); // color
			pw.print(((RealEstateSquare) sq).getPrice() + sep);
			writeList(((RealEstateSquare) sq).getRent(), true);
			pw.print(((RealEstateSquare) sq).getBuildCost() + sep);
			pw.print(((RealEstateSquare) sq).getLevel());
			break;
		case "railroad":
			pw.print(sep);
			owner = ((RailroadSquare) sq).getOwner();
			if (owner != null)
				pw.print(owner.getName() + sep);
			else
				pw.print(null + sep);
			pw.print(sq.getName() + sep);
			pw.print(((RailroadSquare) sq).getPrice() + sep);
			pw.print(((RailroadSquare) sq).isUpgraded());
			break;
		case "utility":
			pw.print(sep);
			owner = ((UtilitySquare) sq).getOwner();
			if (owner != null)
				pw.print(owner.getName() + sep);
			else
				pw.print(null + sep);
			pw.print(sq.getName());
			break;

		default:
			break;

		}
		pw.println();

	}

}
