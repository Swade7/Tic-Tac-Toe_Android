package com.zybooks.tic_tac_toe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import androidx.core.view.children

class MainActivity : AppCompatActivity() {
    private lateinit var game: TicTacToe
    private lateinit var ticTacToeGridLayout: GridLayout
    private var player1Wins = 0
    private var player2Wins = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ticTacToeGridLayout = findViewById(R.id.tic_tac_toe_grid)

        // Add the same click handler to all grid buttons
        for (gridButton in ticTacToeGridLayout.children) {
            gridButton.setOnClickListener(this::onBoardClick)


        }
        game = TicTacToe()
    }

    private fun onBoardClick(view: View) {
        val buttonIndex = ticTacToeGridLayout.indexOfChild(view)
        val row = buttonIndex / GRID_SIZE
        val col = buttonIndex % GRID_SIZE

        if (game.checkValidMove(row, col)) {
            game.makeMove(row, col)
        }


        // TODO: Launch the game_over Activity if the game is over
    }

}