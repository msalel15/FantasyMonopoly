package domain.elements;
// This class is in elements package, since this class is called by Board class many times.

import domain.card.Card;
import domain.card.ChanceGoJail;
import domain.card.ChanceHolidayBonus;
import domain.card.ChanceHurricaneLandfall;
import domain.card.ComPayHost;
import domain.card.ComTornadoHits;

// Produces different types of cards with respect to given info (id)
public class CardFactory {

	private static CardFactory instance;

	public static CardFactory getInstance() {
		if (instance == null)
			instance = new CardFactory();
		return instance;
	}

	private CardFactory() {

	}

	public Card createCard(int id) {
		// Add varied cases for producing different cards
		switch (id) {
		case 1:
			return new ChanceGoJail();
		case 2:
			return new ChanceHolidayBonus();
		case 3:
			return new ChanceHurricaneLandfall();
		case 4:
			return new ComPayHost();
		case 5:
			return new ComTornadoHits();

		default:
			return null;
		}
	}

}
