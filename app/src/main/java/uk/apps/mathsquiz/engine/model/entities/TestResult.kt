package uk.apps.mathsquiz.engine.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//This will become an Entity notification
@Entity(tableName = "results_table") //Name of the table
data class TestResult(
    //Names of the columns
    @ColumnInfo val number: String,
    @ColumnInfo val operator: String,
    @ColumnInfo val name: String,
    @ColumnInfo val score: String,
    @ColumnInfo val date: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0 //Primary Key needs to be defined for the table
)
