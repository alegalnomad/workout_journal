package com.example.workout_journal.data.repository

import com.example.workout_journal.data.dao.BodyHeightWeightDAO
import com.example.workout_journal.data.entity.BodyHeightWeight
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BodyHeightWeightRepository @Inject constructor(private val dao: BodyHeightWeightDAO) {
    val all: Flow<List<BodyHeightWeight>> = dao.getAll()
    suspend fun getLatest(): BodyHeightWeight? = dao.getLatest()
    suspend fun add(entry: BodyHeightWeight) = dao.insertBodyHeightWeight(entry)
    suspend fun remove(entry: BodyHeightWeight) = dao.deleteBodyHeightWeight(entry)
}