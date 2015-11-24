
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 * Contains all essential methods and object to display program.
 * @author karol
 *
 */
public class SnakeView extends JFrame
{
	
	private static final long serialVersionUID = 1L;
	private int width = 600;
    private int height = 600;
    
    
    final private SnakeModel snakeMod;
    private SnakeControler snakeCont; 
    final private SnakeView thisSnakeView = this;
  //Elements of menu
  	private JPanel menuPanel;
  	private JButton newGameButton;
  	private JButton exitButton;
  	private JTextArea scoreLayout;
  	private JButton scoresButton;
  	
  	private JInternalFrame scores;
  	TheBoard theBoard;
  	private ScheduledThreadPoolExecutor executor;
    /**
     * Constructor. Sets all essential JFrame options and displays Menu.
     * @param snakeModel reference to object of SnakeModel class.
     */
    public SnakeView(SnakeModel snakeModel)
    {
    	this.snakeMod = snakeModel;
    	//Setting frame display options
    	this.setTitle("Snake");	
    	this.setSize(this.width, this.height);
    	this.setLocationRelativeTo(null);
    	this.setResizable(false);
    	executor = new ScheduledThreadPoolExecutor(1);
    	//Closes app when 'x' button pressed
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	this.prepareMenu();
  
    	/**
    	 * Creates KeyListener and adds it to frame
    	 */
 		addKeyListener(new KeyListener() {

    		@Override
    		public void keyPressed(KeyEvent e) {
    			// TODO Auto-generated method stub
   					
 			}

    		@Override
    		public void keyReleased(KeyEvent e) 
    		{ 
    			//When arrow up released sets next move for UP
    			if (e.getKeyCode()==38)
    			 {
    				 snakeMod.setMove(Directions.UP);
    			 }else//When arrow down released sets next move for DOWN 
    				 if(e.getKeyCode()==40)
    			 {
    				 snakeMod.setMove(Directions.DOWN);
    			 }else//When arrow right released sets next move for RIGHT 
    				 if(e.getKeyCode()==39)
    			 {
    				 snakeMod.setMove(Directions.RIGHT);
    			 }else //When arrow left released sets next move for LEFT 
    				 if(e.getKeyCode()==37)
    			 {
    				 snakeMod.setMove(Directions.LEFT);
    			 }else //When esc released closes app 
    				 if(e.getKeyCode()==27)
    			 {
    				 System.exit(ABORT);
    			 }
    					          
    					
    		}

    		@Override
    		public void keyTyped(KeyEvent e) {
    					
    		}
    				
    	});
 		
 		
 		this.executor.execute(new ShowMenu());
    }
    /**
     * Contains every action event for buttons.
     * @author karol
     *
     */
    private class ListenForButton implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			
			if(e.getSource() == newGameButton)
			{
				thisSnakeView.setVisible(false);
				thisSnakeView.remove(menuPanel);
				theBoard = new TheBoard(snakeMod);
				thisSnakeView.add(theBoard);
				thisSnakeView.setVisible(true);
				snakeCont.startGame();
				executor = new ScheduledThreadPoolExecutor(1);
		    	executor.scheduleAtFixedRate(theBoard, 0, 20, TimeUnit.MILLISECONDS);
				
			}
			
			if(e.getSource() == exitButton)
			{
				//Closes app
				snakeCont.abort();
			}
			
