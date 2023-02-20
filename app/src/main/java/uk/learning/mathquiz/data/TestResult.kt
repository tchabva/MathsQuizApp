package uk.learning.mathquiz.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "maths_quiz_history")
data class TestResult(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "number")
    val number: String,

    @ColumnInfo(name = "operator")
    val operator: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "score")
    val score: String,

    @ColumnInfo(name = "date")
    val date: String,
)