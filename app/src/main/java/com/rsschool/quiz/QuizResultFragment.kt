package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentQuizResultBinding
import com.rsschool.quiz.listeners.FragmentStartCallback

class QuizResultFragment : Fragment() {

    private var _binding: FragmentQuizResultBinding? = null

    private val binding get() = requireNotNull(_binding)

    private var _fragmentStartCallback: FragmentStartCallback? = null

    private val fragmentStartCallback get() = requireNotNull(_fragmentStartCallback)

    override fun onAttach(context: Context) {
        super.onAttach(context)

        _fragmentStartCallback = context as? FragmentStartCallback
            ?: throw RuntimeException("$context must implement SecondFragmentStarter")
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

        setListeners()
    }

    private fun setListeners() {
        binding.run {
            binding.back.setOnClickListener {
                fragmentStartCallback.startQuizPagerFragment()
            }

            binding.close.setOnClickListener {
                requireActivity().finishAndRemoveTask()
            }

            binding.share.setOnClickListener {

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