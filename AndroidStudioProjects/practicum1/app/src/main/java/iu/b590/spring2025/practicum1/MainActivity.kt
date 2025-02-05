package iu.b590.spring2025.practicum1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import iu.b590.spring2025.practicum1.databinding.ActivityMainBinding
import iu.b590.spring2025.practicum1.QuizViewModel



private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val quizViewModel: QuizViewModel by viewModels()
    private var correctAnswers = 0  // Track correct answers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set initial question
        updateQuestion()

        // Set click listeners for True/False
        binding.trueButton?.setOnClickListener {
            checkAnswer(true)
            disableChoices()
        }

        binding.falseButton?.setOnClickListener {
            checkAnswer(false)
            disableChoices()
        }

        // Set click listener for Next button
        binding.nextButton?.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
            // Enable buttons when moving to the next question
            enableChoices()
        }
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

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer

        // Update the score if the answer is correct
        if (userAnswer == correctAnswer) {
            correctAnswers++
        }

        // Display correct/incorrect message
        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun disableChoices() {
        binding.trueButton?.isEnabled = false
        binding.falseButton?.isEnabled = false
    }

    private fun enableChoices() {
        binding.trueButton?.isEnabled = true
        binding.falseButton?.isEnabled = true
    }

    private fun showScore() {
        val totalQuestions = quizViewModel.getQuestionBankSize()
        val scorePercentage = (correctAnswers.toDouble() / totalQuestions) * 100
        val scoreMessage = "Your score: ${scorePercentage.toInt()}%"
        Toast.makeText(this, scoreMessage, Toast.LENGTH_LONG).show()
    }

    private fun resetScore() {
        correctAnswers = 0  // reset score for the next round
    }
}
