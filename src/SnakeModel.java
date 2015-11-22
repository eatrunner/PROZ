/*
 * SnakeModel class
 * 
 */


import java.awt.*;
import java.util.*;
public class SnakeModel
{
	private int[][] map;//values: 0-free space, 1-snake, 2-snack.
	private int size;
	private Snake snake;
	private Snacks snacks;
	private Directions move;
	private int score;
	//SnakeModel Constructor
	public SnakeModel(int size)
	{
		this.map=new int[size][size];
		this.size=size;
		this.snake=new Snake(size);
		this.snacks=new Snacks();
		this.move = Directions.RIGHT;
		this.setScore(0);
		this.refresh();
	}
	//Function refresh updates map status
	public void refresh()
	{
		//iterate through map and checks if place is taken
		for(int i=0; i<this.size; ++i)
			for(int j=0; j<this.size; ++j)
			{
				if(!this.snake.isFree(i,j))
					this.map[i][j]=1;
				else if(!this.snacks.isFree(i, j))
						this.map[i][j]=2;
					else
						this.map[i][j]=0;
			}
	}
	//Function reset resets Model to its starting point
	public void reset()
	{
		this.snake = new Snake(this.size);
		this.snacks = new Snacks();
		this.addSnack();
		this.setScore(0);
		this.refresh();
		this.move = Directions.RIGHT;
	}
	//Function isFree checks whether point given by coordinates is taken
	private boolean isFree(int x, int y)
	{
		if(map[x][y]==0)
			return true;
		else 
			return false;
				
	}
	//Function isFree checks whether given point is free
	private boolean isFree(Point tmp)
	{
		if(map[(int)tmp.getX()][(int)tmp.getY()]==0)
			return true;
		else 
			return false;
	}
	//Function addSnack adds new snack to map
	public void addSnack()
	{
		Random rand=new Random();
		while(true)
		{
			//generate random point
			
			int x=rand.nextInt(this.size-1),y=rand.nextInt(this.size-1);
			//if generated point is free adds snack. if not loop repeats
			if(this.isFree(x, y))
			{
				this.snacks.addSnack(x, y);
				break;
			}
			
		}
	}
	//Function moveSnake move snake(if possible) in provided direction
	public boolean moveSnake()
	{
		Directions direct=this.move;
		switch(direct)
		{
		case UP:
			return this.moveUP();
		case DOWN:
			return this.moveDown();
		case RIGHT:
			return this.moveRight();
		case LEFT:
			return this.moveLeft();
		default:return false;
		}
	}
	/*
	 * Function moveUP checks whether move up is possible if not returns false
	 * Otherwise move snake and returns true
	 */
	private boolean moveUP()
	{
		Point head=this.snake.getHead();
		//checks whether move is inside map
		if(head.getY()==0)
			return false;
		//checks whether there's free space for the move
		if(this.map[(int)head.getX()][(int)head.getY()-1]==0)
		{
			this.snake.move(Directions.UP, this.size);
			
			return true;
		}
		//Checks whether there's snack in place we want to move
		if(this.map[(int)head.getX()][(int)head.getY()-1]==2)
		{
			Point tail=this.snake.getTail();
			this.snake.move(Directions.UP, this.size);
			this.snake.grow(tail);
			this.snacks.remove((int)head.getX(), (int)head.getY()-1);
			this.addSnack();
			this.setScore(this.getScore() + 1);
			return true;
		}
		if(this.map[(int)head.getX()][(int)head.getY()-1]==1)
			return false;
		return false;
	}
	/*
	 * Function moveDown checks whether move down is possible if not returns false
	 * Otherwise move snake and returns true
	 */
	private boolean moveDown()
	{
		Point head = this.snake.getHead();
		//checks whether move is inside map
		if(head.getY()==this.size-1)
			return false;
		//checks whether there's free space for the move
		if(this.map[(int)head.getX()][(int)head.getY()+1]==0)
		{
			this.snake.move(Directions.DOWN, this.size);
			return true;
		}
		//checkes whether there's snack in place we want to move
		if(this.map[(int)head.getX()][(int)head.getY()+1]==2)
		{
			Point tail=this.snake.getTail();
			this.snake.move(Directions.DOWN, this.size);
			this.snake.grow(tail);
			this.snacks.remove((int)head.getX(),(int)head.getY()+1);
			this.addSnack();
			this.setScore(this.getScore() + 1);
			return true;
		}
		if(this.map[(int)head.getX()][(int)head.getY()+1]==1)
			return false;
		return false;
	}
	/*
	 * Function moveDown checks whether move right is possible if not returns false
	 * Otherwise move snake and returns true
	 */
	private boolean moveRight()
	{
		Point head=this.snake.getHead();
		//checks whether move is inside map
		if(head.getX()==this.size-1)
			return false;
		//checks whether there's free space for the move
		if(this.map[(int)head.getX()+1][(int)head.getY()]==0)
		{
			this.snake.move(Directions.RIGHT, this.size);
			return true;
		}
		//checks whether there's snack in place we want to move
		if(this.map[(int)head.getX()+1][(int)head.getY()]==2)
		{
			Point tail=this.snake.getTail();
			this.snake.move(Directions.RIGHT, this.size);
			this.snake.grow(tail);
			this.snacks.remove((int)head.getX()+1,(int)head.getY());
			this.addSnack();
			this.setScore(this.getScore() + 1);
			return true;
		}
		if(this.map[(int)head.getX()+1][(int)head.getY()]==2)
			return false;
		return false;
	}
	/*
	 * Function moveDown checks whether move left is possible if not returns false
	 * Otherwise move snake and returns true
	 */
	private boolean moveLeft()
	{
		Point head=this.snake.getHead();
		//checks whether move is inside map
		if(head.getX()==0)
			return false;
		//checks whether there's free space for the move
		if(this.map[(int)head.getX()-1][(int)head.getY()]==0)
		{
			this.snake.move(Directions.LEFT, this.size);
			return true;
		}
		//checkes whether there's snack in place we want to move
		if(this.map[(int)head.getX()-1][(int)head.getY()]==2)
		{
			Point tail=this.snake.getTail();
			this.snake.move(Directions.LEFT, this.size);
			this.snake.grow(tail);
			this.snacks.remove((int)head.getX()-1,(int)head.getY());
			this.addSnack();
			this.setScore(this.getScore() + 1);
			return true;
		}
		if(this.map[(int)head.getX()-1][(int)head.getY()]==1)
			return false;
		return false;
	}
	
	public int giveSize()
	{
		return size;
	}
	
	public int[][] giveMap()
	{
		int[][] map = this.map;
		return map;
	}
	
	public void setMove(Directions direct)
	{
		this.move=direct;
	}
	
	public Directions getMove()
	{
		return this.move;
	}
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
}
