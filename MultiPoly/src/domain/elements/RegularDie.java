package domain.elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class RegularDie extends Die {
// No need to instantiate this class with a constructor
	private static final ArrayList<DieFaces> regularList = new ArrayList<DieFaces>(
			Arrays.asList(DieFaces.ONE, DieFaces.TWO, DieFaces.THREE, DieFaces.FOUR, DieFaces.FIVE, DieFaces.SIX));

	// getDieValue method returns a DieFaces object which holds die values
	@Override
	public DieFaces getDieValue() {
		Random dieRand = new Random();
		int die = dieRand.nextInt(6);
		return regularList.get(die);

	}

}
