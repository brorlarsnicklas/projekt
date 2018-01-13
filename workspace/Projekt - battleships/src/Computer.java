import java.util.Random;

public class Computer extends Player 
{
	private final int max = 9;
	private final int min = 0;
	Random random = new Random();
	
	public Computer(String name) 
	{
		super(name);
		// TODO Auto-generated constructor stub
	}

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
					board.startCol = random.nextInt(max + 1);
					board.startRow = random.nextInt(max + 1);
					board.shipDirection = random.nextInt(2+1);
					System.out.print(board.shipDirection);
					
					if(board.shipDirection == 1)
					{
						board.horizontal = false;
					}
					
					if(board.checkEmptySpot(board.startRow, board.startCol, board.horizontal, ships.getShipSize()) && (board.validInput(board.startRow, board.startCol, board.horizontal, ships.getShipSize())))
					{
						missplaced = false;
						board.setShip(board.startRow, board.startCol, board.horizontal, ships.getShipSize(), ships.getShipDescription());	
					}
			}
		}
	}


	@Override
	public void fire(Board oppBoard) 
	{	
		hit = true;
		while (hit) 
		{

			computeHitRate();
			board.printBoard(oppBoard.getshootingboard());
			shotRow = random.nextInt(max + 1);
			shotCol = random.nextInt(max + 1);

			if (checkHit(oppBoard, shotRow, shotCol)) 
			{
				shotsFired++;
				System.out.println("Row: "+ shotRow);
				System.out.println("Col: "+ shotCol);
				if (hit) 
				{
					oppBoard.getshootingboard()[shotRow][shotCol] = 'X';
					oppBoard.getshipboard()[shotRow][shotCol] = 'X';
					hitCount++;

					if (checkGameOver()) 
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
	}
	

	@Override
	public boolean checkGameOver() {
		// TODO Auto-generated method stub
		return super.checkGameOver();
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
	public void computeHitRate() {
		// TODO Auto-generated method stub
		super.computeHitRate();
	}

	@Override
	public float getHitRate() {
		// TODO Auto-generated method stub
		return super.getHitRate();
	}


}
