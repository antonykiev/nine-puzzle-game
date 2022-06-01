package com.ninepuzzle.game

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ninepuzzle.game.databinding.FragmentRulesBinding

class FragmentRules: Fragment(R.layout.fragment_rules) {

    private val binding by viewBinding(FragmentRulesBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {

        }

        binding.btnNext.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.root, FragmentGame())
                .commit()
        }
    }

}