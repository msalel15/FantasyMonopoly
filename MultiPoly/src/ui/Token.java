package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;

public class Token implements Drawable {
	private SquareInfo currentsq;
	private SquareInfo nextSq;
	private Image img;
	private int playerNum;
	ArrayList<SquareInfo> squares;
	private Path myPath;
	private Point myPosition;
	private boolean isMyTurn = false;

	public Token(Image img, Point myPosition, int playerNum) {
		this.img = img;
		this.myPosition = myPosition;
		this.playerNum = playerNum;

	}

	public void Jump(SquareInfo jail) {

		myPosition = new Point(jail.getX(), jail.getY());

	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		if (myPath != null && myPath.hasMoreSteps()) {
			myPosition = myPath.nextPosition();
		} else if (myPath != null && !myPath.hasMoreSteps()) {
			isMyTurn = false;
			myPath = null;

		} else {
			if (isMyTurn) {
				myPath = new TokenLine(squares, playerNum);
				myPosition = myPath.nextPosition();
			}
		}
		g.drawImage(img, myPosition.x, myPosition.y, 20, 20, null);

	}

	public Point getMyPosition() {
		return myPosition;
	}

	public void setMyPosition(Point myPosition) {
		this.myPosition = myPosition;
	}

	public boolean isMyTurn() {
		return isMyTurn;
	}

	public void setMyTurn(boolean isMyTurn) {
		this.isMyTurn = isMyTurn;
	}

}
