import java.util.ArrayList;


public class Row {
	
	//This will be the class for each row in the puzzle
	
	private Space[] spaces = new Space[9];
	private ArrayList<Integer> solvedList;
	private ArrayList<Space> emptyList;
	private ArrayList<Integer> discovered;
	int id;
	
	public Row(int position)
	{
		solvedList = new ArrayList<Integer>();
		emptyList = new ArrayList<Space>();
		discovered = new ArrayList<Integer>();
		id = position;
		
		for(int x = 0; x < 9; x++)
		{
			spaces[x] = main.puzzle[position][x];
			spaces[x].setParentRow(this);
			if(spaces[x].getNumber() != 0)
				this.setAsDiscovered(spaces[x].getNumber());
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
	
	public ArrayList<Space> getEmptyList()
	{
		return emptyList;
	}
	
	public void display()
	{
		for(int x = 0; x < 9; x++)
			System.out.print(spaces[x].getNumber());
		System.out.println("");
	}

}
