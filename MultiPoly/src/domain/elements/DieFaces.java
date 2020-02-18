package domain.elements;

public enum DieFaces {
	BUS(0), MRMON(-1), ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6);

	private final int faceValue;

	DieFaces(int faceValue) {
		this.faceValue = faceValue;
	}

	public int getValue() {
		return faceValue;
	}

}
