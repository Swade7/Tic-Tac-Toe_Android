package com.zybooks.tic_tac_toe

import android.view.View
import kotlin.random.Random

const val GRID_SIZE = 3

enum class Player {
    X,
    O,
    None
}

enum class GameState {
    NotOver,
    Player1Win,
    Player2Win,
    Tie
}

data class Location(val row: Int, val col: Int)
class TicTacToe {
    private var currentPlayer = Player.X
    private val board = Array(GRID_SIZE) { Array(GRID_SIZE) { Player.None } }

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
                        Player.X -> boardString.append('X')
                        Player.O -> boardString.append('O')
                        Player.None -> boardString.append('N')
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
                        'X' -> board[row][col] = Player.X
                        'O' -> board[row][col] = Player.O
                        else -> board[row][col] = Player.None
                    }
                    index++
                }
            }
        }

    // Reset the board
    fun newGame() {
        for (row in 0 until GRID_SIZE) {
            for (col in 0 until GRID_SIZE) {
                board[row][col] = Player.None
            }
        }

        currentPlayer = Player.X
    }

    // Check if the square is empty when trying to make a move
    fun checkValidMove(row: Int, col: Int): Boolean {
        if (board[row][col] == Player.None) {
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
    fun getPlaceValue(row: Int, col: Int): Player {
        return board[row][col]
    }

    fun getCurrentPlayer(): Player {
        return currentPlayer
    }

    // Setters

    fun setCurrentPlayer(player: Player) {
        currentPlayer = player
    }
    private fun changeTurn() {
        currentPlayer = if (currentPlayer == Player.X) {
            Player.O
        } else {
            Player.X
        }
    }

    private fun setPlaceValue(row: Int, col: Int) {
        board[row][col] = currentPlayer
    }


    fun getGameStatus(): GameState {
        // Check for tie
        if (isStalemate()) {
            return GameState.Tie
        }

        // Loop through the possible winning combinations and check if either player has won
        for (combination in winningLocations) {
            val winner = checkForWinner(combination)
            if (winner != Player.None) {
                return when(winner) {
                    Player.X -> GameState.Player1Win
                    else -> GameState.Player2Win
                }
            }
        }

        return GameState.NotOver
    }

   private fun isStalemate(): Boolean {
       for (row in 0 until GRID_SIZE) {
           for (col in 0 until GRID_SIZE) {
                if(board[row][col] == Player.None) {
                    return false
                }
           }
       }

       return true
   }

    // Returns the winning player if present at given combination, else Player.None
    private fun checkForWinner(combination: List<Location>): Player {
        val player = board[combination[0].row][combination[0].col]
        for (location in combination) {
            if (board[location.row][location.col] != player) {
                return Player.None
            }
        }

        return player
    }
}