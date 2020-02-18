package domain.card;

import domain.MultiPolyController;

public abstract class Card {

	// Card Characteristics
	private int id;
	private String explanation; // Is this an explanation or name
	private boolean isImmediate;
	private String cardType;

	public Card(int id, String explanation, boolean isImmediate, String cardType) {
		this.explanation = explanation;
		this.isImmediate = isImmediate;
		this.cardType = cardType;
		this.id = id;

	}

	// Getters
	public String getName() {
		return explanation;
	}

	public boolean isImmediate() {
		return isImmediate;
	}

	public String getCardType() {
		return cardType;
	}

	public int getId() {
		return id;
	}

	// Abstract Method - will be implemented specifically by subclasses
	public abstract void actForCard(MultiPolyController controller);

}
