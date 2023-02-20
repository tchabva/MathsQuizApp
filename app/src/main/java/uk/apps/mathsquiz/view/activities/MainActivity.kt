package uk.apps.mathsquiz.view.activities

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import uk.apps.mathsquiz.R
import uk.apps.mathsquiz.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    //ViewBinding Variable
    private lateinit var mBinding: ActivityMainBinding

    //NavController Variable
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        //This hides the status bar
        fullscreen()

        //This sets up the Fragment host
        val navHostFragment =
            supportFragmentManager.findFragmentById(mBinding.navHostFragment.id) as NavHostFragment
         navController = navHostFragment.navController

    }

    //    hides status bar
    private fun fullscreen(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }else{
            @Suppress("DEPRECATION")
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }
}