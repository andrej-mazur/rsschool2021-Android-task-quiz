package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.rsschool.quiz.data.QuestionStorage
import com.rsschool.quiz.databinding.FragmentQuizBinding
import kotlin.properties.Delegates


class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null

    private val binding get() = requireNotNull(_binding)

    private var _pageChangerListener: PageChangerListener? = null

    private val pageChangerListener get() = requireNotNull(_pageChangerListener)

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
        _pageChangerListener = this.parentFragment as? PageChangerListener
            ?: throw RuntimeException("$context must implement SecondFragmentStarter")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        position = arguments?.getInt(POSITION) ?: throw IllegalArgumentException()

        // set app theme
        contextThemeWrapper = ContextThemeWrapper(requireActivity(), themes[position % themes.size])

        _binding = FragmentQuizBinding.inflate(
            inflater.cloneInContext(contextThemeWrapper),
            container,
            false
        )

        showQuestion(position)

        binding.previousButton.isEnabled = position > 0
        binding.previousButton.setOnClickListener {
            pageChangerListener.previousPage()
        }

        binding.nextButton.isEnabled = false
        binding.nextButton.setOnClickListener {
            pageChangerListener.nextPage()
        }

        binding.radioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
            if (position < QuestionStorage.getQuestionCount() - 1) {
                binding.nextButton.isEnabled = true
            }

            val checkedRadioButton = radioGroup.findViewById<RadioButton>(checkedId)
            val checkedIndex = radioGroup.indexOfChild(checkedRadioButton)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        // set status bar color
        val typedValue = TypedValue()
        contextThemeWrapper.theme.resolveAttribute(android.R.attr.statusBarColor, typedValue, true)
        requireActivity().window.statusBarColor = typedValue.data
    }

    private fun showQuestion(position: Int) {
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