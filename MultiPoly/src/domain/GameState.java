package domain;

import java.util.ArrayList;

import domain.elements.Player;

public class GameState {

	// Main Parts belongs to controller
	private MultiPolyController controller;
	private String gameName;
	private int playerNumber;

	// General Data
	private ArrayList<Player> pList;
	private int currentPlayerId;// 0 to n-1

	// Board Info
	private int poolMoney;
	// Operator
	private Operator op;

	public GameState(MultiPolyController controller) {
		this.controller = controller;

	}

	public void updateGameState() {
		gameName = controller.getGameName();
		pList = controller.getPlayers();
		playerNumber = pList.size();
		findPlayerId();

	}

	public void loadGame() {
		loadFile();
		controller.setPlayers(pList);
		controller.setCurrentPlayer(pList.get(currentPlayerId));
	}

	public void saveGame() {
		saveFile();
	}

	private void loadFile() {
		op = new LoadOperator(this);
	}

	private void saveFile() {
		op = new SaveOperator(this);

	}

	// Getters & Setters
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public void setpList(ArrayList<Player> pList) {
		this.pList = pList;
	}

	// Getters
	public String getGameName() {
		return gameName;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}

	public int getCurrentPlayerId() {
		return currentPlayerId;
	}

	public ArrayList<Player> getpList() {
		return pList;
	}

	public void setCurrentPlayerId(int currentPlayerId) {
		this.currentPlayerId = currentPlayerId;
	}

	// Helper
	private void findPlayerId() {
		if (pList.size() == 0)
			System.out.println("Player list either not constructed or properly updated");
		else {
			Player current = controller.getCurrentPlayer();
			for (int i = 0; i < playerNumber; i++) {
				if (current.getName().equals(pList.get(i).getName()))
					currentPlayerId = i;
			}
		}
	}

	public MultiPolyController getController() {
		return controller;
	}

	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}

	public int getPoolMoney() {
		return poolMoney;
	}

	public void setPoolMoney(int poolMoney) {
		this.poolMoney = poolMoney;
	}

}
