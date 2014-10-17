import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class PlayTTT extends JFrame {
     private final char COMPUTER = 'O';
     private final char HUMAN    = 'X';

     private JButton [][] gameDisplay;/* Game board */
     private TicTacToe t;       
     private int board_size;    /* Size of game board */
     private int max_level;     /* Maximum level of the game tree that
                                   will be explored                    */
     private Dictionary configurations;


    /* Constructor. Creates a panel to represent the game board and destroys
       the panel when its window is closed.                                 */
    public PlayTTT(int size, int to_win, int depth)
    {
        Container c = getContentPane();
	c.setLayout(new GridLayout(size,size));  
	gameDisplay = new JButton[size][size];
	Icon emptySquare = new ImageIcon("empty.gif");
	ClickHandler handler = new ClickHandler(size);

	/* Board is represented as a grid of clickable buttons */
        for(int i = 0; i < size; i++)
           for(int j = 0; j < size; j++) {
               gameDisplay[i][j] = new JButton("",emptySquare);
	       gameDisplay[i][j].setEnabled(true);
               add(gameDisplay[i][j]);
               gameDisplay[i][j].addActionListener(handler);
           }
               
	board_size = size;
	max_level = depth;
        t = new TicTacToe(size,to_win,depth); /* User code needed to play */
    }

    
    /* To run the program type: java PlayTTT size to_win, where 
       size is the size of the board and to_win is the number of
       symbols in line needed to win the game.                   */
    public static void main(String [] args)
    {

        /* Check that the number of arguments is the correct one */
	if (args.length != 3) {
	    System.out.println
		("Usage: java PlayTTT board-size symbols-inline-to-win depth");
	    System.exit(0);
	}

        /* Size of the game board */
	int size = Integer.parseInt(args[0]);

        /* Number of positions marked by the same player in the same row, 
	   column, or diagonal, required to win */
	int adjacent_to_win = Integer.parseInt(args[1]);
	int depth = Integer.parseInt(args[2]);

	/* Create the game board and start the game */
        JFrame f = new PlayTTT(size,adjacent_to_win,depth);

        f.setSize(size*100,size*100);
        f.setVisible(true);

        f.addWindowListener(new WindowAdapter( ) {
            public void windowClosing(WindowEvent event) {
                System.exit( 0 );
            }                 
        });
    }


    /* Panel to represent the game board. It contians methods for detecting
       the position selected by the human player.                           */

    private class ClickHandler implements ActionListener {
	private int board_size;
	private boolean game_ended = false;

	/* Constructor. Save board size in instance variable */
	public ClickHandler(int size) {
	    board_size = size;
	}

	/* When the user has selected a play, this method is invoked to 
	   process the selected play */
        public void actionPerformed(ActionEvent event) {
            if(event.getSource() instanceof JButton) { /* Some position of the 
							 board was selected */
		int row = -1, col = -1;
		PosPlay pos;

		if (game_ended) System.exit(0);
		/* Find out which position was selected by th eplayer */
                for (int i = 0; i < board_size; i++) {
                    for (int j = 0; j < board_size; j++)
                        if(event.getSource() == gameDisplay[i][j]) {
			    row = i;
			    col = j;
			    break;
			}
		    if (row != -1) break;
		}

		if (t.squareIsEmpty(row,col)) {
		    /* Valid play, mark it on the board */
                    gameDisplay[row][col].setIcon(
                         new ImageIcon("human.gif"));
		    gameDisplay[row][col].paint(gameDisplay[row][col].getGraphics());

		    t.storePlay(row,col,HUMAN);
		    if (t.wins(HUMAN)) endGame("Human wins"); 
		    else {
			if (t.isDraw()) endGame("Game is a draw"); 
			else {
			    pos = computerPlay(COMPUTER,-1,4,0);
 			    t.storePlay(pos.getRow(),pos.getCol(),COMPUTER);
			    gameDisplay[pos.getRow()][pos.getCol()].setIcon(
						new ImageIcon("computer.gif"));
			    if (t.wins(COMPUTER)) endGame("Computer wins");
			    else if (t.isDraw()) endGame("Game is a draw");
			}
		    }
		}
		else System.out.println("Invalid play");

            }
        }


	/* Explore the game tree and choose the best move for the computer */
	private PosPlay computerPlay(char symbol, int highest_score, 
                                     int lowest_score, int level) {

	char opponent;           // Opponent's symbol
        PosPlay reply;           // Opponent's best reply
 
        int bestRow = -1;
        int bestColumn = -1;     // Position of best play

        int value;
	int lookupVal;

	if (level == 0)   /* Create new hash table */
	    configurations = t.createDictionary();    

        if( symbol == COMPUTER ) {
            opponent = HUMAN; value = -1;
        }
        else {
            opponent = COMPUTER; value = 4;
        }

        for(int row = 0; row < board_size; row++)
            for(int column = 0; column < board_size; column++) {
                if(t.squareIsEmpty(row,column)) {     // Empty position
                    t.storePlay(row,column,symbol);   // Store next play
		    if (t.wins(symbol)||t.isDraw()||(level >= max_level))
                        // Game ending situation or max number of levels reached 
			reply = new PosPlay(t.evalBoard(),row,column);
		    else {
			lookupVal = t.repeatedConfig(configurations);
			if (lookupVal != -1) 
			    reply = new PosPlay(lookupVal,row,column);
			else {
			    reply = computerPlay(opponent, highest_score, 
                                         lowest_score, level + 1);
			    t.insertConfig(configurations,reply.getScore());
			}
		    }
		    t.storePlay(row,column,' ');
                    
		    if((symbol == COMPUTER && reply.getScore() > value) ||
		       (symbol == HUMAN && reply.getScore() < value)) {
			 bestRow = row; 
			 bestColumn = column;
			 value = reply.getScore();

			 /* Alpha/beta cut */
			 if (symbol == COMPUTER && value > highest_score) 
			     highest_score = value;
			 else if (symbol == HUMAN && value < lowest_score) 
			     lowest_score = value;

			 if (highest_score >= lowest_score) 
			     return new PosPlay(value, bestRow, bestColumn);
		    }
		    
		}
	    }
         return new PosPlay(value, bestRow, bestColumn);
        }


	/* Prompt the user for a key to terminate the game */
	private void endGame(String mssg) {
	    System.out.println(mssg);
	    System.out.println("");
	    System.out.println("Click on board to terminate game");
	    game_ended = true;
	}

    }
}
