/**
 * This class implements all the methods needed by algorithm computerPlay.
 * @author Taha Nasir
 */
public class TicTacToe {
	
	private int board_size;
	private int inline =0;
	private int max_levels;
	private char[][] gameBoard;
	private Dictionary dict;

	/** 
	 * Constructor for TicTacToe that specifies the size, symbols in-line, and maximum levels. 
	 * @param board_size size of game board
	 * @param inline number of symbols in-line needed to win the game
	 * @param max_levels maximum levels of the game tree that will be explored by the program
     */
	public TicTacToe (int board_size, int inline, int max_levels) {
		this.board_size = board_size;
		this.inline = inline;
		this.max_levels = max_levels;
		
		gameBoard = new char[board_size][board_size];
		for (int row = 0; row < board_size; row++) {
			for (int col = 0; col < board_size; col++) {
				gameBoard[row][col] = ' ';
			}
		}
	}
	
	/** 
	 * Method that creates a dictionary of a specified size.
     * @return dict empty Dictionary of the size
     */
	public Dictionary createDictionary() {
		dict = new Dictionary(4999);
		return dict;
	}
	
	/** 
	 * Method first represents the content of gameBoard as a string; 
	 * then checks whether the string representing the gameBoard is in the configurations dictionary. 
	 * @param configurations the config key
     * @return configurations.find(sBoard) associated score
     * @return -1 otherwise
     */	
	public int repeatedConfig(Dictionary configurations) {
		
		String sBoard = "";
		for (int row = 0; row < board_size; row++){
			for (int col = 0; col < board_size; col++){
				sBoard = sBoard + gameBoard[row][col];
			}
		}
		
		if (configurations.find(sBoard) != -1) {
			return configurations.find(sBoard);
		}
		return -1;
	}
	
	/** 
	 * Method method first represents the content of gameBoard as a string; 
	 * then it inserts this string and score in the configurations dictionary.
	 * @param configurations the config key
	 * @param score associated score
     */	
	public void insertConfig(Dictionary configurations, int score) {
		
		String sBoard = "";
			for (int row = 0; row < board_size; row++){
				for (int col = 0; col < board_size; col++){
					sBoard = sBoard + gameBoard[row][col];
				}
			}
			
		try {
		      configurations.insert(new DictEntry(sBoard, score));
		    } catch (DictionaryException e) {
		      System.out.println("Excpetion: DictEntry already exists.");
		    }
	}
	
	/** 
	 * Method which stores a symbol in gameBoard[row][col]
	 * @param row represents the rows in the game board
	 * @param col represents the column in the game board
	 * @param symbol associated symbol represented as a character
     */
	public void storePlay(int row, int col, char symbol) {
		gameBoard[row][col] = symbol;
	}
	
	/** 
	 * Method which checks if the game board contains a blank character 
	 * @param row represents the rows in the game board
	 * @param col represents the column in the game board
	 * @return true  if gameBoard[row][col] is ’ ’
	 * @return false otherwise 
     */
	public boolean squareIsEmpty (int row, int col) {
		if (gameBoard[row][col] == ' ') {
			return true;
		}
		else return false;
	}
	
	/** 
	 * Method checks if the player has won the game. 
	 * Checks all possible combinations of winning; horizontal, vertical and diagonal 
	 * @param symbol associated symbol represented as a character
	 * @return true if k adjacent occurrences of symbol
	 * @return false otherwise 
     */
	public boolean wins(char symbol) {
		
		int k = 0;
		int d1 = 0;
		int d2 = 0;
		
		for (int row = 0; row < board_size; row++){
			for (int col = 0; col < board_size; col++){
				if (gameBoard[row][col] == symbol){
				k++;	
				}
			}
			if (k == inline){
			return true;
			}
			k=0;
		}
		
		for (int row = 0; row < board_size; row++){
			for (int col = 0; col < board_size; col++){
				if (gameBoard[col][row] == symbol){
				k++;	
				}
			}
			if (k == inline){
			return true;
			}
			k=0;
		}
		
		for (int row = 0; row < board_size; row++){
			for (int col = 0; col < board_size; col++){
				if ((row == col) && (gameBoard[row][col] == symbol)){
					d1++;
				}
			}	
			if (d1 == inline) {return true;}
		}
		
		for (int row = 0; row < board_size; row++){
			for (int col = 0; col < board_size; col++){
				if ((row + col == board_size - 1) && (gameBoard[row][col] == symbol)){
					d2++;	
				}
			}	
			if (d2 == inline) {return true;}		
		}	
		
		return false;
	}
	
	/** 
	 * Method checks if the game is a draw. 
	 * @return true  if gameBoard has no empty positions left and no player has won the game
	 * @return false otherwise
     */
	public boolean isDraw() {
		boolean pass = true;

		for (int row = 0; row < board_size; row++) {
		    for (int col = 0; col < board_size; col++) {
		    	if (gameBoard[row][col] == ' ') {return false;}
		    }
		}
		return true;
	}
	
	/** 
	 * Method which evaluates the result of the game board. 
	 * @return 3 if the computer has won
	 * @return 0 if the human player has won
	 * @return 2 if the game is a draw
	 * @return 1 if the game is still undecided
     */
	public int evalBoard() {
		if (wins('O')) {return 3;}
		else if (wins('X')) {return 0;}
		else if (isDraw()) {return 2;}
		else 
			return 1;
	    }		
	
}