package engine;
import java.util.ArrayList;


public class Ocean
{
	private String[] boatNames = {"Aircraft Carrier", "Battleship", "Cruiser", "Destroyer", "Submarine"};

	protected ArrayList<Boat> boats = new ArrayList<Boat>();

	public void placeBoat(String boatName, String direction, Position pos) throws 
	Exception
	{
		
		Boat potentialBoat = new Boat(boatName, pos, direction);
		
		if (isBoatAt(getBoatLocs(potentialBoat)))
		{
			throw new Exception("A boat is already at that location sorry");
		}
		else if (! isBoatOnGrid(potentialBoat))
		{
			throw new Exception("Nice try that boats not even on the grid");
		}
		else
		{
			boats.add(potentialBoat);
		}
	}
	
	public void placeAllBoats()
	{
		boolean hasPlacedBoat;

		for(int boat=0 ; boat<5 ; boat++)
		{
			hasPlacedBoat = false;
			
			while(! hasPlacedBoat)
			{
				try
				{					
					placeBoat(boatNames[boat], getRandomDirection(), 
					getRandomPosition());
					hasPlacedBoat = true;
				}
				catch(Exception e)
				{
					hasPlacedBoat = false;
				}
			}
		}
	}
	
	private String getRandomDirection()
	{
		return (int) (Math.random() * 2) == 0 ? "horizontal" : "vertical";
	}
	
	private Position getRandomPosition()
	{
		return new Position(getRandomIndex(), getRandomIndex());
	}
	
	private int getRandomIndex()
	{
		return (int) (Math.random() * 10);
	}
	
	public void shootAt(Position pos)
	{
		Boat victim = getBoat(pos);
		
		if (victim == null)
			return;
		
		victim.hit(pos);
	}
	
	public boolean hit(Position pos)
	{
		Boat boat = getBoat(pos);
		
		if (boat == null)
			return false;
		else
			return boat.isHit(pos);
	}
	
	public char boatInitial(Position pos)
	{
		return getBoat(pos).abbreviation();
	}
	
	public String boatName(Position pos)
	{
		return getBoat(pos).name();
	}
	
	public boolean sunk(Position pos)
	{
		Boat boat = getBoat(pos);
		
		if (boat == null)
			return false;
		else
			return boat.sunk();
	}
	
	public boolean allSunk()
	{
		for (Boat boat : boats)
		{
			if (!boat.sunk())
				return false;
		}
		
		return true;
	}
	
	void printLocs()
	{
		for(int i=0 ; i<10 ; i++)
		{
			for(int j=0 ; j<10 ; j++)
			{
				System.out.print(isBoatAt(new Position(i, j)) ? "*" : "-");
			}
			System.out.println();
		}
	}
	
	private Boat getBoat(Position pos)
	{
		for (int i=0 ; i<boats.size() ; i++)
		{
			if (boats.get(i).onBoat(pos))
			{
				return boats.get(i);
			}
		}
		
		return null;
	}
	
	private boolean isBoatAt(Position pos)
	{
		Boat datBoat = getBoat(pos);
		
		return datBoat != null;
	}
	
	//return true if theres a boat at any positions in posi
	private boolean isBoatAt(ArrayList<Position> posi)
	{
		for (Position pos : posi)
		{
			if (isBoatAt(pos))
				return true;
		}
		
		return false;
	}
	
	//returns all the locations on the boat
	private ArrayList<Position> getBoatLocs(Boat boat)
	{
		ArrayList<Position> posi = new ArrayList<Position>();
		posi.add(boat.position());
		
		if (boat.direction().equals("horizontal"))
		{
			for(int i=1 ; i<boat.size() ; i++)
			{
				posi.add(getPositionToRight(posi.get(posi.size() - 1)));
			}
		}
		else
		{
			for(int i=1 ; i<boat.size() ; i++)
			{
				posi.add(getPositionBelow(posi.get(posi.size() - 1)));
			}
		}
		

		return posi;
		
	}
	
	private Position getPositionToRight(Position pos)
	{
		char row = pos.row();
		int newCol = pos.column() + 1;
		
		return new Position(row, newCol);
	}
	
	private Position getPositionBelow(Position pos)
	{
		int rowAnsii = (int) pos.row();
		int rowBelowAnsii = rowAnsii + 1;
		
		char newRow = (char) rowBelowAnsii;
		int col = pos.column();
		
		return new Position(newRow, col);
	}
	
	private boolean isBoatOnGrid(Boat suspiciousBoat)
	{
		ArrayList<Position> posi = getBoatLocs(suspiciousBoat);
		
		for(Position pos : posi)
		{
			if (! isPositionOnGrid(pos))
				return false;
		}
		
		return true;
	}
	
	private boolean isPositionOnGrid(Position sketchyPosition)
	{
		if (sketchyPosition.rowIndex() < 0 || sketchyPosition.rowIndex() > 9)
			return false;
		else if (sketchyPosition.columnIndex() < 0 || 
				 sketchyPosition.columnIndex() > 9)
			return false;
		else if ((int) sketchyPosition.row() >= (int) 'K')
			return false;
		else
			return true;
	}
}
