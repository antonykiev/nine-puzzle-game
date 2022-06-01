package com.ninepuzzle.game

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ninepuzzle.game.databinding.FragmentNinepuzzleGameBinding

class FragmentGame: Fragment(R.layout.fragment_ninepuzzle_game) {

    private val binding by viewBinding(FragmentNinepuzzleGameBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}