import java.io.File;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Scanner;

public class HighScores {

	HashMap<String, Float> toplist;
	private Formatter formatter;
	private Scanner scanner;
	private float highScore;

	public void openWriteFile(){
		try{
			formatter  = new Formatter("highscore.txt");
		}
		catch(Exception e){
			System.out.println("ERROR");
		}
	}
	
	public void deleteRecords(){
		toplist.clear();
	}
	
	public void addRecord(String name, float score){
		formatter.format("%s%f\n", name+" ", score);
	}
	
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
