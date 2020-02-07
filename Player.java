
public class Player {
	
	//constructors 
	private String playerName;
	private String playerColor;
	
	public Player(String name, String color)
	{
		playerName = name;
		playerColor = color;
	}

	//methods
	public String getName()
	{
		return playerName;
	}
	
	public String getColor()
	{
		return playerColor;
	}
	
	public void setName(String set)
	{
		playerName = set;
	}
	
	public void setColor(String set)
	{
		playerColor = set;
	}
}
