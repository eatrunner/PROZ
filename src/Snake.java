import java.awt.Point;


//Snake class
public class Snake
{
	//Array body contains every point of snake on the map
	private Point[] body;
	private int length;
	//Constructor of class snake
	//Head of the snake is set to center of the map
	public Snake(int mapSize)
	{
		this.length=5;
		this.body= new Point[this.length];
		mapSize=mapSize/2;
		
		for(int i=0; i<5; ++i)
		{
			this.body[i]=new Point(mapSize-i, mapSize);
		}
	}
	//Function move moves head of the snake in given direction
	//Direction and size of of map must be provided
	public boolean move(Directions direct, int mapSize)
	{
		switch(direct)
		{
			case UP:
				if(body[0].getY()!=0)
				{
					/*
					 * Moves every point in the place of previous one
					 * does it from "tail" towards "head"
					 */
					for(int i=length-1; i>0; --i)
						this.body[i].setLocation(this.body[i-1]);
					this.body[0].setLocation(this.body[0].x, this.body[0].y-1);
					return true;
				}else
					return false;
			case DOWN:
				if(body[0].getY()!=mapSize-1)
				{
					/*
					 * Moves every point in the place of previous one
					 * does it from "tail" towards "head"
					 */
					for(int i=length-1; i>0; --i)
						this.body[i].setLocation(this.body[i-1]);
					this.body[0].setLocation(this.body[0].x, this.body[0].y+1);
					return true;
				}else
					return false;
			case RIGHT:
				if(body[0].getX()!=mapSize-1)
				{
					/*
					 * Moves every point in the place of previous one
					 * does it from "tail" towards "head"
					 */
					for(int i=length-1; i>0; --i)
						this.body[i].setLocation(this.body[i-1]);
					this.body[0].setLocation(this.body[0].x+1, this.body[0].y);
					return true;
				}else
					return false;
			case LEFT:
				if(body[0].getX()!=0)
				{
					/*
					 * Moves every point in the place of previous one
					 * does it from "tail" towards "head"
					 */
					for(int i=length-1; i>0; --i)
						this.body[i].setLocation(this.body[i-1]);
					this.body[0].setLocation(this.body[0].x-1, this.body[0].y);
					return true;
				}else
					return false;
			default: return false;
		}
	}
	//Function isFree checks whether given point is taken by snake
	private boolean isFree(Point tmp)
	{
		for(int i=0; i<this.length; ++i)
		{
			if(this.body[i].equals(tmp))
				return false;
		}
		return true;
	}
	//Function isFree checks whether point given by coordinates is taken by snake
	public boolean isFree(int x, int y)
	{
		for(int i=0; i<this.length; ++i)
		{
			if(this.body[i].distanceSq(x, y)==0)
				return false;
		}
		return true;
	}
	//Function grow adds new point to snake
	public void grow(Point newTail)
	{
		//adds new point to snake
		Point tmp[]=new Point[this.length+1];
		for(int i=0; i<this.length; ++i)
		{
			tmp[i].setLocation(this.body[i]);
		}
		tmp[this.length]=newTail;
		this.body=tmp;		
		++this.length;
	}
	//Function getHead returns head point
	public Point getHead()

	{
		return this.body[0];
	}
	//Function getTail returns tail point
	public Point getTail()
	{
		return this.body[this.length-1];
	}
	
	
}