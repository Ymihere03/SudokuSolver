import java.util.ArrayList;


public class Box {

	//This will be the class for each 3x3 box in the puzzle
	
	private Space[] spaces = new Space[9];
	private ArrayList<Integer> solvedList;
	private ArrayList<Space> emptyList;
	private ArrayList<Integer> discovered;
	
	/*
	 * Number position relative to Space index is as follows:
	 * 		0 1 2
	 * 		3 4 5
	 * 		6 7 8
	 */
	
	public Box(int position)
	{
		solvedList = new ArrayList<Integer>();
		emptyList = new ArrayList<Space>();
		discovered = new ArrayList<Integer>();
		
		int yOffset = (position%3)*3;
		int xOffset = (int)(Math.floor(position/3)*3);
		
		int n = 0;
		for(int y = yOffset; y < yOffset+3; y++)
			for(int x = xOffset; x < xOffset+3; x++)
			{
				spaces[n] = main.puzzle[y][x];
				spaces[n].setParentBox(this);
				n++;
			}
	}
	
	public boolean contains(int target)
	{
		if(solvedList.contains(target) || discovered.contains(target))
			return true;
		else
			return false;
	}
	
	public boolean isDiscovered(int target)
	{
		if(discovered.contains(target))
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
	}
	
	public void setAsDiscovered(int target)
	{
		discovered.add(target);
	}
	
	public void search(int target)
	{
		Row currentR = null;
		Column currentC = null;
		
		ArrayList<Space> tempList = new ArrayList<Space>();
		
		//Checks if the target number is already found in the box
		if(!solvedList.contains(target))
		{
			System.out.println("Searching " + target);
			//Populates tempList with spaces whose row or column does not contain the target number
			for(Space s: emptyList)
			{
				if((!s.getParentRow().contains(target) && !s.getParentColumn().contains(target) && !s.isLocked()) || s.isAnnotated(target))
				{
					System.out.println("Empty (" + s.getParentColumn().id + ", " + s.getParentRow().id + ")");
					tempList.add(s);
				}
			}
			System.out.println("\n");
			
			switch(tempList.size())
			{
				//Switch is based on the number of spaces in tempList
				case 3:
				case 2:
					//Checks if the spaces in tempList are on the same Row
					boolean same = true;
					boolean aligned = false; // When true, the spaces line up in a row or column
					for(Space s: tempList)
					{
						if(currentR == null)
							currentR = s.getParentRow();
						else if(currentR != s.getParentRow())
							same = false;
					}
					//If tempList spaces are on the same row then that target number has been discovered in that row
					if(same)
					{
						currentR.setAsDiscovered(target);
						aligned = true;
					}
					else
					{
						same = true;
						//Checks if the spaces are on the same Column
						for(Space s: tempList)
						{
							if(currentC == null)
								currentC = s.getParentColumn();
							else if(currentC != s.getParentColumn())
								same = false;
						}
						//If tempList spaces are on the same column then that target number has been discovered in that column
						if(same)
						{
							currentC.setAsDiscovered(target);
							aligned = true;
							System.out.println("column discovered  " + target);
						}
					}
					
					if(tempList.size() < 3 || aligned){ // Will allow all combinations except for 3 spaces that do not line up in a row or column
					for(Space s: tempList) // Marks all checked spaces with annotations
						s.annotate(target);
					
					lockSearch(tempList);
					this.setAsDiscovered(target);
					}
					
					break;
				case 1:
					tempList.get(0).setAsSolved(target);
					//Space was solved for int target
					
			}
		}
	}
	
