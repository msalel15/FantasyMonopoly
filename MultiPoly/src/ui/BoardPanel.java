package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

import domain.MultiPolyController;

public class BoardPanel extends JPanel implements Runnable, ActionListener {
	// Size related info
	private int frameHeight;
	// Related components
	private GameFrame frame; // as upper level
	private MultiPolyController controller; // as controller from domain

	// Animator controller
	private boolean isAnimatorStopped;

	// Current Player Number
	private int currentPlayerNum;

	// Card Id for Card Data
	private int cardId;

	// Data
	private ArrayList<SquareInfo> squares; // square data
	private ArrayList<Token> tokens; // figure data
	private SquareInfo nex; // square data
	private ArrayList<Integer> diceResults;

	// Sub-components
	private JButton playCard;

	private Vector elementsToDraw = new Vector();
	private long sleepTime = 5;
	

	private static final String defaultDir = System.getProperty("user.dir"); // default directory

	public BoardPanel(int frameHeight, GameFrame frame) {
		this.frameHeight = frameHeight;
		this.frame = frame;
		controller = frame.getController();

		isAnimatorStopped = true;
		currentPlayerNum = 0;
		cardId = -1;

		squares = new ArrayList<>();
		tokens = new ArrayList<>();
		nex = null; // to be set
		diceResults = controller.getDiceValues();

		playCard = null;

		loadBoardComponents();
		loadTokens(controller.getPlayers().size());

		addButtons();

		(new Thread(this)).start();

	}

	public ArrayList<SquareInfo> generateSquareList(ArrayList<Integer> list) {

		ArrayList<SquareInfo> sqInflist = new ArrayList<SquareInfo>();
		for (int i = 0; i < list.size(); i++) {
			sqInflist.add(squares.get(list.get(i)));

		}

		return sqInflist;
	}

	private void addButtons() {
		int buttonWidth = frameHeight / 17;
		int buttonHeight = frameHeight / 17;

		playCard = new JButton("Play Card");
		playCard.setBounds(8 * buttonWidth, 6 * buttonHeight, buttonWidth, buttonHeight);
		playCard.setActionCommand("playCard");
		playCard.addActionListener(this);
//		playCard.setVisible(false);

		add(playCard);
	}

