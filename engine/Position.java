package engine;

public class Position
{
	
	private char row;
	private int col;

	public Position(char newRow, int newCol)
	{
		row = newRow;
		col = newCol;
	}
	
	public Position(int rowIndex, int colIndex)
	{
		row = (char) (rowIndex + 65);
		col = colIndex + 1;
	}
	
	public char row()
	{
		return row;
	}
	
	public int column()
	{
		return col;
	}
	
	public int rowIndex()
	{
		return ((int) row) - 65;
	}
	
	public int columnIndex()
	{
		return col - 1;
	}
	
	public String toString()
	{
		return String.valueOf(row) + "-" + String.valueOf(col);
	}
	
}
