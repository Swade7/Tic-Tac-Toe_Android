package com.zybooks.tic_tac_toe

import android.view.View
import kotlin.random.Random

const val GRID_SIZE = 3

enum class BoardValue {
    X,
    O,
    None
}

enum class GameState {
    Player1Win,
    Player2Win,
    Tie,
    NotOver
}

data class Location(val row: Int, val col: Int)
class TicTacToe {
    private var currentPlayer = BoardValue.X
    private val board = Array(GRID_SIZE) { Array(GRID_SIZE) { BoardValue.None } }

    // Store the locations of the possible locations that would result in a win
    // ChatGPT - "How would I store a collection of winning tic tac toe values in Kotlin?"
    private val winningLocations: List<List<Location>> = listOf(
        listOf(Location(0, 0), Location(0, 1), Location(0, 2)),
        listOf(Location(1, 0), Location(1, 1), Location(1, 2)),
        listOf(Location(2, 0), Location(2, 1), Location(2, 2)),

        // Vertical combinations
        listOf(Location(0, 0), Location(1, 0), Location(2, 0)),
        listOf(Location(0, 1), Location(1, 1), Location(2, 1)),
        listOf(Location(0, 2), Location(1, 2), Location(2, 2)),

        // Diagonal combinations
        listOf(Location(0, 0), Location(1, 1), Location(2, 2)),
        listOf(Location(0, 2), Location(1, 1), Location(2, 0))
    )

    var state: String
        get() {
            val boardString = StringBuilder()

            var index = 0
            for (row in 0 until GRID_SIZE) {
                for (col in 0 until GRID_SIZE) {

                    when (board[row][col]) {
                        BoardValue.X -> boardString.append('X')
                        BoardValue.O -> boardString.append('O')
                        BoardValue.None -> boardString.append('N')
                    }
                }
            }
            return boardString.toString()
        }
        set(value) {
            var index = 0
            for (row in 0 until GRID_SIZE) {
                for (col in 0 until GRID_SIZE) {
                    when (value[index]) {
                        'X' -> board[row][col] = BoardValue.X
                        'O' -> board[row][col] = BoardValue.O
                        else -> board[row][col] = BoardValue.None
                    }
                    index++
                }
            }
        }

    // Reset the board
    fun newGame() {
        for (row in 0 until GRID_SIZE) {
            for (col in 0 until GRID_SIZE) {
                board[row][col] = BoardValue.None
            }
        }
    }

    // Check if the square is empty when trying to make a move
    fun checkValidMove(row: Int, col: Int): Boolean {
        if (board[row][col] == BoardValue.None) {
            return true
        }
        return false
    }


    // Set the selected place to the correct user's piece and change the turn
    fun makeMove(row: Int, col: Int) {
        setPlaceValue(row, col)
        changeTurn()
    }

    // Getters
    fun getPlaceValue(row: Int, col: Int): BoardValue {
        return board[row][col]
    }

    fun getCurrentPlayer(): BoardValue {
        return currentPlayer
    }

    // Setters
    private fun changeTurn() {
        if (currentPlayer == BoardValue.X) {
            currentPlayer = BoardValue.O
        }
        currentPlayer = BoardValue.X
    }

    private fun setPlaceValue(row: Int, col: Int) {
        board[row][col] = currentPlayer
    }

    /*
    fun isGameOver(): GameState {
        // Check if player 1 won
        if (board[0][0] == BoardValue.X && board[0][1] == BoardValue.X && board[0][2] == BoardValue.X)
    }

     */


}