package com.ninepuzzle.game

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class ViewModelGame: ViewModel() {

    val initSequence = MutableLiveData(generateSequence())

    val winResult = MutableLiveData(false)
    val stepCounter = MutableLiveData(0)
    val timer = MutableLiveData("00:00")

    /**
     * Uncomment this to test WIN
     */
/*    init {
        viewModelScope.launch {
            delay(5000)
            val newSequence = generateSequence().map { it.toInt() }
            analyzeWinResult(newSequence)
            initSequence.postValue(
                newSequence.map { "$it" }
            )
        }
    }*/

    fun performStep(buttonIndex: Int) {
        stepCounter.value = (stepCounter.value ?: 0) + 1
        val newSequence = gameLogicCore(
            initSequence.value?.map { it.toInt() } ?: emptyList(),
            buttonIndex
        )
        analyzeWinResult(newSequence)
        initSequence.postValue(
            newSequence.map { "$it" }
        )
    }

    private fun generateSequence() : List<String> {
        var randomSteps = (1..9).toList()

        repeat(50) {
            val randomIndex = (0..8).random()
            randomSteps = gameLogicCore(randomSteps, randomIndex)

        }
        return randomSteps  .map { "$it" }
    }

    private fun gameLogicCore(
        initList: List<Int>,
        pushedIndex: Int
    ): List<Int> {

        if (initList.isEmpty()) throw NullPointerException()

        listOf(-3, -1, +1, +3)
            .forEach {
                val currentIndex = pushedIndex + it
                val isNine: Boolean = runCatching { initList[currentIndex] == 9 }.getOrElse { false }
                if (isNine) {
                    val result = initList.toMutableList()
                   Collections.swap(result, pushedIndex, currentIndex)
                   return result
                }
            }
        return initList
    }

    private fun analyzeWinResult(sequence: List<Int>) {
        val result: Boolean = sequence == sequence.sorted()
        if (result)
            winResult.postValue(result)
    }

}