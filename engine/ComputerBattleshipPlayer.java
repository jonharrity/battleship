package engine;

public class ComputerBattleshipPlayer extends BattleshipPlayer
{
	
	@Override
	public void updatePlayer(Position pos, boolean hit, char initial, 
	String boatName, boolean sunk, boolean gameOver, boolean tooManyTurns, 
	int turns)
	{
		updateGrid(pos, hit, initial);
	}
	
	@Override
	public String playerName()
	{
		return "Computer Battleship Player";
	}
	
	@Override
	public Position shoot()
	{
		int row, column;
		Position pos;
		
		do
		{
			row = (int) (Math.random() * 10);
			column = (int) (Math.random() * 10);
			pos = new Position(row, column);
		}
		while(! getGrid().empty(pos));
	
		return pos;
	}
	
	@Override
	public void startGame()
	{
		initializeGrid();
	}
	
	
	
	
}
