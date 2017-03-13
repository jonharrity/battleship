package engine;

import java.util.ArrayList;


public class Boat 
{
	
	private String name;
	private Position startPos;
	private String orientation;
	private ArrayList<Position> hitList;

	public Boat(String theName, Position startingPos, String theOrientation)
	{
		name = theName;
		startPos = startingPos;
		orientation = theOrientation;
		
		hitList = new ArrayList<Position>();
	}
	
	public String name()
	{
		return name;
	}
	
	public char abbreviation()
	{
		return name.charAt(0);
	}
	
	public int size()
	{
		int size = 0;
		
		switch (name)
		{
			case "Aircraft Carrier":
				size = 5;
				break;
			
			case "Battleship":
				size = 4;
				break;
				
			case "Cruiser":
				
			case "Submarine": 
				size = 3;
				break;
				
			case "Destroyer":
				size = 2; 
				break;
				
			default: 
				RuntimeException up = new RuntimeException("You spelled the name wrong");
				throw up;//ew
				
		}
		
		return size;
	}
	
	public boolean onBoat(Position pos)
	{
		if (orientation.equals("horizontal"))
		{
			if (startPos.row() != pos.row())
				return false;
			else
			{
				if (pos.column() < startPos.column() || pos.column() >= startPos.column() + size())
					return false;
			}
			
			return true;
		}
		else 
		{
			if (startPos.column() != pos.column())
				return false;
			else
			{
				if (pos.row() < startPos.row() || pos.row() >= startPos.row() + size())
					return false;
			}
			
			return true;
			
		}
	}
	
	public boolean isHit(Position pos)
	{
		return doesHitListContainPosition(pos);
	}
	
	public void hit(Position pos)
	{
		if (onBoat(pos))
			recordHit(pos);
	}
	
	public boolean sunk()
	{
		return hitList.size() >= size();
	}
	
	public Position position()
	{
		return startPos;
	}
	
	public String direction()
	{
		return orientation;
	}
	
	private void recordHit(Position pos)
	{
		if (! doesHitListContainPosition(pos))
			hitList.add(pos);
	}
	
	private boolean doesHitListContainPosition(Position meanPos)
	{
		for (int i=0 ; i<hitList.size() ; i++)
		{
			if (doPositionsMatch(hitList.get(i), meanPos))
				return true;
		}
		return false;
	}
	
	private boolean doPositionsMatch(Position a, Position b)
	{
		return (a.row() == b.row()) && (a.column() == b.column());
	}
	
	
}
