package domain.elements;

import java.util.ArrayList;

// Pure Fabrication
public class ThrowDice {

	// Data
	private ArrayList<DieFaces> die;
	private ArrayList<Integer> rollThreeDie;

	// Dice as Subcomponents
	private RegularDie reg;
	private SpeedDie speed;
	private int numDub; // could be static dont know why? check it!!!
	private boolean isDouble;
	private boolean isTriple;

	public ThrowDice() {
		die = new ArrayList<>();
		rollThreeDie = new ArrayList<>();

		reg = new RegularDie();
		speed = new SpeedDie();
		numDub = 0;

	}

	// Getter for die
	public ArrayList<DieFaces> getDie() {
		return die;
	}

	// Helper Methods
	public void forgetDie() {
		die.clear(); // clear the die

	}

	public int getRegularValues() {

		if (die.size() == 3)
			return die.get(0).getValue() + die.get(1).getValue();

		return 0;
	}

	public boolean isMrMonolopy() {
		if (die.size() == 3)
			return die.get(2).getValue() == -1;
		
		return false;
	}
	public boolean isBusIcon() {
		if (die.size() == 3)
			return die.get(2).getValue() == 0;
		
		return false;
	}
	public int getNotSpecialThreeValue() {

		if (die.size() == 3)
			return die.get(0).getValue() + die.get(1).getValue() + die.get(2).getValue();

		return 0;
	}

	public boolean isDouble() {
		if (die.size() == 3 && die.get(0).getValue() == die.get(1).getValue()) {
			return true;
		}
		return false;
	}

	public boolean isTriple() {
		if (die.size() == 3 && die.get(0).getValue() == die.get(1).getValue()
				&& die.get(1).getValue() == die.get(2).getValue()) {
			return true;
		}
		return false;
	}

	public boolean isMyDiceClear() {

		return die.isEmpty();
	}

	public boolean isSpecialDie() {
		return (die.contains(DieFaces.BUS) || die.contains(DieFaces.MRMON));

	}

	/**
	 * @requires
	 * @modifies die
	 * @effects Initializes the die to the given object in param of func
	 */
	public void manuallyDiceConfiguration(ArrayList<DieFaces> myDice) {
		die = myDice;

	}

	public int getNumDub() {
		return numDub;
	}

	public void resetNumDub() {
		this.numDub = 0;
	}

	/**
	 * @requires
	 * @modifies numDub
	 * @effects Increases the number of dublicates
	 */
	public void incNumDub() {
		numDub++;
	}

	public boolean repOk() {

		if (die != null && numDub >= 0 && numDub <= 3) {
			int checker = 0;
			for (int i = 0; i < die.size(); i++) {

				if ((die.get(i).getValue() > 0 && die.get(i).getValue() <= 6 && isValidDie(die.get(i)))
						|| die.get(i).equals(DieFaces.MRMON) || die.get(i).equals(DieFaces.BUS)) {
					checker++;
				}
			}
			return (checker == die.size());
		}

		return false;

	}

	public boolean isValidDie(DieFaces df) {

		return (df.getValue() == 1 || df.getValue() == 2 || df.getValue() == 3 || df.getValue() == 4
				|| df.getValue() == 5 || df.getValue() == 6);
	}

	// Throw and Get the die values

	/**
	 * adds proper values to die list, and returns it
	 * 
	 * 
	 * @return ArrayList (list of domain.elements.DieFaces)
	 */

	public ArrayList<DieFaces> throwAndGet() {

		// REQUIES: die is not null (already initialized)
		// MODIFIES : die (ArrayList)
		// EFFECTS : Adds new die value to die

		die.add(reg.getDieValue());
		die.add(reg.getDieValue());
		die.add(speed.getDieValue());
		return die;

	}

	// Throw and Get the roll3 values
	/**
	 * adds proper roll three values to rollThreeDie list, and returns it
	 * 
	 * 
	 * @return ArrayList rollThreeDie (list of rollThree results)
	 */

	public ArrayList<Integer> roll3ThrowAndGet() {
		// REQUIRES die is not null (already initialized)
		// MODIFIES : die (ArrayList)
		// EFFECTS : Adds new die value to die
		if (die != null) {
			rollThreeDie.add(reg.getDieValue().getValue());
			rollThreeDie.add(reg.getDieValue().getValue());
			rollThreeDie.add(reg.getDieValue().getValue());
			return rollThreeDie;

		}
		return rollThreeDie;
	}

	@Override
	public String toString() {
		return "ThrowDice [die=" + die + ", rollThreeDie=" + rollThreeDie + ", reg=" + reg + ", speed=" + speed + "]";
	}

}