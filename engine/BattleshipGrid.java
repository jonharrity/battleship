package engine;

public class BattleshipGrid 
{
	int[][] grid = new int[10][10];
	//each element has 3 possible values: 0, 1, or the ansii code of the ship 
	//initial

	public void shotAt(Position pos, boolean hit, char initial)
	{
		int newValue = 1;
		
		if(hit)
			newValue = (int) initial;
		
		grid[pos.rowIndex()][pos.columnIndex()] = newValue;
	}
	
	public boolean hit(Position pos)
	{
		return grid[pos.rowIndex()][pos.columnIndex()] > 1;
	}
	
	public boolean miss(Position pos)
	{
		return grid[pos.rowIndex()][pos.columnIndex()] == 1;
	}
	
	public boolean empty(Position pos)
	{
		return grid[pos.rowIndex()][pos.columnIndex()] == 0;
	}
	
	public char boatInitial(Position pos)
	{
		return (char) grid[pos.rowIndex()][pos.columnIndex()];
	}
	
	
}
