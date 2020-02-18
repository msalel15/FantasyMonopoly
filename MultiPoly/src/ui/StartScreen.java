package ui;

import java.awt.Choice;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;

import domain.MultiPolyController;

public class StartScreen extends JPanel implements ActionListener {

	private final String[] numberOfPlayersArr = { "2", "3", "4", "5", "6", "7", "8" };
	private final String[] numberOfClientsArr = { "1", "2" };

	private ArrayList<String> numberOfBotsArr;
	private MultiPolyController controller;
	private GameFrame parent;

	private ArrayList<String> usernames = new ArrayList<String>();
	private JButton onlineGameButton;
	private JButton offlineGameButton;
	private JButton createGameButton;
	private JButton joinGameButton;
	private JButton newGameButton;
	private JButton loadGameButton;
	private JButton quitGameButton;
	private JButton continueButton;
	private JButton mainMenuButton;
	private JComboBox<String> numberOfPlayersList;
	private JComboBox<String> numberOfBotsList;
	private JTextField inputUsernameTextField = new JTextField();

	private int panelWidth;
	private int panelHeight;
	private int numberOfPlayers = -1;
	private int currentPlayerInputIndex = 1;

	private boolean settingsPanel = false;
	private boolean invalidFileName = false;
	private boolean numberOfPlayersSet = false;
	private boolean finalized = false;

	private Color lilac = new Color(203, 159, 207);
	private Color seaGreen = new Color(164, 207, 159);
	private Color blue = new Color(159, 189, 207);
	private Color tan = new Color(207, 177, 159);

	Border roundedBorder = new LineBorder(Color.WHITE, 8, true);

	private int buttonWidth = panelWidth / 4;
	private int buttonHeight = panelHeight / 12;

	private int smallButWidth = 240;
	private int smallButHeight = 30;

