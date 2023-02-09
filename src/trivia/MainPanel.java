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
	
	private CardLayout cl;
	private JPanel container;
	
	public MainPanel(CardLayout c, JPanel p) {
		super();
		this.setLayout(new GridLayout(2,1));
		
		cl = c;
		container = p;
		title = new JLabel("Welcome to Trivia");
		
		textPanel = new JPanel();
		buttonPanel = new JPanel();
		
		startButton = new JButton("PLAY TRIVIA");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(container, "PLAY");
			}
		});
		
		textPanel.add(title);
		buttonPanel.add(startButton);
		this.add(textPanel);
		this.add(buttonPanel);
	}
}
