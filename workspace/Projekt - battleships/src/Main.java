import java.util.Scanner;

public class Main {

	protected static boolean gameOn = true;
	protected static Scanner input = new Scanner(System.in);
	protected static Scanner input2 = new Scanner(System.in);
	
	protected static String playerName, winnerName;
	protected static Computer computer;
	protected static Player playerOne, playerTwo;
	private static HighScores hs= new HighScores();
	
	protected static boolean gameMenu = true;
	protected static float topHitRate;
	protected static float currentWinnerHitRate;
	protected static int choice, numPlayers = 0;
	
	public static void main(String[] args)
	{
		// Läser in bästa hit rate 
		hs.readFile();
		topHitRate = hs.getHighScore();
		
		// Startar spelmeny
		menu();
		// Skapar spelomgång 
		if(gameOn)
		{
		createGame();
		playBattleships();
		}
	}
	
	// Startar spelomgång, håller koll på vems tur det är och om någon har vunnit.
	// Sparar hit rate och startar om spelmenyn
	public static void playBattleships()
	{
		boolean playerTurn = true;
		
		// Om ingen har vunnit körs loopen
		while(gameOn)
		{	
			if(playerTurn)
			{	playerOne.playerChoice();
				System.out.println("***" + playerOne.name + " it's your turn: ***");
				// Låter spelare 1 avfyra ett skott
				playerOne.fire(playerTwo.board);
				// Ändrar spelartur 
				playerTurn = !playerTurn;
				
			}
			else
			{
				playerTwo.playerChoice();
				System.out.println("***" + playerTwo.name+" it's your turn: ***");
				// Låter spelare 2 avfyra ett skott
				playerTwo.fire(playerOne.board);
				// Ändrar spelartur
				playerTurn = !playerTurn;
			}
		}
		// Printar spelplanerna när spelet är slut.  
		System.out.println("\n"+playerOne.name + " ships: ");
		playerOne.board.printBoard(playerOne.board.getshipboard());
		System.out.println(playerTwo.name + " ships: ");
		playerTwo.board.printBoard(playerTwo.board.getshipboard());
		
		// Om spelare 1 vann sparas den spelarens hit rate annars hit rate för spelare 2
		if(playerOne.winner == true){
			winnerName = playerOne.name;
			currentWinnerHitRate = playerOne.getHitRate();
		}
		else{
			winnerName = playerTwo.name;
			currentWinnerHitRate = playerTwo.getHitRate();
		}
		// Uppdaterar highscorelistan 
		if(topHitRate < currentWinnerHitRate){
			hs.openWriteFile();
			hs.deleteRecords();
			hs.addRecord(winnerName, currentWinnerHitRate);
			hs.closeWriteFile();
		}
		// Läser in highscore och startar spelmenyn igen
		hs.readFile();
		topHitRate = hs.getHighScore();
		gameMenu = true;
		menu();
	}

	
	// Meny för spelval
	public static void menu()
	{
		System.out.println("Welcome to Battleships!");
		while(gameMenu)
		{
			boolean valid = true;
			do {
				try 
				{
					System.out.println("");
					System.out.println("Menu options");
					System.out.println("1: Play the game\n2: Show high score\n3: Show game rules\n4: Exit");
					System.out.println("Enter your choice: \n");
					choice = input2.nextInt();
					valid = false;
				}
			catch (Exception e) 
			{
				System.out.println("\n* Input must be an int value, Captain! *\n");
				input2.next();
			}
		} while (valid);
			
			switch(choice)
			{
			case 1:
				gameMenu = false;
				gameOn = true;
				break;
			case 2:
				System.out.println("Best hit rate at the moment: \n"+ hs.toplist.keySet());
				System.out.println("\nHit rate: \n" + topHitRate + "%");
				break;
			case 3: 
				System.out.println("*** Bomb ships ***\n");
				break;
			case 4: 
				gameOn = false;
				gameMenu = false;
				System.out.println("Exiting the game!");
				break;
			default:
					break;
			}
		}
	}
	
	// Skapar 2 st spelare med valfritt namn
	public static void createGame()
	{
		// Startar spel, visar bästa hit rate och ger valmöjlighet att spela mot dator
		System.out.println("\nStarting a new game of Battleships");
		System.out.println("\nThe best hit rate so far is " + topHitRate + "% by " + hs.toplist.keySet() + "\nTry to beat it");
		boolean valid = true;
		do {
			try 
			{
				System.out.println("\nWould you like to play against a computer\n1: Yes\n2: No");
				choice = input2.nextInt();
				valid = false;
			}
		catch (Exception e) 
		{
			System.out.println("\n* Input must be an int value, Captain! *\n");
			input2.next();
		}
	} while (valid);
		
		// Skapar en datorspelare och placerar ut skepp på spelplanen
		if(choice == 1)
		{	
			playerName = "Pirate(COM)";
			playerOne = new Computer(playerName);
			playerOne.shipPlacement();
		}
		// Skapar en mänsklig spelare och placerar ut skepp på spelplanen
		else
		{
			System.out.println("What's the name of player 1: ");
			playerName = input.nextLine();
			playerOne = new Player(playerName);
			playerOne.shipPlacement();
		}
		
		System.out.println("Ship placement for "+playerName+" is finished");
		
		// Skapar en till mänsklig spelare och placerar ut skepp på spelplanen
		System.out.println("What's the name of player 2: ");
		playerName = input.nextLine();
		playerTwo = new Player(playerName);
		playerTwo.shipPlacement();
		
		System.out.println("\n\nShip placement for both players finished\nTime to enjoy the game!");
	}
	
}
