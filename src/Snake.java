import java.awt.Point;
import java.util.ArrayList;
import java.util.ListIterator;


//Snake class
public class Snake
{
	//Array body contains every point of snake on the map
	private ArrayList<Point> body;
	private int length;
	//Constructor of class snake
	//Head of the snake is set to center of the map
	public Snake(int mapSize)
	{
		this.length=5;
		this.body= new ArrayList<Point>();
		mapSize=mapSize/2;
		for(int i=0; i<5; ++i)
		{
			body.add(new Point(mapSize-i, mapSize));
		}
	}
	//Function move moves head of the snake in given direction
	//Direction and size of of map must be provided
	public boolean move(Directions direct, int mapSize)
	{
		ListIterator<Point> it=this.body.listIterator();
		Point tmp=it.next();
		switch(direct)
		{
		
			case UP:
				if(tmp.getY() != 0)
				{
					this.moveUp();
					return true;
				}else
					return false;
			case DOWN:
				if(tmp.getY() != mapSize-1)
				{
					moveDown();
					return true;
				}else
					return false;
			case RIGHT:
				if(tmp.getX() != mapSize-1)
				{
					moveRight();
					return true;
				}else
					return false;
			case LEFT:
				if(tmp.getX() != 0)
				{
					moveLeft();
					return true;
				}else
					return false;
			default: return false;
		}
	}
	//Moves head of the snake up with all its body
	private void moveUp()
	{
		ListIterator<Point> it=this.body.listIterator();
		Point tmp1, tmp2;
		tmp1=it.next();
		tmp2=new Point(tmp1.x, tmp1.y-1);
		it.set(tmp2);
		tmp2=tmp1;
		while(it.hasNext())
		{
			tmp1=it.next();
			it.set(tmp2);
			tmp2=tmp1;
		}
	}
	//Moves head of the snake down with all its body
	private void moveDown()
	{
		ListIterator<Point> it=this.body.listIterator();
		Point tmp1, tmp2;
		tmp1=it.next();
		tmp2=new Point(tmp1.x, tmp1.y+1);
		it.set(tmp2);
		tmp2=tmp1;
		while(it.hasNext())
		{
			tmp1=it.next();
			it.set(tmp2);
			tmp2=tmp1;
		}
	}
	//Moves head of the snake left with all its body
	private void moveLeft()
	{
		ListIterator<Point> it=this.body.listIterator();
		Point tmp1, tmp2;
		tmp1=it.next();
		tmp2=new Point(tmp1.x-1, tmp1.y);
		it.set(tmp2);
		tmp2=tmp1;
		while(it.hasNext())
		{
			tmp1=it.next();
			it.set(tmp2);
			tmp2=tmp1;
		}
	}
	//Moves head of the snake right with all its body
	private void moveRight()
	{
		ListIterator<Point> it=this.body.listIterator();
		Point tmp1, tmp2;
		tmp1=it.next();
		tmp2=new Point(tmp1.x+1, tmp1.y);
		it.set(tmp2);
		tmp2=tmp1;
		while(it.hasNext())
		{
			tmp1=it.next();
			it.set(tmp2);
			tmp2=tmp1;
		}
	}
	//Function isFree checks whether given point is taken by snake
	private boolean isFree(Point tmp)
	{
		ListIterator<Point> it=this.body.listIterator();
		for(int i=0; i<this.length; ++i)
		{
			if(tmp==it.next())
				return false;
		}
		return true;
	}
	//Function isFree checks whether point given by coordinates is taken by snake
	public boolean isFree(int x, int y)
	{
		ListIterator<Point> it=this.body.listIterator();
		Point tmp;
		for(int i=0; i<this.length; ++i)
		{
			tmp=it.next();
			if(tmp.distanceSq(x, y)==0)
				return false;
		}
		return true;
	}
	//Function grow adds new point to snake
	public void grow(Point newTail)
	{
		//adds new point to snake
		this.body.add(newTail);
		this.length++;
	}
	//Function getHead returns head point
	public Point getHead()

	{
		ListIterator<Point> it=this.body.listIterator();
		return it.next();
	}
	//Function getTail returns tail point
	public Point getTail()
	{
		ListIterator<Point> it=this.body.listIterator();
		for(int i=0;i<this.length-1;it.next(), ++i);
		return it.next();
	}
	
	
}