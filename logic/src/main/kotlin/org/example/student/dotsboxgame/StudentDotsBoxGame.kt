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
                    val BoxAX: Int = lineX - 1
                    val BoxBX: Int = lineX
                    val BoxAY: Int = lineY / 2
                    val BoxBY:Int = lineY / 2
                } else {//line is horizontal
                    val BoxAY: Int = lineY / 2 - 1
                    val BoxBY: Int = lineY / 2
                    val BoxAX: Int = lineX
                    val BoxBX: Int = lineX
                }

            if (lineX == 0 || lineY == 0) {//no left or top box
                    return Pair(null, boxes[BoxBX, BoxBY])
                } else if(lineX == 3 || lineY==6) {//no right or bottom box
                    return Pair(boxes[BoxAX, BoxAY], null)
                }
                else
                return Pair<boxes[BoxAX, BoxAY], boxes[BoxBX, BoxBY]>
        }


        override fun drawLine() {
            //check if lines are drawn
            if (isDrawn == true) {
                throw Exception("Line already drawn")
            } else {
                isDrawn = true
            }

            //reset if all boxes are complete
            if (lines.all { true }){
                lines.all { false }

            //store if box is completed
            if(boundingLine.isdrawn == true && currentPlayer == players[0]) {
                //assign box to player 0
            }else{
                //assign box to player 1
            }

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
                get() {
                    var boundingList = listOf {

                        //left line
                        lines[boxX, boxY / 2 + 1]
                        //right line
                        lines[boxX + 1, boxY / 2 + 1]
                        //top line
                        lines[boxX, boxY * 2]
                        //bottom line
                        lines[boxX, boxY * 2 + 2]
                    }
                    return boundingList
                }

    }
}