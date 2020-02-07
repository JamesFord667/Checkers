import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class BoardGUI {
	
	private boolean clicked = false;
    private int red = 0;								//keeps track of how many red pieces are left
	private int black = 0;								//keeps track of how many black pieces are left
	private Color burlywood = new Color(222, 184, 135); //color for the board
	private JFrame mainScreen = new JFrame("Checkers"); //frame that holds the board and surrounding buttons
    private Board b;									//stores the board
    private ActionListener menu = new MenuListener();   //listener for the menu
    private boardG bg = new boardG();
    private Mouse m = new Mouse();
    private int xClick = 0;					//stores the value of the x pixel value from clicks
    private int yClick = 0;					//stores the value of the y pixel value from clicks
    private boolean t;				//stores the current turn
    private int selectedX = 0;		//stores the clicked x value
    private int selectedY = 0;		//stores the clicked y value
    private boolean previousJump = false;   //stores if the previous move was a jump
    private int previousJumpX = 0;
    private int previousJumpY = 0;
	private JPanel sidePanel = new JPanel();
	private boolean drawFlag;  //used to keep track of who called for a draw.
	private boolean drawCalled = false;  //keeps track of if a draw has been called. 
	private int drawCount = 0;      //keeps track of the number of turns that have been had and count toward a draw condition. 
	JLabel playerTurn = new JLabel();	//used in the sidePanel
	JLabel redLeft = new JLabel();		//used in the sidePanel
	JLabel blackLeft = new JLabel();	//used in the sidePanel
	
    class MenuListener implements ActionListener //creates the listener for the buttons
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{	
			if(e.getActionCommand().equals("Quit"))
			{
				quitConfirmation();
			}
			else if(e.getActionCommand().equals("Draw"))
			{
				drawClicked(t);
			}
			else if(e.getActionCommand().equals("Give Up"))
			{
				surrenderConfirmation(t);
			}
		}	
	}
    
    @SuppressWarnings("serial")
	private class boardG extends JPanel  //draws the gameplay board
	{
    	@Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            Graphics2D g2 = (Graphics2D) g;
            int row = 0;
    		int column =0;
    		final int SIZE = 125;
    		int previousLocationY = 0;
    		int previousLocationX = 0;
    		boolean color = false;   //false for gray, true for burlywood
    		
    		//draw the board background
            for(row = 0; row < 8; row++)
    		{
            	if(row % 2 == 0)
				{
					g.setColor(Color.gray);
					color = false;
				}
				else
				{
					g.setColor(burlywood);
					color = true;
				}
    			for (column = 0; column < 8; column++)
    			{
    				g.fillRect(previousLocationX, previousLocationY, SIZE, SIZE);
    				previousLocationX += SIZE;
    				if(color)
    				{
    					g.setColor(Color.gray);
    					color = false;
    				}
    				else
    				{
    					g.setColor(burlywood);
    					color = true;
    				}
    			}
    			previousLocationX = 0;
    			previousLocationY += SIZE;
    		}
            
            g2.setStroke(new BasicStroke(8)); //sets width for borders on selected pieces
            
            //draw the pieces on the board
            for(row = 0; row < 8; row++) 
            {
            	for(column = 0; column < 8; column++)
            	{
            		//draws normal red pieces
            		if(b.contains(column, row).getColor().equals("red") && b.contains(column, row).getSelect() == false && b.contains(column, row).getType() == 1)
            		{
            			g.setColor(Color.red);
            			g.fillOval(row*SIZE, column*SIZE, SIZE, SIZE);
            		}
            		//draws normal black pieces
            		else if(b.contains(column, row).getColor().equals("black") && b.contains(column, row).getSelect() == false && b.contains(column, row).getType() == 1) 
            		{
            			g.setColor(Color.black);
            			g.fillOval(row*SIZE, column*SIZE, SIZE, SIZE);
            		}
            		//draws highlighted red pieces
            		else if(b.contains(column, row).getColor().equals("red") && b.contains(column, row).getSelect() == true && b.contains(column, row).getType() == 1) 
            		{
            			g.setColor(Color.red);
            			g.fillOval(row*SIZE, column*SIZE, SIZE, SIZE);
            			g.setColor(Color.yellow);
            			g.drawOval(row*SIZE, column*SIZE, SIZE, SIZE);
            		}
            		//draws highlighted black pieces
            		else if(b.contains(column, row).getColor().equals("black") && b.contains(column, row).getSelect() == true && b.contains(column, row).getType() == 1) 
            		{
            			g.setColor(Color.black);
            			g.fillOval(row*SIZE, column*SIZE, SIZE, SIZE);
            			g.setColor(Color.yellow);
            			g.drawOval(row*SIZE, column*SIZE, SIZE, SIZE);
            		}
            		/////////////////the next four statements are for drawing kings
            		//draws red kings
            		else if(b.contains(column, row).getColor().equals("red") && b.contains(column, row).getSelect() == false && b.contains(column, row).getType() == 2)
            		{
            			g.setColor(Color.red);
            			g.fillOval(row*SIZE, column*SIZE, SIZE, SIZE);
            			g.setColor(Color.white);
            			g.fillOval(row*SIZE + 37, column*SIZE + 37, 50, 50);
            		}
            		//draws black kings
            		else if(b.contains(column, row).getColor().equals("black") && b.contains(column, row).getSelect() == false && b.contains(column, row).getType() == 2) 
            		{
            			g.setColor(Color.black);
            			g.fillOval(row*SIZE, column*SIZE, SIZE, SIZE);
            			g.setColor(Color.white);
            			g.fillOval(row*SIZE + 37, column*SIZE + 37, 50, 50);
            		}
            		//draws highlighted red pieces
            		else if(b.contains(column, row).getColor().equals("red") && b.contains(column, row).getSelect() == true && b.contains(column, row).getType() == 2) 
            		{
            			g.setColor(Color.red);
            			g.fillOval(row*SIZE, column*SIZE, SIZE, SIZE);
            			g.setColor(Color.yellow);
            			g.drawOval(row*SIZE, column*SIZE, SIZE, SIZE);
            			g.setColor(Color.white);
            			g.fillOval(row*SIZE + 37, column*SIZE + 37, 50, 50);
            		}
            		//draws highlighted black pieces
            		else if(b.contains(column, row).getColor().equals("black") && b.contains(column, row).getSelect() == true && b.contains(column, row).getType() == 2) 
            		{
            			g.setColor(Color.black);
            			g.fillOval(row*SIZE, column*SIZE, SIZE, SIZE);
            			g.setColor(Color.yellow);
            			g.drawOval(row*SIZE, column*SIZE, SIZE, SIZE);
            			g.setColor(Color.white);
            			g.fillOval(row*SIZE + 37, column*SIZE + 37, 50, 50);
            		}
            	}
            }
        }
	}
    
    class Mouse implements MouseListener  //tracks clicks made on the board during game play.
    {	
    	
    	private int actualX = 0;
    	private int actualY = 0;
    	@Override
    	public void mouseClicked(MouseEvent e) {
    	    xClick = e.getX();
    	    yClick = e.getY();
    	    actualX = xClick / 125;   //this finds the position on the board that is indicated by the pixel value
    	    actualY = yClick / 125;	  //this finds the position on the board that is indicated by the pixel value
    	    
    	    
    	    if(!clicked)
    	    {
    	    	if(b.isLegalClick(actualY, actualX, t))
    	    	{
    	    		b.contains(actualY, actualX).flipSelect();
    	    		clicked = true;
    				selectedX = actualX;
    				selectedY = actualY;
    	    	}
    	    	else
    	    	{
    	    		//System.out.println("You can't do that now");  //debug
    	    	}
    	    }
    	    else if(clicked)
    	    {
    	    	System.out.println("turn: " + t);
    	    	if(b.contains(actualY, actualX).getSelect() == true)
    	    	{
    	    		b.contains(actualY, actualX).flipSelect();
    	    		clicked = false;
    	    	}
    	    	else if(b.isLegalMove(actualY, actualX, selectedY, selectedX, t) && !b.hasJump(t))
    	    	{
    	    		b.move(selectedY, selectedX, actualY, actualX);
    	    		//used to increment or reset the draw count
    	    		if(b.contains(actualY, actualX).getType() == 2)	//drawCount increments if there is no jump or no piece moves toward king row
    	    		{
    	    			drawCount++;
    	    		}
    	    		else
    	    		{
    	    			drawCount = 0;
    	    		}
    	    		  //logic for making kings
    	    		if(t)
    	    		{
    	    			if(actualY == 0)
    	    			{
    	    				b.contains(actualY, actualX).king();
    	    			}
    	    		}
    	    		else
    	    		{
    	    			if(actualY == 7)
    	    			{
    	    				b.contains(actualY, actualX).king();
    	    			}
    	    		}
    	    			//end of king logic
    	    		//logic for changing turn flag
    	    		if(t == true)
    	    		{
    	    			t = false;
    	    		}
    	    		else
    	    		{
    	    			t = true;
    	    		}
    	    		//end of changing turn flag
    	    		b.contains(actualY, actualX).flipSelect();
    	    		clicked = false;
    	    		previousJump = false;
    	    	}
    	    	else if(b.isJump(selectedY, selectedX, actualY, actualX))
    	    	{
    	    		if(!previousJump)
    	    		{
    	    			b.jump(selectedY, selectedX, actualY, actualX, t);
        	    		previousJump = true;
        	    		previousJumpX = actualX;
        	    		previousJumpY = actualY;
        	    		if(!b.hasJumpAt(actualY, actualX, t))
        	    		{
        	    			  //logic for making kings
        	        		if(t)
        	        		{
        	        			if(actualY == 0)
        	        			{
        	        				b.contains(actualY, actualX).king();
        	        			}
        	        		}
        	        		else
        	        		{
        	        			if(actualY == 7)
        	        			{
        	        				b.contains(actualY, actualX).king();
        	        			}
        	        		}
        	        			//end of king logic
        	    			if(t == true)
            	    		{
            	    			t = false;
            	    		}
            	    		else
            	    		{
            	    			t = true;
            	    		}
        	    			previousJump = false;
        	    			drawCount = 0;
        	    		}
        	    		b.contains(actualY, actualX).flipSelect();
        	    		clicked = false;
    	    		}
    	    		else
    	    		{
    	    			if(selectedY == previousJumpY && selectedX == previousJumpX)
    	    			{
    	    				b.jump(selectedY, selectedX, actualY, actualX, t);
    	    				if(!b.hasJumpAt(actualY, actualX, t))
            	    		{
    	    					  //logic for making kings
    	    		    		if(t)
    	    		    		{
    	    		    			if(actualY == 0)
    	    		    			{
    	    		    				b.contains(actualY, actualX).king();
    	    		    			}
    	    		    		}
    	    		    		else
    	    		    		{
    	    		    			if(actualY == 7)
    	    		    			{
    	    		    				b.contains(actualY, actualX).king();
    	    		    			}
    	    		    		}
    	    		    			//end of king logic
            	    			if(t == true)
                	    		{
                	    			t = false;
                	    		}
                	    		else
                	    		{
                	    			t = true;
                	    		}
            	    			drawCount = 0;
            	    			previousJump = false;
            	    		}
            	    		previousJumpX = actualX;
            	    		previousJumpY = actualY;
    	    				b.contains(actualY, actualX).flipSelect();
            	    		clicked = false;
    	    			}
    	    		}
    	    		
    	    	}
    	    	else if(b.hasJump(t))
    	    	{
    	    		System.out.println("board has jump: " + b.hasJump(t) + "    t: " + t);
    	    		JOptionPane.showMessageDialog (null, "You are required to make your available jump", "Alert", JOptionPane.INFORMATION_MESSAGE);
    	    	}
    	    }
    	    if(drawCalled && t != drawFlag)
    	    {
    	    	drawConfirmation();
    	    	drawCalled = false;
    	    }
    	    System.out.println("Current turn: " + t + "    previousJump: " + previousJump);
    	    updateBoard(t);
    	    if(b.isWinner())
    	    {
    	    	victoryScreen(t);
    	    }
    	    if(drawCount >= 40)
    	    {
    	    	JOptionPane.showMessageDialog (null, "40 turns have passed since a piece has been jumped or advanced towards king row. \nTherefore, a draw has been called.", "Draw", JOptionPane.INFORMATION_MESSAGE);
    	    	System.exit(0);
    	    }
    	}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}
    }
	
    //Makes the side panel with the surrender, quit, and give up buttons, along with the player turn and pieces remaining information. 
    public JPanel makeSidePanel(boolean turn)
    {
		JPanel sidePane = new JPanel();
		updateSidePanel();
		
		//side pane with buttons
		JButton draw = new JButton();
		JButton giveUp = new JButton();
		JButton quit = new JButton();
		
		draw.addActionListener(menu);
		giveUp.addActionListener(menu);
		quit.addActionListener(menu);
		
		draw.setText("Draw");
		giveUp.setText("Give Up");
		quit.setText("Quit");
		
		//putting all of the stuff in the board
		sidePane.setLayout(new GridLayout(6, 1));
		sidePane.add(draw);
		sidePane.add(giveUp);
		sidePane.add(quit);
		sidePane.add(playerTurn);
		sidePane.add(redLeft);
		sidePane.add(blackLeft);
		
		return sidePane;
    }
    
    private void updateSidePanel()
    {
    	red = b.getRedCount();
		black = b.getBlackCount();
		redLeft.setText("red pieces left: " + red);
		blackLeft.setText("black pieces left: " + black);
    	if(t == true) 
		{
			playerTurn.setText("It is black's turn");
		}
		else
		{
			playerTurn.setText("It is red's turn");
		}
    }
    
	public void displayBoard(Board board, boolean turn)
	{
		b = board;
		sidePanel = makeSidePanel(t);
		
		// makes the play screen
		mainScreen.setLayout(new BorderLayout());
		mainScreen.setSize(1150, 1150);
		//mainScreen.EXIT_ON_CLOSE(true);
		
	    bg.addMouseListener(m);
		
		mainScreen.add(sidePanel, BorderLayout.EAST);
		mainScreen.add(bg, BorderLayout.CENTER);
		
		mainScreen.setVisible(true);
		mainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void quitConfirmation()
	{
		JFrame q = new JFrame("quit");
		q.setVisible(true);
		q.setSize(800, 500);
		
		class ClickListener implements ActionListener //creates the listener for the buttons
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{	
				if(e.getActionCommand().equals("Yes"))
				{
					System.exit(0);
				}
				else if(e.getActionCommand().equals("No"))
				{
					q.dispose();
				}
			}	
		}
		
		ActionListener listener = new ClickListener();
		
		JLabel message = new JLabel("Are you sure you want to quit?");
		q.setLayout(new BorderLayout());
		q.add(message, BorderLayout.CENTER);
		message.setFont(new Font("Franklin Gothin Medium", Font.BOLD, 48));
		message.setBackground(Color.WHITE);
		
		JButton yes = new JButton("Yes");
		JButton no = new JButton("No");
		yes.addActionListener(listener);
		no.addActionListener(listener);
		yes.setSize(30, 30);
		no.setSize(30, 30);
		yes.setFont(new Font("Franklin Gothin Medium", Font.BOLD, 55));
		no.setFont(new Font("Franklin Gothin Medium", Font.BOLD, 55));
		yes.setForeground(Color.RED);
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1,2));
		buttons.add(yes);
		buttons.add(no);

		q.add(buttons, BorderLayout.SOUTH);	
	}
	
	//this handles a player selecting surrender
	private void surrenderConfirmation(boolean currentTurn)
	{
		boolean surrenderTurn = currentTurn;
		JFrame q = new JFrame("surrender");
		q.setVisible(true);
		q.setSize(800, 500);
		
		class SurrenderListener implements ActionListener //creates the listener for the buttons
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{	
				if(e.getActionCommand().equals("Yes"))
				{
					if(surrenderTurn)
					{
						JOptionPane.showMessageDialog (null, "The red player wins!", "Alert", JOptionPane.INFORMATION_MESSAGE);
					}
					else
					{
						JOptionPane.showMessageDialog (null, "The black player wins!", "Alert", JOptionPane.INFORMATION_MESSAGE);
					}
					System.exit(0);
				}
				else if(e.getActionCommand().equals("No"))
				{
					q.dispose();
				}
			}	
		}
		
		SurrenderListener listener = new SurrenderListener();
		
		JLabel message = new JLabel("Are you sure you want to surrender?");
		q.setLayout(new BorderLayout());
		q.add(message, BorderLayout.CENTER);
		message.setFont(new Font("Franklin Gothin Medium", Font.BOLD, 42));
		message.setBackground(Color.WHITE);
		
		JButton yes = new JButton("Yes");
		JButton no = new JButton("No");
		yes.addActionListener(listener);
		no.addActionListener(listener);
		yes.setSize(30, 30);
		no.setSize(30, 30);
		yes.setFont(new Font("Franklin Gothin Medium", Font.BOLD, 55));
		no.setFont(new Font("Franklin Gothin Medium", Font.BOLD, 55));
		yes.setForeground(Color.RED);
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1,2));
		buttons.add(yes);
		buttons.add(no);

		q.add(buttons, BorderLayout.SOUTH);	
	}
	
	//used when a player clicks on draw. 
	public void drawClicked(boolean turn)
	{
		JOptionPane.showMessageDialog (null, "You have called for a draw", "Alert", JOptionPane.INFORMATION_MESSAGE);
		drawFlag = turn;
		drawCalled = true;
	}
	
	//used to confirm a draw
	public void drawConfirmation()
	{
		JFrame q = new JFrame("draw");
		q.setVisible(true);
		q.setSize(800, 500);
		
		class DrawListener implements ActionListener //creates the listener for the buttons
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{	
				if(e.getActionCommand().equals("Yes"))
				{
					JOptionPane.showMessageDialog (null, "A draw has been called.", "Alert", JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
				}
				else if(e.getActionCommand().equals("No"))
				{
					q.dispose();
				}
			}	
		}
		DrawListener listener = new DrawListener();
		
		JLabel message = new JLabel("Your opponent wishes to call a draw. Do you accept?");
		q.setLayout(new BorderLayout());
		q.add(message, BorderLayout.CENTER);
		
		JButton yes = new JButton("Yes");
		JButton no = new JButton("No");
		yes.addActionListener(listener);
		no.addActionListener(listener);
		yes.setSize(30, 30);
		no.setSize(30, 30);
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1,2));
		buttons.add(yes);
		buttons.add(no);

		q.add(buttons, BorderLayout.SOUTH);	
	}
	
	public void victoryScreen(boolean turn)
	{
		JOptionPane.showMessageDialog (null, "Congratulations, the " + b.getWinner() + " player has won!", "Victory!", JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
	}
	
	public void updateBoard(boolean turn1)
	{
		t = turn1;
		updateSidePanel();
		mainScreen.revalidate();
	    mainScreen.repaint();
	}
}