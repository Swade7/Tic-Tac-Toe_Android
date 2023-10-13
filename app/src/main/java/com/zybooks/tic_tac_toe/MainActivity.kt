package com.zybooks.tic_tac_toe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children

const val GAME_STATE = "gameState"

class MainActivity : AppCompatActivity() {
    private lateinit var game: TicTacToe
    private lateinit var ticTacToeGridLayout: GridLayout
    private var player1Wins = 0
    private var player2Wins = 0
    private var xColor = 0
    private var oColor = 0
    private var noneColor = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            startGame()
        } else {
            game.state = savedInstanceState.getStringArray(GAME_STATE).toString()!!
            setButtonValues()
        }

        ticTacToeGridLayout = findViewById(R.id.tic_tac_toe_grid)

        // Add the same click handler to all grid buttons
        for (gridButton in ticTacToeGridLayout.children) {
            gridButton.setOnClickListener(this::onBoardClick)
        }


        xColor = ContextCompat.getColor(this, R.color.red)
        oColor = ContextCompat.getColor(this, R.color.blue)
        noneColor - ContextCompat.getColor(this, R.color.clear)

        game = TicTacToe()
    }

    private fun startGame() {
        game.newGame()
        setButtonValues()
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

    private fun setButtonValues() {

        // Set all buttons' background color and content description
        for (buttonIndex in 0 until ticTacToeGridLayout.childCount) {
            val gridButton = ticTacToeGridLayout.getChildAt(buttonIndex)

            // Find the button's row and col
            val row = buttonIndex / GRID_SIZE
            val col = buttonIndex % GRID_SIZE

            // ChatGPT - I provided the code without the check for if it is a button and asked how
            // to change the text and text color without using FindViewById
            if (gridButton is Button) {
                when (game.getPlaceValue(row, col)) {
                    BoardValue.X -> {
                        // Set the text and color
                        gridButton.setText(R.string.x)
                        gridButton.setTextColor(xColor)
                    }
                    BoardValue.O -> {
                        gridButton.setText(R.string.o)
                        gridButton.setTextColor(oColor)
                    }
                    // Empty button
                    else -> {
                        gridButton.setText(R.string.none)
                        gridButton.setTextColor(noneColor)
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(GAME_STATE, game.state)
    }

}