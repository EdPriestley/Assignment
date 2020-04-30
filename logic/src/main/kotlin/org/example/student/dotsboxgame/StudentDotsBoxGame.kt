package org.example.student.dotsboxgame

import uk.ac.bournemouth.ap.dotsandboxeslib.AbstractDotsAndBoxesGame
import uk.ac.bournemouth.ap.dotsandboxeslib.ComputerPlayer
import uk.ac.bournemouth.ap.dotsandboxeslib.DotsAndBoxesGame
import uk.ac.bournemouth.ap.dotsandboxeslib.Player
import uk.ac.bournemouth.ap.dotsandboxeslib.matrix.Matrix
import uk.ac.bournemouth.ap.dotsandboxeslib.matrix.MutableMatrix
import uk.ac.bournemouth.ap.dotsandboxeslib.matrix.MutableSparseMatrix
import uk.ac.bournemouth.ap.dotsandboxeslib.matrix.SparseMatrix

class StudentDotsBoxGame(val columns: Int = 5, val rows: Int = 5, players: List<Player>) :
    AbstractDotsAndBoxesGame() {

    //SETTING UP PLAYER LOGIC
    override val players: List<Player> = players.toList()
    private var currentPlayerIndex: Int = 0
    override val currentPlayer: Player
        get() = players[currentPlayerIndex]


    override val boxes: Matrix<DotsAndBoxesGame.Box> = MutableMatrix(columns, rows, ::StudentBox)

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

        override val adjacentBoxes: Pair<DotsAndBoxesGame.Box?, DotsAndBoxesGame.Box?>
            get() = {
                if (lineY % 2 == 1) {//line is vertical
                    val leftBoxX: Int = lineX - 1
                    val rightBoxX: Int = lineX
                    val boxY: Int = lineY / 2
                } else {//line is horizontal
                    val topBoxY: Int = lineY / 2 - 1
                    val bottomBoxY: Int = lineY / 2
                    val boxX: Int = lineX
                }

                if (lineX = 0) {//no left box
                    override val leftBoxX = null
                    override val BoxY = null
                } else if (lineX = 3) {//no right box
                    override val rightBoxX = null
                    override val BoxY = null
                } else if (lineY = 0) {//no top box
                    override val topBoxY = null
                    override val boxX = null
                } else if (lineY = 6) {//no bottom box
                    override val bottomBoxY = null
                    override val boxX = null
                }

                return Pair(boxes[leftBoxX, leftBoxY], boxes[leftBoxX, leftBoxY])
            }


        override fun drawLine() {
            // Implement the logic for a player drawing a line. Don't forget to inform the listeners (fireGameChange, fireGameOver)")
            // NOTE read the documentation in the interface, you must also update the current player.
            //check if lines are drawn
            //  val lineSelected = isDrawn
            if (isDrawn == true) {
                throw Exception("Line already drawn")
            } else {

                isDrawn = true

                //check if bounding line are drawn for boxes

                //update if all boxes completed


                //store if box is completed


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
    }

        inner class StudentBox(boxX: Int, boxY: Int) : AbstractBox(boxX, boxY) {
            //DECIDES WHO OWNS THE BOX CREATED - USING BOUNDING LINE
            override val owningPlayer: Player? = null

            override val boundingLines: Iterable<DotsAndBoxesGame.Line>
                get()  = listOf {
                    lines[val leftLineX = boxX;val leftLineY = boxY/2+1]
                    lines[val rightLineX = boxX+1;val rightLineY = boxY/2+1]
                    lines[val topLineX = boxX;val topLineY = boxY*2]
                    lines[val bottomLineX = boxX;val bottomLineY = boxY*2+2]
                }

    }
}