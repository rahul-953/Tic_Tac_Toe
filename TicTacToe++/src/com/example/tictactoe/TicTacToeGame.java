package com.example.tictactoe;

/**
 * @author rahul
 * 
 * @version V-1.01
 * 
 * @developed date June 1, 2017
 */
public class TicTacToeGame {

	/**
	 * For current turn in which Block of completedBlock[][] players can play. -1 if
	 * Players can move in any Block.
	 */
	private int blockToPlay_x, blockToPlay_y;

	/**
	 * It is a 9X9 Board set up to track moves of players: ................... -1 :
	 * No one has played on this square yet............................... 0 :
	 * Player 2(0) has make a move on this square .......................,. 1 :
	 * Player 1(X) has make a move on this square.
	 */
	private int board[][];

	/**
	 * It is a 3X3 Block mapping of 9X9 Board to track which player has won it. -1 :
	 * No one has win the game on this block. Block is available to play.. 00 :
	 * Player 2 has win the game on this block. Not available for playing. 01 :
	 * Player 1 has win the game on this block. Not available for playing.
	 */
	private int completedBlock[][];

	/**
	 ** This boolean stores status of Game. True if GameOver, otherwise False.
	 */
	private boolean isGameOver;

	/**
	 * Name of Player 1
	 */
	private String namePlayer1;

	/**
	 * Name of Player 2
	 */
	private String namePlayer2;

	/**
	 * To track which Player's turn it is: 0 -> Player TWO. 1 -> Player ONE.
	 */
	private int playerTurn;

	/**
	 ** To store name of Winner otherwise Draw.
	 */
	private String winner;

	/*
	 * Methods
	 */

	private int checkBoard(int row, int col) {

		// case 1/8
		if (board[row][col] == board[row + 1][col] && board[row + 2][col] == board[row + 1][col]
				&& board[row + 1][col] != -1)
			return board[row + 1][col];

		// case 2/8
		else if (board[row][col] == board[row][col + 1] && board[row][col + 2] == board[row][col + 1]
				&& board[row][col + 1] != -1)
			return board[row][col + 1];

		// case 3/8
		else if (board[row][col] == board[row + 1][col + 1] && board[row + 2][col + 2] == board[row + 1][col + 1]
				&& board[row][col] != -1)
			return board[row][col];

		// case 4/8
		else if (board[row][col + 1] == board[row + 1][col + 1] && board[row + 2][col + 1] == board[row + 1][col + 1]
				&& board[row + 1][col] != -1)
			return board[row + 1][col];

		// case 5/8
		else if (board[row][col + 2] == board[row + 1][col + 2] && board[row + 2][col + 2] == board[row + 1][col + 2]
				&& board[row + 1][col + 2] != -1)
			return board[row + 1][col + 2];

		// case 6/8
		else if (board[row + 1][col] == board[row + 1][col + 1] && board[row + 1][col + 1] == board[row + 1][col + 2]
				&& board[row + 1][col + 2] != -1)
			return board[row + 1][col + 2];

		// case 7/8
		else if (board[row + 2][col] == board[row + 2][col + 1] && board[row + 2][col + 1] == board[row + 2][col + 2]
				&& board[row + 2][col + 2] != -1)
			return board[row + 2][col + 2];

		// case 8/8
		else if (board[row + 2][col] == board[row + 1][col + 1] && board[row + 2][col] == board[row][col + 2]
				&& board[row + 2][col + 2] != -1)
			return board[row + 2][col + 2];

		return -1;
	}

	private boolean checkCompletedBlock(int row, int col) {

		// case 1/8
		if (completedBlock[row][col] == completedBlock[row + 1][col]
				&& completedBlock[row + 2][col] == completedBlock[row + 1][col] && completedBlock[row + 1][col] != -1)
			return true;

		// case 2/8
		else if (completedBlock[row][col] == completedBlock[row][col + 1]
				&& completedBlock[row][col + 2] == completedBlock[row][col + 1] && completedBlock[row][col + 1] != -1)
			return true;

		// case 3/8
		else if (completedBlock[row][col] == completedBlock[row + 1][col + 1]
				&& completedBlock[row + 2][col + 2] == completedBlock[row + 1][col + 1]
				&& completedBlock[row][col] != -1)
			return true;

		// case 4/8
		else if (completedBlock[row][col + 1] == completedBlock[row + 1][col + 1]
				&& completedBlock[row + 2][col + 1] == completedBlock[row + 1][col + 1]
				&& completedBlock[row + 1][col] != -1)
			return true;

		// case 5/8
		else if (completedBlock[row][col + 2] == completedBlock[row + 1][col + 2]
				&& completedBlock[row + 2][col + 2] == completedBlock[row + 1][col + 2]
				&& completedBlock[row + 1][col + 2] != -1)
			return true;

		// case 6/8
		else if (completedBlock[row + 1][col] == completedBlock[row + 1][col + 1]
				&& completedBlock[row + 1][col + 1] == completedBlock[row + 1][col + 2]
				&& completedBlock[row + 1][col + 2] != -1)
			return true;

		// case 7/8
		else if (completedBlock[row + 2][col] == completedBlock[row + 2][col + 1]
				&& completedBlock[row + 2][col + 1] == completedBlock[row + 2][col + 2]
				&& completedBlock[row + 2][col + 2] != -1)
			return true;

		// case 8/8
		else if (completedBlock[row + 2][col] == completedBlock[row + 1][col + 1]
				&& completedBlock[row + 2][col] == completedBlock[row][col + 2]
				&& completedBlock[row + 2][col + 2] != -1)
			return true;

		return false;
	}

