package ui;

import java.awt.Image;

public class SquareInfo {

	// Location-Related Info
	private int x; // x-coordinate on the field
	private int y; // y-coordinate on the field

	// Image Related Info
	private int width;
	private int height;
	private Image img;
	boolean isCorner;
	double moveSize;
	// Player Location Related Info
	private int index;

	// Constructor Using Fields
	public SquareInfo(int index, int x, int y, int width, int height, Image img, boolean isCorner, double moveSize) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.img = img;
		this.isCorner = isCorner;
		this.moveSize = moveSize;
		// index-based implementation may differ
		this.index = index;
	}

	// Getters & Setters
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
