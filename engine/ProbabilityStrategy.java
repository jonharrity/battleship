package engine;

	//Author: Jon Harrity

import java.util.ArrayList;


public class ProbabilityStrategy extends ComputerBattleshipPlayer
{
	public class BT
	{
		private Position mepos;
		private int min, max, size, dir, type;
		
		public BT(Position thePos, int theSize, int theDir, int theType)
		{
			mepos = thePos;
			size = theSize;
			dir = theDir;
			type = theType;
			
			if(dir == 0)
			{
				min = thePos.columnIndex();
				max = thePos.columnIndex() + theSize - 1;
			}
			else
			{
				min = thePos.rowIndex();
				max = thePos.rowIndex() + theSize - 1;
			}
		}
		
		public boolean contains(Position pos)
		{
			if(dir == 0)
				return (pos.columnIndex() >= min) && (pos.columnIndex() <= max) && (mepos.rowIndex() == pos.rowIndex());
			else
				return (pos.rowIndex() >= min) && (pos.rowIndex() <= max) && (mepos.columnIndex() == pos.columnIndex());
		}
		
		public Position pos()
		{
			return mepos;
		}
		
		public int dir()
		{
			return dir;
		}
		
		public int type()
		{
			return type;
		}
		
		public int size()
		{
			return size;
		}
	}

	
	private double[][] dem;
	private ArrayList<BT> bti;
	private double[] pa = new double[5];

	private final int[] boatSizes = {5, 4, 3, 3, 2};
	
	@Override
	public Position shoot()
	{	
		return getFirstHighestPosition();
	}

	@Override
	public void startGame()
	{
		initializeGrid();
		initializeVariables();
	}

	@Override
	public void updatePlayer(Position pos, boolean hit, char initial, String
	boatName, boolean sunk, boolean gameOver, boolean tooManyTurns, int turns)
	{
		super.updatePlayer(pos, hit, initial, boatName, sunk, gameOver, tooManyTurns, turns);
		
		updateGrid(pos, hit, initial);	
		
		if(hit)//only affects boats of given initial
		{
			if(sunk)
				removeBTIOfBoat(getBoatType(initial));
			else
				removeBTIOfBoatNotContaining(getBoatType(initial), pos);
			
			removeBTINotOfBoatContaining(getBoatType(initial), pos);
		}
		else
			removeBTIContaining(pos);
		
		
		populateProbability();
		
	}
	
	@Override
	public String playerName()
	{
		return "Probability Strategy";
	}
	
	private void populateProbability()
	{
		dem = new double[10][10];
		
		setPA();
		
		Position[] posi;
		
		for(BT bt : bti)
		{
			posi = getLocs(bt.pos(), bt.size(), bt.dir());
			
			for(Position pos : posi)
			{
				dem[pos.rowIndex()][pos.columnIndex()] += pa[bt.type()];
			}
		}
	}
	
	private void setPA()
	{
		for(int i=0 ; i<5 ; i++)
		{
			pa[i] = 1.0 / getBTCount(i);
		}
	}
	
	private int getBTCount(int type)
	{
		int count = 0;
		
		for(BT bt : bti)
			if(bt.type() == type)
				count++;
	
		return count == 0 ? 1 : count;
	}
	
	private Position[] getLocs(Position startLoc, int size, int orientation)
	{
		Position[] posi = new Position[size];
		for(int i=0 ; i<size ; i++)
		{
			if(orientation == 0)
				posi[i] = new Position(startLoc.rowIndex(), startLoc.columnIndex() + i);
			else
				posi[i] = new Position(startLoc.rowIndex() + i, startLoc.columnIndex());
		}

		return posi;
	}

	private Position getFirstHighestPosition()
	{
		double maxProbability = -1;

		int mRow = -1, mCol = -1;
		
		for(int row=0 ; row<10 ; row++)
		{
			for(int col=0 ; col<10 ; col++)
			{
				if(dem[row][col] > maxProbability)
				{
					if(getGrid().empty(new Position(row, col)))
					{
						maxProbability = dem[row][col];
						mRow = row;
						mCol = col;
					}
					
				}
			}
		}
		
		return new Position(mRow, mCol);
	}
	
	private void removeBTIOfBoatNotContaining(int type, Position pos)
	{
		for(int i=0 ; i<bti.size() ; i++)
			if(bti.get(i).type() == type && (! bti.get(i).contains(pos)))
			{
				bti.remove(i);
				i--;
			}
	}
	
	private void removeBTIOfBoat(int type)
	{
		for(int i=0 ; i<bti.size() ; i++)
			if(bti.get(i).type() == type)
			{
				bti.remove(i);
				i--;
			}
	}
	
	private void removeBTIContaining(Position pos)
	{
		for(int i=0 ; i<bti.size() ; i++)
			if(bti.get(i).contains(pos))
			{
				bti.remove(i);
				i--;
			}
	}
	
	private void removeBTINotOfBoatContaining(int type, Position pos)
	{
		for(int i=0 ; i<bti.size() ; i++)
			if(bti.get(i).type() != type && bti.get(i).contains(pos))
			{
				bti.remove(i);
				i--;
			}	
	}
	
	private int getBoatType(char initial)
	{
		if(initial == 'a' || initial == 'A')
			return 0;
		else if(initial == 'b' || initial == 'B')
			return 1;
		else if(initial == 'c' || initial == 'C')
			return 2;
		else if(initial == 's' || initial == 'S')
			return 3;
		else 
			return 4;
	}
	
	private void initializeVariables()
	{
		bti = new ArrayList<BT>();
		populateBTI();
		populateProbability();
	}
	
	private void populateBTI()
	{
		ArrayList<Position> posi;
		
		for(int i=0 ; i<5 ; i++)
		{
			for(int dir=0 ; dir<2 ; dir++)
			{
				posi = getPotentialPosi(boatSizes[i], dir);
				for(Position start : posi)
					bti.add(new BT(start, boatSizes[i], dir, i));
			}
		}
	}
	
	private ArrayList<Position> getPotentialPosi(int size, int orientation)
	{
		ArrayList<Position> posi = new ArrayList<Position>();
		int rowBound, colBound;
		
		if(orientation == 0)
		{
			rowBound = 9;
			colBound = 10 - size;
		}
		else
		{
			rowBound = 10 - size;
			colBound = 9;
		}
		
		for(int i=0 ; i<=rowBound ; i++)
			for(int j=0 ; j<=colBound ; j++)
				posi.add(new Position(i, j));
		
		return posi;
	}

}
