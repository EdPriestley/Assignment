package uk.ac.bournemouth.ap.dotsandboxes

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View

class GameView: View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
                                                                                   )

    //declare variables
    private val colCount = 7
    private val rowCount = 9

    private var GridPaint: Paint
    private var ConnectCircle: Paint
    private var PlayerLine: Paint
    private var ScoreBoard: Paint

    val col = 1
    val row = 1
    var playerOneScore = 0
    var playerTwoScore = 0

    //making paint objects so we can paint the grid
    init {
        GridPaint = Paint().apply {
            style = Paint.Style.FILL
            color = Color.LTGRAY
        }

        ConnectCircle = Paint().apply {
            style = Paint.Style.FILL
            color = Color.BLACK
        }
        PlayerLine = Paint().apply {
            style = Paint.Style.FILL
            color = Color.DKGRAY
        }

        ScoreBoard = Paint().apply {
            style = Paint.Style.FILL
            color = Color.BLACK
            setTextAlign(Paint.Align.LEFT)
            setTextSize(100.toFloat())
            setTypeface(Typeface.SERIF)
        }
    }

    //draw it onto the canvas
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val viewWidth: Float = width.toFloat()
        val viewHeight: Float = height.toFloat()


        //draw the game board
        canvas.drawRect(0.toFloat(), 0.toFloat(), viewWidth, viewHeight, GridPaint)


        //draw the circles onto the game board
        for (col in 1 until colCount - 1) {
            for (row in 3 until rowCount - 1) {

                val paint = ConnectCircle

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
                val paint = PlayerLine
                canvas.drawLine(((col * viewWidth) / colCount), ((row * viewHeight) / rowCount), ((col) * viewWidth / colCount), ((row + 1) * viewHeight / rowCount), paint)
            }
        }
        //draw the horizontal lines onto the game board
        for (col in 1 until colCount - 2) {
            for (row in 3 until rowCount - 1) {
                val paint = PlayerLine
                canvas.drawLine(((col * viewWidth) / colCount), ((row * viewHeight) / rowCount), ((col + 1) * viewWidth / colCount), ((row) * viewHeight / rowCount), paint)
            }
        }


        //set up the scoreboard
        canvas.drawText("Player 1: ", (col * viewWidth/colCount),((row * viewHeight) / rowCount),ScoreBoard)
        canvas.drawText("Player 2: ", (col * viewWidth/colCount),(((row+1) * viewHeight) / rowCount),ScoreBoard)
        canvas.drawText(playerOneScore.toString(), ((col + 3) * viewWidth/colCount),((row * viewHeight) / rowCount),ScoreBoard)
        canvas.drawText(playerTwoScore.toString(), ((col + 3)* viewWidth/colCount),(((row + 1)* viewHeight) / rowCount),ScoreBoard)



    }
}





