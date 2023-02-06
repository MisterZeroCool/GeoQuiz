package ru.foxdev.geoquiz

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var resetButton: Button
    private lateinit var prevButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var questionTextView: TextView
    private lateinit var answerPercentTextView: TextView

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this)[QuizViewModel::class.java]
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex


        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        resetButton = findViewById(R.id.reset_button)
        prevButton = findViewById(R.id.prev_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)
        answerPercentTextView = findViewById(R.id.answer_percent)

        answerPercentTextView.text = quizViewModel.percentAnswer.toString()+"%"


        trueButton.setOnClickListener {
            checkAnswer(true)
            falseButton.isEnabled = false
            trueButton.isEnabled = false
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
            falseButton.isEnabled = false
            trueButton.isEnabled = false
        }

        resetButton.setOnClickListener {
            quizViewModel.currentIndex = 0
            quizViewModel.countTrueAnswers = 0
            quizViewModel.percentAnswer = 0
            updateQuestion()
            falseButton.isEnabled = true
            trueButton.isEnabled = true
            answerPercentTextView.text = "0%"
        }

//        prevButton.setOnClickListener {
//            currentIndex = (currentIndex + questionBank.size-1) %questionBank.size
//            falseButton.isEnabled = true
//            trueButton.isEnabled = true
//            updateQuestion()
//        }

        /*TODO: При перевороте activity кнопки стоновятся доступными,
           в результате чего возможно нажимать их заново
           Необходимо устранить БАГ
         */


        nextButton.setOnClickListener {
            if (quizViewModel.currentIndex < quizViewModel.getSize()-1){
            quizViewModel.moveToNext()
            falseButton.isEnabled = true
            trueButton.isEnabled = true
            updateQuestion()
            }
        }

        updateQuestion()
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)

    }

    private fun answerPercent(count: Int): Int {
        return 100*count/quizViewModel.getSize()
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = if (userAnswer == correctAnswer) {
            quizViewModel.countTrueAnswers += 1
            quizViewModel.percentAnswer = answerPercent( quizViewModel.countTrueAnswers)
            answerPercentTextView.text ="${quizViewModel.percentAnswer}%"
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

}

