/*
 * SnakeModel class
 * 
 */


import java.awt.*;
import java.util.*;
/**
 * Contains game mechanics.
 * @author karol
 *
 */
public class SnakeModel
{
	///Game board
	private Fields[][] map;//values: 0-free space, 1-snake, 2-snack.
	private int size;
	private Snake snake;
	private Snacks snacks;
	private Directions move;
	private int score;
	/**
	 * Constructor.
	 * @param size size of square game board
	 */
	public SnakeModel(int size)
	{
		this.map=new Fields[size][size];
		for(int i=0; i<size; ++i)
			for(int j=0; j<size; ++j)
				this.map[i][j] = Fields.empty;
		this.size=size;
		this.snake=new Snake(size);
		this.snacks=new Snacks();
		this.move = Directions.RIGHT;
		this.setScore(0);
		this.refresh();
	}
	/**
	 * Refreshes game board to current view.
	 */
	public void refresh()
	{
		//iterate through map and checks if place is taken
		for(int i=0; i<this.size; ++i)
			for(int j=0; j<this.size; ++j)
			{
				if(!this.snake.isFree(i,j))
					this.map[i][j]=Fields.snake;
				else if(!this.snacks.isFree(i, j))
						this.map[i][j]=Fields.snack;
					else
						this.map[i][j]=Fields.empty;
			}
	}
	/**
	 * Resets game board to its primary view.
	 */
	public void reset()
	{
		this.snake = new Snake(this.size);
		this.snacks = new Snacks();
		this.addSnack();
		this.setScore(0);
		this.refresh();
		this.move = Directions.RIGHT;
	}
	/**
	 * Checks whether point given by coordinates is taken
	 * @param x 1st coordinate
	 * @param y 2nd coordinate
	 * @return true-free space, false-taken space 
	 */
	private boolean isFree(int x, int y)
	{
		if(map[x][y]==Fields.empty)
			return true;
		else 
			return false;
				
	}
	/**
	 * Checks whether given point is free
	 * @param tmp point method checks
	 * @return	true-free space, false-taken space 
	 */
	private boolean isFree(Point tmp)
	{
		if(map[(int)tmp.getX()][(int)tmp.getY()]==Fields.empty)
			return true;
		else 
			return false;
	}
	/**
	 * Function addSnack adds new snack to game board
	 */
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
	/**
	 * Function moveSnake move snake(if possible) in provided direction
	 * @return true-move was made, false-move cannot be made
	 */
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
	/**
	 * Checks whether move up is possible if not returns false
	 * Otherwise move snake and returns true
	 * @return true-move was made, false-made cannot be made
	 */
	private boolean moveUP()
	{
		Point head=this.snake.getHead();
		//checks whether move is inside map
		if(head.getY()==0)
			return false;
		//checks whether there's free space for the move
		if(this.map[(int)head.getX()][(int)head.getY()-1]==Fields.empty)
		{
			this.snake.move(Directions.UP, this.size);
			
			return true;
		}
		//Checks whether there's snack in place we want to move
		if(this.map[(int)head.getX()][(int)head.getY()-1]==Fields.snack)
		{
			Point tail=this.snake.getTail();
			this.snake.move(Directions.UP, this.size);
			this.snake.grow(tail);
			this.snacks.remove((int)head.getX(), (int)head.getY()-1);
			this.addSnack();
			this.setScore(this.getScore() + 1);
			return true;
		}
		if(this.map[(int)head.getX()][(int)head.getY()-1]==Fields.snake)
			return false;
		return false;
	}
	/**
	 * Function moveDown checks whether move down is possible if not returns false
	 * Otherwise move snake and returns true
	 * @return true-move was made, false-made cannot be made
	 */
	private boolean moveDown()
	{
		Point head = this.snake.getHead();
		//checks whether move is inside map
		if(head.getY()==this.size-1)
			return false;
		//checks whether there's free space for the move
		if(this.map[(int)head.getX()][(int)head.getY()+1]==Fields.empty)
		{
			this.snake.move(Directions.DOWN, this.size);
			return true;
		}
		//checkes whether there's snack in place we want to move
		if(this.map[(int)head.getX()][(int)head.getY()+1]==Fields.snack)
		{
			Point tail=this.snake.getTail();
			this.snake.move(Directions.DOWN, this.size);
			this.snake.grow(tail);
			this.snacks.remove((int)head.getX(),(int)head.getY()+1);
			this.addSnack();
			this.setScore(this.getScore() + 1);
			return true;
		}
		if(this.map[(int)head.getX()][(int)head.getY()+1]==Fields.snake)
			return false;
		return false;
	}
	/*
	 * Function moveDown checks whether move right is possible if not returns false
	 * Otherwise move snake and returns true
	 * @return true-move was made, false-made cannot be made
	 */
	private boolean moveRight()
	{
		Point head=this.snake.getHead();
		//checks whether move is inside map
		if(head.getX()==this.size-1)
			return false;
		//checks whether there's free space for the move
		if(this.map[(int)head.getX()+1][(int)head.getY()]==Fields.empty)
		{
			this.snake.move(Directions.RIGHT, this.size);
			return true;
		}
		//checks whether there's snack in place we want to move
		if(this.map[(int)head.getX()+1][(int)head.getY()]==Fields.snack)
		{
			Point tail=this.snake.getTail();
			this.snake.move(Directions.RIGHT, this.size);
			this.snake.grow(tail);
			this.snacks.remove((int)head.getX()+1,(int)head.getY());
			this.addSnack();
			this.setScore(this.getScore() + 1);
			return true;
		}
		if(this.map[(int)head.getX()+1][(int)head.getY()]==Fields.snake)
			return false;
		return false;
	}
	/*
	 * Function moveDown checks whether move left is possible if not returns false
	 * Otherwise move snake and returns true
	 * @return true-move was made, false-made cannot be made
	 */
	private boolean moveLeft()
	{
		Point head=this.snake.getHead();
		//checks whether move is inside map
		if(head.getX()==0)
			return false;
		//checks whether there's free space for the move
		if(this.map[(int)head.getX()-1][(int)head.getY()]==Fields.empty)
		{
			this.snake.move(Directions.LEFT, this.size);
			return true;
		}
		//checkes whether there's snack in place we want to move
		if(this.map[(int)head.getX()-1][(int)head.getY()]==Fields.snack)
		{
			Point tail=this.snake.getTail();
			this.snake.move(Directions.LEFT, this.size);
			this.snake.grow(tail);
			this.snacks.remove((int)head.getX()-1,(int)head.getY());
			this.addSnack();
			this.setScore(this.getScore() + 1);
			return true;
		}
		if(this.map[(int)head.getX()-1][(int)head.getY()]==Fields.snake)
			return false;
		return false;
	}
	/**
	 * 
	 * @return game board size
	 */
	public int giveSize()
	{
		return size;
	}
	/**
	 * 
	 * @return copy of game board
	 */
	public Fields[][] giveMap()
	{
		Fields[][] map = this.map;
		return map;
	}
	/**
	 * Sets directions of next move.
	 * @param direct Directions enum type
	 */
	public void setMove(Directions direct)
	{
		this.move=direct;
	}
	/**
	 * 
	 * @return next move
	 */
	public Directions getMove()
	{
		return this.move;
	}
	/**
	 * 
	 * @return current score
	 */
	public int getScore() {
		return score;
	}
	/**
	 * 
	 * @param score value of score
	 */
	public void setScore(int score) {
		this.score = score;
	}
}
