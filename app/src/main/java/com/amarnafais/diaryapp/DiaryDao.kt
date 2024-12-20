package com.amarnafais.diaryapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DiaryDao {
    @Insert
    suspend fun insert(entry: DiaryEntry)

    @Query("SELECT * FROM diary_entries ORDER BY id DESC")
    fun getAllEntries(): LiveData<List<DiaryEntry>>

    @Query("SELECT * FROM diary_entries WHERE id = :entryId")
    suspend fun getEntryById(entryId: Int): DiaryEntry?

    @Query("SELECT * FROM diary_entries WHERE date = :selectedDate ORDER BY id DESC")
    fun getEntriesByDate(selectedDate: String): LiveData<List<DiaryEntry>>

    @Update
    suspend fun update(entry: DiaryEntry)

    @Query("DELETE FROM diary_entries WHERE id IN (:entryIds)")
    suspend fun deleteEntriesByIds(entryIds: List<Int>)
}