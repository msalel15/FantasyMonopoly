package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import domain.MultiPolyController;
import domain.Observer;

public class GameFrame extends JFrame implements Observer {

	

	

	// Sub-components Frame
	private LeftInfoPanel ipanel;
	private BoardPanel bpanel;
	private RightControlPanel cpanel;

	private MultiPolyController controller;
	
	// Constructor using Fields
	public GameFrame(MultiPolyController controller) {
		setLayout(null);
		this.controller = controller;

		String osname = System.getProperty("os.name").toLowerCase();
		boolean mac = osname.startsWith("mac os x");
		System.out.println("mac " + mac);

		if (mac) {
			getContentPane().setPreferredSize(new Dimension(1280, 720));
		} else {
			setExtendedState(JFrame.MAXIMIZED_BOTH);
			setUndecorated(true);
		}

		pack();
		setVisible(true);
		getContentPane().setSize(new Dimension(getWidth(), getHeight()));
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// switchToGameScreen();// should be comment to see start screen

		StartScreen startScreen = new StartScreen(getContentPane().getWidth(), getContentPane().getHeight());
		getContentPane().add(startScreen);
		startScreen.initAncestor();
		startScreen.repaint();

	}

	public void switchToStartScreen() {
		getContentPane().removeAll();
		StartScreen startScreen = new StartScreen(getContentPane().getWidth(), getContentPane().getHeight());
		getContentPane().add(startScreen);

		startScreen.initAncestor();

		repaint();

	}

	public void switchToGameScreen() {
		getContentPane().removeAll();
		setLayout(null);
		int xMargin = getContentPane().getWidth() / 50;
		int yMargin = getContentPane().getHeight() / 50;

		// Panel Size
		int panelHeight = getContentPane().getHeight() - 2 * yMargin;
		int panelWidth = (getContentPane().getWidth() - panelHeight) / 2 - 2 * xMargin;
		bpanel = new BoardPanel(panelHeight, this);
		ipanel = new LeftInfoPanel(panelWidth, panelHeight, bpanel);
		cpanel = new RightControlPanel(panelWidth, panelHeight, bpanel);

		setLayout(new BorderLayout(xMargin, yMargin));

		add(ipanel, BorderLayout.WEST);
		add(bpanel, BorderLayout.CENTER);
		add(cpanel, BorderLayout.EAST);

		getContentPane().setSize(new Dimension(getWidth(), getHeight()));
		setVisible(true);
		repaint();
	}

