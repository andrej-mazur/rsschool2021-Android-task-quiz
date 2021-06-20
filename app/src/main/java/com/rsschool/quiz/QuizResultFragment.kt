package com.rsschool.quiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.rsschool.quiz.data.DataStorage
import com.rsschool.quiz.databinding.FragmentQuizResultBinding
import com.rsschool.quiz.listeners.FragmentStartCallback

class QuizResultFragment : Fragment() {

    private var _binding: FragmentQuizResultBinding? = null

    private val binding get() = requireNotNull(_binding)

    private var _fragmentStartCallback: FragmentStartCallback? = null

    private val fragmentStartCallback get() = requireNotNull(_fragmentStartCallback)

    private var correctOptions = 0

    private var shortResult = ""

    private var fullResult = ""

    override fun onAttach(context: Context) {
        super.onAttach(context)

        _fragmentStartCallback = context as? FragmentStartCallback
            ?: throw RuntimeException("$context must implement FragmentStartCallback")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for ((index, question) in DataStorage.questions.withIndex()) {
            val selectedOptionIndex = DataStorage.selectedOptions[index]
            val selectedOption = question.options[selectedOptionIndex]
            if (selectedOption.correct) {
                correctOptions++
            }

            fullResult += getString(
                R.string.full_report,
                (index + 1),
                question.text,
                selectedOption.text,
                if (selectedOption.correct) getString(R.string.right) else getString(R.string.wrong)
            )
        }

        shortResult = getString(R.string.short_result, correctOptions, DataStorage.questions.size)

        binding.result.text = shortResult

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finishAndRemoveTask()
                }
            })

        setListeners()
    }

    private fun setListeners() {
        binding.run {
            binding.share.setOnClickListener {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_SUBJECT, getString(R.string.result_title));
                    putExtra(Intent.EXTRA_TEXT, "${shortResult}\n${fullResult}")
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(sendIntent, null))
            }

            binding.back.setOnClickListener {
                fragmentStartCallback.startQuizPagerFragment()
            }

            binding.close.setOnClickListener {
                requireActivity().finishAndRemoveTask()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            QuizResultFragment().apply {
                arguments = bundleOf()
            }
    }
}