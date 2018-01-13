import java.util.Scanner;

/*
 * Lägga till om Highscore är tom, vad händer då?
 * Lägga till andra vinst efter minst antal skott i highscore
 * Lägga till computer shipplacement 
 * 
 *
 * */

public class Main {

	protected static boolean gameOn = true;
	protected static Scanner input = new Scanner(System.in);
	protected static Scanner input2 = new Scanner(System.in);
	
	protected static String playerName, winnerName;
	protected static Computer computer;
	protected static Player playerOne, playerTwo;
	protected static boolean gameMenu = true;
	protected static float topHitRate;
	protected static float currentWinnerHitRate;
	private static HighScores hs;
	protected static int choice;
	
	public static void main(String[] args)
	{

		hs = new HighScores();
		hs.readFile();
		topHitRate = hs.getHighScore();
		
		menu();
		createGame();
		playBattleships();
	}
	
	public static void playBattleships()
	{
boolean playerTurn = true;
		
		while(gameOn)
		{	
			if(playerTurn)
			{
				System.out.println(playerOne.name + ": \n");
				playerOne.fire(playerTwo.board);				
				playerTurn = !playerTurn;
			}
			else
			{
				System.out.println(playerTwo.name+": \n");
				playerTwo.fire(playerOne.board);
				playerTurn = !playerTurn;
			}
		}
		
		if(playerOne.winner == true){
			winnerName = playerOne.name;
			currentWinnerHitRate = playerOne.getHitRate();
		}
		else{
			winnerName = playerTwo.name;
			currentWinnerHitRate = playerTwo.getHitRate();
		}
		
		if(topHitRate < currentWinnerHitRate){
			hs.openWriteFile();
			hs.deleteRecords();
			hs.addRecord(winnerName, currentWinnerHitRate);
			hs.closeWriteFile();
		}
	}

	public static void menu()
	{
		System.out.println("Welcome to Battleships!");
		while(gameMenu)
		{
			System.out.println("");
			System.out.println("Menu options");
			System.out.println("1: Play the game\n2: Show high score\n3: Show game rules");
			System.out.println("Enter your choice: ");
			choice = input2.nextInt();
			
			
			switch(choice)
			{
			case 1:
				gameMenu = false;
				break;
			case 2:
				System.out.println("Best hit rate at the moment: \n"+ hs.toplist.keySet());
				System.out.println("Hit rate: \n" + topHitRate + "%");
				break;
			case 3: 
				System.out.println("Bomb ships");
				System.out.println("");
				break;
				default:
					break;
			}
		}
	}
	
	// Skapar 2 st spelare med valfritt namn
	public static void createGame()
	{
		System.out.println("Starting a new game of Battleships");
		System.out.println("The best hit rate so far is " + topHitRate + "%. \nTry to beat it");
		
		System.out.println("Would you like to play against a computer\n1: Yes\n2: No");
		choice = input2.nextInt();
		
		if(choice == 1)
		{	
			playerName = "Jack Sparrow (COMPUTER)";
			playerOne = new Computer(playerName);
			playerOne.shipPlacement();
		}
		else
		{
			System.out.println("What's the name of player 1: ");
			playerName = input.nextLine();
			playerOne = new Player(playerName);
			playerOne.shipPlacement();
		}
		
		System.out.println("Ship placement for "+playerName+" is finished");
		
		System.out.println("What's the name of player 2: ");
		playerName = input.nextLine();
		playerTwo = new Player(playerName);
		playerTwo.shipPlacement();
		
		System.out.println("\n\nShip placement for both players finished\nTime to enjoy the game!");
	}
	
}
