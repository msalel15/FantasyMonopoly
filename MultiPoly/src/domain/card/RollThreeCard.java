package domain.card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class RollThreeCard {

	// Data
	private ArrayList<ArrayList<Integer>> rollThreeCards; // all roll3 cards
	private ArrayList<Integer> rollThreeCard; // current roll3 card

	public RollThreeCard() {
		rollThreeCards = new ArrayList<ArrayList<Integer>>();
		rollThreeCard = new ArrayList<>();

		initRollThreeCard();

		Random rand = new Random();
		int randomCardIndex = rand.nextInt(rollThreeCards.size());
		rollThreeCard = rollThreeCards.get(randomCardIndex);
	}

	private void initRollThreeCard() {
		ArrayList<Integer> rollThree;

		rollThree = new ArrayList<Integer>(Arrays.asList(1, 2, 3));
		rollThreeCards.add(rollThree);

		rollThree = new ArrayList<Integer>(Arrays.asList(1, 2, 4));
		rollThreeCards.add(rollThree);

		rollThree = new ArrayList<Integer>(Arrays.asList(1, 2, 5));
		rollThreeCards.add(rollThree);

		rollThree = new ArrayList<Integer>(Arrays.asList(1, 2, 6));
		rollThreeCards.add(rollThree);

		rollThree = new ArrayList<Integer>(Arrays.asList(1, 3, 4));
		rollThreeCards.add(rollThree);

		rollThree = new ArrayList<Integer>(Arrays.asList(1, 3, 5));
		rollThreeCards.add(rollThree);

		rollThree = new ArrayList<Integer>(Arrays.asList(1, 3, 6));
		rollThreeCards.add(rollThree);

		rollThree = new ArrayList<Integer>(Arrays.asList(1, 4, 5));
		rollThreeCards.add(rollThree);

		rollThree = new ArrayList<Integer>(Arrays.asList(1, 4, 6));
		rollThreeCards.add(rollThree);

		rollThree = new ArrayList<Integer>(Arrays.asList(1, 5, 6));
		rollThreeCards.add(rollThree);

		rollThree = new ArrayList<Integer>(Arrays.asList(2, 3, 4));
		rollThreeCards.add(rollThree);

		rollThree = new ArrayList<Integer>(Arrays.asList(2, 4, 5));
		rollThreeCards.add(rollThree);

		rollThree = new ArrayList<Integer>(Arrays.asList(2, 4, 6));
		rollThreeCards.add(rollThree);

		rollThree = new ArrayList<Integer>(Arrays.asList(2, 5, 6));
		rollThreeCards.add(rollThree);

		rollThree = new ArrayList<Integer>(Arrays.asList(3, 4, 5));
		rollThreeCards.add(rollThree);

		rollThree = new ArrayList<Integer>(Arrays.asList(3, 4, 6));
		rollThreeCards.add(rollThree);

		rollThree = new ArrayList<Integer>(Arrays.asList(3, 5, 6));
		rollThreeCards.add(rollThree);

		rollThree = new ArrayList<Integer>(Arrays.asList(4, 5, 6));
		rollThreeCards.add(rollThree);

		rollThree = new ArrayList<Integer>(Arrays.asList(2, 4, 6));
		rollThreeCards.add(rollThree);

		rollThree = new ArrayList<Integer>(Arrays.asList(2, 5, 6));
		rollThreeCards.add(rollThree);

		rollThree = new ArrayList<Integer>(Arrays.asList(3, 4, 5));
		rollThreeCards.add(rollThree);

		rollThree = new ArrayList<Integer>(Arrays.asList(3, 4, 6));
		rollThreeCards.add(rollThree);

		rollThree = new ArrayList<Integer>(Arrays.asList(3, 5, 6));
		rollThreeCards.add(rollThree);

		rollThree = new ArrayList<Integer>(Arrays.asList(4, 5, 6));
		rollThreeCards.add(rollThree);

	}

	// Getter for all
	public ArrayList<Integer> getRollThreeCard() {
		return rollThreeCard;
	}

	public void setRollThreeCard(ArrayList<Integer> rollThreeCard) {
		this.rollThreeCard = rollThreeCard;
	}

}
