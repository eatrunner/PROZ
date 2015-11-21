
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class SnakeControler{
	//References to SnakeModel, SnakeView and on itself
	private SnakeModel snakeMod;
	private SnakeView snakeView;
	private SnakeControler thisSnakeCont = this;
	//Variable tells whether fail was made
	private boolean fail=false;
	
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
		this.snakeView.setSnakeControler(this);
		
		//Adds snack to SnakeModel
		this.snakeMod.addSnack();
		
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
    	executor.scheduleAtFixedRate(new Run(), 0, 150, TimeUnit.MILLISECONDS);
		
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
		//Adds snack to SnakeModel
		this.snakeMod.addSnack();
				
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
		executor.scheduleAtFixedRate(new Run(), 0, 150, TimeUnit.MILLISECONDS);
	}
}