			if(e.getSource() == scoresButton)
			{
				executor = new ScheduledThreadPoolExecutor(1);
				executor.execute(new ShowScores());
			}
		}
	}
    /**
     * Adds component to panel with specified options.
     * @param thePanel 
     * @param comp component to add.
     * @param xPos	
     * @param yPos
     * @param compWidth
     * @param compHeight
     * @param place 	must be GridBagConstraints enum type
     * @param stretch	must be GridBagConstraints enum type
     */
	private void addComp(JPanel thePanel, JComponent comp, int xPos, int yPos,
				int compWidth, int compHeight, int place, int stretch)
	{
		GridBagConstraints gridConstraints = new GridBagConstraints();

		gridConstraints.gridx = xPos;

		gridConstraints.gridy = yPos;

		gridConstraints.gridwidth = compWidth;

		gridConstraints.gridheight = compHeight;

		gridConstraints.weightx = 1;

		gridConstraints.weighty = 1;

		gridConstraints.insets = new Insets(0,0,0,0);

		gridConstraints.anchor = place;

		gridConstraints.fill = stretch;

		thePanel.add(comp, gridConstraints);
		}
	/**
	 * Sets reference on SnakeControler object.
	 * @param snakeCont
	 */
	public void setSnakeControler(SnakeControler snakeCont)
	{
		this.snakeCont = snakeCont;
	}
    /**
     * Stops refreshing game board and removes it from frame.
     */
    public void stopRefreshing()
    {
    	executor.shutdown();
    	this.remove(theBoard);
    }
    /**
     * Prepares menu panel.
     */
    private void prepareMenu()
    {
    	
    	//Creating MenuPanel and buttons it contains
    	this.menuPanel = new JPanel();
    	this.newGameButton = new JButton("New Game");
    	this.exitButton = new JButton("Exit");
    	this.scoreLayout = new JTextArea("Score: " + String.valueOf(this.snakeMod.getScore()));
    	this.scoresButton = new JButton("Best scores");
    	this.scoreLayout.setEditable(false);
    			
    	this.menuPanel.setLayout(new GridBagLayout());
    	this.menuPanel.setMaximumSize(new Dimension(100,100));
    	this.menuPanel.setBackground(Color.WHITE);
    	//Creating Listeners
    	ListenForButton lForButton = new ListenForButton();
    	
    	//Adds ActionListener to buttons
    	this.newGameButton.addActionListener(lForButton);
    	this.exitButton.addActionListener(lForButton);
    	this.scoresButton.addActionListener(lForButton);
    		
    			
    	//Adds elements to menuPanel
    	this.addComp(this.menuPanel, this.scoreLayout, 0, 0, 0, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
    	this.addComp(this.menuPanel, this.newGameButton, 0, 1, 0, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
    	this.addComp(this.menuPanel, this.scoresButton, 0, 2, 0, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
    	this.addComp(this.menuPanel, this.exitButton, 0, 3, 0, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
    	
    }
    /**
     * Displays menu.
     */
    public void showMenu()
    {
    	this.prepareMenu();
    	this.setVisible(false);
		this.add(this.menuPanel);
		this.setVisible(true);
    }
    /**
     * Contains menu display method. 
     * @author karol
     *
     */
    class ShowMenu implements Runnable
    {

    	public ShowMenu(){}
    	/**
    	 * Prepares menu panel and displays it.
    	 */
		public void run() {
			prepareMenu();
			showMenu();
			
		}
    	
    }
    
    class ShowScores implements Runnable
    {
    	public ShowScores(){}
		
		public void run() {
			thisSnakeView.setVisible(false);
			scores = new Scores();
			thisSnakeView.remove(menuPanel);
			thisSnakeView.add(scores);
			try {
				scores.setSelected(true);
			} catch (PropertyVetoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			scores.setVisible(true);
			thisSnakeView.setVisible(true);
			thisSnakeView.add(menuPanel);
		}
    	
    }
    
    /**
     * Contains display game board method that displays game board on screen.
     * @author karol
     *
     */
    class TheBoard extends JComponent implements Runnable
    {
    	 /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private SnakeModel snakeMod;
		private int[][] map;
		private int mapSize;
		/**
		 * Constructor.
		 * @param snakeMod reference on SnakeModel object.
		 */
		public TheBoard(SnakeModel snakeMod)
		{
			this.snakeMod = snakeMod;
			this.map = snakeMod.giveMap();
			this.mapSize = snakeMod.giveSize();
		}
		/**
		 * Method displays board on screen.
		 */
		public void paint(Graphics g) {

    	   	// Allows me to make many settings changes in regards to graphics
   	    	Graphics2D graphicSettings = (Graphics2D)g;

   	    	// Draw a black background that is as big as the game board

   	    	graphicSettings.setColor(Color.BLACK);

   	    	graphicSettings.fillRect(0, 0, width, height);

   	    	// Set rendering rules

   	    	// graphicSettings.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    	    		        
   	    	//Snake body will be white
    	    		        
   	    	graphicSettings.setColor(Color.WHITE);
    	    		        
   	    	//Set snake on display
    	    		        
   	    	for(int i=0; i<this.mapSize; ++i)
   	    		for(int j=0; j<this.mapSize; ++j)
   	    			if(this.map[i][j]==1)
   	    				graphicSettings.fillRect(i*width/this.mapSize, j*height/this.mapSize, 
   	    		        							width/this.mapSize, height/this.mapSize);
    	    		        
   	    	//Set color on orange
    	    		        
   	    	graphicSettings.setColor(Color.ORANGE);
    	    		        
   	    	//Set snacks on the display(every digit 2 on this.map)
    	    		        
   	    	for(int i=0; i<this.mapSize; ++i)
   	    		for(int j=0; j<this.mapSize; ++j)
   	    			if(this.map[i][j]==2)
   	    				graphicSettings.fillRect(i*width/this.mapSize, j*height/this.mapSize, 
   	    		        							width/this.mapSize, height/this.mapSize);
    	    	
   	    	graphicSettings.setColor(Color.RED);
    	    	
   	    	graphicSettings.drawString("SCORE: " + String.valueOf(snakeMod.getScore()), 10, 10);
   	    	//graphicSettings.
   	    }
		/**
		 * Runs paint method.
		 */
		public void run() {
			this.repaint();
			
		}
    	
    }
    
    class Scores extends JInternalFrame
    {
    	final Path path = Paths.get("scores");
    	private ArrayList<Score> scoresList;
    	private JPanel panel;
    	private JTextArea first;
    	private JTextArea second;
    	private JTextArea third;
    	private JTextArea fourth;
    	private JTextArea fifth;
    	private JButton addButton;
    	private JTextField nameField;
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		public Scores()
		{
			super("Scores",
					true, true, true, true
					);
			this.scoresList = new ArrayList<Score>();
			Charset charset = Charset.forName("US-ASCII");
			//Reads scores from file
			try (BufferedReader reader = Files.newBufferedReader(this.path, charset)) {
			    String line = null;
			    for (int i=0; i<5 && ((line = reader.readLine()) != null); ++i) {
			        System.out.println(line);
			        this.scoresList.add(new Score(line));
			    }
			} catch (IOException x) {
			    System.err.format("IOException: %s%n", x);
			}
			//Prepares frame for display
			
			this.setResizable(true);
			this.setSize(new Dimension(200,200));
			this.setMaximumSize(new Dimension(200,200));
			
			this.panel = new JPanel();
			this.panel.setPreferredSize(new Dimension(100, 200));
			Score tmp = this.scoresList.get(0);
			this.first = new JTextArea("1."+tmp.name + "\t" + String.valueOf(tmp.score));
			tmp = this.scoresList.get(1);
			this.second = new JTextArea("2."+tmp.name + "\t" + String.valueOf(tmp.score));
			tmp = this.scoresList.get(2);
			this.third = new JTextArea("3."+tmp.name + "\t" + String.valueOf(tmp.score));
			tmp = this.scoresList.get(3);
			this.fourth = new JTextArea("4."+tmp.name + "\t" + String.valueOf(tmp.score));
			tmp = this.scoresList.get(4);
			this.fifth = new JTextArea("5."+tmp.name + "\t" + String.valueOf(tmp.score));
			this.addButton = new JButton("Add");
			this.nameField = new JTextField("Name", 15);
			ListenForButton lForButton = new ListenForButton();
			this.addButton.addActionListener(lForButton);
			this.panel.add(this.first);
			this.panel.add(this.second);
			this.panel.add(this.third);
			this.panel.add(this.fourth);
			this.panel.add(this.fifth);
			
			
			
			this.panel.setAlignmentY(CENTER_ALIGNMENT);
			this.panel.add(this.nameField);
			this.panel.add(this.addButton);
			this.add(this.panel);
		}
		
		private class ListenForButton implements ActionListener
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == addButton)
				{
					String name = nameField.getText();
					Score newScore = new Score(name, snakeMod.getScore());
					ListIterator<Score> it = scoresList.listIterator();
					for(int i = 0; i<scoresList.size(); ++i)
					{
						Score tmp = it.next();
						if(tmp.getScore() < newScore.getScore())
						{
							it.previous();
							it.add(newScore);
							break;
							
						}
					}
					save();
				}
				
			}
			
		}
		
		public Score giveScore(int index)
		{
			return this.scoresList.get(index);
		}
		
		public void save()
		{
			Charset charset = Charset.forName("US-ASCII");
			try (BufferedWriter writer = Files.newBufferedWriter(this.path, charset)) {
				for(int i = 0; i<5 && i<this.scoresList.size(); ++i)
				{
					Score tmp = this.scoresList.get(i);
					String s = tmp.name + "\t" + String.valueOf(tmp.score) + "\n";
					writer.write(s, 0, s.length());
					
				}
					
			} catch (IOException x) {
			    System.err.format("IOException: %s%n", x);
			}
		}
		
    	
    }
    
    class Score
    {
    	private String name;
    	private int score;
    	public Score(String name, int score)
    	{
    		this.setName(name);
    		this.setScore(score);
    	}
    	public Score(String string)
    	{
    			for(int i=0; i<string.length(); ++i)
    				if(string.charAt(i)=='\t')
    				{
    					this.name = string.substring(0, i);
    					this.score = new Integer(string.substring(++i));
    				}
    	}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getScore() {
			return score;
		}
		public void setScore(int score) {
			this.score = score;
		}
    	
    }
}