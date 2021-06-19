package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.rsschool.quiz.data.QuestionStorage
import com.rsschool.quiz.databinding.FragmentQuizBinding
import com.rsschool.quiz.listeners.FragmentStartCallback
import com.rsschool.quiz.listeners.ViewPagerCallback
import kotlin.properties.Delegates


class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null

    private val binding get() = requireNotNull(_binding)

    private var _viewPagerCallback: ViewPagerCallback? = null

    private val viewPagerCallback get() = requireNotNull(_viewPagerCallback)

    private var _fragmentStartCallback: FragmentStartCallback? = null

    private val fragmentStartCallback get() = requireNotNull(_fragmentStartCallback)

    private var position by Delegates.notNull<Int>()

    private var contextThemeWrapper by Delegates.notNull<ContextThemeWrapper>()

    private val themes = listOf(
        R.style.Theme_Quiz_First,
        R.style.Theme_Quiz_Second,
        R.style.Theme_Quiz_Third,
        R.style.Theme_Quiz_Fourth,
        R.style.Theme_Quiz_Fifth
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)

        _viewPagerCallback = parentFragment as? ViewPagerCallback
            ?: throw RuntimeException("$context must implement SecondFragmentStarter")

        _fragmentStartCallback = context as? FragmentStartCallback
            ?: throw RuntimeException("$context must implement SecondFragmentStarter")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        position = arguments?.getInt(POSITION) ?: throw IllegalArgumentException()

        contextThemeWrapper = ContextThemeWrapper(requireActivity(), themes[position % themes.size])

        inflater.cloneInContext(contextThemeWrapper).let { localInflater ->
            _binding = FragmentQuizBinding.inflate(localInflater, container, false)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setQuestion()
        setStates()
        setListeners()
    }

    private fun setQuestion() {
        binding.toolbar.title = getString(R.string.title, position + 1)

        val question = QuestionStorage.getQuestion(position)
        question?.let {
            binding.question.text = it.question
            binding.optionOne.text = it.options[0].answer
            binding.optionTwo.text = it.options[1].answer
            binding.optionThree.text = it.options[2].answer
            binding.optionFour.text = it.options[3].answer
            binding.optionFive.text = it.options[4].answer
        }
    }

    private fun setStates() {
        binding.run {
            val isFirstQuestion = QuestionStorage.isFirstQuestion(position)
            val isFinalQuestion = QuestionStorage.isFinalQuestion(position)

            previousButton.isEnabled = !isFirstQuestion


            if (isFinalQuestion) {
                nextButton.visibility = View.GONE
                submitButton.visibility = View.VISIBLE

            } else {
                nextButton.visibility = View.VISIBLE
                submitButton.visibility = View.GONE
            }

            toolbar.navigationIcon =
                if (!isFirstQuestion)
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_baseline_chevron_left_24
                    )
                else
                    null
        }
    }

    private fun setListeners() {
        binding.run {
            toolbar.setNavigationOnClickListener {
                viewPagerCallback.previousPage()
            }

            previousButton.setOnClickListener {
                viewPagerCallback.previousPage()
            }

            nextButton.setOnClickListener {
                viewPagerCallback.nextPage()
            }

            submitButton.setOnClickListener {
                fragmentStartCallback.startQuizResultFragment()
            }

            radioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
                if (QuestionStorage.isFinalQuestion(position)) {
                    submitButton.isEnabled = true
                } else {
                    nextButton.isEnabled = true
                }

                val checkedRadioButton = radioGroup.findViewById<RadioButton>(checkedId)
                val checkedPosition = radioGroup.indexOfChild(checkedRadioButton)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        // set status bar color
        val typedValue = TypedValue()
        contextThemeWrapper.theme.resolveAttribute(android.R.attr.statusBarColor, typedValue, true)
        requireActivity().window.statusBarColor = typedValue.data
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance(position: Int) =
            QuizFragment().apply {
                arguments = bundleOf(
                    POSITION to position
                )
            }

        private const val POSITION = "position"
    }
}