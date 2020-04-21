/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.guesstheword.screens.game

import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.GameFragmentBinding

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {

    //ViewModel Instance
    private lateinit var mViewModel: GameViewModel


    private lateinit var binding: GameFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        Log.i("GameFragment","Called ViewModelProvider")
        mViewModel =  ViewModelProvider(this).get(GameViewModel::class.java)

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.game_fragment,
                container,
                false
        )



        binding.correctButton.setOnClickListener {
            mViewModel.onCorrect()

        }
        binding.skipButton.setOnClickListener {
            mViewModel.onSkip()

        }

        //Use of LiveData which notify view when value changes in ViewModel
        mViewModel.score.observe(viewLifecycleOwner, Observer { newScore ->

            binding.scoreText.text = newScore.toString()
        })
        mViewModel.word.observe(viewLifecycleOwner, Observer { newWord ->
            binding.wordText.text = newWord
        })

        mViewModel.eventGameFinish.observe(viewLifecycleOwner, Observer { hasFinished ->
            if(hasFinished)
            {
                val action = GameFragmentDirections.actionGameToScore(mViewModel.score.value?: 0)
                findNavController(this).navigate(action)
                mViewModel.onGameFinished() //This sets eventGameFinish to false again to avoid this action to happen again

            }
        })

        mViewModel.currentTime.observe(viewLifecycleOwner, Observer { newTime ->
            binding.timerText.text = DateUtils.formatElapsedTime(newTime)
        })
        return binding.root

    }






}