	public void lockSearch(ArrayList<Space> list)
	{
		/*
		 * Takes in a list of Spaces that have annotations.
		 * It copies all the annotations from all the Spaces in the list. Multiples of the same annotation will occur.
		 * It checks the list to see of the annotation occurs in the list twice (or three times) and that means it is eligible be locked onto the Space.
		 * 
		 * Then it compares the size of the eligible annotations with the number of spaces it is checking.
		 * If the sizes of both lists are the same then the spaces can be locked.
		 * 		e.g. There are two possible spaces and two possible numbers meaning no other number can go here.
		 * 				There are three possible spaces and three possible numbers meaning no other number can go there.
		 */
		/*if(list.size() == 2)
		{
			boolean check = true;
			int target2 = 0;
			for(int i = 1; i<=9; i++){
				if(i!=target){
					check = true;
					for(Space s: list)
					{
						System.out.println("(" + s.getParentColumn().id +", "+ s.getParentRow().id + ") is being checked" + i);
						if(!s.isAnnotated(i))
						{
							check = false;
							break;
						}
					}
					if(check){
						target2 = i;
						break;
					}
				}
			}
			if(check)
				lock(list);
		}
		else //list.size has to be of length 3
		{*/
			ArrayList<Integer> temp = new ArrayList<Integer>();
			ArrayList<Integer> targetAnnotations = new ArrayList<Integer>();
			
			for(Space s: list){ // For all spaces in the spaces being checked
				temp.addAll(s.getAnnotations()); // Get all their annotations
					for(int n: temp){ // For each annotation
						ArrayList<Space> spaceTemp = this.spacesWithAnnotation(n); // Get all spaces with annotation N
						if(list.containsAll(spaceTemp) && list.size() == spaceTemp.size()) // If the spaces with annotation N are exactly the same spaces as the spaces being checked (list)
							if(!targetAnnotations.contains((Integer) n)) // If targetAnnotations does not already contain N
								targetAnnotations.add(n);	// Add N as a possible annotation
					}
			}
			
			
			/*for(int i = 1; i<=9; i++)
				if(temp.contains(i)) // Checks if the list has the annotation
					if(temp.indexOf(i) != temp.lastIndexOf(i)) //Checks if the list has multiple of the annotation
					{
						targetAnnotations.add(i);
					}
			
			if(targetAnnotations.size() > 1)
			{
				for(int i: targetAnnotations)
					if(this.spacesWithAnnotation(i).size() != list.size())
						test = false;
			}
			else
				test = false; // Only one set of annotations so there is nothing to compare with and the spaces should not be locked.*/
			
			for(Space s: list){
				System.out.print("(" + s.getParentColumn().id + "," + s.getParentRow().id + ") ");
			}
			System.out.println("  " + targetAnnotations.toString());
			
			if(targetAnnotations.size() > 1) // If there are multiple annotations in the list
			{
				lock(list, targetAnnotations); // Lock the listed spaces because no other numbers can exist in them
			}
				
				
		//}
	}
	
	/*public void lock(ArrayList<Space> list)
	{
		Space s1 = list.get(0);
		Space s2 = list.get(1);
		
		System.out.print("Locking (" + s1.getParentColumn().id + ", " + s1.getParentRow().id + ")");
		s1.dispAnnot();
		System.out.print("Locking (" + s2.getParentColumn().id + ", " + s2.getParentRow().id + ")\n");
		s2.dispAnnot();
		
		list.get(0).lockWith(list.get(1));
	}*/
	
	public void lock(ArrayList<Space> list, ArrayList<Integer> targetAnnotations)
	{
		for(Space s: list)
		{
			s.getAnnotations().retainAll(targetAnnotations);
			s.lock();
		}
	}
	
	public ArrayList spacesWithAnnotation(int target)
	{
		ArrayList<Space> temp = new ArrayList<Space>();
		for(Space s: spaces)
		{
			if(s.isAnnotated(target))
				temp.add(s);
		}
		
		return temp;
	}
	
	public void addToEmpty(Space target)
	{
		//Adds the target Space to the emptyList of the box.
		//Means the Space does not contain a number
		emptyList.add(target);
	}
	
	public void display()
	{
		//Prints the contents of the box.
		for(int n = 0; n < 9; n++)
			System.out.print(spaces[n].getNumber());
		System.out.println("");
	}
}
