package uk.ac.bournemouth.ap.dotsandboxes

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import org.example.student.dotsboxgame.StudentDotsBoxGame
import uk.ac.bournemouth.ap.dotsandboxeslib.HumanPlayer

class GameView : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
                                                                                   )


    private val myGestureDetector = GestureDetector(context, MyGestureListener())
    private var myGame: StudentDotsBoxGame =
        StudentDotsBoxGame(5, 5, listOf(HumanPlayer(), HumanPlayer()))
    private val colCount = myGame.columns
    private val rowCount = myGame.rows

    private var gridPaint: Paint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.LTGRAY
    }
    private var connectCircle: Paint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.BLACK
    }
    private var playerLine: Paint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.DKGRAY
    }

    private var player1Paint: Paint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.GREEN
    }

    private var player2Paint: Paint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.RED
    }
    private var scoreBoard: Paint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.BLACK
        textAlign = Paint.Align.LEFT
        textSize = 100.toFloat()
        typeface = Typeface.SERIF
    }


    private val col = 1
    private val row = 1
    var highScore = 0


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val viewWidth: Float = width.toFloat() - 1
        val viewHeight: Float = height.toFloat() - 20

        //draw game board
        canvas.drawRect(0.toFloat(), 0.toFloat(), viewWidth + 20, viewHeight + 20, gridPaint)


        //draw the circles onto the game board
        for (col in 1 until colCount + 1) {
            for (row in 1 until rowCount + 1) {
                val paint = connectCircle
                val cx = viewWidth * col / colCount - viewWidth / 10
                val cy = viewHeight / 2 * row / rowCount + 4 + viewHeight / 3
                val radius = 10F
                canvas.drawCircle(cx, cy, radius, paint)
            }
        }
        //draw horizontal lines
        for (col in 1 until colCount + 1) {
            for (row in 1 until rowCount - 3) {
                var currentPaint = playerLine
                val lineAtPos = myGame.lines[col, row].isDrawn
                // Choose the correct colour for the circle
                currentPaint = if (lineAtPos) {
                    if (myGame.currentPlayer == myGame.players[0]) {
                        player1Paint
                    } else {
                        player2Paint
                    }
                } else {
                    playerLine
                }
                canvas.drawLine(
                    ((viewWidth * col / colCount - viewWidth / 10)),
                    (viewHeight / 2 * row / rowCount + 4 + viewHeight / 3),
                    (viewWidth * col / colCount - viewWidth / 10),
                    (viewHeight / 2 * row + 1 / rowCount + 4 + viewHeight / 3),
                    currentPaint
                               )
            }
        }
        //draw verticle lines
        for (col in 1 until colCount - 3) {
            for (row in 1 until rowCount + 1) {
                var currentPaint = playerLine
                val lineAtPos = myGame.lines[col, row].isDrawn
                // Choose the correct colour for the circle
                currentPaint = if (lineAtPos) {
                    if (myGame.currentPlayer == myGame.players[0]) {
                        player1Paint
                    } else {
                        player2Paint
                    }
                } else {
                    playerLine
                }

                canvas.drawLine(
                    ((viewWidth * col / colCount - viewWidth / 10)),
                    (viewHeight / 2 * row / rowCount + 4 + viewHeight / 3),
                    (viewWidth * col + 1 / colCount - viewWidth / 10),
                    ((viewHeight / 2 * row / rowCount + 4 + viewHeight / 3)),
                    currentPaint
                               )
            }
        }

        //set up the scoreboard && highscore
        val playerOneScore = myGame.getScores()[0]
        val playerTwoScore = myGame.getScores()[1]

        if (highScore < playerOneScore) {
            highScore = playerOneScore
        } else if (highScore < playerTwoScore) {
            highScore = playerTwoScore
        }
        canvas.drawText(
            "HighScore: ",
            (viewWidth * col / colCount - viewWidth / 10),
            (viewHeight / 3 * row / rowCount),
            scoreBoard
                       )
        canvas.drawText(
            "Player 2: ",
            (viewWidth * col / colCount - viewWidth / 10),
            (viewHeight / 3 * row + 1 / rowCount),
            scoreBoard
                       )
        canvas.drawText(
            "Player 1: ",
            (viewWidth * col / colCount - viewWidth / 10),
            (viewHeight / 4 * row + 1 / rowCount),
            scoreBoard
                       )
        //canvas.drawText(highScore.toString(), (viewWidth*4/colCount-viewWidth/10),(viewHeight/3*row/rowCount),ScoreBoard)
        canvas.drawText(
            playerTwoScore.toString(),
            (viewWidth * 4 / colCount - viewWidth / 10),
            (viewHeight / 3 * row + 1 / rowCount),
            scoreBoard
                       )
        canvas.drawText(
            playerOneScore.toString(),
            (viewWidth * 4 / colCount - viewWidth / 10),
            (viewHeight / 4 * row + 1 / rowCount),
            scoreBoard
                       )
        canvas.drawText(
            highScore.toString(),
            (viewWidth * 4 / colCount - viewWidth / 10),
            (viewHeight / 3 * row / rowCount),
            scoreBoard
                       )
    }

    //gesture control
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return myGestureDetector.onTouchEvent(ev) || super.onTouchEvent(ev)
    }

    inner class MyGestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(ev: MotionEvent): Boolean {
            return true
        }

        override fun onSingleTapUp(ev: MotionEvent): Boolean {
            val xCoordinate = ev.x
            val yCoordinate = ev.y
            val viewWidth: Float = width.toFloat() - 1
            val viewHeight: Float = height.toFloat() - 20
            var foundClickX: Int = 0
            var foundClickY: Int = 0

            for (row in 1 until rowCount - 3) {
                if (yCoordinate == viewHeight / 2 * row / rowCount + 4 + viewHeight / 3) {
                    foundClickY = row
                }
            }
            if (yCoordinate <= viewHeight / 2 * row / rowCount + 4 + viewHeight / 3 && yCoordinate >= viewHeight / 2 * row + 3 / rowCount + 4 + viewHeight / 3) {
                for (col in 1 until colCount + 1) {
                    if (xCoordinate > viewWidth * col / colCount - viewWidth / 10 && xCoordinate < viewWidth * col + 1 / colCount - viewWidth / 10) {
                        foundClickX = col
                    }
                }
                myGame.lines[foundClickX, foundClickY].drawLine()
            } else {
                for (col in 1 until colCount - 3) {
                    if (xCoordinate == viewWidth * col / colCount - viewWidth / 10) {
                        foundClickX = col
                    }
                }
                for (row in 1 until rowCount + 1) {
                    if (yCoordinate > viewHeight / 2 * row / rowCount + 4 + viewHeight / 3 && yCoordinate < viewHeight / 2 * row + 1 / rowCount + 4 + viewHeight / 3) {
                        foundClickY = row
                    }
                }
                myGame.lines[foundClickX, foundClickY].drawLine()
            }

            Log.d(LOGTAG, "SingleTapUp x= $xCoordinate y= $yCoordinate")
            Toast.makeText(
                context,
                "SingleTapUp x= $xCoordinate y= $yCoordinate",
                Toast.LENGTH_SHORT
                          ).show()
            return true
        }
    }

    companion object { // declare a constant (must be in the companion)
        const val LOGTAG = "MyTask"
    }
}



