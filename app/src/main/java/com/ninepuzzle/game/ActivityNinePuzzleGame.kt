package com.ninepuzzle.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ActivityNinePuzzleGame : AppCompatActivity(R.layout.activity_nine_puzzle_game) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .replace(R.id.root, FragmentRules())
            .commit()
    }
}