package domain.elements;

import domain.square.Square;

public class Piece {

	// Square's location
	private Square location;

	public Piece(Square location) {
		setLocation(location);
	}

	public Square getLocation() {
		return location;
	}

	public void setLocation(Square location) {
		this.location = location;
	}

}
