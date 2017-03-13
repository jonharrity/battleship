package engine;

public class PlayerEvaluator 
{
	private int minTurns;
	private int maxTurns;
	private float averageTurns;
	
	public PlayerEvaluator(ComputerBattleshipPlayer player, int runs)
	{
		minTurns = 100;
		maxTurns = 0;
		averageTurns = 0;
		
		int turns;

		for(int i=0 ; i<runs ; i++)
		{	
			BattleshipGame game = new BattleshipGame(player);
			turns = game.play();

			if(turns < minTurns)
				minTurns = turns;
			
			if(turns > maxTurns)
				maxTurns = turns;
			
			averageTurns += turns;
		}
	
		averageTurns /= runs;
	}
	
	public int minTurns()
	{
		return minTurns;
	}
	
	public int maxTurns()
	{
		return maxTurns;
	}
	
	public float averageTurns()
	{
		return averageTurns;
	}

}
