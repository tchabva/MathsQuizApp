package uk.learning.mathquiz.ui.homePageScreen
/*
This creates the different states that the HomeScreen Composable can be in. Will be used by the
viewmodel to alter what is presented to the user.
 */

sealed class HomeScreenState(){
    class EmptyDb(): HomeScreenState()

    class DbNotEmpty(): HomeScreenState()
}
