package id.ac.unhas.roommvvmcrudapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TemanDAO {
    @Insert
    suspend fun insertTeman(teman: Teman) : Long

    @Update
    suspend fun updateTeman(teman: Teman) : Int

    @Delete
    suspend fun deleteTeman(teman: Teman) : Int

    @Query("DELETE FROM teman_data_table")
    suspend fun deleteAll() : Int

    @Query("SELECT * FROM teman_data_table")
    fun getAllTeman():Flow<List<Teman>>
}