package uk.learning.mathquiz.ui


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import uk.learning.mathquiz.R
import uk.learning.mathquiz.ui.homePageScreen.HomeViewModel
import uk.learning.mathquiz.ui.numberTableScreen.NumberTableViewModel
import uk.learning.mathquiz.ui.quizQuestionScreen.Operator
import uk.learning.mathquiz.ui.screen.*

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun MathsQuizApp(){
    //Initiate the mutable state booleans for the tje
    val openDialog = remember{ mutableStateOf(false) }

    Navigation(openDialog)
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun Navigation(
    openDialog: MutableState<Boolean>,

){
    //The navController
    val navController = rememberNavController()

    //Navigation routes and arguments for the app
    NavHost(
        navController = navController,
        startDestination = "Splash",
    ) {
        //Splash Screen Composable
        composable("Splash"){
            SplashScreen(navController)

        }
        //Home Screen Composable
        composable("Home"){
            val viewModel: HomeViewModel
            HomeScreen(navController)
        }
        //Landing Page Composable
        composable(
            "Landing/{userName}",
            arguments = listOf(navArgument("userName"){type = NavType.StringType})
        ){
            navBackStackEntry ->
            val userName = navBackStackEntry.arguments?.getString("userName").toString()
            LandingScreen(navController, userName)
        }
        //Number Table Composable
        composable(
            //An example of parsing multiple arguments
            "NumberTable/{userName}/{operator}",
            arguments = listOf(
                navArgument("userName"){type = NavType.StringType},
                navArgument("operator"){type = NavType.StringType}
            )
        ){
            //An example of parsing multiple arguments
            navBackStackEntry ->
            val userName = navBackStackEntry.arguments?.getString("userName").toString()
            val operatorArg = navBackStackEntry.arguments?.getString("operator").toString()

            //This will set the value for the Operator Enum class based on user input
            val operator = if (operatorArg == stringResource(R.string.multiplication)){
                Operator.MULTIPLICATION
            }else{
                Operator.DIVISION
            }

            val viewModel = NumberTableViewModel(operator)
            NumberTableScreen(navController, userName, operatorArg, viewModel)
        }
        //Quiz Questions Screen Composable
        composable(
            "QuizQuestions/{userName}/{operator}/{number}",
            arguments = listOf(
                navArgument("userName"){type = NavType.StringType},
                navArgument("operator"){type = NavType.StringType},
                navArgument("number"){type = NavType.IntType}
            )
        ){
            navBackStackEntry ->
            val userName = navBackStackEntry.arguments?.getString("userName").toString()
            val operatorArg = navBackStackEntry.arguments?.getString("operator").toString()
            val number = navBackStackEntry.arguments!!.getInt("number")

            //This will set the value for the Operator Enum class based on user input
            val operator = if (operatorArg == stringResource(R.string.multiplication)){
                Operator.MULTIPLICATION
            }else{
                Operator.DIVISION
            }

            //Do not need to pass total question because we have default argument
            val viewModel = quizQuestionViewModel(number = number, operator = operator)

            QuizQuestionsScreen(navController, userName, viewModel, openDialog, number, operatorArg)

        }
        //Results Screen Composable
        composable(
            "Results/{userName}/{correctAnswers}",
            arguments = listOf(
                navArgument("userName"){type = NavType.StringType},
                navArgument("correctAnswers"){type = NavType.StringType}
            )
        ){
            navBackStackEntry ->
            val userName = navBackStackEntry.arguments?.getString("userName").toString()
            val correctAnswers = navBackStackEntry.arguments?.getString("correctAnswers").toString()
            ResultsScreen(navController, userName, correctAnswers)
        }
        //History Table Composable
        composable("History"){

            HistoryScreen(navController)
        }
    }
}

