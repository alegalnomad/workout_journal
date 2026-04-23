package com.example.workout_journal.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.workout_journal.data.repository.RunRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RunViewModel @Inject constructor(
    private val runRepository: RunRepository
) : ViewModel(){

}