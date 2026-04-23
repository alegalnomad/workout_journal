package com.example.workout_journal.service

import android.content.Intent
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class TimerState(
    val phase: String = "delay",
    val secondsLeft: Int = 0,
    val currentRound: Int = 0,
    val totalRounds: Int = 0,
    val currentSet: Int = 0,
    val totalSets: Int = 0,
    val currentExercise: String = "",
    val isRunning: Boolean = false,
    val isDone: Boolean = false
)


class HIITService : LifecycleService(){
    private var timerJob: Job? = null

    companion object {
        private val _timerState = MutableStateFlow(TimerState())
        val isPaused = MutableStateFlow(false)
        val timerState: StateFlow<TimerState> = _timerState.asStateFlow()
    }




    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            "START" -> {
                val sets = intent.getIntExtra("sets", 0)
                val rounds = intent.getIntExtra("rounds", 0)
                val workSeconds = intent.getIntExtra("workSeconds", 0)
                val restSeconds = intent.getIntExtra("restSeconds", 0)
                val setRestSeconds = intent.getIntExtra("setRestSeconds", 0)
                val delayStart = intent.getIntExtra("delayStart", 0)
                val myExerciseList = intent.getStringArrayListExtra("exerciseList")

                isPaused.value = false

                startTimer(sets, rounds, workSeconds, restSeconds, setRestSeconds,delayStart,myExerciseList)
            }
            "PAUSE_RESUME" -> {
                pauseResumeService()
            }
            "STOP" -> {
                stopService()
            }
        }
        return super.onStartCommand(intent, flags, startId)

    }

    private fun startTimer(
        sets: Int, rounds: Int, workSeconds: Int, restSeconds: Int,
        setRestSeconds: Int, delayStart: Int, myExerciseList: ArrayList<String>?
    ) {
        timerJob?.cancel()
        timerJob = lifecycleScope.launch {

            // --- Delay phase ---
            if (delayStart > 0) {
                _timerState.update { it.copy(phase = "delay", currentExercise = "Get Ready",
                    isRunning = true, totalSets = sets, totalRounds = rounds) }
                updateTimerState(delayStart)
            }

            // --- Main loop ---
            for (set in 1..sets) {
                for (round in 1..rounds) {
                    // Work phase
                    _timerState.update { it.copy(
                        phase = "work",
                        currentSet = set,
                        currentRound = round,
                        totalSets = sets,
                        totalRounds = rounds,
                        isRunning = true,
                        currentExercise = myExerciseList?.getOrNull(round - 1) ?: ""
                    ) }
                    updateTimerState(workSeconds)

                    // Rest between rounds (skip after last round in a set)
                    if (round < rounds) {
                        _timerState.update { it.copy(phase = "rest", currentExercise = "Rest") }
                        updateTimerState(restSeconds)
                    }
                }

                // Set rest (skip after last set)
                if (set < sets) {
                    _timerState.update { it.copy(phase = "setRest", currentExercise = "Set Rest") }
                    updateTimerState(setRestSeconds)
                }
            }

            // --- Done ---
            _timerState.update { it.copy(isDone = true, isRunning = false) }
        }
    }
    private fun pauseResumeService() {
        if (!isPaused.value) {
            isPaused.value = true
            _timerState.update { it.copy(isRunning = false) }
        }else{
            val state = _timerState.value
            if (state.isDone || state.isRunning)return
            _timerState.update { it.copy(isRunning = true) }
            isPaused.value = false
        }

    }
    private suspend fun updateTimerState(secondsLeft: Int) {
            for (seconds in secondsLeft downTo 0) {
                while (isPaused.value) {delay(200)}
                _timerState.update { it.copy(secondsLeft = seconds) }
                delay(1000)
            }
        }
    private fun stopService() {
        timerJob?.cancel()
        isPaused.value = false
        _timerState.update { TimerState() }
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()}

        override fun onDestroy() {
            timerJob?.cancel()
            super.onDestroy()
        }

    }


