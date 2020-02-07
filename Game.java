import java.util.Random;
import javax.swing.JOptionPane;

public class Game {
	//Game functions as a driver. Below the "driving" is the class Game
	
	public static void main(String[] args) throws InterruptedException {
		
		Player p1 = new Player("player 1", "grey");
		Player p2 = new Player("player 2", "gray");
		Game checkers = new Game(0);
		Board b = new Board();
		GUI graphics = new GUI();
		graphics.displayMenu();

		while(graphics.getP2Name().equals(" "))     //This loop should not exist, but I cannot make the game wait for user input without it. 
		{
			p1.setName(graphics.getP1Name());
			p2.setName(graphics.getP2Name());
			Thread.sleep(1);
		}
		p2.setName(graphics.getP2Name());
		
		p1.setColor(checkers.assignColor());
		p2.setColor(checkers.assignOtherColor(p1.getColor()));
		
		BoardGUI board = new BoardGUI();	
		
		board.displayBoard(b, checkers.getTurn());
		JOptionPane.showMessageDialog (null, p1.getName() + ", you have been assigned " + p1.getColor() + "\n" + p2.getName() + ", you have been assigned " + 
				p2.getColor(), "Color Assignment", JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////// This is the game class    //////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////

	//attributes
	boolean turn;  //false means red turn, true means black turn
	
	
	public Game(int draw)
	{
		turn = false;
	}
	
	//randomly assigns a color to a player
	public String assignColor()
	{
		Random rand = new Random();
		if(rand.nextInt() < 0.5)
		{
			return "red";
		}
		else
		{
			return "black";
		}
	}
	
	//assigns the color to the other player
	public String assignOtherColor(String color)
	{
		if(color.equals("red"))
		{
			return "black";
		}
		else
		{
			return "red";
		}
	}
	
	public boolean getTurn()
	{
		return turn;
	}
}
	