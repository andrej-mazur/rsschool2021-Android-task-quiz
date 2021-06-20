package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rsschool.quiz.data.DataStorage
import com.rsschool.quiz.databinding.FragmentQuizPagerBinding
import com.rsschool.quiz.listeners.ViewPagerCallback


class QuizPagerFragment : Fragment(), ViewPagerCallback {

    private var _binding: FragmentQuizPagerBinding? = null

    private val binding get() = requireNotNull(_binding)

    override fun onAttach(context: Context) {
        super.onAttach(context)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizPagerBinding.inflate(inflater, container, false)
        binding.pager.adapter = QuizPagerAdapter(this)
        binding.pager.isUserInputEnabled = false
        binding.pager.offscreenPageLimit = 1
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding.pager.currentItem > 0) {
                        previousPage()
                    } else {
                        requireActivity().finishAndRemoveTask()
                    }
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun previousPage() {
        if (binding.pager.currentItem > 0) {
            binding.pager.currentItem = binding.pager.currentItem - 1
        }
    }

    override fun nextPage() {
        if (binding.pager.currentItem < DataStorage.getQuestionCount() - 1) {
            binding.pager.currentItem = binding.pager.currentItem + 1
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            QuizPagerFragment().apply {
                arguments = bundleOf()
            }
    }

    class QuizPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int = DataStorage.getQuestionCount()

        override fun createFragment(position: Int): Fragment = QuizFragment.newInstance(position)
    }
}