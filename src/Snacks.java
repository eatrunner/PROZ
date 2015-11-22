import java.awt.*;
import java.util.*;
/**
 * Contains list of snacks.
 * @author karol
 *
 */
public class Snacks {
	private ArrayList<Point> snackList;
	/**
	 * Snacks Constructor
	 */
	public Snacks()
	{
		snackList=new ArrayList<Point>();
	}
	/**
	 * Function addSnack adds snack.
	 * @param x 1st coordinate
	 * @param y 2nd coordinate
	 * @return true-succes, false-fail
	 */
	public boolean addSnack(int x, int y)
	{
		ListIterator<Point> it=this.snackList.listIterator();
		Point tmp=new Point(x,y);
		while(true)
		{
			
			if(it.equals(tmp)){
				System.err.println("Added snack already exist");
				return false;
			}
			if(it.hasNext())
				it.next();
			else
				break;
		}
		it.add(tmp);
		return true;
	}
	/**
	 * Function remove removes snack with given coordinates.
	 * @param x 1st coordinate
	 * @param y 2nd coordinate
	 * @return true-succes, false-fail
	 */
	public boolean remove(int x, int y)
	{
		Point tmp=new Point(x,y), listEl;
		ListIterator<Point> it=this.snackList.listIterator();
		while(it.hasNext())
		{
			listEl=it.next();
			if(listEl.equals(tmp))
			{
				it.remove();
				return true;
			}
		}
		return false;
	}
	/**
	 * Function isFree checks whether point given by coordinates is taken by any snack
	 * @param x 1st coordinate
	 * @param y 2nd coordinate
	 * @return true-free space, false-space taken
	 */
	public boolean isFree(int x, int y)
	{
		Point e=new Point(x,y), tmp;
		ListIterator<Point> it=this.snackList.listIterator();
		//iterate through list
		while(it.hasNext())
		{
			
			tmp=it.next();
			//checks if point e is equal to element from the list
			if(tmp.equals(e))
			{
				return false;
			}
		}
		//gets here if there are no equal elements to e
		return true;
	}
	
	
	
}
