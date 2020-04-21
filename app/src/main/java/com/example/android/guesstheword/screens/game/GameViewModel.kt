package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.sql.Date

class GameViewModel : ViewModel(){

    // The current word
    //var word = ""
    //Use of LiveData
    val word = MutableLiveData<String>()

    // The current score
    //var score = 0
    val score = MutableLiveData<Int>()

    val eventGameFinish = MutableLiveData<Boolean>()

    val currentTime = MutableLiveData<Long>()

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    val timer: CountDownTimer


    init {
        eventGameFinish.value = false
        score.value = 0
        Log.i("GameViewModel", "GameViewModel Created !!")
        resetList()
        nextWord()

        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                // TODO implement what should happen each tick of the timer
                currentTime.value = (millisUntilFinished / ONE_SECOND)
            }

            override fun onFinish() {
                currentTime.value = DONE
                eventGameFinish.value =true
            }
        }


        timer.start()
    }

    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        const val COUNTDOWN_TIME = 6000L
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()

        Log.i("GameViewModel", " GameViewModel Destroyed !")
    }


    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
          //  gameFinished()  //Temporarily Commented
         //   eventGameFinish.value = true  //Solved using LiveData
            resetList() // because we don't want to finish game until timer ends
        } else {
            word.value  = wordList.removeAt(0)
        }

    }

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordList.shuffle()
    }


    /** Methods for buttons presses **/

    fun onSkip() {
        score.value = score.value?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        score.value = score.value?.plus(1)
        nextWord()
    }

    /**
     * Called when the game is finished
     */
    fun onGameFinished() {

        eventGameFinish.value = false
    }


}