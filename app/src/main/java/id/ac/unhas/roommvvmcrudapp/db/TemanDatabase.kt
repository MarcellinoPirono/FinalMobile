package id.ac.unhas.roommvvmcrudapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Teman::class], version = 1)
abstract class TemanDatabase : RoomDatabase() {
    abstract val temanDAO: TemanDAO

    companion object {
        @Volatile
        private var INSTANCE: TemanDatabase? = null
        fun getInstance(context: Context): TemanDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TemanDatabase::class.java,
                        "teman_data_database"
                    ).build()
                }
                return instance
            }
        }
    }
}