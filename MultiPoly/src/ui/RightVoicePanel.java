package ui;

import java.awt.BasicStroke;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;


import domain.MultiPolyController;

public class RightVoicePanel extends JPanel implements ActionListener {

	// Location Related Info
	Image img = null;
	private boolean isTittleDeed = false;
	private int width;
	private int height;
	private Choice properties;
	private int currentTittleDeed;
	private JButton mrMonopolyButton;
	private JButton choose;
	private JButton upgradeButton;
	private JButton quitGameButton;
	private JButton busIconButton;
	private JTextArea tittleDeedInfo;
	private JButton pause;
	private ArrayList<String> propertiesInfo;
	private boolean isCard = false;
	private int cardId = 0;
	private JButton playCardButton;
	// Connection to Controller
	private MultiPolyController controller;
	private static final String defaultDir = System.getProperty("user.dir");

	public RightVoicePanel(int width, int height, BoardPanel board) {
		setLayout(null);
		this.width = width;
		this.height = height;
		controller = board.getController();
		setPanel();
		loadPermenantButtons();
	}

	public void ViewCard(int cardIndex) {
		int buttonWidth = width / 2;
		int buttonHeight = height / 6;
		
		cardId = cardIndex;
		try {
			img = ImageIO.read(new File(defaultDir + "/components/cards/" + cardIndex + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		isCard = true;
	
		playCardButton.setEnabled(true);
		repaint();
	}

	private void loadPermenantButtons() {
		int buttonWidth = width / 2;
		int buttonHeight = height / 12;
		// TODO Auto-generated method stub

		quitGameButton = new JButton("Quit Game");
		quitGameButton.setBounds((int) (width - buttonWidth), 0, buttonWidth, buttonHeight);
		quitGameButton.setActionCommand("quitGame");
		quitGameButton.addActionListener(this);
		quitGameButton.setForeground(Color.black);
		quitGameButton.setFocusPainted(false);
		quitGameButton.setFont(new Font("Batang", Font.BOLD, 15));
		quitGameButton.setOpaque(true);
		add(quitGameButton);

		pause = new JButton("Pause Game");
		pause.setBounds(0, 0, buttonWidth, buttonHeight);
		pause.setActionCommand("pauseGame");
		pause.addActionListener(this);
		pause.setForeground(Color.black);
		pause.setFocusPainted(false);
		pause.setFont(new Font("Batang", Font.BOLD, 15));
		pause.setOpaque(true);
		add(pause);

		
		playCardButton = new JButton("Play Card");
		playCardButton.setBounds(0, 5 * height / 6, buttonWidth, buttonHeight);
		playCardButton.setActionCommand("playCard");
		playCardButton.addActionListener(this);
		playCardButton.setForeground(Color.black);
		playCardButton.setFocusPainted(false);
		playCardButton.setFont(new Font("Batang", Font.BOLD, 10));
		playCardButton.setOpaque(true);
		playCardButton.setEnabled(false);
		add(playCardButton);
		
		mrMonopolyButton = new JButton("Mr.Monopoly Move");
		mrMonopolyButton.setBounds(buttonWidth , 5 * height / 6, buttonWidth, buttonHeight);
		mrMonopolyButton.setActionCommand("mrMonopolyMove");
		mrMonopolyButton.addActionListener(this);
		mrMonopolyButton.setForeground(Color.black);
		mrMonopolyButton.setFocusPainted(false);
		mrMonopolyButton.setFont(new Font("Batang", Font.BOLD, 10));
		mrMonopolyButton.setOpaque(true);
		mrMonopolyButton.setEnabled(false);
		add(mrMonopolyButton);
		
		busIconButton = new JButton("Bus Icon Move");
		busIconButton.setBounds(0, 5 * height / 6+height/12, buttonWidth, buttonHeight);
		busIconButton.setActionCommand("busMove");
		busIconButton.addActionListener(this);
		busIconButton.setForeground(Color.black);
		busIconButton.setFocusPainted(false);
		busIconButton.setFont(new Font("Batang", Font.BOLD, 10));
		busIconButton.setOpaque(true);
		busIconButton.setEnabled(false);
		add(busIconButton);
		
		upgradeButton = new JButton("Upgrade !!");
		upgradeButton.setBounds(buttonWidth,5 * height / 6+height/12, buttonWidth, buttonHeight);
		upgradeButton.setActionCommand("upgrade");
		upgradeButton.addActionListener(this);
		upgradeButton.setForeground(Color.black);
		upgradeButton.setFocusPainted(false);
		upgradeButton.setFont(new Font("Batang", Font.BOLD, 15));
		upgradeButton.setOpaque(true);
		upgradeButton.setEnabled(false);
		add(upgradeButton);
		
		
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getActionCommand()) {
		case "quitGame":
			controller.quitGame();
			break;
		case "pauseGame":
			controller.pauseGame();
			// change here later
			controller.load();
			System.out.println("Loaded");			
			//
			break;
		case "upgrade":
			controller.upgradeProperty();
			

			break;
		case "choose":
			String[] index = properties.getSelectedItem().split(" ");
			controller.showTitleDeed(Integer.parseInt(index[0]));
			break;
		case "busMove":
			String[] ind2 = properties.getSelectedItem().split(" ");
			controller.setSquareIdFromPlayer(Integer.parseInt(ind2[0]));
			controller.getMovementHandle().moveWithBusIcon();
			
			break;

		case "mrMonopolyMove":
			controller.getMovementHandle().moveWithMrMonopoly();
			mrMonopolyButton.setEnabled(false);
			

			break;
		case "resumeGame":
			controller.resumeGame();
			break;
		case "playCard":
			controller.useCard(cardId);
			cardId = -1;
			

			break;

		}

	}

	public void clear() {
		if (properties != null)
			remove(properties);
		properties = null;
		isTittleDeed = false;
		if (playCardButton != null)
			playCardButton.setEnabled(false);
		if (tittleDeedInfo != null)
			remove(tittleDeedInfo);
		if (upgradeButton != null)
			upgradeButton.setEnabled(false);
		if (busIconButton != null)
			busIconButton.setEnabled(false);

		if (mrMonopolyButton != null)
			mrMonopolyButton.setEnabled(false);
			

		if (choose != null)
			remove(choose);
	}

	public void MrMonopolyMode() {
		int buttonWidth = width / 2;
		int buttonHeight = height / 6;
		
		if(!isCard)
		mrMonopolyButton.setEnabled(true);
		if(isCard) playCardButton.setEnabled(true);
		repaint();
	}

	public void BusIconMode() {
		
		int buttonWidth = width / 2;
		int buttonHeight = height / 6;
		
		if(!isCard) {
		properties = new Choice();
		if (propertiesInfo != null) {
			for (String n : propertiesInfo) {
				properties.add(n);
			}
		}
		properties.setBounds((int) 0, height / 6 + 10, width, height / 12);
		properties.setFont(new Font("Tahoma", Font.BOLD, 12));
		add(properties);
		
		busIconButton.setEnabled(true);
		}else if(isCard) {
			playCardButton.setEnabled(true);
		}
		
		repaint();

	}

	public void ViewPropertiesWithChoose() {
		
		choose = new JButton("Choose");
		choose.setBounds((int) 2 * width / 3, height / 6, width / 3, height / 12);
		choose.setActionCommand("choose");
		choose.addActionListener(this);
		choose.setForeground(Color.black);
		choose.setFocusPainted(false);
		choose.setFont(new Font("Batang", Font.BOLD, 10));
		choose.setOpaque(true);
		add(choose);
		properties = new Choice();
		if (propertiesInfo != null) {
			for (String n : propertiesInfo) {
				properties.add(n);
			}
		}
		properties.setBounds((int) 0, height / 6 + 10, 2 * width / 3, height / 12);
		properties.setFont(new Font("Tahoma", Font.BOLD, 12));
		add(properties);
		repaint();

	}

	public void ViewTitleDeed(int squareIndex, ArrayList<String> titleDeedInfo) {
		
		ViewPropertiesWithChoose();
		currentTittleDeed = squareIndex;
		int buttonWidth = width / 2;
		int buttonHeight = height / 6;
		try {
			img = ImageIO.read(new File(defaultDir + "/components/titleDeeds/" + squareIndex + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		isTittleDeed = true;
		tittleDeedInfo = new JTextArea();
		tittleDeedInfo.setBackground(new Color(10, 77, 143));
		tittleDeedInfo.setForeground(new Color(234, 238, 247));
		tittleDeedInfo.setFont(new Font("Calibri", Font.PLAIN, 16));
		tittleDeedInfo.setLineWrap(true);
		tittleDeedInfo.setWrapStyleWord(true);
		tittleDeedInfo.setEditable(false);
		tittleDeedInfo.setBounds(0, 4 * height / 6 + 2 * height / 18, width, height / 18);
		for (int i = 0; i < titleDeedInfo.size(); i++) {
			tittleDeedInfo.append(titleDeedInfo.get(i) + "\n");
		}
		add(tittleDeedInfo);

		upgradeButton.setEnabled(true);
	
		repaint();

	}

	private void setPanel() {
		setPreferredSize(new Dimension(width, height));

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (isTittleDeed) {
			if (img != null)
				g.drawImage(img, width / 2 - 3 * width / 8, 2 * height / 6, 6 * width / 8,
						2 * height / 6 + 2 * height / 18, null);
		} else if (isCard) {
			if (img != null)
				g.drawImage(img, 0, 2 * height / 6, width, 2 * height / 6, null);

		}
//		loadPermenantButtons();
		isTittleDeed = false;
		isCard = false;
	}

	public ArrayList<String> getPropertiesInfo() {
		return propertiesInfo;
	}

	public void setPropertiesInfo(ArrayList<String> titleDeedInfo) {
		this.propertiesInfo = titleDeedInfo;
	}

	public boolean isTittleDeed() {
		return isTittleDeed;
	}

	public void setTittleDeed(boolean isTittleDeed) {
		this.isTittleDeed = isTittleDeed;
	}

	public JButton getMrMonopolyButton() {
		return mrMonopolyButton;
	}

	public void setMrMonopolyButton(JButton mrMonopolyButton) {
		this.mrMonopolyButton = mrMonopolyButton;
	}

	public JButton getChoose() {
		return choose;
	}

	public void setChoose(JButton choose) {
		this.choose = choose;
	}

	public JButton getUpgradeButton() {
		return upgradeButton;
	}

	public void setUpgradeButton(JButton upgradeButton) {
		this.upgradeButton = upgradeButton;
	}

	public JButton getQuitGameButton() {
		return quitGameButton;
	}

	public void setQuitGameButton(JButton quitGameButton) {
		this.quitGameButton = quitGameButton;
	}

	public JButton getBusIconButton() {
		return busIconButton;
	}

	public void setBusIconButton(JButton busIconButton) {
		this.busIconButton = busIconButton;
	}

	public JButton getPause() {
		return pause;
	}

	public void setPause(JButton pause) {
		this.pause = pause;
	}

}
