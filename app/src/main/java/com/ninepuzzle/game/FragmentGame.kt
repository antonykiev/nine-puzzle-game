package com.ninepuzzle.game

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ninepuzzle.game.databinding.FragmentNinepuzzleGameBinding

class FragmentGame: Fragment(R.layout.fragment_ninepuzzle_game) {
    
    private val viewModelGame by activityViewModels<ViewModelGame>()
    private val binding by viewBinding(FragmentNinepuzzleGameBinding::bind)

    private val viewList by lazy { listOf(
        binding.tvPuzzle0,
        binding.tvPuzzle1,
        binding.tvPuzzle2,
        binding.tvPuzzle3,
        binding.tvPuzzle4,
        binding.tvPuzzle5,
        binding.tvPuzzle6,
        binding.tvPuzzle7,
        binding.tvPuzzle8,
    ) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelGame.winResult.observe(viewLifecycleOwner) {
            if (it) gameIsWin()
        }

        viewModelGame.stepCounter.observe(viewLifecycleOwner) {
            binding.tvSteps.text = "$it"
        }

        viewModelGame.timer.observe(viewLifecycleOwner) {
            timerTick (it)
        }

        viewModelGame.initSequence.observe(viewLifecycleOwner) {
                it.forEachIndexed { index, s ->
                    if (s == "9") {
                        viewList[index].visibility = View.INVISIBLE
                        return@forEachIndexed
                    }
                    viewList[index].visibility = View.VISIBLE
                    viewList[index].text = s
                }
            }

        viewList.forEach {
            it.setOnClickListener {
                val index = kotlin.runCatching { it.tag.toString().toInt() }.getOrNull() ?: 0
                viewModelGame.performStep(index)
            }
        }

        binding.btnGiveUp.setOnClickListener {
            nextScreen()
        }
    }

    private fun timerTick(seconds: Int) {
        val minutes = String.format("%02d", seconds / 60)
        val seconds = String.format("%02d", seconds % 60)

        binding.tvTimer.text = "$minutes:$seconds"

    }

    private fun gameIsWin() {
        viewList.forEach { it.isClickable = false }

        binding.btnGiveUp.visibility = View.INVISIBLE
        binding.btnNext.visibility = View.VISIBLE
        binding.tvGoodjob.visibility = View.VISIBLE

        binding.btnNext.setOnClickListener {
            nextScreen()
        }
    }

    private fun nextScreen() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.root, FragmentColorGameSelector())
            .commit()
    }

}