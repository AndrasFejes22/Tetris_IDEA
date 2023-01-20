package tetris2;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainWindow {

	private JFrame mainFrame;
	private JTextField scoreTextField;
	private JTextArea gameArea;
	private TetrisGame tetrisGame;
	private JTextArea nextBlockTextArea;
	private JButton pauseOrResumeButton;
	private JLabel gameStateLabel;

	/**
	 * Launch the application.
	 * EventQueue: kattingatáskor eseményeket generálunk, ezekre lehet reagálni a kódban
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() { //paraméterként át van adva egy névtelen osztály
			public void run() {
				try {
					MainWindow mainWindow = new MainWindow();
					mainWindow.mainFrame.setVisible(true);
					// A játék logikája egy külön szálon fusson:
					Executors.newSingleThreadExecutor().execute(new TetrisGame(mainWindow)); // ez egy külön szál
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}
	
	public void setTetrisGame(TetrisGame tetrisGame) {
		this.tetrisGame = tetrisGame;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mainFrame = new JFrame();
		mainFrame.setResizable(false);
		mainFrame.setTitle("Tetris v 0.5");
		mainFrame.setBounds(100, 100, 473, 589);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel eastPanel = new JPanel();
		mainFrame.getContentPane().add(eastPanel, BorderLayout.EAST);
		GridBagLayout gbl_eastPanel = new GridBagLayout();
		gbl_eastPanel.columnWidths = new int[] {50};
		gbl_eastPanel.rowHeights = new int[] {30, 30, 30, 30, 30, 0};
		gbl_eastPanel.columnWeights = new double[]{0.0};
		gbl_eastPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		eastPanel.setLayout(gbl_eastPanel);
		
		//pauseOrResumeButton = new JButton("Start");
		pauseOrResumeButton = new JButton("Pause");
		pauseOrResumeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(pauseOrResumeButton.getText().equals("Pause")) {
					pauseOrResumeButton.setText("Resume");
					tetrisGame.pause();
				} else if (pauseOrResumeButton.getText().equals("Resume")){
					pauseOrResumeButton.setText("Pause");
					tetrisGame.resume();
				} else if(pauseOrResumeButton.getText().equals("Start")) {
					pauseOrResumeButton.setText("Pause");
					tetrisGame.start();
				}
				gameArea.requestFocusInWindow();
			}
		});
		GridBagConstraints gbc_pauseOrResumeButton = new GridBagConstraints();
		gbc_pauseOrResumeButton.insets = new Insets(0, 0, 5, 0);
		gbc_pauseOrResumeButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_pauseOrResumeButton.gridx = 0;
		gbc_pauseOrResumeButton.gridy = 0;
		eastPanel.add(pauseOrResumeButton, gbc_pauseOrResumeButton);
		
		JLabel scoreLabel = new JLabel("Pont:");
		GridBagConstraints gbc_scoreLabel = new GridBagConstraints();
		gbc_scoreLabel.insets = new Insets(0, 0, 5, 0);
		gbc_scoreLabel.anchor = GridBagConstraints.WEST;
		gbc_scoreLabel.gridx = 0;
		gbc_scoreLabel.gridy = 1;
		eastPanel.add(scoreLabel, gbc_scoreLabel);
		
		scoreTextField = new JTextField();
		scoreTextField.setText("0");
		scoreTextField.setEditable(false);
		GridBagConstraints gbc_scoreTextField = new GridBagConstraints();
		gbc_scoreTextField.insets = new Insets(0, 0, 5, 0);
		gbc_scoreTextField.anchor = GridBagConstraints.WEST;
		gbc_scoreTextField.gridx = 0;
		gbc_scoreTextField.gridy = 2;
		eastPanel.add(scoreTextField, gbc_scoreTextField);
		scoreTextField.setColumns(10);
		
		JLabel nextBlockLabel = new JLabel("K\u00F6vetkez\u0151:");
		GridBagConstraints gbc_nextBlockLabel = new GridBagConstraints();
		gbc_nextBlockLabel.insets = new Insets(0, 0, 5, 0);
		gbc_nextBlockLabel.anchor = GridBagConstraints.WEST;
		gbc_nextBlockLabel.gridx = 0;
		gbc_nextBlockLabel.gridy = 3;
		eastPanel.add(nextBlockLabel, gbc_nextBlockLabel);
		
		nextBlockTextArea = new JTextArea();
		nextBlockTextArea.setEditable(false);
		nextBlockTextArea.setFont(new Font("Consolas", Font.BOLD, 16)); //31
		nextBlockTextArea.setHighlighter(null);
		GridBagConstraints gbc_nextBlockTextArea = new GridBagConstraints();
		gbc_nextBlockTextArea.insets = new Insets(0, 0, 5, 0);
		gbc_nextBlockTextArea.anchor = GridBagConstraints.WEST;
		gbc_nextBlockTextArea.gridx = 0;
		gbc_nextBlockTextArea.gridy = 4;
		eastPanel.add(nextBlockTextArea, gbc_nextBlockTextArea);
		
		gameStateLabel = new JLabel("");
		GridBagConstraints gbc_gameStateLabel = new GridBagConstraints();
		gbc_gameStateLabel.gridx = 0;
		gbc_gameStateLabel.gridy = 5;
		eastPanel.add(gameStateLabel, gbc_gameStateLabel);
		
		gameArea = new JTextArea();
		gameArea.setFont(new Font("Consolas", Font.BOLD, 31));
		gameArea.setEditable(false);
		gameArea.setHighlighter(null); // utólag generálva: a szövegen ne lehessen kijelölni
		gameArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent keyEvent) {
				int pressedKeyCode = keyEvent.getKeyCode();
				switch (pressedKeyCode) {
				case KeyEvent.VK_LEFT: tetrisGame.moveBlockLeft(); 
				break;
				case KeyEvent.VK_RIGHT: tetrisGame.moveBlockRight(); 
				break;
				case KeyEvent.VK_DOWN: tetrisGame.dropBlock(); // lefele nyillal gyorsítjuk (a sleep()-en keresztül)
				break;
				case KeyEvent.VK_SPACE: tetrisGame.rotate(); // lefele nyillal gyorsítjuk (a sleep()-en keresztül)
				break;
				default:
					break;
				}
			}
		});
		mainFrame.getContentPane().add(gameArea, BorderLayout.CENTER);
		
		
	}

	public JTextArea getGameArea() {
		return gameArea;
	}

	public JTextField getScoreTextField() {
		return scoreTextField;
	}
	
	
	public JTextArea getNextBlockTextArea() {
		return nextBlockTextArea;
	}

	
	public JButton getPauseOrResumeButton() {
		return pauseOrResumeButton;
	}
	public JLabel getGameStateLabel() {
		return gameStateLabel;
	}
}