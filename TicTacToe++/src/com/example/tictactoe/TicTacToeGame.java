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
	 * It is a 9X9 Board set up to track moves of players: ........................
	 * -1 : No one has played on this square yet...................................
	 * 0 : Player 2(0) has make a move on this square .............................
	 * 1 : Player 1(X) has make a move on this square.
	 */
	private int board[][];

	/**
	 * It is a 3X3 Block mapping of 9X9 Board to track which player has won it....
	 * -1 : No one has win the game on this block. Block is available to play.....
	 * 00 : Player 2 has win the game on this block. Not available for playing....
	 * 01 : Player 1 has win the game on this block. Not available for playing. ..
	 * 02 : This 3X3 block is DRAW
	 * 
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
	 * Name of WINNER otherwise DRAW
	 */
	private String winner;

	/**
	 * To track which Player's turn it is: 0 -> Player TWO. 1 -> Player ONE.
	 */
	private int playerTurn;

	/*
	 * Methods
	 */

	private int checkBoard(int row, int col) {

		// case 1st diagonal
		if (board[row][col] == board[row + 1][col + 1] && board[row + 2][col + 2] == board[row + 1][col + 1]
				&& board[row][col] != -1)
			return board[row][col];

		// case 2nd diagonal
		if (board[row][col + 2] == board[row + 1][col + 1] && board[row + 2][col] == board[row + 1][col + 1]
				&& board[row][col + 2] != -1)
			return board[row][col + 2];

		for (int i = row; i < row + 3; i++) {
			// win in ith row
			if (board[i][col] == board[i][col + 1] && board[i][col + 1] == board[i][col + 2] && board[i][col + 2] != -1)
				return board[i][col];
		}

		for (int j = col; j < col + 3; j++) {
			// win in jth col
			if (board[row][j] == board[row + 1][j] && board[row + 1][j] == board[row + 2][j] && board[row][j] != -1)
				return board[row][j];
		}

		// This square is not won by any player yet so check for draw
		for (int i = row; i < row + 3; i++) {
			for (int j = col; j < col + 3; j++) {
				if (board[i][j] == -1)
					return -1;
			}
		}
		return 2;

	}

	private boolean checkCompletedBlock(int row, int col) {

		// Assuming current player's turn is winner
		setWinner(getTurn() % 2 == 0 ? namePlayer2 : namePlayer1);

		// case 1st diagonal
		if (completedBlock[0][0] == completedBlock[1][1] && completedBlock[2][2] == completedBlock[1][1]
				&& completedBlock[0][0] != -1) {

			return true;
		}
		// case 2nd diagonal
		if (completedBlock[0][2] == completedBlock[1][1] && completedBlock[2][0] == completedBlock[1][1]
				&& completedBlock[0][2] != -1)
			return true;

		for (int i = 0; i < 3; i++) {
			// win in ith row
			if (completedBlock[i][0] == completedBlock[i][1] && completedBlock[i][1] == completedBlock[i][2]
					&& completedBlock[i][2] != -1)
				return true;

			// win in ith column
			if (completedBlock[0][i] == completedBlock[1][i] && completedBlock[1][i] == completedBlock[2][i]
					&& completedBlock[0][i] != -1)
				return true;
		}

		// This game is not won by any player yet so check for draw
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (completedBlock[i][j] == -1)
					return false;
			}
		}

		setWinner("DRAW"); // Game is DRAW so update assumed winner
		setIsGameOver(true); // Game is draw, so it's over
		return true;
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

	public int getRow(int id) {
		return (id - 1) / 9;
	}

	public int getColumn(int id) {
		return id - 1 - ((id - 1) / 9) * 9;
	}

	public int getTurn() {
		return playerTurn;
	}

	public String getWinner() {
		return winner;
	}

	private void setWinner(String win) {
		winner = win;
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

		if (board[row][column] == -1) {
			board[row][column] = playerTurn % 2;
			int startId = block_start_row * 27 + 1 + block_start_col * 3;
			completedBlock[row / 3][column / 3] = checkBoard(getRow(startId), getColumn(startId));

			setIsGameOver(isGameOver());
			setValidBlock(id);
			setPlayerTurn(playerTurn + 1);
		}
	}

	/*
	 ** Method to update the x co-ordinate of 3X3 block to play in current turn.
	 *
	 ** @arguments: int x: x co-ordinate for the turn.
	 */
	private void setBlockToPlay_x(int x) {
		blockToPlay_x = x;
	}

	/*
	 ** Method to update the y co-ordinate of 3X3 block to play in current turn.
	 *
	 ** @arguments: int y: y co-ordinate for the turn.
	 */
	private void setBlockToPlay_y(int y) {
		blockToPlay_y = y;
	}

	/*
	 ** Method to update the variable isGameOver
	 * 
	 ** @arguments: boolean isIt : true if Game is Over.
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

	private int getBlockMap(int id) {

		for (int i = 0; i <= 8; i++) {
			for (int j = 0; j < 9; j++) {
				if (MyConstants.MAP[i][j] == id)
					return i;
			}
		}
		return id;
	}

	private void setValidBlock(int id) {

		int temp = getBlockMap(id);
		blockToPlay_x = temp / 3;
		blockToPlay_y = temp % 3;

		if (completedBlock[temp / 3][temp % 3] != -1) {
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
		setWinner("DRAW");
	}
}