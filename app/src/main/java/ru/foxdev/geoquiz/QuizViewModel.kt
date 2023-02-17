package ru.foxdev.geoquiz

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {

    var currentIndex = 0;
    var countTrueAnswers = 0
    var percentAnswer = 0

    var isCheater = false

    private val questionBank = listOf(
        Question(R.string.question_moscow,true),
        Question(R.string.question_eswatini,true),
        Question(R.string.question_andorra,false),
        Question(R.string.question_buj_khalifa,true),
        Question(R.string.question_atomium,false),
        Question(R.string.question_gobi,false),
        Question(R.string.question_north_centre_america,true)
    )

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun getSize(): Int{
       return questionBank.size
    }
}