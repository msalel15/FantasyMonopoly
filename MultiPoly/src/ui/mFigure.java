package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

class Mfigure /* implements Drawable */ {

	private boolean changeTrack;
	private boolean direction;
	private SquareInfo currentsq;
	private int playerNum;
	private Image img;
	private SquareInfo nextSq;
	private boolean mv = false;
	private int xPos;
	private int yPos;
	private SquareInfo middleSq;
	double vx;
	double vy;
	int counterX;
	int currentId;
	ArrayList<SquareInfo> squares;
	BoardPanel panel;
	private Point newPoint;
//	Timer timer = new Timer(); //timer for animation
	private static final int animationSpeed = 500; // passed as milliseconds, determines animation speed

	public Mfigure(boolean changeTrack, boolean direction, SquareInfo currentsq, int playerNum, Image img,
			ArrayList<SquareInfo> squares, BoardPanel panel) {
		super();
		this.changeTrack = changeTrack;
		this.direction = direction;
		this.currentsq = currentsq;
		this.img = img;
		this.playerNum = playerNum;
		this.middleSq = currentsq;
		counterX = 0;
		currentId = currentsq.getIndex();
		this.squares = squares;
		this.panel = panel;
		placeFigure();

	}

	private void placeFigure() {
		// TODO Auto-generated method stub
		if (playerNum % 2 == 0) {
			xPos = currentsq.getX() + currentsq.getWidth() / 7;
			yPos = currentsq.getY() + currentsq.getHeight() / 8 * (playerNum / 2) + currentsq.getHeight() / 8;

		} else {
			xPos = currentsq.getX() + 4 * currentsq.getWidth() / 7;
			yPos = currentsq.getY() + currentsq.getHeight() / 8 * (playerNum / 2) + currentsq.getHeight() / 8;

		}
	}


	

	public boolean isChangeTrack() {
		return changeTrack;
	}

	public void setChangeTrack(boolean changeTrack) {
		this.changeTrack = changeTrack;
	}

	public boolean isDirection() {
		return direction;
	}

	public void setDirection(boolean direction) {
		this.direction = direction;
	}

	public SquareInfo getCurrentsq() {
		return currentsq;
	}

	public void setCurrentsq(SquareInfo currentsq) {
		this.currentsq = currentsq;
	}

	public int getPlayerNum() {
		return playerNum;
	}

	public void setPlayerNum(int playerNum) {
		this.playerNum = playerNum;
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	public SquareInfo getNextSq() {
		return nextSq;
	}

	public void setNextSq(SquareInfo nextSq) {
		this.nextSq = nextSq;
	}

	public boolean isMv() {
		return mv;
	}

	public void setMv(boolean mv) {
		this.mv = mv;
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public SquareInfo getMiddleSq() {
		return middleSq;
	}

	public void setMiddleSq(SquareInfo middleSq) {
		this.middleSq = middleSq;
	}

	public double getVx() {
		return vx;
	}

	public void setVx(int vx) {
		this.vx = vx;
	}

	public double getVy() {
		return vy;
	}

	public void setVy(int vy) {
		this.vy = vy;
	}

	public int getCounterX() {
		return counterX;
	}

	public void setCounterX(int counterX) {
		this.counterX = counterX;
	}

//	TODO
	public void jump(SquareInfo next) {
		currentsq = next;
		placeFigure();
		synchronized (panel) {
			panel.setAnimatorStopped(true);
			panel.notify();
		}
	}
	
	public void animatedMove(int x, int y) {
		this.xPos = x;
		this.yPos = y;
		placeFigure();
	}

	public void sameTrackMove(SquareInfo next) {

		vx = squares.get(middleSq.getIndex() + 1).getWidth() / 60.0;
		vy = squares.get(middleSq.getIndex() + 1).getHeight() / 60.0;

		if (middleSq.getIndex() == next.getIndex()) {
			vx = 0;
			vy = 0;
			this.currentsq = squares.get(next.getIndex());
			synchronized (panel) {
				panel.setAnimatorStopped(true);
				panel.notify();
			}
		} else {
			if ((currentId >= 0 && currentId < 14) || (currentId >= 56 && currentId < 66)
					|| (currentId >= 96 && currentId < 102)) {

				xPos += vx;
			} else if ((currentId >= 14 && currentId < 28) || (currentId >= 66 && currentId < 76)
					|| (currentId >= 102 && currentId < 108)) {
				yPos += vy;
			} else if ((currentId >= 28 && currentId < 42) || (currentId >= 76 && currentId < 86)
					|| (currentId >= 108 && currentId < 114)) {
				xPos -= vx;
			} else if ((currentId >= 42 && currentId < 56) || (currentId >= 86 && currentId < 96)
					|| (currentId >= 114 && currentId < 120)) {
				yPos -= vy;
			}
			counterX++;
			if (counterX == 50) {
				counterX = 0;
				if (currentId == 55) {
					middleSq = squares.get(0);
				} else if (currentId == 95) {
					System.out.println("False");
					middleSq = squares.get(56);
				} else if (currentId == 119) {

					middleSq = squares.get(96);

				} else {

					middleSq = squares.get(middleSq.getIndex() + 1);
				}

				currentId = middleSq.getIndex();
			}

		}

	}

	public boolean isInner(SquareInfo sq) {
		if (sq.getIndex() >= 96 && sq.getIndex() <= 119) {
			return true;
		}
		return false;
	}

	public boolean isOuter(SquareInfo sq) {
		if (sq.getIndex() >= 0 && sq.getIndex() <= 55) {
			return true;
		}
		return false;
	}

	public boolean isMedium(SquareInfo sq) {
		if (sq.getIndex() >= 56 && sq.getIndex() <= 95) {
			return true;
		}
		return false;
	}

	public boolean isSameTrack(SquareInfo sq1, SquareInfo sq2) {
		if (isInner(sq1) && isInner(sq2)) {
			return true;

		} else if (isMedium(sq1) && isMedium(sq2)) {
			return true;

		} else if (isOuter(sq1) && isOuter(sq2)) {
			return true;

		}

		return false;
	}

}