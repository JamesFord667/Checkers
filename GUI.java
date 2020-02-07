import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GUI extends JComponent{


	//variables
	private String p1Name = " ";
	private String p2Name = " ";
		
	public void displayMenu()
	{
		JFrame frame = new JFrame("Checkers");
		class ClickListener implements ActionListener //creates the listener for the buttons
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{	
				if(e.getActionCommand().equals("New Game"))
				{
					p1Name = getPlayer1Name();
					p2Name = getPlayer2Name();
					frame.dispose();
				}
				else if(e.getActionCommand().equals("Exit"))
				{
					System.exit(0);
				}	
			}	
		}
		
		//creates the listener for the buttons
		ActionListener listener = new ClickListener();
		
		//makes the settings for the frame that has everything in it
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(1600, 900);
		
		//makes the top label of the frame (that says "Checkers")
		JLabel title = new JLabel("Checkers");
		title.setForeground(Color.orange);
		title.setFont(new Font("Dialog", Font.BOLD, 70));
		JPanel titleText = new JPanel();
		titleText.add(title);
		titleText.setBounds(50, 50, 120, 120);
		
		//makes play button
		JButton play = new JButton("New Game");
		play.setFont(new Font("Dialog", Font.PLAIN, 36));
		play.addActionListener(listener);
		
		//makes exit button
		JButton leave = new JButton("Exit");
		leave.setFont(new Font("Dialog", Font.PLAIN, 36));
		leave.addActionListener(listener);
		
		//puts all of the pieces into the frame
		JPanel menu = new JPanel();
		JPanel middle = new JPanel();
		
		middle.setLayout(new GridLayout(2, 1));
		middle.add(play);
		middle.add(leave);
		
		menu.setLayout(new BorderLayout());
		menu.add(titleText, BorderLayout.NORTH);
		menu.add(middle, BorderLayout.CENTER);
		frame.add(menu);
	}
	
	public String getPlayer1Name()
	{
		String playerName = JOptionPane.showInputDialog("Player 1, please enter your name: ");
		return playerName;
	}
	
	public String getPlayer2Name()
	{
		String playerName = JOptionPane.showInputDialog("Player 2, please enter your name: ");
		return playerName;
	}
	
	public String getP1Name()
	{
		return p1Name;
	}
	
	public String getP2Name()
	{
		return p2Name;
	}
}
