package com.rsschool.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.rsschool.quiz.data.QuestionStorage
import com.rsschool.quiz.databinding.FragmentQuizBinding

class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null

    private val binding get() = requireNotNull(_binding)

    private lateinit var options: List<RadioButton>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)

        options = listOf(
            binding.optionOne,
            binding.optionTwo,
            binding.optionThree,
            binding.optionFour,
            binding.optionFive
        )

        val questionIndex = arguments?.getInt(QUESTION_INDEX) ?: throw IllegalArgumentException()
        showQuestion(questionIndex)

        return binding.root
    }

    private fun showQuestion(questionIndex: Int) {
        val question = QuestionStorage.getQuestion(questionIndex)
        question?.let {
            binding.question.text = it.question
            binding.optionOne.text = it.options[0].answer
            binding.optionTwo.text = it.options[1].answer
            binding.optionThree.text = it.options[2].answer
            binding.optionFour.text = it.options[3].answer
            binding.optionFive.text = it.options[4].answer
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance(questionIndex: Int) =
            QuizFragment().apply {
                arguments = bundleOf(
                    QUESTION_INDEX to questionIndex
                )
            }

        private const val QUESTION_INDEX = "QUESTION_INDEX"
    }
}