	public StartScreen(int panelWidth, int panelHeight) {
		setLayout(null);

		setSize(panelWidth, panelHeight);
		this.panelWidth = panelWidth;
		this.panelHeight = panelHeight;

		buttonWidth = panelWidth / 4;
		buttonHeight = panelHeight / 12;

		setBackground(lilac);

		setVisible(true);
		initButtons();

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (settingsPanel) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Batang", Font.PLAIN, 20));
			if (!numberOfPlayersSet) {
				g.drawString("Choose Number Of Players ", (panelWidth - 256) / 2, (panelHeight - 150) / 2);
			} else {
				for (int i = 0; i < numberOfPlayers; i++) {
					g.drawString(usernames.get(i), (panelWidth - smallButWidth) / 2,
							(panelHeight + 5 * smallButHeight) / 2 + i * 30);
				}

				if (!finalized) {
					g.drawString("Enter Player " + currentPlayerInputIndex + "'s name:",
							(panelWidth - smallButWidth + 20) / 2, panelHeight / 2 - 45);
				} else {
					g.drawString("Enter a name for this MultiPoly Game:", (panelWidth - smallButWidth - 80) / 2,
							panelHeight / 2 - 45);
					if (invalidFileName) {
						g.setColor(Color.red);
						g.drawString("Invalid File Name!", panelWidth / 2 - 75, panelHeight / 2 + 80);
						g.setColor(new Color(59, 89, 182));
					}
					repaint();
				}
			}
		} else {

		}

	}

	public void initAncestor() {
		parent = (GameFrame) SwingUtilities.getWindowAncestor(this);
		controller = parent.getController();
	}

	public void initButtons() {

		System.out.println(panelWidth);
		System.out.println(panelHeight);

		int buttonWidth = panelWidth / 4;
		int buttonHeight = panelHeight / 12;

		offlineGameButton = new JButton("Offline Game");
		offlineGameButton.setBorder(roundedBorder);
		offlineGameButton.setBounds((panelWidth - buttonWidth) / 2, (panelHeight - 4 * buttonHeight) / 2, buttonWidth,
				buttonHeight);
		offlineGameButton.setActionCommand("offlineGame");
		offlineGameButton.addActionListener(this);

		offlineGameButton.setBackground(blue);
		offlineGameButton.setForeground(Color.WHITE);
		offlineGameButton.setFont(new Font("Batang", Font.BOLD, 20));
		offlineGameButton.setOpaque(true);

		onlineGameButton = new JButton("Online Game");
		onlineGameButton.setBorder(roundedBorder);
		onlineGameButton.setBounds((panelWidth - buttonWidth) / 2, (panelHeight - buttonHeight) / 2, buttonWidth,
				buttonHeight);
		onlineGameButton.setActionCommand("onlineGame");
		onlineGameButton.addActionListener(this);

		onlineGameButton.setBackground(seaGreen);
		onlineGameButton.setForeground(Color.WHITE);
		onlineGameButton.setFont(new Font("Batang", Font.BOLD, 20));
		onlineGameButton.setOpaque(true);

		quitGameButton = new JButton("Quit Game");
		quitGameButton.setBorder(roundedBorder);
		quitGameButton.setBounds((panelWidth - buttonWidth) / 2, (panelHeight + 2 * buttonHeight) / 2, buttonWidth,
				buttonHeight);
		quitGameButton.setActionCommand("quitGame");
		quitGameButton.addActionListener(this);

		quitGameButton.setBackground(tan);
		quitGameButton.setForeground(Color.WHITE);
		quitGameButton.setFocusPainted(false);
		quitGameButton.setFont(new Font("Batang", Font.BOLD, 20));
		quitGameButton.setOpaque(true);

		add(offlineGameButton);
		add(onlineGameButton);
		add(quitGameButton);

		repaint();

	}

	public void onlineGameButtons() {

		removeAll();
		int buttonWidth = panelWidth / 4;
		int buttonHeight = panelHeight / 12;

		mainMenuButton = new JButton("Main Menu");
		mainMenuButton.setBounds((int) (getWidth() - buttonWidth), 0, buttonWidth, buttonHeight);
		mainMenuButton.setActionCommand("mainMenu");
		mainMenuButton.addActionListener(this);

		mainMenuButton.setBackground(seaGreen);
		mainMenuButton.setForeground(Color.WHITE);
		mainMenuButton.setFocusPainted(false);
		mainMenuButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		mainMenuButton.setOpaque(true);
		mainMenuButton.setBorderPainted(false);

		createGameButton = new JButton("Create Game");
		createGameButton.setBorder(roundedBorder);
		createGameButton.setBounds((panelWidth - buttonWidth) / 2, (panelHeight - 4 * buttonHeight) / 2, buttonWidth,
				buttonHeight);
		createGameButton.setActionCommand("createGame");
		createGameButton.addActionListener(this);

		createGameButton.setBackground(blue);
		createGameButton.setForeground(Color.WHITE);
		createGameButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		createGameButton.setOpaque(true);

		joinGameButton = new JButton("Join Game");
		joinGameButton.setBorder(roundedBorder);
		joinGameButton.setBounds((panelWidth - buttonWidth) / 2, (panelHeight - buttonHeight) / 2, buttonWidth,
				buttonHeight);
		joinGameButton.setActionCommand("joinGame");
		joinGameButton.addActionListener(this);

		joinGameButton.setBackground(seaGreen);
		joinGameButton.setForeground(Color.WHITE);
		joinGameButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		joinGameButton.setOpaque(true);

		quitGameButton = new JButton("Quit Game");
		quitGameButton.setBorder(roundedBorder);
		quitGameButton.setBounds((panelWidth - buttonWidth) / 2, (panelHeight + 2 * buttonHeight) / 2, buttonWidth,
				buttonHeight);
		quitGameButton.setActionCommand("quitGame");
		quitGameButton.addActionListener(this);

		quitGameButton.setBackground(tan);
		quitGameButton.setForeground(Color.WHITE);
		quitGameButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		quitGameButton.setOpaque(true);

		add(mainMenuButton);
		add(createGameButton);
		add(joinGameButton);
		add(quitGameButton);

		repaint();
	}

	public void gameModeButtons() {

		removeAll();
		System.out.println(panelWidth);
		System.out.println(panelHeight);

		int buttonWidth = panelWidth / 4;
		int buttonHeight = panelHeight / 12;

		mainMenuButton = new JButton("Main Menu");
		mainMenuButton.setBounds((int) (getWidth() - smallButWidth), 0, smallButWidth, smallButHeight);
		mainMenuButton.setActionCommand("mainMenu");
		mainMenuButton.addActionListener(this);

		mainMenuButton.setBackground(new Color(162, 150, 191));
		mainMenuButton.setForeground(Color.WHITE);
		mainMenuButton.setFocusPainted(false);
		mainMenuButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		mainMenuButton.setOpaque(true);
		mainMenuButton.setBorderPainted(false);

		newGameButton = new JButton("New Game");
		newGameButton.setBorder(roundedBorder);
		newGameButton.setBounds((panelWidth - buttonWidth) / 2, (panelHeight - 4 * buttonHeight) / 2, buttonWidth,
				buttonHeight);
		newGameButton.setActionCommand("newGame");
		newGameButton.addActionListener(this);

		newGameButton.setBackground(blue);
		newGameButton.setForeground(Color.WHITE);
		newGameButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		newGameButton.setOpaque(true);

		loadGameButton = new JButton("Load Game");
		loadGameButton.setBorder(roundedBorder);
		loadGameButton.setBounds((panelWidth - buttonWidth) / 2, (panelHeight - buttonHeight) / 2, buttonWidth,
				buttonHeight);
		loadGameButton.setActionCommand("loadGame");
		loadGameButton.addActionListener(this);

		loadGameButton.setBackground(seaGreen);
		loadGameButton.setForeground(Color.WHITE);
		loadGameButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		loadGameButton.setOpaque(true);

		quitGameButton = new JButton("Quit Game");
		quitGameButton.setBorder(roundedBorder);
		quitGameButton.setBounds((panelWidth - buttonWidth) / 2, (panelHeight + 2 * buttonHeight) / 2, buttonWidth,
				buttonHeight);
		quitGameButton.setActionCommand("quitGame");
		quitGameButton.addActionListener(this);

		quitGameButton.setBackground(tan);
		quitGameButton.setForeground(Color.WHITE);
		quitGameButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		quitGameButton.setOpaque(true);

		add(newGameButton);
		add(loadGameButton);
		add(quitGameButton);
		add(mainMenuButton);

		repaint();
	}

	public void numPlayerButtons() {
		removeAll();

		continueButton = new JButton("Continue!");
		continueButton.setBounds(panelWidth / 2 - smallButWidth / 2, panelHeight / 2 - smallButHeight / 2,
				smallButWidth, smallButHeight);
		continueButton.setActionCommand("setNumberOfPlayers");
		continueButton.addActionListener(this);
		continueButton.setBackground(blue);
		continueButton.setForeground(Color.WHITE);
		continueButton.setFocusPainted(false);
		continueButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		continueButton.setOpaque(true);
		continueButton.setBorderPainted(false);

		mainMenuButton = new JButton("Main Menu");
		mainMenuButton.setBounds((int) (getWidth() - smallButWidth), 0, smallButWidth, smallButHeight);
		mainMenuButton.setActionCommand("mainMenu");
		mainMenuButton.addActionListener(this);
		mainMenuButton.setBackground(new Color(162, 150, 191));
		mainMenuButton.setForeground(Color.WHITE);
		mainMenuButton.setFocusPainted(false);
		mainMenuButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		mainMenuButton.setOpaque(true);
		mainMenuButton.setBorderPainted(false);

		numberOfPlayersList = new JComboBox<String>(numberOfPlayersArr);
		numberOfPlayersList.setVisible(true);
		numberOfPlayersList.setSelectedIndex(0);
		numberOfPlayersList.setEditable(false);

		numberOfPlayersList.setBounds(panelWidth / 2 - smallButWidth / 2, panelHeight / 2 - smallButHeight / 2 - 50,
				smallButWidth, smallButHeight);
		numberOfPlayersList.setFont(new Font("Tahoma", Font.BOLD, 12));

		add(numberOfPlayersList);
		add(continueButton);
		add(mainMenuButton);

		revalidate();
		repaint();
	}

	public void numBotButtons() {
		removeAll();

		continueButton = new JButton("Continue!");
		continueButton.setBounds(panelWidth / 2 - smallButWidth / 2, panelHeight / 2 - smallButHeight / 2,
				smallButWidth, smallButHeight);
		continueButton.setActionCommand("setNumberOfBots");
		continueButton.addActionListener(this);
		continueButton.setBackground(blue);
		continueButton.setForeground(Color.WHITE);
		continueButton.setFocusPainted(false);
		continueButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		continueButton.setOpaque(true);
		continueButton.setBorderPainted(false);

		mainMenuButton = new JButton("Main Menu");
		mainMenuButton.setBounds((int) (getWidth() - smallButWidth), 0, smallButWidth, smallButHeight);
		mainMenuButton.setActionCommand("mainMenu");
		mainMenuButton.addActionListener(this);
		mainMenuButton.setBackground(new Color(162, 150, 191));
		mainMenuButton.setForeground(Color.WHITE);
		mainMenuButton.setFocusPainted(false);
		mainMenuButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		mainMenuButton.setOpaque(true);
		mainMenuButton.setBorderPainted(false);
		numberOfBotsList = new JComboBox<String>();
		numberOfBotsArr = new ArrayList<String>();
		for (int i = 0; i < numberOfPlayers; i++) {
			numberOfBotsArr.add("" + i);
		}

		for (String n : numberOfBotsArr) {
			numberOfPlayersList.addItem(n);
		}
		numberOfBotsList.setBounds(panelWidth / 2 - smallButWidth / 2, panelHeight / 2 - smallButHeight / 2 - 50,
				smallButWidth, smallButHeight);
		numberOfBotsList.setFont(new Font("Tahoma", Font.BOLD, 12));

		add(numberOfBotsList);
		add(continueButton);
		add(mainMenuButton);

		revalidate();
		repaint();
	}

	public void initUsernames() {
		for (int i = 0; i < numberOfPlayers; i++) {
			usernames.add("Player " + (i + 1) + ": ");
		}
	}

	public void initUsernameInput() {
		initUsernames();
		inputUsernameTextField.setBounds(panelWidth / 2 - smallButWidth / 2, panelHeight / 2 - smallButHeight / 2 - 15,
				smallButWidth, smallButHeight);
		inputUsernameTextField.setBackground(Color.WHITE);
		inputUsernameTextField.setForeground(blue);
		inputUsernameTextField.setFont(new Font("Tahoma", Font.BOLD, 12));
		add(inputUsernameTextField);

		continueButton = new JButton("Continue!");
		continueButton.setBounds(panelWidth / 2 - smallButWidth / 2,
				panelHeight / 2 - smallButHeight / 2 + smallButHeight, smallButWidth, smallButHeight);
		continueButton.setActionCommand("continueNameInput");
		continueButton.addActionListener(this);
		continueButton.setBackground(blue);
		continueButton.setForeground(Color.WHITE);
		continueButton.setFocusPainted(false);
		continueButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		continueButton.setOpaque(true);
		continueButton.setBorderPainted(false);
		add(continueButton);

		inputUsernameTextField.requestFocus();

	}

	public boolean nameCheck(String name) {
		if (!usernames.contains(name) && name != null && !name.toLowerCase().equals("-") && !name.contains(" ")
				&& name.length() != 0 && name.length() <= 10)
			return true;

		return false;

	}

	public boolean tryToStartGame() {
		String gamename = inputUsernameTextField.getText();
		if (gamename == null) {
			return false;
		}

		Pattern p = Pattern.compile("[^a-zA-Z0-9]");
		boolean hasSpecialChar = p.matcher(gamename).find();

		if (hasSpecialChar || gamename.length() == 0 || gamename.length() > 10) {
			return false;
		}
		parent.getController().setGameName(gamename);
		return true;
		// return parent.controller.isDuplicateGameName(gamename);
	}

	public void loadGameSequence() {
		JFileChooser fileChooser = new JFileChooser("./save");
		fileChooser.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				return null;
			}

			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				} else {
					if (f.getAbsolutePath().toLowerCase().endsWith("txt") && !f.getName().equals("default.txt"))
						return true;
				}

				return false;
			}
		});
		int choice = fileChooser.showOpenDialog(null);

		if (choice == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			if (selectedFile.getAbsolutePath().toLowerCase().endsWith("autosave.txt")) {
				String filename = selectedFile.getName();
				int iend = filename.indexOf("-");
				String gameName = "";
				if (iend != -1) {
					gameName = filename.substring(0, iend);
				}
				parent.getController().setGameName(gameName);
				parent.getController().load();
				parent.switchToGameScreen();
			} else {
				JOptionPane.showMessageDialog(parent, "Selected file is invalid to be loaded!", "Invalid File",
						JOptionPane.WARNING_MESSAGE);
			}

		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {

		case "offlineGame":
			gameModeButtons();
			break;

		case "onlineGame":

		case "quitGame":

		case "mainMenu":
			parent.switchToStartScreen();
			break;
		case "newGame":
			settingsPanel = true;
			numPlayerButtons();
			break;

		case "loadGame":
			loadGameSequence();
			break;

		case "setNumberOfPlayers":
			remove(continueButton);
			remove(numberOfPlayersList);
			numberOfPlayersSet = true;
			numberOfPlayers = (Integer.parseInt((String) numberOfPlayersList.getSelectedItem()));
			initUsernameInput();
			repaint();
			break;
		case "continueNameInput":
			if (nameCheck(inputUsernameTextField.getText())) {
				usernames.set(currentPlayerInputIndex - 1,
						usernames.get(currentPlayerInputIndex - 1) + inputUsernameTextField.getText());
				inputUsernameTextField.setText("");
				currentPlayerInputIndex++;
				if (currentPlayerInputIndex == numberOfPlayers + 1) {
					finalized = true;
					remove(continueButton);
					JButton startButton = new JButton("Start!");
					startButton.setBounds((panelWidth - smallButWidth) / 2, (panelHeight + smallButHeight) / 2,
							smallButWidth, smallButHeight);
					startButton.setActionCommand("start");
					startButton.addActionListener(this);
					startButton.setBackground(blue);
					startButton.setForeground(Color.WHITE);
					startButton.setFocusPainted(false);
					startButton.setFont(new Font("Tahoma", Font.BOLD, 12));
					startButton.setOpaque(true);
					startButton.setBorderPainted(false);
					add(startButton);
				}
			}
			inputUsernameTextField.requestFocus();
			repaint();
			break;
		case "start":

			if (tryToStartGame()) {
//				parent.loadGame("InitialGameState.null");
//				parent.switchToGamePanel(usernames, inputUsernameTextField.getText());
			} else {
				invalidFileName = true;
				repaint();
			}

			repaint();
			parent.switchToGameScreen();
			repaint();
			break;

		}

	}

}
