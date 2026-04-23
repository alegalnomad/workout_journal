package com.example.workout_journal.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.workout_journal.data.repository.HIITRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HIITViewModel @Inject constructor(
    private val hiitRepository: HIITRepository
) : ViewModel() {
}