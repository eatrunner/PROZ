
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.*;



import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SnakeView extends JFrame
{
	/**
	 * 
	 */
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
  	TheBoard theBoard;
  	private ScheduledThreadPoolExecutor executor;
    
    public SnakeView(SnakeModel snakeModel)
    {
    	this.snakeMod = snakeModel;
    	//Setting frame display options
    	this.setTitle("Snake");	
    	this.setSize(600,600);
    	this.setLocationRelativeTo(null);
    	this.setResizable(false);
    	executor = new ScheduledThreadPoolExecutor(5);
    	//Closes app when 'x' button pressed
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	this.prepareMenu();
    	//this.add(new ImageComponent("/home/karol/Pulpit/Chiquita_Minionek3.jpg"));
  
    	//Creates KeyListener and adds it to frame
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
    			// TODO Auto-generated method stub
    					
    		}
    				
    	});
 		
 		this.executor.execute(new ShowMenu());
 		
 		
    			
    	
    }
    
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
		}
	}
    
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
	

	public void setSnakeControler(SnakeControler snakeCont)
	{
		this.snakeCont = snakeCont;
	}
    
       

    
    public void stopRefreshing()
    {
    	executor.shutdown();
    	this.remove(theBoard);
    }
    
    private void prepareMenu()
    {
    	
    	//Creating MenuPanel and buttons it contains
    	this.menuPanel = new JPanel();
    	this.newGameButton = new JButton("New Game");
    	this.exitButton = new JButton("Exit");
    	this.scoreLayout = new JTextArea("Score: " + String.valueOf(this.snakeMod.getScore()));
    	this.scoreLayout.setEditable(false);
    			
    	this.menuPanel.setLayout(new GridBagLayout());
    	this.menuPanel.setMaximumSize(new Dimension(100,100));
    	this.menuPanel.setBackground(Color.WHITE);
    	//Creating Listeners
    	ListenForButton lForButton = new ListenForButton();
    	
    	//Adds ActionListener to buttons
    	this.newGameButton.addActionListener(lForButton);
    	this.exitButton.addActionListener(lForButton);
    		
    			
    	//Adds elements to menuPanel
    	this.addComp(this.menuPanel, this.scoreLayout, 0, 0, 0, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
    	this.addComp(this.menuPanel, this.newGameButton, 0, 1, 0, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
    	this.addComp(this.menuPanel, this.exitButton, 0, 2, 0, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
    }
    
    public void showMenu()
    {
    	this.prepareMenu();
    	this.setVisible(false);
		this.add(this.menuPanel);
		this.setVisible(true);
    }
    
    class ShowMenu implements Runnable
    {

    	public ShowMenu(){}

		public void run() {
			prepareMenu();
			showMenu();
			
		}
    	
    }
    
    class TheBoard extends JComponent implements Runnable
    {
    	 /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private SnakeModel snakeMod;
		private int[][] map;
		private int mapSize;
		
		public TheBoard(SnakeModel snakeMod)
		{
			this.snakeMod = snakeMod;
			this.map = snakeMod.giveMap();
			this.mapSize = snakeMod.giveSize();
		}
		
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
		
		public void run() {
			this.repaint();
			
		}
    	
    }
}