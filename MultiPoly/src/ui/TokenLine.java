package ui;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Purpose: The Path class defines a starting position, stopping position, and
 * number of steps. It then calculates the next position that would occur along
 * the path. It is first used in Program 7.1, but is used in all the animation
 * examples, and is an essential part to the final aniamtor.
 * 
 * Note that in this case it is a simple straight line, but the path could be a
 * spline curve, or Bessel function, etc.
 */

public class TokenLine implements Path {


	int steps = 3;
	int currentStep = -1;
	private int nextsq = 1;
	private int currentsq = 0;
	private SquareInfo finalsq;
	private int index;
	int startX, startY, endX, endY;
	double deltaX, deltaY;
	private ArrayList<SquareInfo> squares;
	int currentX, currentY;

	public TokenLine(ArrayList<SquareInfo> squares, int index) {
		this.squares = squares;
		this.index = index;
		this.finalsq = squares.get(squares.size() - 1);
		calculatePos();
	}

	private void calculatePos() {
		startX = squares.get(currentsq).getX();
		startY = squares.get(currentsq).getY();
		endX = squares.get(nextsq).getX();
		endY = squares.get(nextsq).getY();
		deltaX = ((double) (endX - startX)) / steps;
		deltaY = ((double) (endY - startY)) / steps;

	}

	public boolean hasMoreSteps() {
		if (currentX == finalsq.getX() && currentY == finalsq.getY())
			return false;
		return true;
	}

	public Point nextPosition() {
		currentStep++;
		if (currentStep > steps) {
			if (hasMoreSteps()) {
				currentStep = 0;
				currentsq++;
				nextsq++;
				calculatePos();
			} else {
				return new Point(endX, endY);
			}
		}
		currentX = (int) (startX + (deltaX * currentStep));
		currentY = (int) (startY + (deltaY * currentStep));
		return new Point((int) (startX + (deltaX * currentStep)), (int) (startY + (deltaY * currentStep)));
	}
}
