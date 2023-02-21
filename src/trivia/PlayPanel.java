package trivia;

import javax.swing.*;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class PlayPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	private class Problem {
		String question;
		String[] choices = new String[3];
		int correctIndex;
	}
	
	private ArrayList<Problem> problems = new ArrayList<Problem>();
	private ArrayList<Problem> usedProblems = new ArrayList<Problem>();
	boolean loaded = false;
	
	private Random random = new Random();
	private File questionsFile = new File("data/questions.txt");
	private Scanner sc;
	
	private CardLayout clREF;
	private JPanel jpREF;
	
	private JPanel infoPanel = new JPanel(new FlowLayout());
	/* infoPanel member */ private JLabel scoreLabel;
	/* infoPanel member */ private JButton quitButton;
	
	private JPanel questionPanel = new JPanel();
	private JPanel answersPanel = new JPanel(new FlowLayout());
	
	private int currScore = 0;
	
	/*
	 * Constructor for PlayPanel.
	 * @param c - A CardLayout object that manages all primary panels for the current TriviaGame
	 * @param p - A JPanel object that can be used with c to change the current panel displayed in the TriviaGame
	 */
	public PlayPanel(CardLayout c, JPanel p) {
		try {
			sc = new Scanner(questionsFile);
			clREF = c;
			jpREF = p;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Initialize a game
	 */
	protected void init() {
		this.setLayout(new GridLayout(3,1));
		
		quitButton = new JButton("Quit");
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
				clREF.show(jpREF, "MAIN");
			}
		});
		scoreLabel = new JLabel("Score: " + currScore);
		infoPanel.add(scoreLabel);
		infoPanel.add(quitButton);
		
		if(!loaded) { loadQuestions(); }
		this.add(infoPanel);
		this.add(questionPanel);
		this.add(answersPanel);
		newQuestion(false);
	}
	
	/*
	 * Loads questions from file specified by this.questionsFile
	 */
	private void loadQuestions() {
		while(sc.hasNextLine()) {
			String line = sc.nextLine();
			int index = line.indexOf(';');
			Problem pr = new Problem();
			pr.question = line.substring(0, index);
			for(int i = 0; i < pr.choices.length; i++) {
				line = line.substring(index+1);
				index = line.indexOf(';');
				pr.choices[i] = line.substring(0, index);
			}
			pr.correctIndex = Integer.parseInt(line.substring(index+1));
			problems.add(pr);
		}
		sc.close();
		loaded = true;
	}
	
	private void newQuestion(boolean lastCorrect) {
		if(problems.size() == 0) {
			endGame();
			return;
		}
		
		questionPanel.removeAll();
		answersPanel.removeAll();
		
		int index = random.nextInt(problems.size());
		Problem p = problems.remove(index);
		usedProblems.add(p);
		questionPanel.add(new JLabel(p.question));
		
		for(int i = 0; i < 3; i++) {
			JButton tmp = new JButton(p.choices[i]);
			if(i == p.correctIndex) {
				tmp.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						currScore++;
						scoreLabel.setText("Score: " + currScore);
						newQuestion(true);
					}
				});
			}else {
				tmp.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						newQuestion(false);
					}
				});
			}
			answersPanel.add(tmp);
		}
		
		questionPanel.revalidate();
		questionPanel.repaint();
		answersPanel.revalidate();
		answersPanel.repaint();
	}
	
	private void endGame() {
		this.remove(answersPanel);
		this.revalidate();
		this.repaint();
		
		questionPanel.removeAll();
		questionPanel.add(new JLabel("You finished the game!"));
		questionPanel.revalidate();
		questionPanel.repaint();
	}
	
	private void reset() {
		infoPanel.removeAll();
		infoPanel.revalidate();
		infoPanel.repaint();
		
		for(Problem p : usedProblems) {
			problems.add(p);
		}
		usedProblems.clear();
		currScore = 0;
		scoreLabel.setText("Score: " + currScore);
	}
}
