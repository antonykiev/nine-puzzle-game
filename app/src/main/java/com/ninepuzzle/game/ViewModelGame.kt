package com.ninepuzzle.game

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class ViewModelGame: ViewModel() {

    val initSequence = MutableLiveData(generateSequence())

    val winResult = MutableLiveData(false)
    val stepCounter = MutableLiveData(0)
    val timer = MutableLiveData(0)



    init {
        /**
         * Uncomment this to test WIN
         */
/*        viewModelScope.launch {
            delay(5000)
            val newSequence = (1..8).map { it.toInt() }
            analyzeWinResult(newSequence)
            initSequence.postValue(
                newSequence.map { "$it" }
            )
        }*/

        viewModelScope.launch {
            while (winResult.value == false) {
                delay(1_000)
                timer.postValue((timer.value ?: 0) + 1)
            }
        }
    }

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

    fun calculateScore(): LiveData<Int> {
        if (winResult.value == false)
            return MutableLiveData(0)

        val result = (300 - (stepCounter.value ?: 0)) + 50
        return MutableLiveData(result)
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