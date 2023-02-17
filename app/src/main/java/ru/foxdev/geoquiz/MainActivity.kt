package ru.foxdev.geoquiz


import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

private const val REQUEST_CODE_CHEAT = 0


class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var resetButton: Button
    private lateinit var prevButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var questionTextView: TextView
    private lateinit var answerPercentTextView: TextView
    private lateinit var cheatButton: Button

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this)[QuizViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()


        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        resetButton = findViewById(R.id.reset_button)
        prevButton = findViewById(R.id.prev_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)
        answerPercentTextView = findViewById(R.id.answer_percent)
        cheatButton = findViewById(R.id.cheat_button)

        answerPercentTextView.text = quizViewModel.percentAnswer.toString()+"%"

        falseButton.isEnabled = quizViewModel.buttonF
        trueButton.isEnabled = quizViewModel.buttonT

        trueButton.setOnClickListener {
            checkAnswer(true)
            falseButton.isEnabled = false
            trueButton.isEnabled = false
            quizViewModel.buttonT = false
            quizViewModel.buttonF = false
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
            falseButton.isEnabled = false
            trueButton.isEnabled = false
            quizViewModel.buttonT = false
            quizViewModel.buttonF = false
        }

        resetButton.setOnClickListener {
            quizViewModel.currentIndex = 0
            quizViewModel.countTrueAnswers = 0
            updateQuestion()
            falseButton.isEnabled = true
            trueButton.isEnabled = true
            quizViewModel.buttonT = true
            quizViewModel.buttonF = true
            quizViewModel.percentAnswer = 0
            answerPercentTextView.text = quizViewModel.percentAnswer.toString() +"%"
        }

//        prevButton.setOnClickListener {
//            currentIndex = (currentIndex + questionBank.size-1) %questionBank.size
//            falseButton.isEnabled = true
//            trueButton.isEnabled = true
//            updateQuestion()
//        }

        nextButton.setOnClickListener {
            if (quizViewModel.currentIndex < quizViewModel.getSize()-1){
            quizViewModel.moveToNext()
            falseButton.isEnabled = true
            trueButton.isEnabled = true
            updateQuestion()
            }
        }

        cheatButton.setOnClickListener {
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
        }
        updateQuestion()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK){
            return
        }
        if (requestCode == REQUEST_CODE_CHEAT){
            quizViewModel.isCheater =
                data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }



//    private fun updateQuestion() {
//        val questionTextResId = questionBank[currentIndex].textResId
//        questionTextView.setText(questionTextResId)
//    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun answerPercent(count: Int): Int {
        return 100*count/quizViewModel.getSize()
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
//        val messageResId = if (userAnswer == correctAnswer) {
//            R.string.correct_toast
//        } else {
//            R.string.incorrect_toast
//        }
        val messageResId = when{
            quizViewModel.isCheater -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
            }
        if (userAnswer == correctAnswer) {
            quizViewModel.countTrueAnswers += 1
            quizViewModel.percentAnswer = answerPercent( quizViewModel.countTrueAnswers)
            answerPercentTextView.text ="${quizViewModel.percentAnswer}%"
            trueButton.isEnabled = false
            falseButton.isEnabled = false
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }
}

