package com.zybooks.tic_tac_toe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class GameOver : AppCompatActivity() {
    private lateinit var gameResultTextView: TextView
    private lateinit var player1winsTextView: TextView
    private lateinit var player2winsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        val player1Wins = intent.getStringExtra("player1Wins")
        val player2Wins = intent.getStringExtra("player2Wins")
        val gameResult = intent.getIntExtra("gameResult", 0)
        // get the game result
        when(gameResult) {
            0 -> R.string.error
            1 -> R.string.player_1_win
            2 -> R.string.player_2_win
            3 -> R.string.tie
        }

        // Might need to send some data saying to start a new game
        fun newGameBtnClick(view: View) {
            setResult(RESULT_OK)
            finish()
        }
    }
}