
public class Board {
	
	private int redCount;	//the number of red pieces remaining
	private int blackCount;	//the number of black pieces remaining
	
	//the pieces used in the game. 
	private Checker r1 = new Checker("red", 1, 1, false);
	private Checker r2 = new Checker("red", 1, 1, false);
	private Checker r3 = new Checker("red", 1, 1, false);
	private Checker r4 = new Checker("red", 1, 1, false);
	private Checker r5 = new Checker("red", 1, 1, false);
	private Checker r6 = new Checker("red", 1, 1, false);
	private Checker r7 = new Checker("red", 1, 1, false);
	private Checker r8 = new Checker("red", 1, 1, false);
	private Checker r9 = new Checker("red", 1, 1, false);
	private Checker r10 = new Checker("red", 1, 1, false);
	private Checker r11 = new Checker("red", 1, 1, false);
	private Checker r12 = new Checker("red", 1, 1, false);
	private Checker b1 = new Checker("black", 2, 1, false);
	private Checker b2 = new Checker("black", 2, 1, false);
	private Checker b3 = new Checker("black", 2, 1, false);
	private Checker b4 = new Checker("black", 2, 1, false);
	private Checker b5 = new Checker("black", 2, 1, false);
	private Checker b6 = new Checker("black", 2, 1, false);
	private Checker b7 = new Checker("black", 2, 1, false);
	private Checker b8 = new Checker("black", 2, 1, false);
	private Checker b9 = new Checker("black", 2, 1, false);
	private Checker b10 = new Checker("black", 2, 1, false);
	private Checker b11 = new Checker("black", 2, 1, false);
	private Checker b12 = new Checker("black", 2, 1, false);
	private Checker[][] board = {
			{null, r1, null, r2, null, r3, null, r4},
			{r5, null, r6, null, r7, null, r8, null},
			{null, r9, null, r10, null, r11, null, r12},
			{null, null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null, null},
			{b1, null, b2, null, b3, null, b4, null},
			{null, b5, null, b6, null, b7, null, b8},
			{b9, null, b10, null, b11, null, b12, null},
	};
	
	//"empty" checker used when a space is empty. 
	Checker empty = new Checker("none", -1, -1, false);
	
	public Board()
	{
		redCount = 12;
		blackCount = 12;
	}
	
