package id.ac.unhas.roommvvmcrudapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teman_data_table")
data class teman(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "teman_name")
    var id : Int,

    @ColumnInfo(name =  "teman_id")
    var name : String,

    @ColumnInfo(name =  "teman_email")
    var email : String

)
