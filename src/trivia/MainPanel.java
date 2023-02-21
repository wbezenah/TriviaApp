package trivia;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MainPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private JPanel textPanel;
	private JPanel buttonPanel;
	private JLabel title;
	private JButton startButton;
	
	//References
	private CardLayout cl;
	private JPanel container;
	private PlayPanel playPanel;
	
	public MainPanel(CardLayout c, JPanel p, PlayPanel pp) {
		super();
		this.setLayout(new GridLayout(2,1));
		
		cl = c;
		container = p;
		playPanel = pp;
		title = new JLabel("Welcome to Trivia");
		
		textPanel = new JPanel();
		buttonPanel = new JPanel();
		
		startButton = new JButton("PLAY TRIVIA");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(container, "PLAY");
				playPanel.init();
			}
		});
		
		textPanel.add(title);
		buttonPanel.add(startButton);
		this.add(textPanel);
		this.add(buttonPanel);
	}
}
