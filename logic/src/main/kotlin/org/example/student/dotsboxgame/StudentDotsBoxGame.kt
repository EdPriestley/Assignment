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
            get() = findBoxes()

        private fun findBoxes(): Pair<DotsAndBoxesGame.Box?, DotsAndBoxesGame.Box?> {


            val BoxAX: Int
            val BoxBX: Int
            val BoxAY: Int
            val BoxBY: Int

            if (lineY % 2 == 1) {//line is vertical
                BoxAX = lineX - 1
                BoxBX = lineX
                BoxAY = lineY / 2
                BoxBY = lineY / 2
            } else {//line is horizontal
                BoxAY = lineY / 2 - 1
                BoxBY = lineY / 2
                BoxAX = lineX
                BoxBX = lineX
            }

            val boxA: DotsAndBoxesGame.Box?
            val boxB: DotsAndBoxesGame.Box?


            if (lineX == 0 || lineY == 0) {//no left or top box

                boxA = null
                boxB = boxes[BoxBX, BoxBY]
            } else if (lineX == 3 || lineY == 6) {//no right or bottom box
                boxA = boxes[BoxAX, BoxAY]
                boxB = null
            } else {
                boxA = boxes[BoxAX, BoxAY]
                boxB = boxes[BoxBX, BoxBY]
            }
            return Pair(boxA, boxB)
        }


        var playerTurn: Int = 0
        var playerOneScore = 0
        var playerTwoScore = 0
        var highScore = 0


        override fun drawLine() {
            //check if lines are drawn
            if (isDrawn == true) {
                throw Exception("Line already drawn")
            } else {
                isDrawn = true
            }


            //reset if all boxes are complete
            if (lines.all { true }) {
                lines.all { false }
            }

            //store if box is completed
            var box1 = adjacentBoxes.first
            var box2 = adjacentBoxes.second
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
            if (playerTurn % 2 == 1) {//odd number turns
                currentPlayerIndex = 0
            } else {//even number turns
                currentPlayerIndex = 1
            }

        }
    }

        inner class StudentBox(boxX: Int, boxY: Int) : AbstractBox(boxX, boxY) {
            //DECIDES WHO OWNS THE BOX CREATED - USING BOUNDING LINE
            override var owningPlayer: Player? = null
            override val boundingLines: Iterable<DotsAndBoxesGame.Line>
                get() = listOf (//left line
                                lines[boxX, boxY *2 +1],
                                //right line
                                lines[boxX + 1, boxY * 2 + 1],
                                //top line
                                lines[boxX, boxY * 2],
                                //bottom line
                                lines[boxX, boxY * 2 + 2])

        }

    }
