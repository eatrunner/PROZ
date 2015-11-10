
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.*;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SnakeView extends JComponent
{
	private int width = 600;
    private int height = 600;
    private int mapSize;
    private int[][] map;
    private JTextArea scoreLayout;
    private SnakeModel snakeMod;
    
    
    public SnakeView(SnakeModel snakeMod)
    {
    	this.map = snakeMod.giveMap();
    	this.mapSize = snakeMod.giveSize();
    	this.snakeMod = snakeMod;
    	ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
    	executor.scheduleAtFixedRate(new RepaintTheBoard(this), 0, 20, TimeUnit.MILLISECONDS);
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