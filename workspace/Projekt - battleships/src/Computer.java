import java.util.HashMap;
import java.util.Random;
import java.util.Stack;

public class Computer extends Player 
{
	private final int max = 10;
	Random random = new Random(); // För att generera koordinater 
	
	public Computer(String name) 
	{
		super(name);
		// TODO Auto-generated constructor stub
	}

	// Placerar skepp med random koordinater
	@Override
	public void shipPlacement() 
	{
		for(Ships ships : Ships.values())
		{
			board.printBoard(board.getshipboard());
			System.out.println("Name of the ship: "+ ships.getShipName()+"\nSize: "+ships.getShipSize() + " units");
			boolean missplaced = true;
			while(missplaced)
			{
					board.horizontal = true;
					board.startCol = random.nextInt(max);
					board.startRow = random.nextInt(max);
					board.shipDirection = random.nextInt(2);
					System.out.print(board.shipDirection);

					System.out.print("col: " + board.startCol+ " Row: "+ board.startRow + " dir: " + board.shipDirection);
					
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
			}
		}
		board.printBoard(board.getshipboard());
		board.printEmptyRows();
	}


	// Avfyrar skott med random koordinater
	@Override
	public void fire(Board oppBoard) 
	{	
		hit = true;
		while (hit) 
		{
			computeStats();
			board.printBoard(oppBoard.getshootingboard());
			
			// Vid träff skjuter datorn på närliggande koordinater med hjälp av stacken 
			if(stack.isEmpty())
			{
				shotRow = random.nextInt(max);
				shotCol = random.nextInt(max);	
			}
			else
			{
				shotCol = (int) stack.pop();
				shotRow = (int) stack.pop();
				
				//System.out.println("SR: " + shotRow + " SC: " + shotCol);
				//System.out.println(stack);
			}

			if (checkHit(oppBoard, shotRow, shotCol)) 
			{
				shotsFired++;
				System.out.println("Row: "+ shotRow+ " Col: "+ shotCol);
				playerChoice();
				if (hit) 
				{
					oppBoard.getshootingboard()[shotRow][shotCol] = 'X';
					oppBoard.getshipboard()[shotRow][shotCol] = 'X';
					hitCount++;
					lastHitRow = shotRow;
					lastHitCol = shotCol;
					fireCoordinates(lastHitRow, lastHitCol);
					
					if (checkGameOver()) 
					{
						Main.gameOn = false;
						winner = true;
						oppBoard.printBoard(oppBoard.getshootingboard());
						System.out.println("Ahoy, " + name + " YOU WON THE GAME!!!!");
						
						System.out.println("\nWinning stats: ");
						computeStats();
						
						hit = false;
					} 
					else 
					{
						oppBoard.printBoard(oppBoard.getshootingboard());
						System.out.println("\nHIT! Fire another one, Captain!");
						playerChoice();	
					}
				} 
				else 
				{
					oppBoard.getshootingboard()[shotRow][shotCol] = 'O';
					oppBoard.getshipboard()[shotRow][shotCol] = 'O';
					oppBoard.printBoard(oppBoard.getshootingboard());
					System.out.println("Aaaaargh you missed! Better luck next time, Captain!\n");
				}
			}
		}
	}
	

	@Override
	public boolean checkGameOver() {
		// TODO Auto-generated method stub
		return super.checkGameOver();
	}

	@Override
	public void fireCoordinates(int row, int col) {
		// TODO Auto-generated method stub
		super.fireCoordinates(row, col);
	}

	@Override
	public boolean validInput(int testRow, int testCol) {
		// TODO Auto-generated method stub
		return super.validInput(testRow, testCol);
	}

	@Override
	public boolean checkHit(Board oppBoard, int row, int col) {
		// TODO Auto-generated method stub
		return super.checkHit(oppBoard, row, col);
	}

	@Override
	public void computeStats() {
		// TODO Auto-generated method stub
		super.computeStats();
	}

	@Override
	public float getHitRate() {
		// TODO Auto-generated method stub
		return super.getHitRate();
	}


}
