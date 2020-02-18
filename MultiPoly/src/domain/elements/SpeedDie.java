package domain.elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class SpeedDie extends Die {

	ArrayList<DieFaces> speedList = new ArrayList<DieFaces>
	// (Arrays.asList(DieFaces.ONE,DieFaces.TWO,DieFaces.THREE,DieFaces.FOUR,DieFaces.MRMON,DieFaces.BUS));
//	(Arrays.asList(DieFaces.MRMON, DieFaces.MRMON, DieFaces.ONE, DieFaces.TWO, DieFaces.THREE, DieFaces.SIX));
	(Arrays.asList(DieFaces.BUS,DieFaces.BUS,DieFaces.BUS,DieFaces.BUS,DieFaces.BUS,DieFaces.BUS));
	// getDieValue method returns a DieFaces object which holds die values
	@Override
	public DieFaces getDieValue() {

		Random rand = new Random();
		int index = rand.nextInt(6);

		return speedList.get(index);

	}

}