	private void setFrame() {
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		// setUndecorated(true);
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	

	@Override
	public void update(String string, Object obj1, Object obj2) {
		controller.updatePlayerInfo();
		switch (string) {
		case "GoToJail":
			bpanel.setCurrentPlayerNum((int) obj1);
			int currentplayer = bpanel.getCurrentPlayerNum();
			bpanel.getTokens().get(currentplayer).Jump(bpanel.getSquares().get((int) obj2));
			bpanel.repaint();
			bpanel.setVisible(true);
			break;

		case "InJail":
			cpanel.getButtonPanel().getRollDice().setText("Roll to be Free");
			cpanel.getButtonPanel().getRollDice().setActionCommand("rollFree");
			cpanel.getButtonPanel().getBuy().setText("Pay Penalty");
			cpanel.getButtonPanel().getBuy().setActionCommand("pay");
			break;

		case "FreeFromJail":
			cpanel.getButtonPanel().getRollDice().setText("Roll Dice");
			cpanel.getButtonPanel().getRollDice().setActionCommand("rollDice");
			cpanel.getButtonPanel().getBuy().setText("Buy");
			cpanel.getButtonPanel().getBuy().setActionCommand("buy");
			break;

		case "UpdatePlayerInfo":
			// Para g�ncellemesi

			break;
	/*	case "Card":
			cpanel.getVoicePanel().ViewCard((int) obj1);

			break;
			*/
		case "BotIsThinking":
			JOptionPane.showMessageDialog(null, "Bot is thinking..  Please Wait", "Please Wait", JOptionPane.INFORMATION_MESSAGE);
			break;
			
		case "HollandTunnel":
		
			bpanel.setCurrentPlayerNum((int) obj1);
			int currentply = bpanel.getCurrentPlayerNum();
			bpanel.getTokens().get(currentply).Jump(bpanel.getSquares().get((int) obj2));
			controller.useless();
		
			break;

		case "RollThree":
			bpanel.setDiceResults((ArrayList<Integer>) obj1);
			bpanel.repaint();
			break;

		case "MrMonopolyMove":
			bpanel.setCurrentPlayerNum((int) obj1);
			bpanel.getTokens().get((int) obj1).squares = bpanel.generateSquareList((ArrayList<Integer>) obj2);
			bpanel.getTokens().get((int) obj1).setMyTurn(true);
			controller.mrMonopolyAfterActButtonHandler();

			synchronized (bpanel) {
				bpanel.setAnimatorStopped(false);
				bpanel.notify();
			}
			bpanel.setDiceResults(controller.getDiceValues());
			break;

		case "BusIconMove":
			bpanel.setCurrentPlayerNum((int) obj1);
			int cplayer = bpanel.getCurrentPlayerNum();
			bpanel.getTokens().get(cplayer).Jump(bpanel.getSquares().get((int) obj2));
			controller.busAfterActButtonHandler();
			break;

		case "RegularMove":
			bpanel.setCurrentPlayerNum((int) obj1);
			bpanel.getTokens().get((int) obj1).squares = bpanel.generateSquareList((ArrayList<Integer>) obj2);
			bpanel.getTokens().get((int) obj1).setMyTurn(true);
			controller.regularMoveButtonHandler();

			synchronized (bpanel) {
				bpanel.setAnimatorStopped(false);
				bpanel.notify();
			}
			bpanel.setDiceResults(controller.getDiceValues());
			break;
		case "PauseGame":
			cpanel.getVoicePanel().getPause().setText("Resume Game");
			cpanel.getVoicePanel().getPause().setFont(new Font("Batang", Font.BOLD, 12));
			cpanel.getVoicePanel().getPause().setActionCommand("resumeGame");
			bpanel.setAnimatorStopped(true);

			break;
		case "ResumeGame":
			synchronized (bpanel) {
				bpanel.setAnimatorStopped(false);
				bpanel.notify();
			}
			cpanel.getVoicePanel().getPause().setText("Pause Game");
			cpanel.getVoicePanel().getPause().setFont(new Font("Batang", Font.BOLD, 15));

			cpanel.getVoicePanel().getPause().setActionCommand("pauseGame");

			break;
		case "ShowProperties":
			cpanel.getVoicePanel().setPropertiesInfo((ArrayList<String>) obj1);
			cpanel.getVoicePanel().ViewPropertiesWithChoose();
			break;
		case "StartBusIconProcess":
			cpanel.getVoicePanel().setPropertiesInfo((ArrayList<String>) obj1);
			cpanel.getVoicePanel().BusIconMode();
			cpanel.getVoicePanel().getBusIconButton().setText("Bus Icon Move");
			controller.busBeforeActButtonHandler();
			break;

		case "showTitleDeed":
			cpanel.getVoicePanel().ViewTitleDeed((int) obj1, (ArrayList<String>) obj2);
			break;
		case "EndBusIconProcess":

			break;

		case "EnableThrowDiceButton":
			cpanel.getButtonPanel().getRollDice().setEnabled(true);
			break;
		case "EnableBuyButton":
			cpanel.getButtonPanel().getBuy().setEnabled(true);
			break;

		case "EnableMrMonopolyButton":
			cpanel.getVoicePanel().MrMonopolyMode();
			controller.mrMonopolyBeforeActButtonHandler();
			break;
		case "EnablePropertiesButton":
			cpanel.getButtonPanel().getProperties().setEnabled(true);
			break;
		case "EnableEndTurnButton":
			cpanel.getButtonPanel().getEndTurn().setEnabled(true);
			break;
		case "EnableAllButtons":
			cpanel.getButtonPanel().setButtonsEnable(true);
			break;
		case "DisableBuyButton":
			cpanel.getButtonPanel().getBuy().setEnabled(false);
			break;
		case "DisableThrowDiceButton":
			cpanel.getButtonPanel().getRollDice().setEnabled(false);
			break;
		case "DisablePropertiesButton":
			cpanel.getButtonPanel().getProperties().setEnabled(false);
			break;
		case "DisableMrMonopolyButton":
			cpanel.getVoicePanel().getMrMonopolyButton().setEnabled(false);
			
		case "DisableBusButton":
			cpanel.getVoicePanel().getBusIconButton().setEnabled(false);
			
		case "DisableEndTurnButton":
			cpanel.getButtonPanel().getEndTurn().setEnabled(false);
			break;
		case "DisableAllButtons":
			cpanel.getButtonPanel().setButtonsEnable(false);
			break;

		case "RepresentJailDie":
			bpanel.setDiceResults(controller.getDiceJailValues());
			break;
			
		case "moveWithTriple":
			cpanel.getVoicePanel().getBusIconButton().setText("MoveWithTriple");
					
			break;
			
		case "moveWithSubway":
			cpanel.getVoicePanel().getBusIconButton().setText("MoveWithSubway");
			break;

		case "EndTurn":

			break;
		default:

			repaint();
			setVisible(true);
			break;

		}
	
		ipanel.displayPlayerInfo(controller.getPlayerInfo());
		ipanel.displayGameInfo(controller.getGameFlow());

		repaint();
		setVisible(true);

	}

	public LeftInfoPanel getIpanel() {
		return ipanel;
	}

	public RightControlPanel getCpanel() {
		return cpanel;
	}

	public void setCpanel(RightControlPanel cpanel) {
		this.cpanel = cpanel;
	}

	public MultiPolyController getController() {
		return controller;
	}

	public BoardPanel getBpanel() {
		return bpanel;
	}

}