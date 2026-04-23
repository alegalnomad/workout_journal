package com.example.workout_journal.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.workout_journal.data.repository.WeightRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeightsViewModel @Inject constructor(
    private val weightsRepository: WeightRepository
) : ViewModel(){

}