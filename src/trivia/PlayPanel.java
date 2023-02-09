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

public class PlayPanel extends JPanel{
	private static final long serialVersionUID = 2L;

	private class Problem{
		String question;
		String[] choices = new String[3];
		int correctIndex;
	}
	
	private ArrayList<Problem> problems;
	private ArrayList<Problem> usedProblems;
	private Random random = new Random();
	
	private File questionsFile = new File("data/questions.txt");
	private Scanner sc;
	private CardLayout cl;
	private JPanel container;
	
	private JPanel informationPanel;
	private JPanel questionPanel;
	private JPanel answersPanel;
	
	private JButton[] choiceButtons = new JButton[3];
	
	private JButton quitButton;
	private JLabel scoreLabel;
	
	private int score = 0;
	
	public PlayPanel(CardLayout c, JPanel p) {
		try {
			sc = new Scanner(questionsFile);
			container = p;
			cl = c;
			
			informationPanel = new JPanel(new FlowLayout());
			
			quitButton = new JButton("Quit");
			quitButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					reset();
					cl.show(container, "MAIN");
				}
			});
			scoreLabel = new JLabel("Score: " + score);
			
			informationPanel.add(quitButton);
			informationPanel.add(scoreLabel);
			
			usedProblems = new ArrayList<Problem>();
			problems = new ArrayList<Problem>();
			readQuestions();
			
			questionPanel = new JPanel();
			answersPanel = new JPanel(new FlowLayout());
			
			this.setLayout(new GridLayout(3,1));
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/*
	 * Reads in questions from scanner/text file
	 */
	private void readQuestions() {
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
	}
	
	/*
	 * Loads an unused question into the PlayPanel
	 * Returns false if no questions are left, else true
	 */
	protected boolean loadQuestion() {
		if(problems.size() == 0) { 
			endGame();
			return false;
		}
		
		this.add(informationPanel);
		this.add(questionPanel);
		this.add(answersPanel);
		
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
						score++;
						scoreLabel.setText("Score: " + score);
						loadQuestion();
					}
				});
			}else {
				tmp.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						loadQuestion();
					}
				});
			}
			choiceButtons[i] = tmp;
		}
		
		for(JButton b: choiceButtons) {
			answersPanel.add(b);
		}
		questionPanel.revalidate();
		answersPanel.revalidate();
		questionPanel.repaint();
		answersPanel.repaint();
		
		return true;
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
	
	/*
	 * Resets PlayPanel information including which questions have been used and the score
	 */
	private void reset() {
		problems.clear();
		for(Problem p : usedProblems) {
			problems.add(p);
		}
		usedProblems.clear();
		score = 0;
		scoreLabel.setText("Score: " + score);
	}
	
//	public static void main(String args[]) {
//
//	}
}
