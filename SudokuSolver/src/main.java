import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class main {

	static Space[][] puzzle = new Space[9][9];
	static Row[] rows = new Row[9];
	static Column[] columns = new Column[9];
	static Box[] boxes = new Box[9];
	
	public static void main(String[] args) {
		
		
		for(int y = 0; y < 9; y++)
			for(int x = 0; x < 9; x++)
				puzzle[y][x] = new Space();
				
		Scanner s = null;
		
		try {
			s = new Scanner(new File("sudoku2.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int n = 0; n < 9; n++)
		{
			rows[n] = new Row(n);
			columns[n] = new Column(n);
			boxes[n] = new Box(n);
		}
		
		for(int y = 0; y < 9; y++)
			for(int x = 0; x < 9; x++)
				puzzle[y][x].setAsSolved(s.nextInt());
		System.out.println("Original: ");
		displayPuzzle();
		
		boolean solved = false;
		int i = 0;
		while(!solved && i < 20)
		{
			search();
			solved = checkFinished();
			i++;
			if(i%100 == 0)
			{
				System.out.println(i);
				displayPuzzle();
			}
			checkColumns();
			checkRows();
			displayPuzzle();
		}
		System.out.println("Solved: " + i + " iterations");
		displayPuzzle();
		
		
		
	}
	
	public static void checkColumns()
	{
		//Checks each column individually to see if any number can exist in only one square of that column
		
		for(Column c: columns) // For each column in the puzzle
		{
			/*
			 * Checks each column individually to see if any number can exist in only one square of that row
			 */
			Space[] spaceTemp = c.getSpaceList(); // Get all the empty square of that column
			for(int i = 1; i <= 9; i++) // For each number
			{
				ArrayList<Space> temp = new ArrayList<Space>();
				
					for(Space s: spaceTemp) // For each empty space in the column
					{
						if(s.getNumber() == 0)
							if(((!s.getParentRow().contains(i) && !s.getParentBox().contains(i) && !c.contains(i)) || s.isAnnotated(i))){
								//if(s.isAnnotated(i)){ // If the Row or Box of the square doesn't contain the number
									temp.add(s);
									System.out.println("   COLcheck3---" + i);
								}
					}
				
				if(temp.size() == 1)
				{
					temp.get(0).setAsSolved(i);
					temp.clear();
				}
			}
		}
	}
	
	public static void checkRows()
	{
		
		
		for(Row r: rows) // For each row in the puzzle
		{
			
			Space[] spaceTemp = r.getSpaceList(); // Get all the spaces of that row
			for(int i = 1; i <= 9; i++) // For each number
			{
				/*
				 * Checks each row individually to see if any number can exist in only one square of that row
				 */
				ArrayList<Space> temp = new ArrayList<Space>();
				
					for(Space s: spaceTemp) // For each empty space in the row
					{
						if(s.getNumber() == 0)
							if((!s.getParentColumn().contains(i) && !s.getParentBox().contains(i) && !r.contains(i)) || s.isAnnotated(i)){
								//if(s.isAnnotated(i)){ // If the Column or Box of the square doesn't contain the number
								temp.add(s);
								System.out.println("   check---" + i);
							}
				}
				if(temp.size() == 1)
				{
					temp.get(0).setAsSolved(i);
					temp.clear();
				}
			}
			for(Space s: spaceTemp)
			{
				/*
				 * Checks each space individually to see if there is only one number that can exist in that space
				 */
				ArrayList<Integer> temp = new ArrayList<Integer>();
				for(int i = 1; i <= 9; i++)
				{
					if(s.getNumber() == 0)
						if((!s.getParentColumn().contains(i) && !s.getParentBox().contains(i) && !r.contains(i)) || s.isAnnotated(i)){
							//if(s.isAnnotated(i)){ // If the Column or Box of the square doesn't contain the number
							temp.add(i);
							System.out.println("   check2---" + i);
						}
				}
				if(temp.size() == 1)
				{
					s.setAsSolved(temp.get(0));
					temp.clear();
				}
			}
		}
	}
	
	public static void displayPuzzle()
	{
		for(int y = 0; y < 9; y++){
			for(int x = 0; x < 9; x++)
			{
				System.out.print(puzzle[y][x].getNumber() + " ");
				if((x+1)%3 == 0)
					System.out.print(" ");
			}
			System.out.println("");
			if((y+1)%3 == 0)
				System.out.println("");
		}
		System.out.println("----------------");
	}
	
	public static boolean checkFinished()
	{
		for(int y = 0; y < 9; y++){
			for(int x = 0; x < 9; x++)
			{
				if(puzzle[y][x].getNumber() == 0)
					return false;
			}
		}
		return true;
	}
	
	public static void search()
	{
			for(int n = 1; n <= 9; n++)
				for(Box b: boxes)
					b.search(n);
	}

}
