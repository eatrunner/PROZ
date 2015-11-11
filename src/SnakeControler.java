import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class SnakeControler extends JFrame{
	//References to SnakeModel, SnakeView and on itself
	private SnakeModel snakeMod;
	private SnakeView snakeView;
	private SnakeControler thisSnakeCont = this;
	//Variable tells whether fail was made
	private boolean fail=false;
	//Elements of menu
	private JPanel menuPanel;
	private JButton newGameButton;
	private JButton exitButton;
	private JTextArea scoreLayout;
	//Executor for refreshing the SnakeModel
	private ScheduledThreadPoolExecutor executor;
	
	
	public static void main(String[] args) {
		
		new SnakeControler();

	}
	//SnakeControler Constructor
	public SnakeControler()
	{
		//Creating objects of SnakeModel and SnakeView class
		this.snakeMod = new SnakeModel(30);
		this.snakeView = new SnakeView(this.snakeMod);
		
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
		
		
		
		
		//Adds snack to SnakeModel
		this.snakeMod.addSnack();
		
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
				
				//Hides frame
				thisSnakeCont.setVisible(false);
				//Removes menuPanel from frame
				thisSnakeCont.remove(menuPanel);
				//Adds snakeView to frame 
				thisSnakeCont.add(snakeView);
				//Sets score for 0
				snakeMod.setScore(0);
				//Sets next move for RIGHT
				snakeMod.setMove(Directions.RIGHT);
				//Shows frame
				thisSnakeCont.setVisible(true);
				//Sets executor
				executor = new ScheduledThreadPoolExecutor(5);
				executor.scheduleAtFixedRate(new Run(), 0, 150, TimeUnit.MILLISECONDS);
			}
			
			if(e.getSource() == exitButton)
			{
				//Closes app
				thisSnakeCont.abort();
			}
		}
		
		
	}
	
	class Run implements Runnable
	{
	
		public Run() {}
		public void run() 
		{
			//Moves snake and and sets setFail for true when move impossible to make
			thisSnakeCont.setFail(!snakeMod.moveSnake(snakeMod.getMove()));
			//refreshes gameboard
			snakeMod.refresh();
			//When fail was set for true
			if(thisSnakeCont.getFail())
			{
				//CHANGING GAMEDISPLAY FOR MENU
				
				//Hides frame
				thisSnakeCont.setVisible(false);
				//Removes snakeView from frame
				thisSnakeCont.remove(snakeView);
				//sets present score which will be displayed
				scoreLayout.setText("Score: " + String.valueOf(snakeMod.getScore()));
				//Adds menuPanel to frame
				thisSnakeCont.add(menuPanel);
				//Displays frame
				thisSnakeCont.setVisible(true);
				//Resets fail for false
				thisSnakeCont.setFail(false);
				//Resets snakeModel
				snakeMod.reset();
				//Shuts down executor
				executor.shutdown();
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
	
	
	 class ImageComponent extends JComponent {

	      BufferedImage img;

	      public void paint(Graphics g) {
	         g.drawImage(img, 0, 0, null);
	      }

	      public ImageComponent(String path) {
	         try {
	            img = ImageIO.read(new File(path));
	         } catch (IOException e) {
	            e.printStackTrace();
	         }
	      }
	 }
	
	public void setFail(boolean fail)
	{
		this.fail=fail;
	}
	
	public boolean getFail()
	{
		return this.fail;
	}
	//Function stops app
	public void abort()
	{
		System.exit(ABORT);
	}
}


