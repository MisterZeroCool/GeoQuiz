package ru.foxdev.geoquiz

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var prevButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var questionTextView: TextView

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
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        prevButton = findViewById(R.id.prev_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)


        trueButton.setOnClickListener { view: View -> checkAnswer(true)
        }

        falseButton.setOnClickListener { view: View -> checkAnswer(false)
        }

        prevButton.setOnClickListener {
            currentIndex = (currentIndex + questionBank.size-1) %questionBank.size
            updateQuestion()
        }


        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) %questionBank.size
            updateQuestion()
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
            R.string.correct_toast
        }else{
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }
}

