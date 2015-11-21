
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

import javax.swing.*;



import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SnakeView extends JFrame
{
	private int width = 600;
    private int height = 600;
    private int mapSize;
    private int[][] map;
    private SnakeModel snakeMod;
    private SnakeControler snakeCont; 
    final private SnakeView thisSnakeView = this;
  //Elements of menu
  	private JPanel menuPanel;
  	private JButton newGameButton;
  	private JButton exitButton;
  	private JTextArea scoreLayout;
    
    public SnakeView(final SnakeModel snakeMod)
    {
    	this.map = snakeMod.giveMap();
    	this.mapSize = snakeMod.giveSize();
    	this.snakeMod = snakeMod;
    	
    	//Setting frame display options
    	this.setTitle("Snake");	
    	this.setSize(600,600);
    	this.setLocationRelativeTo(null);
    	this.setResizable(false);
    	
    	//Closes app when 'x' button pressed
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	//Creating MenuPanel and buttons it contains
    	menuPanel = new JPanel();
    	newGameButton = new JButton("New Game");
    	exitButton = new JButton("Exit");
    	scoreLayout = new JTextArea("Score: " + String.valueOf(snakeMod.getScore()));
    	scoreLayout.setEditable(false);
    			
    	menuPanel.setLayout(new GridBagLayout());
    	menuPanel.setMaximumSize(new Dimension(100,100));
    	menuPanel.setBackground(Color.WHITE);
    	
    	//Creating Listeners
    	ListenForButton lForButton = new ListenForButton();
    		
    	//Adds ActionListener to buttons
    	newGameButton.addActionListener(lForButton);
    	exitButton.addActionListener(lForButton);
    		
    			
    	//Adds elements to menuPanel
    	this.addComp(menuPanel, scoreLayout, 0, 0, 0, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
    	this.addComp(menuPanel, newGameButton, 0, 1, 0, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
    	this.addComp(menuPanel, exitButton, 0, 2, 0, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
    	//Adds menuPanel to Frame
    	//this.add(new ImageComponent("/home/karol/Pulpit/Chiquita_Minionek3.jpg"));
    	this.add(menuPanel);
    	
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
    			
    	//Shows frame on desktop
    	this.setVisible(true);
    }
    
    private class ListenForButton implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			
			if(e.getSource() == newGameButton)
			{
				//CHANGING MENU FOR GAMEDISPLAY
				
				snakeCont.startGame();
				//Hides frame
				thisSnakeView.setVisible(false);
				thisSnakeView.remove(menuPanel);
				thisSnakeView.setVisible(true);
				ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
		    	executor.scheduleAtFixedRate(new RepaintTheBoard(thisSnakeView), 0, 20, TimeUnit.MILLISECONDS);
				
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
    
    public void paint(Graphics g) {

    	// Allows me to make many settings changes in regards to graphics

    	Graphics2D graphicSettings = (Graphics2D)g;

    	// Draw a black background that is as big as the game board

    	graphicSettings.setColor(Color.BLACK);

    	graphicSettings.fillRect(0, 0, this.width, this.height);

    	// Set rendering rules

    	// graphicSettings.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    		        
    	//Snake body will be white
    		        
    	graphicSettings.setColor(Color.WHITE);
    		        
    	//Set snake on display
    		        
    	for(int i=0; i<this.mapSize; ++i)
    		for(int j=0; j<this.mapSize; ++j)
    			if(this.map[i][j]==1)
    				graphicSettings.fillRect(i*this.width/this.mapSize, j*this.height/this.mapSize, 
    		        							this.width/this.mapSize, this.height/this.mapSize);
    		        
    	//Set color on orange
    		        
    	graphicSettings.setColor(Color.ORANGE);
    		        
    	//Set snacks on the display(every digit 2 on this.map)
    		        
    	for(int i=0; i<this.mapSize; ++i)
    		for(int j=0; j<this.mapSize; ++j)
    			if(this.map[i][j]==2)
    				graphicSettings.fillRect(i*this.width/this.mapSize, j*this.height/this.mapSize, 
    		        							this.width/this.mapSize, this.height/this.mapSize);
    	
    	graphicSettings.setColor(Color.RED);
    	
    	graphicSettings.drawString("SCORE: " + String.valueOf(snakeMod.getScore()), 10, 10);
    	//graphicSettings.
    }
    
    public void showMenu()
    {
    	this.setVisible(false);
    	this.add(menuPanel);
    	this.setVisible(true);
    }
    
    class RepaintTheBoard implements Runnable
    {
        SnakeView theBoard;

        public RepaintTheBoard(SnakeView theBoard){

            this.theBoard = theBoard;
        }

        public void run() {

            theBoard.repaint();
        }
    }
}