	//returns true if the selected space is empty. 
	public boolean isEmpty(int i, int j)
	{
		if(board[i][j] == null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	//returns the checker in a selected location
	public Checker contains(int i, int j)
	{
		if(board[i][j] != null)
		{
			return board[i][j];
		}
		else return empty;
	}
	
	//removes a checker from a selected location
	public void remove(int i, int j)
	{
		String color = contains(i, j).getColor();
		board[i][j] = null;
		if(color.equals("red"))
		{
			redCount--;
		}
		else if(color.equals("black"))
		{
			blackCount--;
		}
	}
	
	//moves a piece
	public void move(int i, int j, int x, int y)
	{
		board[x][y] = board[i][j];
		board[i][j] = null;
		System.out.println("moved");
	}
	
	//determines if someone has won the game
	public boolean isWinner()
	{
		if (blackCount == 0 || redCount == 0)
		{
			return true;
		}
		return false;
	}
	
	//finds the side that won
	public String getWinner()
	{
		if(redCount == 0)
		{
			return "black";
		}
		else
		{
			return "red";
		}
	}
	
	//used to makes jumps
	public void jump(int i, int j, int x, int y, boolean turn)  //false for red, true for black
	{
		move(i, j, x, y);
		if(contains(x, y).getType() == 2)  //for kings
		{//System.out.println("King is jumping"); //debug
			if((i - x) > 0 )
			{
				remove(i-1, ((y-j)/2) + j);
			}
			else
			{
				remove(i+1, ((y-j)/2) + j);
			}
		}
		else if(turn) //for black pieces
		{
			remove(i-1, ((y-j)/2) + j);
		}
		else if(!turn) //for red pieces
		{
			remove(i+1, ((y-j)/2) + j);
		}
	}
	
	public int getRedCount()
	{
		return redCount;
	}
	
	public int getBlackCount()
	{
		return blackCount;
	}
	
	public void setLocation(int y, int x, Checker c) 
	{
		board[y][x] = c;
	}
	
	public boolean isLegalClick(int y, int x, boolean turn)  //turn is false for red, true for black
	{
		if(contains(y, x).getColor().equals("black") && turn == true)
		{
			return true;
		}
		if(contains(y, x).getColor().equals("red") && turn == false)
		{
			return true;
		}
		return false;
	}
	
	public boolean isLegalMove(int endY, int endX, int firstY, int firstX, boolean turn)  //turn is false for red, true for black
	{
		if(contains(endY, endX).getColor().equals("none"))
		{
			if(contains(firstY, firstX).getType() == 2)  //handles any king piece
			{
				if((firstY - 1 == endY && (firstX + 1 == endX || firstX - 1 == endX)) || (firstY + 1 == endY && (firstX + 1 == endX || firstX - 1 == endX)))
				{
					return true;
				}
			}
			else if(turn)  //used for normal black pieces
			{
				if(firstY - 1 == endY && (firstX + 1 == endX || firstX - 1 == endX))
				{
					return true;
				}
			}
			else   //used for normal red pieces
			{
				if(firstY + 1 == endY && (firstX + 1 == endX || firstX - 1 == endX))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	//determines if the piece at the given location has any jumps
	public boolean hasJumpAt(int y, int x, boolean turn)   	//for turn, false is red, true is black
	{
		if(contains(y, x).getType() == 2 && contains(y, x).getColor().equals("black") && turn == true)  //for black kings
		{System.out.println("Black king triggered");
			if(x >= 2 && x <= 5 && contains(y, x).getColor().equals("black"))    //piece in middle of board
			{
				if((y-2 >= 0 && contains(y-2, x-2).getColor().equals("none") && contains(y-1, x-1).getColor().equals("red")) || (y+2 <= 7 && contains(y+2, x-2).getColor().equals("none") && contains(y+1, x-1).getColor().equals("red")))
				{
					//System.out.println("debug 1, y: " + y + "  x: " + x);
					return true;
				}
				else if((y-2 >= 0 && contains(y-2, x+2).getColor().equals("none") && contains(y-1, x+1).getColor().equals("red")) || (y+2 <= 7 && contains(y+2, x+2).getColor().equals("none") && contains(y+1, x+1).getColor().equals("red")))
				{
					//System.out.println("debug 2");
					return true;
				}
				else
				{
					return false;
				}
			}
			else if(x <= 1 && contains(y, x).getColor().equals("black"))   //piece on left edge
			{
				if((y-2 >= 0 && contains(y-2, x+2).getColor().equals("none") && contains(y-1, x+1).getColor().equals("red")) || (y+2 <= 7 && contains(y+2, x+2).getColor().equals("none") && contains(y+1, x+1).getColor().equals("red")))
				{
					//System.out.println("debug 3, y: " + y + "  x: " + x);
					return true;
				}
				else
				{
					return false;
				}
			}
			else if(x >= 6 && contains(y, x).getColor().equals("black"))   //piece on right edge
			{
				if((y-2 >= 0 && contains(y-2, x-2).getColor().equals("none") && contains(y-1, x-1).getColor().equals("red")) || (y+2 <= 7 && contains(y+2, x-2).getColor().equals("none") && contains(y+1, x-1).getColor().equals("red")))
				{
					//System.out.println("debug 4");
					return true;
				}
				else
				{
					return false;
				}
			}
		}
		else if(contains(y, x).getType() == 2 && contains(y, x).getColor().equals("red") && turn == false) //for red kings
		{System.out.println("Red king triggered");
			if(x <= 5 && x >= 2 && contains(y, x).getColor().equals("red"))   //piece in middle of board
			{
				if((y+2 <= 7 && contains(y+2, x+2).getColor().equals("none") && (contains(y+1, x+1).getColor().equals("black")) || (y-2 >= 0 && contains(y-2, x+2).getColor().equals("none") && contains(y-1, x+1).getColor().equals("black"))))
				{
					//System.out.println("debug 5");
					return true;
				}
				else if((y+2 <= 7 && contains(y+2, x-2).getColor().equals("none") && (contains(y+1, x-1).getColor().equals("black")) || (y-2 >= 0 && contains(y-2, x-2).getColor().equals("none") && contains(y-1, x-1).getColor().equals("black"))))
				{
					//System.out.println("debug 6");
					return true;
				}
				else
				{
					return false;
				}
			}
			else if(x <= 1 && contains(y, x).getColor().equals("red"))   //piece on left edge
			{
				if((y+2 <= 7 && contains(y+2, x+2).getColor().equals("none") && (contains(y+1, x+1).getColor().equals("black")) || (y-2 >= 0 && contains(y-2, x+2).getColor().equals("none") && contains(y-1, x+1).getColor().equals("black"))))
				{
					//System.out.println("debug 7");
					return true;
				}
				else
				{
					return false;
				}
			}
			else if(x >= 6 && contains(y, x).getColor().equals("red"))   //piece on right edge
			{
				if((y+2 <= 7 && contains(y+2, x-2).getColor().equals("none") && (contains(y+1, x-1).getColor().equals("black")) || (y-2 >= 0 && contains(y-2, x-2).getColor().equals("none") && contains(y-1, x-1).getColor().equals("black"))))
				{
					//System.out.println("debug 8");
					return true;
				}
				else
				{
					return false;
				}
			}
		}
		else if(turn)  //for black pieces
		{System.out.println("Black piece triggered");
			if(y >= 2 && x >= 2 && x <= 5 && contains(y, x).getColor().equals("black"))    //piece in middle of board
			{
				if(contains(y-2, x-2).getColor().equals("none") && contains(y-1, x-1).getColor().equals("red"))
				{
					//System.out.println("debug 1, y: " + y + "  x: " + x);
					return true;
				}
				else if(contains(y-2, x+2).getColor().equals("none") && contains(y-1, x+1).getColor().equals("red"))
				{
					//System.out.println("debug 2");
					return true;
				}
				else
				{
					return false;
				}
			}
			else if(y >= 2 && x <= 1 && contains(y, x).getColor().equals("black"))   //piece on left edge
			{
				if(contains(y-2, x+2).getColor().equals("none") && contains(y-1, x+1).getColor().equals("red"))
				{
					//System.out.println("debug 3, y: " + y + "  x: " + x);
					return true;
				}
				else
				{
					return false;
				}
			}
			else if(y >= 2 && x >= 6 && contains(y, x).getColor().equals("black"))   //piece on right edge
			{
				if(contains(y-2, x-2).getColor().equals("none") && contains(y-1, x-1).getColor().equals("red"))
				{
					//System.out.println("debug 4");
					return true;
				}
				else
				{
					return false;
				}
			}
		}
		else   //for red pieces
		{
			if(y <= 5 && x <= 5 && x >= 2 && contains(y, x).getColor().equals("red"))   //piece in middle of board
			{
				if(contains(y+2, x+2).getColor().equals("none") && contains(y+1, x+1).getColor().equals("black"))
				{
					System.out.println("debug 5");
					return true;
				}
				else if(contains(y+2, x-2).getColor().equals("none") && contains(y+1, x-1).getColor().equals("black"))
				{
					System.out.println("debug 6");
					return true;
				}
				else
				{
					return false;
				}
			}
			else if(y <= 5 && x <= 1 && contains(y, x).getColor().equals("red"))   //piece on left edge
			{
				if(contains(y+2, x+2).getColor().equals("none") && contains(y+1, x+1).getColor().equals("black"))
				{
					System.out.println("debug 7");
					return true;
				}
				else
				{
					return false;
				}
			}
			else if(y <= 5 && x >= 6 && contains(y, x).getColor().equals("red"))   //piece on right edge
			{
				if(contains(y+2, x-2).getColor().equals("none") && contains(y+1, x-1).getColor().equals("black"))
				{
					System.out.println("debug 8");
					return true;
				}
				else
				{
					return false;
				}
			}
		}
		return false;
	}
	
	
	//used to determine if the selected player has any jumps available at all
	public boolean hasJump(boolean turn)
	{
		boolean flag = false;
		for(int y = 0; y < 8; y++)
		{
			for(int x = 0; x < 8; x++)
			{
				if(hasJumpAt(y, x, turn) == true) 
				{
					flag = true;
				}
			}
		}
		return flag;
	}
	
	//used to see if the chosen move is a jump or not
	public boolean isJump(int y1, int x1, int y2, int x2)
	{
		if(contains(y2, x2).getColor().equals("none"))
		{
			if(contains(y1, x1).getType() == 2)  //for black kings
			{
				if(y1 - 2 == y2 && (x1 - 2 == x2 || x1 + 2 == x2))  //toward top of screen
				{
					if(y1 >= 2 && x1 >= 2 && x1 <= 5)  //piece in middle of board
					{
						if(contains(y1, x1).getColor().equals("black") && (contains(y1-1, x1+1).getColor().equals("red") || contains(y1-1, x1-1).getColor().equals("red")))
						{
							return true;
						}
					}
					else if(y1 >= 2 && x1 <= 1)   //piece on left edge
					{
						if(contains(y1, x1).getColor().equals("black") && (contains(y1-1, x1+1).getColor().equals("red")))
						{
							return true;
						}
					}
					else if(y1 >= 2 && x1 >= 6)   //piece on right edge
					{
						if(contains(y1, x1).getColor().equals("black") && (contains(y1-1, x1-1).getColor().equals("red")))
						{
							return true;
						}
					}
				}
				else if(y1 + 2 == y2 && (x1 - 2 == x2 || x1 + 2 == x2))    //toward bottom of screen
				{
					if(y1 <= 5 && x1 >= 2 && x1 <= 5)  //piece in middle of board
					{
						if(contains(y1, x1).getColor().equals("black") && (contains(y1+1, x1+1).getColor().equals("red") || contains(y1+1, x1-1).getColor().equals("red")))
						{
							return true;
						}
					}
					else if(y1 <= 5 && x1 <= 1)   //piece on left edge
					{
						if(contains(y1, x1).getColor().equals("black") && (contains(y1+1, x1+1).getColor().equals("red")))
						{
							return true;
						}
					}
					else if(y1 <= 5 && x1 >= 6)   //piece on right edge
					{
						if(contains(y1, x1).getColor().equals("black") && (contains(y1+1, x1-1).getColor().equals("red")))
						{
							return true;
						}
					}
				}
			}
			/////////
			if(contains(y1, x1).getType() == 2)  //for red kings
			{
				if(y1 - 2 == y2 && (x1 - 2 == x2 || x1 + 2 == x2))  //toward top of screen
				{
					if(y1 >= 2 && x1 >= 2 && x1 <= 5)  //piece in middle of board
					{
						if(contains(y1, x1).getColor().equals("red") && (contains(y1-1, x1+1).getColor().equals("black") || contains(y1-1, x1-1).getColor().equals("black")))
						{
							return true;
						}
					}
					else if(y1 >= 2 && x1 <= 1)   //piece on left edge
					{
						if(contains(y1, x1).getColor().equals("red") && (contains(y1-1, x1+1).getColor().equals("black")))
						{
							return true;
						}
					}
					else if(y1 >= 2 && x1 >= 6)   //piece on right edge
					{
						if(contains(y1, x1).getColor().equals("red") && (contains(y1-1, x1-1).getColor().equals("black")))
						{
							return true;
						}
					}
				}
				else if(y1 + 2 == y2 && (x1 - 2 == x2 || x1 + 2 == x2))    //toward bottom of screen
				{
					if(y1 <= 5 && x1 >= 2 && x1 <= 5)  //piece in middle of board
					{
						if(contains(y1, x1).getColor().equals("red") && (contains(y1+1, x1+1).getColor().equals("black") || contains(y1+1, x1-1).getColor().equals("black")))
						{
							return true;
						}
					}
					else if(y1 <= 5 && x1 <= 1)   //piece on left edge
					{
						if(contains(y1, x1).getColor().equals("red") && (contains(y1+1, x1+1).getColor().equals("black")))
						{
							return true;
						}
					}
					else if(y1 <= 5 && x1 >= 6)   //piece on right edge
					{
						if(contains(y1, x1).getColor().equals("red") && (contains(y1+1, x1-1).getColor().equals("black")))
						{
							return true;
						}
					}
				}
			}
			////
			else if(y1 - 2 == y2 && (x1 - 2 == x2 || x1 + 2 == x2))   //black
			{
				if(y1 >= 2 && x1 >= 2 && x1 <= 5)  //piece in middle of board
				{
					if(contains(y1, x1).getColor().equals("black") && (contains(y1-1, x1+1).getColor().equals("red") || contains(y1-1, x1-1).getColor().equals("red")))
					{
						return true;
					}
				}
				else if(y1 >= 2 && x1 <= 1)   //piece on left edge
				{
					if(contains(y1, x1).getColor().equals("black") && (contains(y1-1, x1+1).getColor().equals("red")))
					{
						return true;
					}
				}
				else if(y1 >= 2 && x1 >= 6)   //piece on right edge
				{
					if(contains(y1, x1).getColor().equals("black") && (contains(y1-1, x1-1).getColor().equals("red")))
					{
						return true;
					}
				}
				
			}
			else if(y1 + 2 == y2 && (x1 - 2 == x2 || x1 + 2 == x2))    //red
			{
				if(y1 <= 5 && x1 >= 2 && x1 <= 5)  //piece in middle of board
				{
					if(contains(y1, x1).getColor().equals("red") && (contains(y1+1, x1+1).getColor().equals("black") || contains(y1+1, x1-1).getColor().equals("black")))
					{
						return true;
					}
				}
				else if(y1 <= 5 && x1 <= 1)   //piece on left edge
				{
					if(contains(y1, x1).getColor().equals("red") && (contains(y1+1, x1+1).getColor().equals("black")))
					{
						return true;
					}
				}
				else if(y1 <= 5 && x1 >= 6)   //piece on right edge
				{
					if(contains(y1, x1).getColor().equals("red") && (contains(y1+1, x1-1).getColor().equals("black")))
					{
						return true;
					}
				}
			}
			return false;
		}
		else
		{
			//System.out.println("We caught the bug in isJump()");
			return false;
		}
	}
}
