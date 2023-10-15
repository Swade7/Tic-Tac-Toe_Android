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
        gameResultTextView = findViewById(R.id.game_result)
        player1winsTextView = findViewById(R.id.player_1_score)
        player2winsTextView = findViewById(R.id.player_2_score)


        val player1Wins = intent.getIntExtra("player1Wins", 0)
        player1winsTextView.text = player1Wins.toString()
        val player2Wins = intent.getIntExtra("player2Wins", 0)
        player2winsTextView.text = player2Wins.toString()


        val gameResult = intent.getIntExtra("gameResult", 0)
        // get the game result
        when(gameResult) {
            0 -> gameResultTextView.setText(R.string.error)
            1 -> gameResultTextView.setText(R.string.player_1_win)
            2 -> gameResultTextView.setText(R.string.player_2_win)
            3 -> gameResultTextView.setText(R.string.tie)
        }
    }

    fun newGameBtnClick(view: View) {
        setResult(RESULT_OK)
        finish()
    }
}