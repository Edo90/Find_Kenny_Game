package com.example.myapplication

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    var score : Int = 0
    var imageArray = ArrayList<ImageView>()
    var handler : Handler = Handler()
    var runnable : Runnable = Runnable {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageArray = arrayListOf(imageView,imageView1, imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8)


        startGame()
    }

    fun startGame(){
        score = 0
        textView.text = "Score: $score"
        timeLeftCounter()
        hideImages()

    }

    fun hideImages(){
        runnable = object : Runnable {
            override fun run() {

                for(image in imageArray){
                    image.visibility = View.INVISIBLE
                }
                val random = Random()
                val index = random.nextInt(8-0)
                imageArray[index].visibility = View.VISIBLE

                handler.postDelayed(runnable,500)

            }
        }
        
        handler.post(runnable)
    }

    fun timeLeftCounter(){
        object : CountDownTimer(10000,1000){
            override fun onFinish() {
                textView2.text = "Time's off"
                handler.removeCallbacks(runnable)
                for(image in imageArray){
                    image.visibility = View.INVISIBLE
                }

                val alertDialog = AlertDialog.Builder(this@MainActivity)
                alertDialog.setTitle("Time's off")
                alertDialog.setMessage("Do you want to try again?")
                alertDialog.setPositiveButton("yes", DialogInterface.OnClickListener(function = { dialogInterface: DialogInterface, i: Int -> startGame()}))
                alertDialog.setNegativeButton("no",DialogInterface.OnClickListener { dialog, which -> Toast.makeText(applicationContext,"Good Game",Toast.LENGTH_SHORT).show()})
                alertDialog.create()
                alertDialog.show()
            }

            override fun onTick(millisUntilFinished: Long) {
                textView2.text = "Left: " + millisUntilFinished/1000
            }

        }.start()

    }

    fun increaseScore(view: View){
           textView.text = "Score: ${score++}"
    }
}
