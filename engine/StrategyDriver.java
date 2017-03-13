package engine;

public class StrategyDriver 
{
	
	public static void main(String[] args)
	{
		int games = 1000;
		
		anal(games, new ProbabilityStrategy(), true);
	}
	
	public static void anal(int games, ComputerBattleshipPlayer player, boolean displayTimes)
	{
		long startTime = System.currentTimeMillis();
		
		PlayerEvaluator pepolice = new PlayerEvaluator(player, games);
		
		System.out.println("--Player evaluator--");
		System.out.println("Games: " + games);
		System.out.println("Min turns: " + pepolice.minTurns());
		System.out.println("Max turns: " + pepolice.maxTurns());
		System.out.println("Average turns: " + pepolice.averageTurns());	
		
		long endTime = System.currentTimeMillis();
		
		if(displayTimes)
		{
			System.out.println("Time took: " + (endTime - startTime) + " milliseconds");
		}
	}

}


/*

--Player evaluator--
Games: 500000
Min turns: 17
Max turns: 72
Average turns: 44.05419
Time took: 555028 milliseconds

*/
