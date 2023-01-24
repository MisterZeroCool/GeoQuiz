package ru.foxdev.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var question: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        question = findViewById(R.id.question_text)

        trueButton.setOnClickListener { view: View -> Toast.makeText(
            this,
            R.string.correct_toast,
            Toast.
            Toast.LENGTH_SHORT).show()
        }

        falseButton.setOnClickListener { view: View -> Toast.makeText(
            this,
            R.string.incorrect_toast,
            Toast.LENGTH_SHORT).show()
        }
    }
}