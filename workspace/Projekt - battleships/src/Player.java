import java.util.Scanner;
import java.util.Stack;

public class Player {

	Board board = new Board();
	Ships ships;
	String name;
	Scanner input = new Scanner(System.in);
	
	protected int cruiserLife = Ships.cruiser.getShipSize();
	protected int battleshipLife = Ships.battleship.getShipSize();
	protected int yachtLife = Ships.yacht.getShipSize();
	protected int submarineLife = Ships.submarine.getShipSize();
	protected int destroyerLife = Ships.destroyer.getShipSize();	
	protected int shotRow, shotCol, tempCol, tempRow, opponentShips = 5, opponentShipParts = 17, hitStreak = 0;
	protected float hitRate = 0, dmgRate = 0, shotsFired = 0, hitCount = 0, missCount = 0;
	protected boolean winner = false, hit;
	protected Stack<Integer> stack = new Stack<Integer>();	// stack med förslag för skjutkoordinater
	protected int lastHitRow = 0, lastHitCol = 0;
	
	
	// Skapar spelplaner åt spelaren
	public Player(String name)
	{
		this.name = name;
		board.createboard(board.getshipboard());
		board.createboard(board.getshootingboard());
	}
	
	// Låter spelaren avfyra skott mot motståndaren
	public void fire(Board oppBoard)
	{
		hit = true;
		boolean valid = true;
		
		while(hit)
		{
			do {
					try 
					{
						computeStats();
						board.printBoard(oppBoard.getshootingboard());
						
						// Stack med förslag på koordinater att avfyra baserat på tidigare träffar
						if(!stack.isEmpty())
						{
							System.out.println("Coordinate suggestions based on last hit: ");
		
							tempCol = (int) stack.pop();
							tempRow = (int) stack.pop();
							while(oppBoard.getshootingboard()[tempRow][tempCol] == 'X' || oppBoard.getshootingboard()[tempRow][tempCol] == 'O')
							{
								if(!stack.isEmpty()){
								tempCol = (int) stack.pop();
								tempRow = (int) stack.pop();
								}	
							}
							System.out.println("Col: " + tempCol);
							System.out.println("Row: " + tempRow);
						}
						System.out.println("Enter a column: ");
						shotCol = input.nextInt();
						System.out.println("Enter a row: ");
						shotRow = input.nextInt();
						
						// Kontrollerar att input är inom spelplanen
						if(validInput(shotRow, shotCol)) 
						{
							valid = false;
							
							// Kontrollerar om valda koordinater ger träff
							if(checkHit(oppBoard,shotRow,shotCol))
							{	shotsFired++;
								if(hit)
								{
								oppBoard.getshootingboard()[shotRow][shotCol] = 'X';
								oppBoard.getshipboard()[shotRow][shotCol] = 'X';
								hitCount++;
								lastHitRow = shotRow;
								lastHitCol = shotCol;
								// Vid träff läggs närliggande koordinater i en stack för tips på kommande skott
								fireCoordinates(lastHitRow, lastHitCol);
								
									// Kontrollerar vid träff om motståndaren har båtar kvar
									if(checkGameOver())
									{
										Main.gameOn = false;
										winner = true;
										System.out.println("\nAhoy "+ name+ ", YOU WON THE GAME!!!!");
										System.out.println("\nWinning stats: ");
										computeStats();
										
										hit = false;
									}
									else
									{	// Låter spelaren skjuta igen vid träff
										oppBoard.printBoard(oppBoard.getshootingboard());
										System.out.println("\nHIT! Fire another one, Captain!");
										playerChoice();
									}
								}
								else
								{	// markerar ut en miss på spelplanen
									oppBoard.getshootingboard()[shotRow][shotCol] = 'O';
									oppBoard.getshipboard()[shotRow][shotCol] = 'O';
									oppBoard.printBoard(oppBoard.getshootingboard());
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
						System.out.println("\n* Input must be an int value, Captain! *\n");
						input.next();
					}
				} while (valid);
		}
	}
	
	// Lägger koordinater i stacken 
	public void fireCoordinates(int row, int col)
	{
		if(row+1 <= 9)
		{
			stack.push(row+1);
			stack.push(col);
		}
		if(row-1 >= 0)
		{
		stack.push(row-1);
		stack.push(col);
		}
		if(col+1 <= 9)
		{
			stack.push(row);
			stack.push(col+1);
		}
		if(col-1 >= 0){
			stack.push(row);
			stack.push(col-1);
		}
	}
	
	// Kontrollerar om motståndaren har skepp kvar
	public boolean checkGameOver()
	{
		if(opponentShips == 0)
		{
			return true;
		}
		return false;
	}
	
	// Kontrollerar om input är inom spelplanen
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
	
	// Kontrollerar om skottkoordinater är en träff eller inte
	public boolean checkHit(Board oppBoard, int row, int col)
	{
		// Om träff minska liv för träffat skepp
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
	
	// Placerar skepp på givna koordinater 
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
					
					// Bestämmer riktning på skeppet
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
					System.out.println("\n* Input must be an int value *\n");
					input.next();
				}
			   } while (valid);		
		}
		board.printBoard(board.getshipboard());
		
		if(playerChoice())
		{
			board.printEmptyRows();
		}
		
	}
	
	// Val för att fortsätta spelet när man är redo
	public boolean playerChoice()
	{
		Scanner inputen = new Scanner(System.in);
		System.out.println("Press enter to continue!");
		String reader = inputen.nextLine();
		
		if(reader.equals(""))
		{
			return true;
		}
		else
			return false;
	}
	
	// Räknar ut nuvarande statistik 
	public void computeStats() {
		if (shotsFired == 0) {
			hitRate = 0;
			dmgRate = 0;
		} else {
			hitRate = (hitCount / shotsFired);
			hitRate *= 100;
			dmgRate = (hitCount / opponentShipParts);
			dmgRate *= 100;
			
		}
		System.out.println("Current hit rate: " + hitRate + "%");
		System.out.println("Current damage rate: " + dmgRate + "%");
		System.out.println("Number of shots fired: " + shotsFired);
		System.out.println("Number of hits: " + hitCount);
		System.out.println("Number of missed shots: " + missCount);
		System.out.println("Remaining ships to sink: " + opponentShips);
		
	}

	// Getter för hit rate
	public float getHitRate() {
		return hitRate;
	}
}
