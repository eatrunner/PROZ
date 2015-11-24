
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
/**
 * This is controler of Snake application.
 * Contains main function.
 * @author Karol Niedzielewski
 *
 */
public final class SnakeControler{
	///References to SnakeModel, SnakeView and on itself
	private SnakeModel snakeMod;
	private SnakeView snakeView;
	private SnakeControler thisSnakeCont = this;
	///Variable tells whether fail was made
	private boolean fail=false;
	private int interval = 200;
	
	///Executor for refreshing the SnakeModel
	private ScheduledExecutorService executor;
	/**
	 * Main function.
	 * @param args
	 */
	
	public static void main(String[] args) {
		
		new SnakeControler();

	}
	/**
	 * Constructor.
	 * Creates SnakeModel object and SnakeView object and saves references to both.
	 */
	public SnakeControler()
	{
		//Creating objects of SnakeModel and SnakeView class
		this.snakeMod = new SnakeModel(30);
		this.snakeView = new SnakeView(this.snakeMod);
		this.snakeView.setSnakeControler(this);
		
	}
	/**
	 * Contains main loop of game.
	 */
	class Run implements Runnable
	{
		/**
		 * Empty constructor.
		 */
		public Run() {}
		/**
		 *  Handles all changes made in Model during the game.
		 *  If any mistake made stops game and sends orders View to display Menu.
		 */
		public void run() 
		{
			//Moves snake and and sets setFail for true when move impossible to make
			thisSnakeCont.setFail(!snakeMod.moveSnake());
			//refreshes gameboard
			snakeMod.refresh();
			//If fail was made stops game and displays menu.
			if(thisSnakeCont.fail)
			{
				thisSnakeCont.setFail(false);
				snakeView.stopRefreshing();
				snakeView.showMenu();
				executor.shutdown();
			}
		}
	
	}
	/**
	 * Sets fail.
	 * @param fail value of fail to be set.
	 */
	public void setFail(boolean fail)
	{
		this.fail=fail;
	}
	/**
	 * Returns value of fail.
	 * @return fail
	 */
	public boolean getFail()
	{
		return this.fail;
	}
	/**
	 * Abort program.
	 */
	public void abort()
	{
		System.exit(0);
	}
	/**
	 * Starts game.
	 */
	public void startGame()
	{
		this.snakeMod.reset();
		executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleAtFixedRate(new Run(), 0, this.interval, TimeUnit.MILLISECONDS);
	}
	public int getInterval() {
		return interval;
	}
	public void setInterval(int interval) {
		this.interval = interval;
	}
	public void setSpeed(int i)
	{
		this.setInterval(20+i*20);
	}
	
}


