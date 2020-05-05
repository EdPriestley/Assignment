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

    override val players: List<Player> = players.toList()
    private var currentPlayerIndex: Int = 0
    override val currentPlayer: Player
        get() = players[currentPlayerIndex]


    override val boxes: Matrix<DotsAndBoxesGame.Box> = MutableMatrix(columns, rows, ::StudentBox)


    override val lines: SparseMatrix<StudentLine> =
        MutableSparseMatrix(columns + 1, rows * 2 + 1, ::StudentLine) { x, y ->
            y % 2 == 1 || x < columns
        }


    override val isFinished: Boolean
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
        private var playerTurn: Int = 0

        override val adjacentBoxes: Pair<DotsAndBoxesGame.Box?, DotsAndBoxesGame.Box?>
            get() = findBoxes()

        private fun findBoxes(): Pair<DotsAndBoxesGame.Box?, DotsAndBoxesGame.Box?> {
            val boxAX: Int
            val boxBX: Int
            val boxAY: Int
            val boxBY: Int

            if (lineY % 2 == 1) {//line is vertical
                boxAX = lineX - 1
                boxBX = lineX
                boxAY = lineY / 2
                boxBY = lineY / 2
            } else {//line is horizontal
                boxAY = lineY / 2 - 1
                boxBY = lineY / 2
                boxAX = lineX
                boxBX = lineX
            }

            val boxA: DotsAndBoxesGame.Box?
            val boxB: DotsAndBoxesGame.Box?

            if (lineX == 0 || lineY == 0) {//no left or top box

                boxA = null
                boxB = boxes[boxBX, boxBY]
            } else if (lineX == 3 || lineY == 6) {//no right or bottom box
                boxA = boxes[boxAX, boxAY]
                boxB = null
            } else {
                boxA = boxes[boxAX, boxAY]
                boxB = boxes[boxBX, boxBY]
            }
            return Pair(boxA, boxB)
        }

        override fun drawLine() {
            //check if lines are drawn
            if (isDrawn) {
                throw Exception("Line already drawn")
            } else {
                isDrawn
            }

            //reset if all boxes are complete
            if (lines.all { true }) {
                lines.all { false }
            }

            //store if box is completed
            val box1 = adjacentBoxes.first
            val box2 = adjacentBoxes.second
            if (box1 != null) {
                if (box1.boundingLines.all() { b -> b.isDrawn }) {
                    box1.owningPlayer = currentPlayer
                }
            }

            if (box2 != null) {
                if (box2.boundingLines.all() { b -> b.isDrawn }) {
                    box2.owningPlayer = currentPlayer
                }
            }

            //determines players turn and increases players turn
            if (playerTurn == 24) {
                playerTurn = 0
            } else {
                playerTurn++
            }
            currentPlayerIndex = if (playerTurn % 2 == 1) {//odd number turns
                0
            } else {//even number turns
                1
            }

        }
    }

    inner class StudentBox(boxX: Int, boxY: Int) : AbstractBox(boxX, boxY) {
        //DECIDES WHO OWNS THE BOX CREATED - USING BOUNDING LINE
        override var owningPlayer: Player? = null
        override val boundingLines: Iterable<DotsAndBoxesGame.Line>
            get() = listOf(//left line
                lines[boxX, boxY * 2 + 1],
                //right line
                lines[boxX + 1, boxY * 2 + 1],
                //top line
                lines[boxX, boxY * 2],
                //bottom line
                lines[boxX, boxY * 2 + 2]
                          )

    }

}
