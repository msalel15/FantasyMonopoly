package domain.square;

import domain.MultiPolyController;

public abstract class Square {

	private int id;
	private String name;
	private Boolean isBuyable;

	public Square(int id, String name, Boolean isBuyable) {
		this.id = id;
		this.name = name;
		this.isBuyable = isBuyable;
	}

	// Getters
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Boolean isBuyable() {
		return isBuyable;
	}

	public void setIsBuyable(Boolean isBuyable) {
		this.isBuyable = isBuyable;
	}

	public abstract void actForSquare(MultiPolyController controller);

	@Override
	public String toString() {
		return "Square [id=" + id + ", name=" + name + "]";
	}

}