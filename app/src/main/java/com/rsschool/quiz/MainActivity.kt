package com.rsschool.quiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startQuiz(0)
    }

    fun startQuiz(questionIndex: Int) {
        val quizFragment: Fragment = QuizFragment.newInstance(questionIndex)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, quizFragment)
            .commit()
    }
}