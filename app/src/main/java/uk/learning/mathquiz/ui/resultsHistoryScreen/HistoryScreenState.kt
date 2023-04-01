package uk.learning.mathquiz.ui.resultsHistoryScreen
/*
This creates the states on the HistoryScreen that will work with the filters in the toolbar
 */
sealed class HistoryScreenState(){
    class FiltersInactive(): HistoryScreenState()

    class FiltersActive(): HistoryScreenState()
}
