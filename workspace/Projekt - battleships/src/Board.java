import java.util.Scanner;

public class Board {

	private final int ROW = 10; // H�jd p� spelplanen
	private final int COL = 10; // Bredd p� spelplanen
	private char[][] shipboard = new char[ROW][COL];
	private char[][] shootingboard = new char[ROW][COL];
	protected int startRow, startCol, shipDirection;
	protected boolean horizontal = true;	// boolean f�r att v�lja riktning p� skepp
	Scanner input = new Scanner(System.in);
	
	// Skapar spelplaner
	public void Board()
	{
		createboard(shipboard);
		createboard(shootingboard);
	}
	
	// L�gger ut "vatten" p� spelplanen
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
	
	// S�tter ut skeppet p� r�tt koordinater
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
	// Kontrollerar om platsen �r ledig
	public boolean checkEmptySpot(int row, int col, boolean horizontal, int shipsize)
	{	
		// R�knar s� att det inte ligger skepp p� efterf�ljande platser
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
	
	// Kontrollerar att spelarens input �r inom spelplanen
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
	
	// Printar tomma rader f�r att d�lja spelplanen f�r motst�ndaren
	public void printEmptyRows()
	{
		for(int i = 0; i < 50; i++)
		{
			System.out.println(". ");
		}
	}
	
	// Getters f�r spelplanerna
	public char[][] getshipboard()
	{
		return shipboard;
	}
	public char[][] getshootingboard()
	{
		return shootingboard;
	}
	
}
