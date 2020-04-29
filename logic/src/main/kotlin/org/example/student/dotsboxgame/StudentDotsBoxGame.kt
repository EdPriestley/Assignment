package org.example.student.dotsboxgame

import uk.ac.bournemouth.ap.dotsandboxeslib.AbstractDotsAndBoxesGame
import uk.ac.bournemouth.ap.dotsandboxeslib.ComputerPlayer
import uk.ac.bournemouth.ap.dotsandboxeslib.DotsAndBoxesGame
import uk.ac.bournemouth.ap.dotsandboxeslib.Player
import uk.ac.bournemouth.ap.dotsandboxeslib.matrix.Matrix
import uk.ac.bournemouth.ap.dotsandboxeslib.matrix.MutableMatrix
import uk.ac.bournemouth.ap.dotsandboxeslib.matrix.MutableSparseMatrix
import uk.ac.bournemouth.ap.dotsandboxeslib.matrix.SparseMatrix

class StudentDotsBoxGame(columns: Int = 5, rows: Int = 5, players: List<Player>) :
    AbstractDotsAndBoxesGame() {

    //SETTING UP PLAYER LOGIC
    override val players: List<Player> = players.toList()
    private var currentPlayerIndex: Int = 0
    override val currentPlayer: Player
        get() = players[currentPlayerIndex]


    override val boxes: Matrix<StudentBox> = MutableMatrix(columns, rows, ::StudentBox)

    override val lines: SparseMatrix<StudentLine> =
        MutableSparseMatrix(columns + 1, rows * 2 + 1, ::StudentLine) { x, y ->
            y % 2 == 1 || x < columns
        }

    override var isFinished: Boolean = false
        get() = lines.all { it.isDrawn }

    override fun playComputerTurns() {
        var current = currentPlayer
        while (current is ComputerPlayer && !isFinished) {
            current.makeMove(this)
            current = currentPlayer
        }
    }

    inner class StudentLine(lineX: Int, lineY: Int) : AbstractLine(lineX, lineY) {
        override var isDrawn: Boolean = false
        //when line clicked set to true


        override val adjacentBoxes: Pair<StudentBox?, StudentBox?>
            get() {
                if (lineY % 2 == 1) {//line is vertical
                    val leftBoxX = lineX - 1
                    val rightBoxX = lineX
                    val boxY = lineY / 2
                } else {//line is horizontal
                    val topBoxY = lineY / 2 - 1
                    val bottomBoxY = lineY / 2
                    val boxX = lineX
                }


            }


        inner class StudentBox(boxX: Int, boxY: Int) : AbstractBox(boxX, boxY) {
            //DECIDES WHO OWNS THE BOX CREATED - USING BOUNDING LINE
            override val owningPlayer: Player? = null


            /**
             * This must be lazy or a getter, otherwise there is a chicken/egg problem with the boxes
             */

            override val boundingLines: Iterable<DotsAndBoxesGame.Line>
                get() {
                    val leftLineX = boxX
                    val rightLineX = boxX
                    val topLineX = boxX
                    val bottomLineX = boxX

                    val leftLineY = boxX
                    val rightLineY = boxX + 1
                    val topLineY = boxX * 2
                    val bottomLineY = boxX * 2 + 2

                }
        }
    }


    override fun drawLine() {
        // Implement the logic for a player drawing a line. Don't forget to inform the listeners (fireGameChange, fireGameOver)")
        // NOTE read the documentation in the interface, you must also update the current player.
        //check if lines are drawn
        val  lineSelected = studentLine().isDrawn
        if (isDrawn == false) {
            isDrawn == true
        } else {
            //display message saying line is already selected
        }

        //check if bounding line are drawn for boxes

        //update if all boxes completed


        //store if box is completed


        //getting player scores and highscores
        var playerOneScore:Int = boxes.count{ it == 1}
        var playerTwoScore:Int = boxes.count{ it == 2}



        //determines players turn and increases players turn
        var playerTurn: Int = 0
        if (playerTurn == 24) {
            playerTurn = 0
        } else {
            playerTurn++
        }

        if (playerTurn % 2 == 1) {//odd number turns
            currentPlayerIndex = 0
        } else {//even number turns
            currentPlayerIndex = 1
        }


    }

}