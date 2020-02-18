package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import domain.MultiPolyController;

public class RightButtonPanel extends JPanel implements ActionListener {
	// Location Related Info
	private int width;
	private int height;

	// Connection to Board
	private BoardPanel board;

	// Connection to Controller
	private MultiPolyController controller;

	// Subcomponents
	private JButton endTurn;
	private JButton rollDice;
	private JButton buy;
	private JButton properties;

	public RightButtonPanel(int width, int height, BoardPanel board) {
		this.width = width;
		this.height = height;
		this.board = board;
		controller = board.getController();

		int xMargin = width / 20;
		int yMargin = height / 20;

		setPanel(xMargin, yMargin);
		addComponents();

	}

	private void setPanel(int xMargin, int yMargin) {
		setLayout(new GridLayout(2, 2, xMargin, yMargin));
		setPreferredSize(new Dimension(width, height));
		setBorder(BorderFactory.createStrokeBorder(new BasicStroke(2.0f)));

	}

	public void setButtonsEnable(boolean enable) {
		endTurn.setEnabled(enable);
		rollDice.setEnabled(enable);
		buy.setEnabled(enable);
		properties.setEnabled(enable);

	}

	private void addComponents() {
		// TODO Auto-generated method stub

		rollDice = new JButton("Roll Dice");

		rollDice.setActionCommand("rollDice");

		rollDice.addActionListener(this);
		// rollDice.setEnabled(false);
		/*
		 * rollDice.addMouseListener(board); rollDice.addMouseMotionListener(board);
		 */

		rollDice.setBackground(new Color(89, 156, 219));
		rollDice.setForeground(new Color(234, 238, 247));
		// endTurn.setFocusPainted(false);
		rollDice.setFont(new Font("Tahoma", Font.BOLD, 12));
		/*
		 * board.buttons.put("endTurn", endTurnButton); endTurn.setOpaque(true);
		 * endTurn.setBorderPainted(false);
		 */
		add(rollDice);

		buy = new JButton("Buy");

		buy.setActionCommand("buy");
		buy.addActionListener(this);

		/*
		 * buy.setEnabled(false); buy.addMouseListener(board);
		 * buy.addMouseMotionListener(board);
		 */
		buy.setBackground(new Color(89, 156, 219));
		buy.setForeground(new Color(234, 238, 247));
		// buy.setFocusPainted(false);
		buy.setFont(new Font("Tahoma", Font.BOLD, 12));
		/*
		 * board.buttons.put("endTurn", endTurnButton); endTurn.setOpaque(true);
		 * endTurn.setBorderPainted(false);
		 */
		add(buy);

		properties = new JButton("Properties");

		properties.setActionCommand("properties");
		 properties.addActionListener(this);
		 /*properties.setEnabled(false);
		 * properties.addMouseListener(board); properties.addMouseMotionListener(board);
		 */
		properties.setBackground(new Color(89, 156, 219));
		properties.setForeground(new Color(234, 238, 247));
		// buy.setFocusPainted(false);
		properties.setFont(new Font("Tahoma", Font.BOLD, 12));
		/*
		 * board.buttons.put("endTurn", endTurnButton); properties.setOpaque(true);
		 * properties.setBorderPainted(false);
		 */
		add(properties);

		endTurn = new JButton("End Turn");

		endTurn.setActionCommand("endTurn");
		endTurn.addActionListener(this);
		/*
		 * endTurn.setEnabled(false); endTurn.addMouseListener(board);
		 * endTurn.addMouseMotionListener(board);
		 */
		endTurn.setBackground(new Color(89, 156, 219));
		endTurn.setForeground(new Color(234, 238, 247));
		// endTurn.setFocusPainted(false);
		endTurn.setFont(new Font("Tahoma", Font.BOLD, 12));
		/*
		 * board.buttons.put("endTurn", endTurnButton); endTurn.setOpaque(true);
		 * endTurn.setBorderPainted(false);
		 */
		add(endTurn);

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

	public JButton getEndTurn() {
		return endTurn;
	}

	public void setEndTurn(JButton endTurn) {
		this.endTurn = endTurn;
	}

	public JButton getRollDice() {
		return rollDice;
	}

	public void setRollDice(JButton rollDice) {
		this.rollDice = rollDice;
	}

	public JButton getBuy() {
		return buy;
	}

	public void setBuy(JButton buy) {
		this.buy = buy;
	}

	public JButton getProperties() {
		return properties;
	}

	public void setProperties(JButton properties) {
		this.properties = properties;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getActionCommand()) {
		case "rollDice":
			controller.throwAndMove(); 
			break;
		case "rollFree":
			controller.throwAndMove(); 
			buy.setText("Buy");
			buy.setActionCommand("buy");
			rollDice.setText("Roll Dice");
			rollDice.setActionCommand("rollDice");
			break;
		case "buy":
			controller.buy();
			break;
		case "pay":
			controller.payPenalty();
			buy.setText("Buy");
			buy.setActionCommand("buy");
			rollDice.setText("Roll Dice");
			rollDice.setActionCommand("rollDice");
			break;	
		case "endTurn":
			controller.endTurn();
			break;
		case "properties":
			controller.showProperties();
			break;

		}
		board.getFrame().getIpanel().displayPlayerInfo(controller.getPlayerInfo());
		board.getFrame().getIpanel().displayGameInfo(controller.getGameFlow());
		repaint();
	

	}

}
