package com.zybooks.tic_tac_toe

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.children

const val GAME_STATE = "gameState"
const val PLAYER_1_WINS = "player1Wins"
const val PLAYER_2_WINS = "player2Wins"


class MainActivity : AppCompatActivity() {
    private lateinit var ticTacToeGridLayout: GridLayout
    private lateinit var currentPlayerTextView: TextView
    private var player1Wins = 0
    private var player2Wins = 0
    private var xColor = 0
    private var oColor = 0
    private var noneColor = 0
    private var game = TicTacToe()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        currentPlayerTextView = findViewById(R.id.current_player_textview)
        ticTacToeGridLayout = findViewById(R.id.tic_tac_toe_grid)
        if (savedInstanceState == null) {
            startGame()

        } else {
            game.state = savedInstanceState.getString(GAME_STATE).toString()

            setButtonValues()

            // Get the saved number of wins for each player and the current player
            player1Wins = savedInstanceState.getInt("player1Wins", 0)
            player2Wins = savedInstanceState.getInt("player2Wins", 0)
            game.setCurrentPlayer(intToPlayer(savedInstanceState.getInt("currentPlayer", 1)))

        }



        // Add the same click handler to all grid buttons
        for (gridButton in ticTacToeGridLayout.children) {
            gridButton.setOnClickListener(this::onBoardClick)
        }

        xColor = ContextCompat.getColor(this, R.color.red)
        oColor = ContextCompat.getColor(this, R.color.blue)
        noneColor - ContextCompat.getColor(this, R.color.clear)

        showCurrentPlayer()
        setButtonValues()
    }

    // Start/reset the game
    private fun startGame() {
        game.newGame()
        setButtonValues()
        showCurrentPlayer()
    }

    private fun showCurrentPlayer() {
        if (game.getCurrentPlayer() == Player.O) {
            currentPlayerTextView.setText(R.string.player_2)
        }
        else {
            currentPlayerTextView.setText(R.string.player_1)
        }
    }

    // Returns an Int value for the game's state
    private fun gameStateToInt(gameState: GameState): Int {
        return when (gameState) {
            GameState.NotOver -> 0
            GameState.Player1Win -> 1
            GameState.Player2Win -> 2
            GameState.Tie -> 3
        }
    }

    private fun playerToInt(player: Player): Int {
        return when(player) {
            Player.None -> 0
            Player.X -> 1
            Player.O -> 2
        }
    }

    private fun intToPlayer(num: Int) : Player {
        return when(num) {
            0 -> Player.None
            1 -> Player.X
            else -> Player.O
        }
    }
    private fun onBoardClick(view: View) {
        // Get the location of the click
        val buttonIndex = ticTacToeGridLayout.indexOfChild(view)
        val row = buttonIndex / GRID_SIZE
        val col = buttonIndex % GRID_SIZE

        if (game.checkValidMove(row, col)) {
            game.makeMove(row, col)
        }

        setButtonValues()
        showCurrentPlayer()

        // Launch the game_over Activity if the game is over
        val gameStatus = game.getGameStatus()
        if (gameStatus != GameState.NotOver) {
            when(gameStatus) {
                GameState.Player1Win -> player1Wins++
                GameState.Player2Win -> player2Wins++
                else -> {}
            }

            // Create an Intent to send data to the game_over activity and save the current state
            val intent = Intent(this, GameOver::class.java)
            val gameStateInt = gameStateToInt(gameStatus)
            val playerInt = playerToInt(game.getCurrentPlayer())
            intent.putExtra("player1Wins", player1Wins)
            intent.putExtra("player2Wins", player2Wins)
            intent.putExtra("gameResult", gameStateInt)
            intent.putExtra("currentPlayer", playerInt)

            resultLauncher.launch(intent)
        }
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
                    Player.X -> {
                        // Set the text and color
                        gridButton.setText(R.string.x)
                        gridButton.setTextColor(xColor)
                    }
                    Player.O -> {
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
        outState.putInt(PLAYER_1_WINS, player1Wins)
        outState.putInt(PLAYER_2_WINS, player2Wins)
    }

    // Restart the game if the NEW GAME button is clicked in GameOver
    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            startGame()
        }
    }

    fun newGame(view: View) {
        startGame()
    }
}