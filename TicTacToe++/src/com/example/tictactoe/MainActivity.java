package com.example.tictactoe;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private TableLayout board;
	private String namePlayer1;
	private String namePlayer2;
	private ImageView image;
	private TicTacToeGame game;

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

				// row.setPadding(0,0,0,5);
			}
			board.addView(row, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		}
	}

	private void playGame() {
		game = new TicTacToeGame();
		game.start("Player1", "Player2");

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
			Toast.makeText(this, "Check Error", Toast.LENGTH_SHORT).show();
			Log.e("ARRAY INDEX", e.toString() + " Clicked Id = " + clicked_id);
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
				// updatedBackgroundImage =
				// String.valueOf(update_imageView.getTag());
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

		if (currentValid[0] == -1 || currentValid[1] == -1) {

			// enable all non-completed blocks
			Toast.makeText(this, "Case of -1", Toast.LENGTH_SHORT).show();

		} else {
			// Toast.makeText(this, "Case of " + currentValid[0] + ", " +
			// currentValid[1], Toast.LENGTH_SHORT).show();
			for (int row = currentValid[0] * 3; row < 3 * currentValid[0] + 3; row++) {
				for (int col = currentValid[1] * 3; col < 3 + 3 * currentValid[1]; col++) {

					if (9 * row + col + 1 < 1 || 9 * row + col + 1 > 81) {
						Toast.makeText(this, "Out of Bounds", Toast.LENGTH_LONG).show();
					}

					update_imageView = (ImageView) findViewById(9 * row + col + 1);
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
	}

}