package engine;

public class BattleshipGame 
{
	private int currentTurn;
	private BattleshipPlayer player;
	private Ocean ocean;

	BattleshipGame(BattleshipPlayer dePlayer)
	{
		currentTurn = 1;
		ocean = new Ocean();
		ocean.placeAllBoats();
		player = dePlayer;
		player.startGame();
	}
	
	public int play()
	{
		boolean stayinalive = true;
		while(stayinalive)
		{
			Position pos = player.shoot();
			ocean.shootAt(pos);
	
			if(ocean.allSunk() || currentTurn >= 100)
				stayinalive = false;
			else if(currentTurn == 99)
			{
				stayinalive = false;
				currentTurn++;
			}
			else
				currentTurn++;
			
			if(ocean.hit(pos))
			{
				player.updatePlayer(pos, true, ocean.boatInitial(pos), 
				ocean.boatName(pos), 
				ocean.sunk(pos), !stayinalive, currentTurn >= 100,
				currentTurn);
			}
			else
				player.updatePlayer(pos, false, (char) 1, null, 
				false, !stayinalive, currentTurn >= 100, currentTurn);
		}

		return currentTurn;
	}
}
