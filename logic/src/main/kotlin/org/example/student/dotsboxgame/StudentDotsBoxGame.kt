package org.example.student.dotsboxgame

import    uk.ac.bournemouth.ap.dotsandboxeslib.*
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


    // NOTE: you may want to me more specific in the box type if you use that type in your class
    //?????
    override val boxes: Matrix<StudentBox> = MutableMatrix(columns, rows, ::StudentBox)


    //Stores OBJECTS(LINE) TO AN ARRAY
    override val lines: SparseMatrix<StudentLine> =
        MutableSparseMatrix(columns + 1, rows * 2 + 1, ::StudentLine) { x, y ->
            y % 2 == 1 || x < columns
        }


    override var isFinished: Boolean = false
        get() = lines.all { it.isDrawn }


    //DECIDES WHEN IT IS THE COMPUTERS TURN - AFTER ABOVE STATEMENT
    override fun playComputerTurns() {
        var current = currentPlayer
        while (current is ComputerPlayer && !isFinished) {
            current.makeMove(this)
            current = currentPlayer
        }
    }


    /**
     * This is an inner class as it needs to refer to the game to be able to look up the correct
     * lines and boxes. Alternatively you can have a game property that does the same thing without
     * it being an inner class.
     */
    inner class StudentLine(lineX: Int, lineY: Int) : AbstractLine(lineX, lineY) {
        override var isDrawn: Boolean = false
        //when line clicked set to true

//find the box to all sides of the lines
        override val adjacentBoxes: Pair<StudentBox?, StudentBox?>
            get() {
                if (lineY%2==1){//line is vertical
                    val leftBoxX = lineX-1
                    val rightBoxX = lineX
                    val boxY = lineY/2
                }else {//line is horizontal
                    val topBoxY = lineY/2-1
                    val bottomBoxY = lineY/2
                    val boxX = lineX
                }



        //WILL CHANGE THE COLOUR OF THE LINES ON THE GAME
        override fun drawLine() {
            TODO("Implement the logic for a player drawing a line. Don't forget to inform the listeners (fireGameChange, fireGameOver)")
            // NOTE read the documentation in the interface, you must also update the current player.
            //check if lines are drawn
            //see if this line is already drawn
            //set is drawn (boolean)
            //store if box is completed
            //check if bounding line are drawn for boxes
            //update if all boxes completed
            //change player turn
            //must call player computer terms
        }
    }


    inner class StudentBox(boxX: Int, boxY: Int) : AbstractBox(boxX, boxY) {
        //DECIDES WHO OWNS THE BOX CREATED - USING BOUNDING LINE
        override val owningPlayer: Player? = null


        /**
         * This must be lazy or a getter, otherwise there is a chicken/egg problem with the boxes
         */

        override val boundingLines: Iterable<DotsAndBoxesGame.Line>
            get() = TODO("Look up the correct lines from the game outer class")

    }
}