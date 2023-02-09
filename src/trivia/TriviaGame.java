package trivia;
import java.awt.*;

import javax.swing.*;

public class TriviaGame {
	private JFrame frame;
	private JPanel panelContainer;
	protected CardLayout cl;
	
	private MainPanel mP;
	private PlayPanel play;
	
	
	public TriviaGame() {
		frame = new JFrame("TriviaGame");
		frame.setSize(500,500);
		panelContainer = new JPanel();
		cl = new CardLayout();
		
		mP = new MainPanel(cl, panelContainer);
		play = new PlayPanel(cl, panelContainer);
		
		panelContainer.setLayout(cl);
		
		
		panelContainer.add(mP, "MAIN");
		panelContainer.add(play, "PLAY");
		
		cl.show(panelContainer, "MAIN");
		
		frame.add(panelContainer);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new TriviaGame();
			}
		});
	}
}
