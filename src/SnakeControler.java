
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

public final class SnakeControler{
	//References to SnakeModel, SnakeView and on itself
	private SnakeModel snakeMod;
	private SnakeView snakeView;
	private SnakeControler thisSnakeCont = this;
	//Variable tells whether fail was made
	private boolean fail=false;
	
	//Executor for refreshing the SnakeModel
	private ScheduledExecutorService executor;
	
	
	public static void main(String[] args) {
		
		new SnakeControler();

	}
	//SnakeControler Constructor
	public SnakeControler()
	{
		//Creating objects of SnakeModel and SnakeView class
		this.snakeMod = new SnakeModel(30);
		this.snakeView = new SnakeView(this.snakeMod);
		this.snakeView.setSnakeControler(this);
		
	}
	
	class Run implements Runnable
	{
	
		public Run() {}
		public void run() 
		{
			//Moves snake and and sets setFail for true when move impossible to make
			thisSnakeCont.setFail(!snakeMod.moveSnake());
			//refreshes gameboard
			snakeMod.refresh();
			if(thisSnakeCont.fail)
			{
				thisSnakeCont.setFail(false);
				snakeView.stopRefreshing();
				snakeView.showMenu();
				executor.shutdown();
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
		System.exit(0);
	}
	
	public void startGame()
	{
		this.snakeMod.reset();
		executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleAtFixedRate(new Run(), 0, 150, TimeUnit.MILLISECONDS);
	}
}


