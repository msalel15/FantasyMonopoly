package domain;

public abstract class Operator {

	// Variables
	protected GameState state;

	protected static final String directoryGeneral = System.getProperty("user.dir") + "/save/";
	protected static final String directorySquare = System.getProperty("user.dir") + "/components/squareinfo/";
	protected static final String sep = "-";
	protected static final String generalFileName = "-autosave.txt";
	protected static final String squareFileName = "-sq.txt";

	// Constructor
	public Operator(GameState state) {
		this.state = state;
		operate();
	}

	// Abstract Applied Operation
	public abstract void operate();

	// Getters & Setters
	protected GameState getState() {
		return state;
	}

	protected void setState(GameState state) {
		this.state = state;
	}
	
	protected void updateName() {
		state.setGameName(state.getController().getGameName());
	}

}
