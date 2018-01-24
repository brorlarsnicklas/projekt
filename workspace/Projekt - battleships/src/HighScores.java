import java.io.File;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Scanner;

public class HighScores {

	HashMap<String, Float> toplist;
	private Formatter formatter;
	private Scanner scanner;
	private float highScore;

	// Öppnar fil för input
	public void openWriteFile(){
		try{
			formatter  = new Formatter("highscore.txt");
		}
		catch(Exception e){
			System.out.println("ERROR");
		}
	}
	
	// Tar bort tidigare resultat
	public void deleteRecords(){
		toplist.clear();
	}
	
	// Lägger till resultat i textfilen
	public void addRecord(String name, float score){
		formatter.format("%s%f\n", name+" ", score);
	}
	
	// Lista för highscore 
	public HashMap<String,Float> readFile(){
		toplist = new HashMap<>();
		try{
			scanner = new Scanner(new File("highscore.txt"));
		}
		catch(Exception e){
			System.out.println("ERROR");
		}
		
		while(scanner.hasNext()){
			String name = scanner.next();
			float score = scanner.nextFloat();
			toplist.put(name, score);
		}
		scanner.close();
		return toplist;
	}
	
	
	public float getHighScore(){
		
		for(String top : toplist.keySet()){
			highScore = toplist.get(top);
		}
		return highScore;
		
	}
	
	public void closeWriteFile(){
		formatter.close();
	}
	
	
	
}
