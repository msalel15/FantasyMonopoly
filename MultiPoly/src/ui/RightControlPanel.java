package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import domain.MultiPolyController;

public class RightControlPanel extends JPanel {

	// Location Related Info
	private int width;
	private int height;

	// Margin(s)
	private int yMargin;

	// Connection to Controller
	private MultiPolyController controller;

	// Sub-components
	private RightVoicePanel voicePanel;
	private RightButtonPanel buttonPanel;

	public RightVoicePanel getVoicePanel() {
		return voicePanel;
	}

	public void setVoicePanel(RightVoicePanel voicePanel) {
		this.voicePanel = voicePanel;
	}

	public RightControlPanel(int width, int height, BoardPanel board) {
		this.width = width;
		this.height = height;

		controller = board.getController();

		yMargin = height / 20;

		voicePanel = new RightVoicePanel(width, 3 * (height - yMargin) / 4, board);
		buttonPanel = new RightButtonPanel(width, (height - yMargin) / 4, board);

		setPanel();
		addComponents();

	}

	public RightButtonPanel getButtonPanel() {
		return buttonPanel;
	}

	private void setPanel() {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(width, height));
	}

	private void addComponents() {
		add(voicePanel, BorderLayout.NORTH);
		add(buttonPanel, BorderLayout.SOUTH);
	}

}
