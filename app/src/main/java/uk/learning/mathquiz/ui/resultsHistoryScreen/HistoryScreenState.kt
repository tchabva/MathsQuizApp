package uk.learning.mathquiz.ui.resultsHistoryScreen
/*
This creates the states on the HistoryScreen that will work with the filters in the toolbar
 */
sealed class HistoryScreenState(
    val storedOperator: String?,
    val storedNumber: String?
){
    class NoFilter(
        storedOperator: String? = null,
        storedNumber: String? = null
    ): HistoryScreenState(storedOperator, storedNumber)

    class FiltersActive(
        storedOperator: String,
        storedNumber: String
    ): HistoryScreenState(storedOperator, storedNumber)
}
