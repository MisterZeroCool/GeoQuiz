package ru.foxdev.geoquiz

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var resetButton: Button
    private lateinit var prevButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var questionTextView: TextView

    private var count = 0

    private val questionBank = listOf(
        Question(R.string.question_moscow,true),
        Question(R.string.question_eswatini,true),
        Question(R.string.question_andorra,false),
        Question(R.string.question_buj_khalifa,true),
        Question(R.string.question_atomium,false),
        Question(R.string.question_gobi,false),
        Question(R.string.question_north_centre_america,true)
    )

    private var currentIndex = 0;



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)


        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        resetButton = findViewById(R.id.reset_button)
        prevButton = findViewById(R.id.prev_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)



        trueButton.setOnClickListener { view: View -> checkAnswer(true)
            falseButton.isEnabled = false
            trueButton.isEnabled = false
        }

        falseButton.setOnClickListener { view: View -> checkAnswer(false)
            falseButton.isEnabled = false
            trueButton.isEnabled = false
        }

        resetButton.setOnClickListener {
            currentIndex = 0
            count = 0
            updateQuestion()
            falseButton.isEnabled = true
            trueButton.isEnabled = true
        }

//        prevButton.setOnClickListener {
//            currentIndex = (currentIndex + questionBank.size-1) %questionBank.size
//            falseButton.isEnabled = true
//            trueButton.isEnabled = true
//            updateQuestion()
//        }

        nextButton.setOnClickListener {
            if (currentIndex < questionBank.size-1) {
                currentIndex = (currentIndex + 1) % questionBank.size
                falseButton.isEnabled = true
                trueButton.isEnabled = true
                updateQuestion()
                var d = answerPercent(count)
                Thread.sleep(1000)
                Toast.makeText(this,"Процентов", Toast.LENGTH_SHORT )
            }
        }
        updateQuestion()

    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId = if (userAnswer == correctAnswer){
            count += 1
            R.string.correct_toast
        }else{
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun answerPercent(count: Int):Int{
        return if (count == 0){
            0
        }else
            100 * count / questionBank.size
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
}


