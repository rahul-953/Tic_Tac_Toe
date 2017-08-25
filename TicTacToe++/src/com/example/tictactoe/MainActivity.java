package com.example.tictactoe;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private TableLayout board;
	private String namePlayer1;
	private String namePlayer2;
	private ImageView image;
	private TicTacToeGame game;
	private TextView player1, player2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

		board = (TableLayout) findViewById(R.id.table);
		displayBoard();
		playGame();
	}

	public void displayBoard() {

		player1 = (TextView) findViewById(R.id.player1);
		player2 = (TextView) findViewById(R.id.player2);

		int count = 1;
		TableRow row = null;

		for (int rows = 0; rows < 9; rows++) {
			row = new TableRow(this);
			for (int col = 0; col < 9; col++) {

				image = new ImageView(this);
				image.setId(count++);
				image.setTag("DARK_CELL");
				image.setOnClickListener(this);
				image.setBackgroundResource(R.drawable.dark_cell);

				row.addView(image);

			}
			board.addView(row, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		}
	}

	private void playGame() {
		game = new TicTacToeGame();
		namePlayer1 = player1.getText().toString();
		namePlayer2 = player2.getText().toString();
		game.start(namePlayer1, namePlayer2);

		Toast.makeText(this, "Player 1 to Play", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onClick(View v) {

		// Get id for clicked View
		int clicked_id = v.getId();

		try {

			// clicked Id is ImageID
			if (clicked_id >= 1 && clicked_id <= 81) {
				game.make_a_move(clicked_id);
				updateUI();

			} else {
				Toast.makeText(this, "Something is pressed which is not Handled Yet", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Toast.makeText(this, "Error !!  " + e.toString(), Toast.LENGTH_SHORT).show();
			Log.e("ARRAY INDEX", e.toString() + " Clicked Id = " + clicked_id);
		}

		if (game.getIsGameOver()) {
			// Stop game and pop-up Result
			showResultPopupWindow();

		}
	}

	private void updateUI() {

		disableAll();
		enableCurrentValidBlock();
	}

	private void disableAll() {

		ImageView update_imageView;
		int currentBoard[][] = game.getBoard();
		int temp_count = 1;
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {

				update_imageView = (ImageView) findViewById(temp_count++);
				update_imageView.setClickable(false);

				if (currentBoard[row][col] == -1) {
					update_imageView.setTag("LIGHT_CELL");
					update_imageView.setBackgroundResource(R.drawable.light_cell);
				} else if (currentBoard[row][col] == 0) {
					update_imageView.setTag("LIGHT_ZERO");
					update_imageView.setBackgroundResource(R.drawable.light_zero);
				} else if (currentBoard[row][col] == 1) {
					update_imageView.setTag("LIGHT_CROSS");
					update_imageView.setBackgroundResource(R.drawable.light_cross);
				}
			}
		}

	}

	private void enableCurrentValidBlock() {

		ImageView update_imageView;
		String updatedBackgroundImage;
		int currentValid[] = game.getCurrentValidBlock();
		int nonPlayable[][] = game.getCompletedBlock();

		if (currentValid[0] == -1 || currentValid[1] == -1) {

			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (nonPlayable[i][j] != -1) {
						// disable this block
						disableThisBlock(i * 3, j * 3);
					} else {
						// enable this block
						enableThisBlock(i * 3, j * 3);
					}
				}
			}

		} else {

			for (int row = currentValid[0] * 3; row < 3 * currentValid[0] + 3; row++) {
				for (int col = currentValid[1] * 3; col < 3 + 3 * currentValid[1]; col++) {

					if (9 * row + col + 1 < 1 || 9 * row + col + 1 > 81) {
						Toast.makeText(this, "Out of Bounds", Toast.LENGTH_LONG).show();
					}

					update_imageView = (ImageView) findViewById(9 * row + col + 1);
					updatedBackgroundImage = String.valueOf(update_imageView.getTag());
					if (nonPlayable[game.getRow(9 * row + col + 1) / 3][game.getColumn(9 * row + col + 1) / 3] != 2) {
						if (updatedBackgroundImage.equals("LIGHT_CELL")) {
							update_imageView.setTag("DARK_CELL");
							update_imageView.setBackgroundResource(R.drawable.dark_cell);
							update_imageView.setClickable(true);
						} else if (updatedBackgroundImage.equals("LIGHT_CROSS")) {
							update_imageView.setTag("DARK_CROSS");
							update_imageView.setBackgroundResource(R.drawable.dark_cross);
							update_imageView.setClickable(true);
						} else if (updatedBackgroundImage.equals("LIGHT_ZERO")) {
							update_imageView.setTag("DARK_ZERO");
							update_imageView.setBackgroundResource(R.drawable.dark_zero);
							update_imageView.setClickable(true);
						}
					}
				}
			}

		}
	}

	private void enableThisBlock(int xStart, int yStart) {

		ImageView update_imageView;
		String updatedBackgroundImage;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				update_imageView = (ImageView) findViewById(9 * (xStart + i) + yStart + j + 1);
				updatedBackgroundImage = String.valueOf(update_imageView.getTag());

				if (updatedBackgroundImage.equals("LIGHT_CELL")) {
					update_imageView.setTag("DARK_CELL");
					update_imageView.setBackgroundResource(R.drawable.dark_cell);
					update_imageView.setClickable(true);
				} else if (updatedBackgroundImage.equals("LIGHT_CROSS")) {
					update_imageView.setTag("DARK_CROSS");
					update_imageView.setBackgroundResource(R.drawable.dark_cross);
					update_imageView.setClickable(true);
				} else if (updatedBackgroundImage.equals("LIGHT_ZERO")) {
					update_imageView.setTag("DARK_ZERO");
					update_imageView.setBackgroundResource(R.drawable.dark_zero);
					update_imageView.setClickable(true);
				}
			}
		}
	}

	private void disableThisBlock(int xStart, int yStart) {
		ImageView update_imageView;
		String updatedBackgroundImage;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				update_imageView = (ImageView) findViewById(9 * (xStart + i) + yStart + j + 1);
				updatedBackgroundImage = String.valueOf(update_imageView.getTag());

				if (updatedBackgroundImage.equals("DARK_CELL")) {
					update_imageView.setTag("LIGHT_CELL");
					update_imageView.setBackgroundResource(R.drawable.light_cell);
					update_imageView.setClickable(false);
				} else if (updatedBackgroundImage.equals("DARK_CROSS")) {
					update_imageView.setTag("LIGHT_CROSS");
					update_imageView.setBackgroundResource(R.drawable.light_cross);
					update_imageView.setClickable(false);
				} else if (updatedBackgroundImage.equals("DARK_ZERO")) {
					update_imageView.setTag("LIGHT_ZERO");
					update_imageView.setBackgroundResource(R.drawable.light_zero);
					update_imageView.setClickable(false);
				}
			}
		}
	}

	public void showResultPopupWindow() {

		// get a reference to the already created main layout
		LinearLayout mainLayout = (LinearLayout) findViewById(R.id.buttonlayout);

		// inflate the layout of the popup window
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		LinearLayout parentLayout = (LinearLayout) mainLayout.findViewById(R.id.orientation);
		View popupView = inflater.inflate(R.layout.popup_window, parentLayout);

		TextView tv = (TextView) popupView.findViewById(R.id.popup);
		if (game.getWinner().equals("DRAW"))
			tv.setText("DRAW !!!");
		else
			tv.setText(game.getWinner() + " WINS the game !!!");

		// create the popup window
		int width = LinearLayout.LayoutParams.WRAP_CONTENT;
		int height = LinearLayout.LayoutParams.WRAP_CONTENT;
		boolean focusable = true; // lets taps outside the popup also dismiss it
		final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

		// show the popup window
		popupWindow.showAtLocation(mainLayout, Gravity.CENTER, 0, 0);

		// dismiss the popup window when touched
		popupView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent event) {
				view.performClick();
				popupWindow.dismiss();
				return true;
			}
		});
	}
}