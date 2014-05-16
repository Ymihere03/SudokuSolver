import java.util.ArrayList;


public class Column {
	
	//This will be the class for each column in the puzzle
	
	private Space[] spaces = new Space[9];
	private ArrayList<Integer> solvedList;
	private ArrayList<Space> emptyList;
	private ArrayList<Integer> discovered;
	int id;
	
	public Column(int position)
	{
		solvedList = new ArrayList<Integer>();
		emptyList = new ArrayList<Space>();
		discovered = new ArrayList<Integer>();
		id = position;
		
		for(int y = 0; y < 9; y++)
		{
			spaces[y] = main.puzzle[y][position];
			spaces[y].setParentColumn(this);
			if(spaces[y].getNumber() != 0)
				this.setAsDiscovered(spaces[y].getNumber());
		}
	}
	
	public boolean contains(int target)
	{
		if(discovered.contains(target) || solvedList.contains(target))
			return true;
		else
			return false;
	}
	
	public void setAsSolved(int target, Space s)
	{
		solvedList.add(target);
		if(discovered.contains(target))
			discovered.remove((Integer) target);
		emptyList.remove(s);
		for(Space sP: spaces)
			sP.unAnnotate(target);
	}
	
	public void setAsDiscovered(int target)
	{
		discovered.add(target);
		for(Space s: spaces)
			s.unAnnotate(target);
	}
	
	public void addToEmpty(Space target)
	{
		emptyList.add(target);
	}
	
	public Space[] getSpaceList()
	{
		return spaces;
	}
	
	public boolean isEmpty()
	{
		if(emptyList.contains(this))
			return true;
		else
			return false;
	}
	
	public void display()
	{
		for(int y = 0; y < 9; y++)
			System.out.println(spaces[y].getNumber());
		System.out.println("");
	}

}
