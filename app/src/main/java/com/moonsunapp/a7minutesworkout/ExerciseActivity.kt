package com.moonsunapp.a7minutesworkout

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.moonsunapp.a7minutesworkout.databinding.ActivityExerciseBinding
import com.moonsunapp.a7minutesworkout.databinding.ActivityMainBinding
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding: ActivityExerciseBinding? = null

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var tts: TextToSpeech? = null
    private var playerMedia: MediaPlayer? = null

    private var exerciseAdapter:ExerciseStatusAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)
        tts = TextToSpeech(this, this)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        exerciseList = Constants.defaultExerciseList()

        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressed()
        }
        setUpRestView()
        setupExerciseStatusRecyclerViewAdapter()
    }

    private fun setupExerciseStatusRecyclerViewAdapter(){
        binding?.rvExerciseStatus?.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        exerciseAdapter= ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter=exerciseAdapter
    }
    private fun setUpRestView() {

        try {
            val soundURI = Uri.parse("android.resource://com.moonsunapp.a7minutesworkout/"+R.raw.press_start)
            playerMedia=MediaPlayer.create(applicationContext,soundURI)
            playerMedia?.isLooping=false
            playerMedia?.start()
        }
        catch (e:Exception){
            e.printStackTrace()
        }

        binding?.flProgressBar?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.tvExercise?.visibility = View.INVISIBLE
        binding?.ivExercise?.visibility = View.INVISIBLE
        binding?.flProgressBarExercise?.visibility = View.INVISIBLE
        binding?.tvUpcomingExerciseTitle?.visibility = View.VISIBLE
        binding?.tvUpcomingExercise?.visibility = View.VISIBLE

        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        binding?.tvUpcomingExercise?.text = exerciseList!![currentExercisePosition + 1].getName()

        setProgressBar()

    }

    private fun setUpExerciseView() {
        binding?.flProgressBar?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.tvExercise?.visibility = View.VISIBLE
        binding?.ivExercise?.visibility = View.VISIBLE
        binding?.flProgressBarExercise?.visibility = View.VISIBLE
        binding?.tvUpcomingExerciseTitle?.visibility = View.INVISIBLE
        binding?.tvUpcomingExercise?.visibility = View.INVISIBLE
        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        speakOut(exerciseList!![currentExercisePosition].getName())
        binding?.ivExercise?.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding?.tvExercise?.text = exerciseList!![currentExercisePosition].getName()
        setProgressBarExercise()
    }

    private fun setProgressBar() {
        binding?.progressBar?.progress = restProgress

        restTimer = object : CountDownTimer(1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                setUpExerciseView()
            }
        }.start()
    }

    private fun setProgressBarExercise() {
        binding?.progressBarExercise?.progress = exerciseProgress

        exerciseTimer = object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress = 30 - exerciseProgress
                binding?.tvTimerExercise?.text = (30 - exerciseProgress).toString()
            }

            override fun onFinish() {
                if (currentExercisePosition < (exerciseList?.size!! - 1)) {
                    setUpRestView()
                } else {
                    Toast.makeText(
                        this@ExerciseActivity,
                        "Congrats! You complete 7 min workout!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()

        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        if (tts!=null){
            tts!!.stop()
            tts!!.shutdown()
        }
        if (playerMedia!=null){
            playerMedia!!.stop()
        }
        binding = null
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            //set US language for TTS
            var result = tts?.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language is not supported")
            }
        } else {
            Log.e("TTS", "Initialization failed")

        }
    }

    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }
}