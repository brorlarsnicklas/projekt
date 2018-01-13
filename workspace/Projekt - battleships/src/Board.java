import java.util.Scanner;

public class Board {

	private final int ROW = 10;
	private final int COL = 10;
	private char[][] shipboard = new char[ROW][COL];
	private char[][] shootingboard = new char[ROW][COL];
	protected int startRow, startCol, shipDirection;
	protected boolean horizontal = true;
	Scanner input = new Scanner(System.in);
	
	public void Board()
	{
		createboard(shipboard);
		createboard(shootingboard);
	}
	
	// Skapar en spelplan
	public char[][] createboard(char[][] board) {
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				board[i][j] = '~';
			}
		}
		return board;
	}
	
	
	// Printar spelplanen
	public void printBoard(char[][] board) {
		System.out.println("\n  0  1  2  3  4  5  6  7  8  9");
		for (int i = 0; i < ROW; i++) {
			System.out.print(i);
			for (int j = 0; j < COL; j++) {
				System.out.print(" " + board[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("");
	}
	
	// Sätter ut skeppet på rätt koordinater
	public void setShip(int startRow, int startCol, boolean horizontal, int shipsize, char shipchar)
	{
		if(horizontal)
		{
			for (int j = 0; j < shipsize; j++) {
				shipboard[startRow][startCol + j] = shipchar;
			}
		}
		else
		{
			for (int j = 0; j < shipsize; j++) {
				shipboard[startRow+j][startCol] = shipchar;
			}
		}
	}

	public boolean checkEmptySpot(int row, int col, boolean horizontal, int shipsize)
	{	// Räknar så att det inte ligger skepp på följande platser, tidigare tog den bara 1:a platsen
		int unitCount = shipsize;
		
		for(int i = 0; i < shipsize; i++)
		{
			if((horizontal && (shipboard[row][col+i] == '~')) || (!horizontal && (shipboard[row + i][col] == '~')))
			{
					unitCount--;	
			}
		}
		
		if(unitCount == 0)
		{
			return true;
		}
		
		return false;
	}
	
/*	
	// Tar input för placering av skepp på spelplanen
	public void shipPlacement()
	{	
		for(Ships ships : Ships.values())
		{
			printBoard(shipboard);
			System.out.println("Name of the ship: "+ ships.getShipName()+"\nSize: "+ships.getShipSize() + " units");
			boolean valid = true;
			boolean missplaced = true;
			while(missplaced)
			do {
				try 
				{
					horizontal = true;
					System.out.println("\nPlace the ship by entering\nstarting coordinates and direction.");
					System.out.println("\nEnter a column: ");
					startCol = input.nextInt();
					
					System.out.println("Enter a row: ");
					startRow = input.nextInt();
					
					System.out.println("Direction of the ship is horizontal.\nDo you want to switch to vertical? \n1: Yes\n2: no");
					shipDirection = input.nextInt();
					
					if(shipDirection == 1)
					{
						horizontal = false;
					}
					
					if(!validInput(startRow, startCol, horizontal, ships.getShipSize()))
					{
						System.out.println("Boat missplaced!\nTry again");
					}
					else
					{
						if(checkEmptySpot(startRow, startCol, horizontal, ships.getShipSize()))
						{
							missplaced = false;
							setShip(startRow, startCol, horizontal, ships.getShipSize(), ships.getShipDescription());	
						}
						else
						{
							System.out.println("These coordinates are already covered by a ship, try again!");
						}
					}
					valid = false;
				} 
				catch (Exception e) 
				{
					System.out.println("\n* Input must be a int value *\n");
					input.next();
				}
			   } while (valid);		
		}
	} */
	
	// Kollar ifall spelarens input är inom spelplanen
	public boolean validInput(int testRow, int testCol, boolean horizontal, int shipSize)
	{
		if(0 > testCol || testCol > 10)
		{
			return false;
		}
		
		if(0 > testRow || testRow > 10)
		{
			return false;
		}
		
		if(horizontal && (testCol + shipSize) > 10)
		{
			return false;
		}
		if(!horizontal && (testRow + shipSize) > 10)
		{
			return false;
		}
		
		return true;
		
	}
	
	// Getter för spelplanen
	public char[][] getshipboard()
	{
		return shipboard;
	}
	public char[][] getshootingboard()
	{
		return shootingboard;
	}
	
}
