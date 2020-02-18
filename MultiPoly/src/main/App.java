package main;

import java.awt.HeadlessException;
import java.io.IOException;

import domain.MultiPolyController;
import ui.GameFrame;

public class App {

	public static void main(String[] args) throws HeadlessException, IOException {

		// TODO Auto-generated method stub
		MultiPolyController controller = MultiPolyController.getInstance();
		GameFrame frame = new GameFrame(controller);
		controller.addObserver(frame);
		new Thread(frame.getBpanel()).start();

	}

}
