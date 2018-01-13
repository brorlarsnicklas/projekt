import java.util.Scanner;

public class Player {

	Board board = new Board();
	Ships ships;
	String name;
	Scanner input = new Scanner(System.in);
	
	protected int cruiserLife = ships.cruiser.getShipSize();
	protected int battleshipLife = ships.battleship.getShipSize();
	protected int yachtLife = ships.yacht.getShipSize();
	protected int submarineLife = ships.submarine.getShipSize();
	protected int destroyerLife = ships.destroyer.getShipSize();	
	protected int shotRow, shotCol, opponentShips = 5;
	protected float hitRate = 0, shotsFired = 0, hitCount = 0, missCount = 0;
	protected boolean winner = false, hit;
	
	

	public Player(String name)
	{
		this.name = name;
		board.createboard(board.getshipboard());
		board.createboard(board.getshootingboard());
	}
	
	public void fire(Board oppBoard)
	{
		hit = true;
		boolean valid = true;
		
		while(hit)
		{
		do {
				try 
				{
					computeHitRate();
					board.printBoard(oppBoard.getshootingboard());
					System.out.println("Enter a column: ");
					shotCol = input.nextInt();
					System.out.println("Enter a row: ");
					shotRow = input.nextInt();
					
					if(validInput(shotRow, shotCol))
					{
						valid = false;
						
						if(checkHit(oppBoard,shotRow,shotCol))
						{	shotsFired++;
							if(hit)
							{
							oppBoard.getshootingboard()[shotRow][shotCol] = 'X';
							oppBoard.getshipboard()[shotRow][shotCol] = 'X';
							hitCount++;
							
								if(checkGameOver())
								{
									Main.gameOn = false;
									winner = true;
									System.out.println("Ahoy, YOU WON THE GAME!!!!");
									hit = false;
								}
								else
								{
									System.out.println("\nHIT! Fire another one, Captain!");	
								}
							}
							else
							{
								oppBoard.getshootingboard()[shotRow][shotCol] = 'O';
								oppBoard.getshipboard()[shotRow][shotCol] = 'O';
								System.out.println("Aaaaargh you missed! Better luck next time, Captain!\n");
							}
						}
					}
					else
					{
						System.out.println("Please enter a number between 0-9!" + "\n("+shotRow+","+shotCol+") is not a valid coordinate Captain!");
					}	
				} 
				catch (Exception e) 
				{
					System.out.println("\n* Input must be a int value, Captain! *\n");
					input.next();
				}
			} while (valid);
		}
	}
	
	public boolean checkGameOver()
	{
		if(opponentShips == 0)
		{
			return true;
		}
		return false;
	}
	
	public boolean validInput(int testRow, int testCol)
	{
		if(0 > testCol || testCol > 10)
		{
			return false;
		}
		
		if(0 > testRow || testRow > 10)
		{
			return false;
		}
	
		return true;
	}
	
	public boolean checkHit(Board oppBoard, int row, int col)
	{
		switch(oppBoard.getshootingboard()[row][col])
		{
		case 'O':
			System.out.println("You've got a miss here already, please try again Captain!");
			return false;
		case 'X':
			System.out.println("You've got a hit here already, please try again Captain!");
			return false;
		default:
			switch(oppBoard.getshipboard()[row][col])
			{
			case 'C':
				hit = true;
				cruiserLife--;
				if (cruiserLife == 0) 
				{
					opponentShips--;
					System.out.println("DESTROYED THE CRUISER!");
				}
				return true;
			case 'B':
				hit = true;
				battleshipLife--;
				if (battleshipLife == 0) 
				{
					opponentShips--;
					System.out.println("DESTROYED THE BATTLESHIP!");
				}
				return true;
			case 'D':
				hit = true;
				destroyerLife--;
				if (destroyerLife == 0) 
				{
					opponentShips--;
					System.out.println("DESTROYED THE DESTROYER!");
				}
				return true;
			case 'Y':
				hit = true;
				yachtLife--;
				if (yachtLife == 0) 
				{
					opponentShips--;
					System.out.println("DESTROYED THE YACHT!");
				}
				return true;
			case 'S':
				hit = true;
				submarineLife--;
				if (submarineLife == 0) 
				{
					opponentShips--;
					System.out.println("DESTROYED THE SUBMARINE!");
				}
				return true;
			case '~': 
				missCount++;
				hit = false;
				return true;
			default:
				return false;
			}
		}
	}
	
	public void shipPlacement()
	{	
		for(Ships ships : Ships.values())
		{
			board.printBoard(board.getshipboard());
			System.out.println("Name of the ship: "+ ships.getShipName()+"\nSize: "+ships.getShipSize() + " units");
			boolean valid = true;
			boolean missplaced = true;
			while(missplaced)
			do {
				try 
				{
					board.horizontal = true;
					System.out.println("\nPlace the ship by entering\nstarting coordinates and direction.");
					System.out.println("\nEnter a column: ");
					board.startCol = input.nextInt();
					
					System.out.println("Enter a row: ");
					board.startRow = input.nextInt();
					
					System.out.println("Direction of the ship is horizontal.\nDo you want to switch to vertical? \n1: Yes\n2: no");
					board.shipDirection = input.nextInt();
					
					if(board.shipDirection == 1)
					{
						board.horizontal = false;
					}
					
					if(!board.validInput(board.startRow, board.startCol, board.horizontal, ships.getShipSize()))
					{
						System.out.println("Boat missplaced!\nTry again");
					}
					else
					{
						if(board.checkEmptySpot(board.startRow, board.startCol, board.horizontal, ships.getShipSize()))
						{
							missplaced = false;
							board.setShip(board.startRow, board.startCol, board.horizontal, ships.getShipSize(), ships.getShipDescription());	
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
	}
	
	public void computeHitRate() {
		if (shotsFired == 0) {
			hitRate = 0;
		} else {
			hitRate = (hitCount / shotsFired);
			hitRate *= 100;
		}
		System.out.println("Current hit rate: " + hitRate + "%");
		System.out.println("Number of shots fired: " + shotsFired);
		System.out.println("Number of hits: " + hitCount);
		System.out.println("Number of missed shots: " + missCount);
		System.out.println("Remaining ships to sink: " + opponentShips);
		
	}

	public float getHitRate() {
		return hitRate;
	}
}
