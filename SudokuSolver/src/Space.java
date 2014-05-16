import java.util.ArrayList;


public class Space {
	
	//This will be the class for each individual square in the puzzle
	
	private int n;
	private boolean locked;
	private Box parentBox;
	private Row parentRow;
	private Column parentColumn;
	private ArrayList<Integer> annotations = new ArrayList<Integer>();
	
	public Space()
	{
		n = 0;
		locked = false;
	}
	
	public Space(int number)
	{
		n = number;
		locked = false;
	}
	
	public int getNumber()
	{
		return n;
	}
	
	public boolean isEmpty()
	{
		if(n == 0)
			return true;
		else
			return false;
	}
	
	public void setAsSolved(int target)
	{
		n = target;
		if(target != 0)
		{
			System.out.println("   Found " + target + " (" + parentColumn.id + ", " + parentRow.id + ")");
			parentBox.setAsSolved(target, this);
			parentRow.setAsSolved(target, this);
			parentColumn.setAsSolved(target, this);
		}
		else
		{
			parentBox.addToEmpty(this);
			parentRow.addToEmpty(this);
			parentColumn.addToEmpty(this);
		}
	}
	
	public void annotate(int target)
	{
		if(!annotations.contains(target))
			annotations.add(target);
	}
	
	public void unAnnotate(int target)
	{
		if(annotations.contains(target)){
			annotations.remove((Integer) target);
			if(isLocked())
				unlock(this);
		}
	}
	
	public boolean isAnnotated(int target)
	{
		if(annotations.contains(target))
			return true;
		else
			return false;
	}
	
	public ArrayList<Integer> getAnnotations()
	{
		return annotations;
	}
	
	public void dispAnnot()
	{
		if(annotations.size() > 0)
		{
			for(int a: annotations)
				System.out.print(a);
			System.out.print("\n");
		}
		else
			System.out.println("None");
	}
	
	public void unlock(Space target)
	{
		locked = false;
	}
	
	public void lock()
	{
		locked = true;
	}
	
	/*public void lockWith(Space target)
	{
		this.annotations.retainAll(target.annotations);
		target.annotations.retainAll(this.annotations);
		locked = true;
		target.locked = true;
	}*/
	
	public boolean isLocked()
	{
		return locked;
	}
	
	public void setParentBox(Box target)
	{
		parentBox = target;
	}
	
	public Box getParentBox()
	{
		return parentBox;
	}
	
	public void setParentRow(Row target)
	{
		parentRow = target;
	}
	
	public Row getParentRow()
	{
		return parentRow;
	}
	
	public void setParentColumn(Column target)
	{
		parentColumn = target;
	}
	
	public Column getParentColumn()
	{
		return parentColumn;
	}

}
