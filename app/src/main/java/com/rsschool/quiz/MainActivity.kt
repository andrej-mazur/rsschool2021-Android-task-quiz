package com.rsschool.quiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.rsschool.quiz.data.DataStorage
import com.rsschool.quiz.databinding.ActivityMainBinding
import com.rsschool.quiz.listeners.FragmentStartCallback

class MainActivity : AppCompatActivity(), FragmentStartCallback {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startQuizPagerFragment()
    }

    override fun startQuizPagerFragment() {
        DataStorage.reset()
        val fragment: Fragment = QuizPagerFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    override fun startQuizResultFragment() {
        val fragment: Fragment = QuizResultFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}