	public int[][] getBoard() {
		return board;
	}

	public int[][] getCompletedBlock() {
		return completedBlock;
	}

	protected int[] getCurrentValidBlock() {
		int validBlock[] = new int[2];
		validBlock[0] = blockToPlay_x;
		validBlock[1] = blockToPlay_y;
		return validBlock;
	}

	public boolean getIsGameOver() {
		return isGameOver;
	}

	private int getRow(int id) {
		return (id - 1) / 9;
	}

	private int getColumn(int id) {
		return id - 1 - ((id - 1) / 9) * 9;
	}

	public int getTurn() {
		return playerTurn;
	}

	public String getWinner() {
		return playerTurn % 2 == 0 ? namePlayer2 : namePlayer1;
	}

	/*
	 ** Initializes starting point of board. -1 signifies the square is available to
	 * play.
	 */
	private void initializeBoard() {

		board = new int[9][9];
		completedBlock = new int[3][3];

		for (int rows = 0; rows < 9; rows++) {
			for (int col = 0; col < 9; col++) {
				board[rows][col] = -1;
			}
		}

		for (int rows = 0; rows < 3; rows++) {
			for (int col = 0; col < 3; col++) {
				completedBlock[rows][col] = -1;
			}
		}
	}

	private boolean isGameOver() {
		return checkCompletedBlock(0, 0);
	}

	/*
	 ** Method to record the moves of player. After each move update these :
	 * board[][], isGameOver, completedBlock[][], validBlock for next Turn, next
	 * Player Turn,
	 */
	public void make_a_move(int id) {

		int row = getRow(id);
		int column = getColumn(id);
		int block_start_row = (row / 3);
		int block_start_col = (column / 3);

		// System.out.println("Player " + playerTurn + " Clicked id = " + id);
		board[row][column] = playerTurn % 2 == 0 ? 0 : 1;
		completedBlock[row / 3][column / 3] = checkBoard(block_start_row, block_start_col);

		setIsGameOver(isGameOver());
		setValidBlock(row % 3, column / 3);
		setPlayerTurn(playerTurn + 1);
	}

	/*
	 ** Method to update the x co-ordinate of 3X3 block to play in current turn.
	 *
	 ** arguments: int x: x co-ordinate for the turn.
	 */
	private void setBlockToPlay_x(int x) {
		blockToPlay_x = x;
	}

	/*
	 ** Method to update the y co-ordinate of 3X3 block to play in current turn.
	 *
	 ** arguments: int y: y co-ordinate for the turn.
	 */
	private void setBlockToPlay_y(int y) {
		blockToPlay_y = y;
	}

	/*
	 ** Method to update the variable isGameOver
	 *
	 ** arguments: boolean isIt : true if Game is Over.
	 */
	private void setIsGameOver(boolean isIt) {
		isGameOver = isIt;
	}

	/*
	 ** Method to store name of Player 1
	 *
	 ** arguments: String P1 : Name of Player 1
	 **/
	private void setPlayerOneName(String P1) {
		namePlayer1 = P1;
	}

	/*
	 ** Method to update the turn of Player
	 *
	 ** arguments: int turn: Player whose turn to Play. 0 for P2. 1 for P1
	 */
	private void setPlayerTurn(int turn) {
		playerTurn = turn % 2;

	}

	/*
	 ** Method to store name of Player 2
	 *
	 ** arguments: String P2 : Name of Player 2
	 */
	private void setPlayerTwoName(String P2) {
		namePlayer2 = P2;
	}

	private void setValidBlock(int current_x, int current_y) {

		if (completedBlock[current_x][current_y] == -1) {

			blockToPlay_x = current_x;
			blockToPlay_y = current_y;

		} else {

			blockToPlay_x = -1;
			blockToPlay_y = -1;
		}
	}

	/*
	 ** Acts like Constructor to set initial conditions for game to begin.
	 *
	 ** arguments: String P1, String P2: Players Names
	 */
	public void start(String P1, String P2) {
		setPlayerOneName(P1);
		setPlayerTwoName(P2);
		setIsGameOver(false);
		setBlockToPlay_x(-1);
		setBlockToPlay_y(-1);
		setPlayerTurn(1);

		initializeBoard();
	}
}