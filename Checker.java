
public class Checker {

	//constructors
	private String color;
	private int player;
	private int type;
	private boolean select;
	
	public Checker(String colorInput, int playerInput, int typeInput, boolean selected)
	{
		color = colorInput;  	//should only hold "red" or "black"
		player = playerInput;	//1 for player 1, 2 for player 2
		type = typeInput;		//1 for a normal checker, 2 for a king
		select = selected;		//false for no, true for yes
	}
	
	//methods
	public String getColor()
	{
		return color;
	}
	
	public int getPlayer()
	{
		return player;
	}
	
	public int getType()
	{
		return type;
	}
	
	private void setType(int input)
	{
		type = input;
	}
	
	//Turns a piece into a king
	public void king()
	{
		setType(2); 
	}
	
	public boolean getSelect()
	{
		return select;
	}
	
	//flips the variable select (used for determining if the given piece is highlighted or not). 
	public void flipSelect()
	{
		if(select)
		{
			select = false;
		}
		else
		{
			select = true;
		}
	}
	
	
}
