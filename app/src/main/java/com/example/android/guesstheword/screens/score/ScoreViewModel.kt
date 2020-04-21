package com.example.android.guesstheword.screens.score

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(finalScore: Int): ViewModel() {

    val score = MutableLiveData<Int>()
    val eventPlayAgain = MutableLiveData<Boolean>()


init {
    score.value = finalScore

    Log.i("value from Factory","$finalScore")
}
    fun onEventPlayAgain() {
        eventPlayAgain.value = true
    }
    fun onEventPlayAgainFalse(){
        eventPlayAgain.value = false
    }




}
