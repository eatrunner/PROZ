import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public final class SnakeControler extends JFrame{
	private SnakeModel snakeMod;
	private SnakeView snakeView;
	private int velocity=150;
	private boolean fail=true;
	
	
	public static void main(String[] args) {
		new SnakeControler();

	}
	
	public SnakeControler()
	{
		this.snakeMod = new SnakeModel(30);
		this.snakeView = new SnakeView(this.snakeMod);
		this.setTitle("SnakeControler");	
		this.setSize(100,100);
		this.setLocationRelativeTo(null);
		this.setResizable(false);		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		this.snakeMod.addSnack();
		this.snakeMod.refresh();
		
		addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) 
			{ 
				if (e.getKeyCode()==38)
				 {
					 System.out.println("UP");
					 snakeMod.setMove(Directions.UP);
					 
				 }else if(e.getKeyCode()==40)
				 {
					 System.out.println("DOWN");
					 snakeMod.setMove(Directions.DOWN);
				 }else if(e.getKeyCode()==39)
				 {
					 System.out.println("RIGHT");
					 snakeMod.setMove(Directions.RIGHT);
				 }else if(e.getKeyCode()==37)
				 {
					 System.out.println("LEFT");
					 snakeMod.setMove(Directions.LEFT);
				 }else if(e.getKeyCode()==107)
				 {
					 velocity+=10;
				 }else if(e.getKeyCode()==109)
				 {
					 velocity-=10;
				 }
					 	                
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
		
		executor.scheduleAtFixedRate(new Run(snakeMod, this), 0, this.velocity, TimeUnit.MILLISECONDS);
		
		this.setVisible(true);
		
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

class Run implements Runnable
{
	private SnakeModel snakeMod;
	private SnakeControler snakeCont;
	
	public Run(SnakeModel snakeMod, SnakeControler snakeCont)
	{
		this.snakeMod=snakeMod;
		this.snakeCont=snakeCont;
	}
	@Override
	public void run() {
		snakeCont.setFail(snakeMod.moveSnake(snakeMod.getMove()));
		snakeMod.refresh();
		if(!snakeCont.getFail())
			snakeCont.abort();
	}
	
}
