package uk.apps.mathsquiz.view.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import uk.apps.mathsquiz.R
import uk.apps.mathsquiz.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    //Fragment binding variable
    private var mBinding: FragmentSplashBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentSplashBinding.inflate(inflater, container, false)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val splashAnimation = AnimationUtils.loadAnimation(requireActivity(),R.anim.anim_splash)
        mBinding?.ivLogo?.animation = splashAnimation

        //Set an animation listener
        splashAnimation.setAnimationListener(object: Animation.AnimationListener{

            //Runs on the when animation is started
            override fun onAnimationStart(animation: Animation?) {
               //Not needed
            }
            //Runs after the animation is finished
            override fun onAnimationEnd(animation: Animation?) {
                //postDelay allows us to execute the code after a delay in milliseconds
                Handler(Looper.getMainLooper()).postDelayed({
                    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
                }, 1000
                )
            }
            //Code runs after if you need the animation to run again
            override fun onAnimationRepeat(animation: Animation?) {
                //Not needed
            }

        })
    }

    //good practice
    override fun onDestroy() {
        //On the fragment being destroyed the variables are set back to null
        super.onDestroy()
        mBinding = null
    }
}