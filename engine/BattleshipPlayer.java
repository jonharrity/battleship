package engine;

import java.util.Scanner;

public class BattleshipPlayer 
{
	private Scanner scanner;
	private BattleshipGrid grid;
	private String playerName = null;

	BattleshipPlayer()
	{
		playerName = null;
	}
	
	public void startGame()
	{
		initializeGrid();
		if(playerName == null)
		{
			setName();
			System.out.println("Welcome to battleship\n");
		}
	}
	
	public String playerName()
	{
		return playerName;
	}
	
	public Position shoot()
	{		
		Scanner mindReader = getScanner();
		String row;
		int column;
		
		System.out.print("Please enter a row (A-J): " );
		row = mindReader.next();
		while(! isValidRow(row))
		{
			System.out.print("error message, enter a row, just one,"
			+ " uppercase inbetween A and J (A-J): ");
			row = mindReader.next();
		}
		System.out.print("now enter a column (1-10): ");
		column = mindReader.nextInt();
		while (! isValidColumn(column))
		{
			System.out.print("error message, enter a column, just one,"
			+ "between 1 and 10: ");
			column = mindReader.nextInt();
		}
		System.out.println();
		return new Position(row.charAt(0), column);
	}
	
	public void updateGrid(Position pos, boolean hit, char initial)
	{
		grid.shotAt(pos, hit, initial);
	}
	
	public BattleshipGrid getGrid()
	{
		return grid;
	}
	
	public void initializeGrid()
	{
		grid = new BattleshipGrid();
	}
	
	public void updatePlayer(Position pos, boolean hit, char initial, String 
	boatName, boolean sunk, boolean gameOver, boolean tooManyTurns, int turns)
	{
		System.out.print("Turn " + turns + ": ");
		System.out.print("Your shot at position " + pos);
		
		updateGrid(pos, hit, initial);
		
		if(hit)
			System.out.println(" has hit a " + boatName + (sunk ? 
			" and it has sunk." : "."));
		else
			System.out.println(" was a miss.");
		
		if(allSunk())
		{			
			System.out.println("Congrats you've sunk all the boats and won");
		}
		else if(gameOver || tooManyTurns)
			System.out.println("The game is over" + (tooManyTurns ? 
			" (it has been going on  too long)" : "") + ".");
		System.out.println();		
		displayGrid();
		System.out.println();
	}
	
	private boolean allSunk()
	{//brute force, if there 17 hits theyre all sunk
		int hitCount = 0;
		for(int i=0 ; i<10 ; i++)
			for(int j=0 ; j<10 ; j++)
				if(grid.hit(new Position(i, j)))
					hitCount++;
		return hitCount == 17;
	}
	
	void displayGrid()
	{
		System.out.print(" ");
		//display columns
		for(int i=1 ; i<=10 ; i++)
			System.out.print(" " + i);
		System.out.println();
		//loop through each row
		for(int i=0 ; i<10 ; i++)
		{
			//indicate row
			System.out.print((char) (i+65));
			//loop through each column
			for(int j=0 ; j<10 ; j++)
			{
				printPos(new Position(i, j));
			}
			System.out.println();
		}
	}
	
	private void printPos(Position pos)
	{
		String s;
		
		if(grid.empty(pos))
			s = ".";
		else if(grid.miss(pos))
			s = "*";
		else
			s = String.valueOf(grid.boatInitial(pos));
		
		System.out.printf(" %s", s);
	}
	
	private boolean isValidRow(String s)
	{
		if(s.length() != 1)
			return false;
		int row = (int) s.charAt(0);
		return row >= 65 && row <= 74;
		
	}
	
	//not the index, the actual like row thing 1-10
	private boolean isValidColumn(int n)
	{
		return n >= 1 && n <= 10;
	}
	
	private void setName()
	{
		Scanner scanner = getScanner();
		System.out.print("Enter your name: ");
		playerName = scanner.next();
	}
	
	//to not make a new scanner each time its needed
	private Scanner getScanner()
	{
		if(scanner == null)
			return new Scanner(System.in);
		else
			return scanner;
	}
	
}
