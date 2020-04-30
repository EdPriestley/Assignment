package uk.ac.bournemouth.ap.dotsandboxes

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import org.example.student.dotsboxgame.StudentDotsBoxGame
import uk.ac.bournemouth.ap.dotsandboxeslib.HumanPlayer

class GameView : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr)



    // private var myGame: StudentDotsBoxGame = StudentDotsBoxGame(5, 5, listOf(HumanPlayer(), HumanPlayer()))

    //declare variables
    private val colCount = 5 //   myGame.columns
    private val rowCount = 5 // myGame.rows


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
    private var scoreBoard: Paint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.BLACK
        textAlign = Paint.Align.LEFT
        textSize = 100.toFloat()
        typeface = Typeface.SERIF
    }

    private val col = 1
    private val row = 1

    //draw it onto the canvas
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val viewWidth: Float = width.toFloat()
        val viewHeight: Float = height.toFloat()

        //draw the game board
        canvas.drawRect(0.toFloat(), 0.toFloat(), viewWidth, viewHeight, gridPaint)

        //draw the circles onto the game board
        for (col in 1 until colCount - 1) {
            for (row in 3 until rowCount - 1) {

                val paint = connectCircle

                //calculate the coordinates of the circles
                val cx = viewWidth * col / 7
                val cy = viewHeight * row / 9

                val radius = 10F

                canvas.drawCircle(cx, cy, radius, paint)
            }
        }

        //draw the vertical lines onto the game board
        for (col in 1 until colCount - 1) {
            for (row in 3 until rowCount - 2) {
                val paint = playerLine
                canvas.drawLine(
                    ((col * viewWidth) / colCount),
                    ((row * viewHeight) / rowCount),
                    ((col) * viewWidth / colCount),
                    ((row + 1) * viewHeight / rowCount),
                    paint)
            }
        }
        //draw the horizontal lines onto the game board
        for (col in 1 until colCount - 2) {
            for (row in 3 until rowCount - 1) {
                val paint = playerLine
                canvas.drawLine(
                    ((col * viewWidth) / colCount),
                    ((row * viewHeight) / rowCount),
                    ((col + 1) * viewWidth / colCount),
                    ((row) * viewHeight / rowCount),
                    paint)
            }
        }


        //set up the scoreboard
        var playerOneScore = 0
        var playerTwoScore = 0
        var highScore = 0

        if (playerOneScore > highScore) {
            highScore = playerOneScore
        } else if (playerTwoScore > highScore) {
            highScore = playerTwoScore
        }

        canvas.drawText(
            "Player 1: ",
            (col * viewWidth / colCount),
            ((row * viewHeight) / rowCount),
            scoreBoard)
        canvas.drawText(
            "Player 2: ",
            (col * viewWidth / colCount),
            (((row + 1) * viewHeight) / rowCount),
            scoreBoard)
        canvas.drawText(
            "High Score: ",
            (col * viewWidth / colCount),
            (((row + 2) * viewHeight) / rowCount),
            scoreBoard)
        canvas.drawText(
            playerOneScore.toString(),
            ((col + 3) * viewWidth / colCount),
            ((row * viewHeight) / rowCount),
            scoreBoard)
        canvas.drawText(
            playerTwoScore.toString(),
            ((col + 3) * viewWidth / colCount),
            (((row + 1) * viewHeight) / rowCount),
            scoreBoard)
        canvas.drawText(
            highScore.toString(),
            ((col + 3) * viewWidth / colCount),
            (((row + 2) * viewHeight) / rowCount),
            scoreBoard)
    }
}



//     myGame.lines[ clickY, clickLineX].drawline()

