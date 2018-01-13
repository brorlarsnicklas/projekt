
public class Appearance {

	public void printHeaderMessage(String message)
	{
		for(int i = 0; i < 45; i++)
		{
			System.out.print("*");
		}
		System.out.println("\n"+message+"\n");
		for(int i = 0; i < 45; i++)
		{
			System.out.print("*");
		}
		System.out.println("");		
	}
	
	public static void printMessage(String message)
	{
		System.out.println("\n"+message+"\n");
	}
	
}
