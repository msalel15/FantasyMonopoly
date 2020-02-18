package ui;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Insets;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import domain.MultiPolyController;

public class LeftInfoPanel extends JPanel {

	// Size related info
	private int width;
	private int height;

	// Related Components
	private MultiPolyController controller;

	// Sub-components
	private JTextArea playerInfo;
	private JTextArea gameInfo;

	public LeftInfoPanel(int width, int height, BoardPanel board) {
		this.width = width;
		this.height = height;
		int xMargin = width / 20;
		int yMargin = height / 20;

		controller = board.getController();
		setPreferredSize(new Dimension(width, height));

		setBorder(BorderFactory.createStrokeBorder(new BasicStroke(5.0f)));

		playerInfo = new JTextArea("Players\n");
		playerInfo.setBackground(new Color(10, 77, 143));
		playerInfo.setForeground(new Color(234, 238, 247));
		playerInfo.setFont(new Font("Calibri", Font.PLAIN, 16));
		playerInfo.setLineWrap(true);
		playerInfo.setWrapStyleWord(true);
		playerInfo.setEditable(false);
		playerInfo.setBounds(50, 50, width, height);
		playerInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JScrollPane scrollPanePlayer = new JScrollPane(playerInfo);
		scrollPanePlayer.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPanePlayer.setPreferredSize(new Dimension(width - xMargin, height / 3 - yMargin / 2));
		add(scrollPanePlayer);

		gameInfo = new JTextArea();
		gameInfo.setBackground(new Color(89, 156, 219));
		gameInfo.setForeground(new Color(234, 238, 247));
		gameInfo.setFont(new Font("Calibri", Font.PLAIN, 16));
		gameInfo.setLineWrap(true);
		gameInfo.setWrapStyleWord(true);
		gameInfo.setEditable(false);
		gameInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		gameInfo.setBounds(0, 0, width - 2 * xMargin, height - 2 * yMargin);
		JScrollPane scrollPaneGame = new JScrollPane(gameInfo);
		scrollPaneGame.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneGame.setPreferredSize(new Dimension(width - xMargin, 2 * height / 3));
		add(scrollPaneGame);

	}

	public void displayPlayerInfo(ArrayList<String> ar) {

		playerInfo.setText("Players\n");
		String str;
		StringTokenizer token;
		String playerName;
		String funds;
		for (int i = 0; i < ar.size(); i++) {
			str = ar.get(i);
			token = new StringTokenizer(str, ":");
			playerName = token.nextToken();
			funds = token.nextToken();
			playerInfo.append(playerName + "\t" + funds + "\n");
		}
	}

	public void displayGameInfo(ArrayList<String> ar) {
		gameInfo.setText("");
		for (int i = 0; i < ar.size(); i++) {
			gameInfo.append(ar.get(i) + "\n");
		}
	}

}
