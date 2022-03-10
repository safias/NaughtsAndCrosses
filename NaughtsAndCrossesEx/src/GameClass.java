import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameClass {
	
	public JButton[] grid = new JButton [9]; //setting grid with 9 buttons
	public ImageIcon xSymbol, oSymbol; //image ready for player symbols
	public int[] board = new int[9]; //integer for each button to reference
	public boolean won = false; //start with no win so we can get to true to end game
	public int playerTurn = 1; //starting with x's playing first
	
	public void play (int id) {
		//printing square value that has been clicked from 0 to 8 when clicked in console
		System.out.println("Playing square " + id);
		//change player turn but first check if square free
		//so if return true for change then we can change player
		if(attemptChange(id)) {
			//if current player is x then current player is o else x
			//allows player turn to change
			playerTurn = (playerTurn == 1) ? 2 : 1;
			checkWin();	
		}
		
	}
	
	public boolean attemptChange(int square) {
		//checking if square already has a value
		if(board [square] != 0 ) {
			//not allowing current value to change, so first clicked keeps that square
			return false;
		}
		board[square] = playerTurn;
		//set icon to player taking turn
		grid[square].setIcon(playerTurn == 1? xSymbol:oSymbol);
		return true;
	}
	
	private void checkWin() {
		//checking if win horizontally on top row by comparing values within board squares
		//include not equal to 0 as if = 0 then a move is left to play
		if ((board[0] == board[1]) && (board[1] == board[2]) && board[2] != 0) {
			win(0);
		}
		//checking if win horizontally on middle row
		if ((board[3] == board[4]) && (board[4] == board[5]) && board[5] != 0) {
			win(5);
		}
		//checking if win horizontally on bottom row
		if ((board[6] == board[7]) && (board[7] == board[8]) && board[8] != 0) {
			win(8);
		}
		//checking if win vertically on left column
		if ((board[0] == board[3]) && (board[3] == board[6]) && board[6] != 0) {
			win(6);
		}
		//checking if win vertically on middle column
		if ((board[1] == board[4]) && (board[4] == board[7]) && board[7] != 0) {	
			win(7);
		}
		//checking if win vertically on right column
		if ((board[2] == board[5]) && (board[5] == board[8]) && board[8] != 0) {
			win(8);
		}
		//checking if win diagonally from top left to bottom right
		if ((board[0] == board[4]) && (board[4] == board[8]) && board[8] != 0) {
			win(8);
		}
		//checking if win diagonally from bottom left to top right
		if ((board[6] == board[4]) && (board[4] == board[2]) && board[2] != 0) {
			win(2);
		}
		
		//if no square on board is = 0 and no won then its a draw
		for(int i = 0; i < board.length; i++ ) {
			if (board[i] == 0) {
				return;
			}
		}
		//if no win then must be a draw
		if(!won) {
			draw();
		}
	}
	

	private void win(int square) {
		//setting a scenario for if won
		won = true;
		//if 1 then x won else o won
		String winner = board[square] == 1? "X":"O";
		//showing pop up with option to replay etc
		if(JOptionPane.showConfirmDialog(new JFrame("Winner! Congratulations  " + winner), "Player " + winner + " has won! Would you like to play again?") == JOptionPane.YES_OPTION) {
			restart();
		} 
		//if not asked to replay then exit window entirely
		else {
			System.exit(0);
		}
		
	}

	public void restart() {
		//setting won to false again to establish winner to be found
		won = false;
		//player turn of x again
		playerTurn = 1;
		//setting all squares on grid to null to remove icons from previous game
		for(int i = 0; i < 9; i++) {
			board[i] = 0;
			grid[i].setIcon(null);
		}
	}

	public void draw() {
		//asking if want to replay and stating it's a draw
		if(JOptionPane.showConfirmDialog(new JFrame("It's a draw!"), "It's a draw! Would you like to play again?") == JOptionPane.YES_OPTION) {
			restart();
		} 
		else {
			System.exit(0);
			}
	}

	//components for pop out grid utilising java imports
	public void init_components() {
		//setting a new jframe with size etc
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(3);
		frame.setVisible(true);
		frame.setResizable(true);
		frame.setSize(50, 50);
		JPanel panel = new JPanel();
		frame.setContentPane(panel);
		//setting a 3 by 3 grid, 3 rows by 3 columns
		panel.setLayout(new GridLayout (3, 3, 0, 0));
		//adding in listener
		panel.addComponentListener(new ComponentListener() {
			
			public void componentResized(ComponentEvent e) {
				//
				init_icons();
				
			}
			public void componentMoved(ComponentEvent e) {}
			public void componentShown(ComponentEvent e) {}
			public void componentHidden(ComponentEvent e) {}
		});
		
		//for the grid adding in buttons
		for(int i = 0; i < 9; i++) {
			final int pos = i;
			//position on grid will be where button goes
			grid[i] = new JButton();
			//adding in action listener for buttons when player is playing
			grid[i].addActionListener(new ActionListener() {
				int id = pos;
				
				public void actionPerformed(ActionEvent e) {
					//go to this method where we will see what square is being clicked on grid
					play(id);
				}
			});
			
			//adding button to grid
			panel.add(grid[i]);
		}
		//setting frame size
		frame.setSize(450, 450);
	}
	
	public void init_icons() {
		//setting image for each player symbol
		xSymbol = new ImageIcon("NaughtsAndCrossesEx/res/x.png");
		oSymbol = new ImageIcon("NaughtsAndCrossesEx/res/o.png");
		for(int i = 0; i < 9; i++) {
			if(grid[i].getIcon() != null)
				grid[i].setIcon(board[i] == 1? xSymbol:oSymbol);
		}
	}
	
	public static void main(String[] args) {
		//enabling game to be run
		GameClass start = new GameClass();
		start.init_components();
		
	}

}