
public enum Ships {

	// Skapar skepp med namn, beteckning och storlek
	cruiser("Cruiser",'C', 5),
	battleship("Battleship",'B', 4),
	yacht("Yacht",'Y', 3),
	submarine("Submarine",'S', 3),
	destroyer("Destroyer",'D', 2);
	
	private final String shipName;
	private final char shipDesc;
	private final int shipSize;
	
	Ships(String name, char description, int size)
	{
		shipName = name;
		shipDesc = description;
		shipSize = size;
	}
	// Getters
	public String getShipName()
	{
		return shipName;
	}
	
	public char getShipDescription()
	{
		return shipDesc;
	}
	
	public int getShipSize(){
		return shipSize;
	}
	
}