	private void loadTokens(int size) {
		Image figimg = null;
		for (int i = 0; i < size; i++) {
			try {
				figimg = ImageIO.read(new File(defaultDir + "/components/figures/" + i + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			SquareInfo currentSq = squares.get(controller.getPlayers().get(i).getPiece().getLocation().getId());
			Token token = new Token(figimg, new Point(currentSq.getX(), currentSq.getY()), i);
			tokens.add(token);
			addDrawable(token);
		}
	}

	public void run() {
		while (true) {
			try {
				synchronized (this) {
					if (isAnimatorStopped == true) {
						wait();
					}
				}
				if (isAnimatorStopped != true)
					Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				System.out.println("Program Interrupted");
				System.exit(0);
			}
			repaint();
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		drawBoardComponents(g);
		drawCards(g);
		drawDie(g);
		Enumeration e = elementsToDraw.elements();
		while (e.hasMoreElements())
			((Drawable) e.nextElement()).draw(g);
	}

	public void addDrawable(Drawable d) {
		elementsToDraw.addElement(d);
	}

	public void removeDrawable(Drawable d) {
		elementsToDraw.removeElement(d);
	}

	private void drawDie(Graphics g) {

		int width = frameHeight / 17;
		int margin = width / 2;
		int xPos = 6 * width;
		int yPos = 9 * width;
		Image imgDieOne = null;
		Image imgDieTwo = null;
		Image imgDieThree = null;

		try {
			if (diceResults.size() > 0) {
				imgDieOne = ImageIO.read(new File(defaultDir + "/components/regdie/" + diceResults.get(0) + ".png"));
				imgDieTwo = ImageIO.read(new File(defaultDir + "/components/regdie/" + diceResults.get(1) + ".png"));
				imgDieThree = ImageIO
						.read(new File(defaultDir + "/components/speedDie/" + diceResults.get(2) + ".png"));
			} else {
				imgDieOne = ImageIO.read(new File(defaultDir + "/components/board/empty.jpg"));
				imgDieTwo = ImageIO.read(new File(defaultDir + "/components/board/empty.jpg"));
				imgDieThree = ImageIO.read(new File(defaultDir + "/components/board/empty.jpg"));

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		imgDieOne = imgDieOne.getScaledInstance(width, width, Image.SCALE_SMOOTH);
		imgDieTwo = imgDieTwo.getScaledInstance(width, width, Image.SCALE_SMOOTH);
		imgDieThree = imgDieThree.getScaledInstance(width, width, Image.SCALE_SMOOTH);

		g.drawImage(imgDieOne, xPos + margin, yPos, width, width, null);
		g.drawImage(imgDieTwo, xPos + 2 * margin + width, yPos, width, width, null);
		g.drawImage(imgDieThree, xPos + 3 * margin + 2 * width, yPos, width, width, null);

	}

	private void drawBoardComponents(Graphics g) {

		for (int i = 0; i < squares.size(); i++) {
			SquareInfo s = squares.get(i);
			g.drawImage(s.getImg(), s.getX(), s.getY(), s.getWidth(), s.getHeight(), null);
			g.drawString("" + s.getIndex(), s.getX() + 10, s.getY() + 10);

		}

	}

	public void drawCards(Graphics g) {
		int width = frameHeight / 17;
		int cardWidth = 2 * width;
		int cardHeight = 7 * width / 6;
		int margin = width / 3;
		Image imgchest = null;
		Image imgchance = null;

		if (cardId == -1) {
			playCard.setVisible(false);
			try {
				imgchest = ImageIO.read(new File(defaultDir + "/components/cards/" + "comChest.jpg"));
				imgchance = ImageIO.read(new File(defaultDir + "/components/cards/" + "chance.JPG"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			imgchest = imgchest.getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH);
			imgchance = imgchance.getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH);
			g.drawImage(imgchest, 6 * width + margin, 6 * width + margin, cardWidth, cardHeight, null);
			g.drawImage(imgchance, 6 * width + 2 * margin + cardWidth, 6 * width + margin, cardWidth, cardHeight, null);
		} else {
			playCard.setVisible(true);
			try {
				imgchest = ImageIO.read(new File(defaultDir + "/components/cards/" + cardId + ".png"));

			} catch (IOException e) {
				e.printStackTrace();
			}
			margin = width;
			cardWidth = 4 * width;
			cardHeight = 2 * width;
			imgchest = imgchest.getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH);
			g.drawImage(imgchest, 6 * width, 5 * width, cardWidth, cardHeight, null);
		}

	}

	private void loadBoardComponents() {
		squares = new ArrayList<SquareInfo>();
		int track = 14;
		int xCoor = 0, yCoor = 0;
		int width = frameHeight / 17;
		int height = 2 * width;
		int deltaVar = width; // fHeight;
		int start = 0;
		Image img = null;

		for (int i = 0; i < 120; i++) {
			try {
				img = ImageIO.read(new File(defaultDir + "/components/board/" + i + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}

			if ((i >= start) && (i < start + track)) {

				if ((i - start) % track == 0) {
					img = img.getScaledInstance(height, height, Image.SCALE_SMOOTH);
					squares.add(new SquareInfo(i, xCoor, yCoor, height, height, img, true, 3 * width / 2.0));

					xCoor += 2 * deltaVar;
				} else {
					img = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
					squares.add(new SquareInfo(i, xCoor, yCoor, width, height, img, false, width));

					xCoor += deltaVar;
				}
			} else if ((i >= start + track) && (i < start + 2 * track)) {

				if ((i - start) % track == 0) {
					img = img.getScaledInstance(height, height, Image.SCALE_SMOOTH);
					squares.add(new SquareInfo(i, xCoor, yCoor, height, height, img, true, 3 * width / 2.0));

					yCoor += 2 * deltaVar;
				} else {
					img = img.getScaledInstance(height, width, Image.SCALE_SMOOTH);
					squares.add(new SquareInfo(i, xCoor, yCoor, height, width, img, false, width));

					yCoor += deltaVar;
				}
			} else if ((i >= start + 2 * track) && (i < start + 3 * track)) {
				if ((i - start) % track == 0) {
					img = img.getScaledInstance(height, height, Image.SCALE_SMOOTH);
					squares.add(new SquareInfo(i, xCoor, yCoor, height, height, img, true, 3 * width / 2.0));

					xCoor -= deltaVar;
				} else if ((i - start) % track == (track - 1)) {
					img = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
					squares.add(new SquareInfo(i, xCoor, yCoor, width, height, img, false, width));

					xCoor -= 2 * deltaVar;
				} else {
					img = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
					squares.add(new SquareInfo(i, xCoor, yCoor, width, height, img, false, width));

					xCoor -= deltaVar;

				}

			} else if ((i >= start + 3 * track) && (i < start + 4 * track)) {

				if ((i - start) % track == 0) {
					img = img.getScaledInstance(height, height, Image.SCALE_SMOOTH);
					squares.add(new SquareInfo(i, xCoor, yCoor, height, height, img, true, 3 * width / 2.0));

					yCoor -= deltaVar;
				} else {
					img = img.getScaledInstance(height, width, Image.SCALE_SMOOTH);
					squares.add(new SquareInfo(i, xCoor, yCoor, height, width, img, false, width));

					yCoor -= deltaVar;
				}
			}

			if (i == 55) {
				start = 56;
				track = 10;
				xCoor = 2 * width;
				yCoor = 2 * width;
			} else if (i == 95) {
				start = 96;
				track = 6;
				xCoor = 4 * width;
				yCoor = 4 * width;
			}

		}

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch (arg0.getActionCommand()) {
		case "playCard":
			controller.useCard(cardId);
			cardId = -1;
			frame.getCpanel().getButtonPanel().setButtonsEnable(true);
			break;

		}
		getFrame().getIpanel().displayPlayerInfo(controller.getPlayerInfo());
		getFrame().getIpanel().displayGameInfo(controller.getGameFlow());
		repaint();

	}

	public ArrayList<Token> getTokens() {
		return tokens;
	}

	public void setTokens(ArrayList<Token> tokens) {
		this.tokens = tokens;
	}

	public int getCardId() {
		return cardId;
	}

	public GameFrame getFrame() {
		return frame;
	}

	public void setCardId(int cardId) {
		this.cardId = cardId;
	}

	public void setCurrentPlayerNum(int currentPlayerNum) {
		this.currentPlayerNum = currentPlayerNum;
	}

	public void setNex(SquareInfo nex) {
		this.nex = nex;
	}

	public void setAnimatorStopped(boolean isAnimatorStopped) {
		this.isAnimatorStopped = isAnimatorStopped;
	}

	public ArrayList<SquareInfo> getSquares() {
		return squares;
	}

	public MultiPolyController getController() {
		return controller;
	}

	public void setDiceResults(ArrayList<Integer> diceResults) {
		this.diceResults = diceResults;
	}

	public int getCurrentPlayerNum() {
		return currentPlayerNum;
	}

}
