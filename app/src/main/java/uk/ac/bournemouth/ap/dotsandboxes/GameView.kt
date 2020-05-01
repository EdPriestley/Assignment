package uk.ac.bournemouth.ap.dotsandboxes

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
        context, attrs, defStyleAttr)



    //declare variables
    private val myGestureDetector = GestureDetector(context, myGestureListener())
    private var myGame: StudentDotsBoxGame = StudentDotsBoxGame(5, 5, listOf(HumanPlayer(), HumanPlayer()))
    private val colCount = myGame.columns
    private val rowCount = myGame.rows
    private var GridPaint: Paint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.LTGRAY
    }
    private var ConnectCircle: Paint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.BLACK
    }
    private var PlayerLine: Paint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.DKGRAY
    }

    private var player1Paint:Paint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.GREEN
    }

    private var player2Paint:Paint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.RED
    }

    var player = myGame.currentPlayer


    private var ScoreBoard: Paint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.BLACK
        textAlign = Paint.Align.LEFT
        textSize = 100.toFloat()
        typeface = Typeface.SERIF
    }
    val col = 1
    val row = 1
    var playerOneScore = 0
    var playerTwoScore = 0


    //draw it onto the canvas
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val viewWidth: Float = width.toFloat() - 1
        val viewHeight: Float = height.toFloat() - 20


        //draw the game board
        canvas.drawRect(0.toFloat(), 0.toFloat(), viewWidth +20, viewHeight+20, GridPaint)


        //draw the circles onto the game board
        for (col in 1 until colCount + 1) {
            for (row in 1 until rowCount + 1) {
                val paint = ConnectCircle
                val cx = viewWidth*col/colCount-viewWidth/10
                val cy = viewHeight/2*row/rowCount+4+viewHeight/3
                val radius = 10F
                canvas.drawCircle(cx, cy, radius, paint)
            }
        }

        for (col in 1 until colCount+1) {
            for (row in 1 until rowCount-3) {
                var currentPaint = PlayerLine
                var lineAtPos = myGame.lines[col, row].isDrawn
                // Choose the correct colour for the circle
                if (lineAtPos == true) {
                    if (myGame.currentPlayer == myGame.players[0]) {
                        currentPaint = player1Paint
                    } else {
                        currentPaint = player2Paint
                    }
                }else {
                    currentPaint = PlayerLine
                }


                //val paint = PlayerLine
                canvas.drawLine(((viewWidth*col/colCount-viewWidth/10)), (viewHeight/2*row/rowCount+4+viewHeight/3), (viewWidth*col/colCount-viewWidth/10), (viewHeight/2*row+1/rowCount+4+viewHeight/3), currentPaint)
            }
        }
        for (col in 1 until colCount-3) {
            for (row in 1 until rowCount+1) {
                var currentPaint = PlayerLine
                var lineAtPos = myGame.lines[col, row].isDrawn
                // Choose the correct colour for the circle
                if (lineAtPos == true) {
                    if (myGame.currentPlayer == myGame.players[0]) {
                        currentPaint = player1Paint
                    } else {
                        currentPaint = player2Paint
                    }
                }else {
                    currentPaint = PlayerLine
                }

                canvas.drawLine(((viewWidth*col/colCount-viewWidth/10)), (viewHeight/2*row/rowCount+4+viewHeight/3), (viewWidth*col+1/colCount-viewWidth/10), ((viewHeight/2*row/rowCount+4+viewHeight/3)), currentPaint)
            }
        }

        //set up the scoreboard
        canvas.drawText("HighScore: ", (viewWidth*col/colCount-viewWidth/10),(viewHeight/3*row/rowCount),ScoreBoard)
        canvas.drawText("Player 2: ", (viewWidth*col/colCount-viewWidth/10),(viewHeight/3*row+1/rowCount),ScoreBoard)
        canvas.drawText("Player 1: ", (viewWidth*col/colCount-viewWidth/10),(viewHeight/4*row+1/rowCount),ScoreBoard)
        //canvas.drawText(highScore.toString(), (viewWidth*4/colCount-viewWidth/10),(viewHeight/3*row/rowCount),ScoreBoard)
        canvas.drawText(playerTwoScore.toString(), (viewWidth*4/colCount-viewWidth/10),(viewHeight/3*row+1/rowCount),ScoreBoard)
        canvas.drawText(playerOneScore.toString(), (viewWidth*4/colCount-viewWidth/10),(viewHeight/4*row+1/rowCount),ScoreBoard)




    }
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return myGestureDetector.onTouchEvent(ev) || super.onTouchEvent(ev)
    }

    inner class myGestureListener: GestureDetector.SimpleOnGestureListener() {
        override fun onDown(ev: MotionEvent): Boolean {
            return true
        }
        override fun onSingleTapUp(ev: MotionEvent): Boolean {
            var xCoord = ev.x
            var yCoord = ev.y
            val viewWidth: Float = width.toFloat() - 1
            val viewHeight: Float = height.toFloat() - 20
            var foundClickX:Int
            var foundClickY:Int








            //create an if statement
            for (row in 1 until rowCount-3) {
                if ( yCoord == viewHeight/2*row/rowCount+4+viewHeight/3)
                {foundClickY = row}
            }
            for (col in 1 until colCount+1) {
                  if (xCoord > viewWidth*col/colCount-viewWidth/10     &&  xCoord   <  viewWidth*col+1/colCount-viewWidth/10  )
                  {foundClickX = col}
            }
            myGame.lines[foundClickX, foundClickY].myGame.drawline()



            for (col in 1 until colCount-3) {
                if ( xCoord == viewWidth * col / colCount - viewWidth / 10))
                {foundClickX = col}
            }
            for (row in 1 until rowCount+1) {
                if (yCoord > viewHeight/2*row/rowCount+4+viewHeight/3     &&  yCoord  <  viewHeight/2*row+1/rowCount+4+viewHeight/3  )
                {foundClickY = row}
            }
            myGame.lines[foundClickX, foundClickY].myGame.drawline()









            Log.d(LOGTAG, "SingleTapUp x= $xCoord y= $yCoord")
            Toast.makeText(context, "SingleTapUp x= $xCoord y= $yCoord", Toast.LENGTH_SHORT).show()
            return true
        }
    } // End of myGestureListener class

    companion object { // declare a constant (must be in the companion)
        const val LOGTAG = "MyTask"
    }
}



