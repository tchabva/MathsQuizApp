package uk.apps.mathsquiz.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import uk.apps.mathsquiz.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    //binding variable
    private var mBinding: FragmentHomeBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return mBinding!!.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Start button instructions and args
        mBinding!!.btnStart.setOnClickListener {
            val etUserName = mBinding!!.etName.text

            if (etUserName.toString().isBlank()){
                Toast.makeText(activity,
                    "Please enter your name to continue",
                    Toast.LENGTH_SHORT
                ).show()
            }else{
                val userName = etUserName.toString()
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLandingPageFragment(
                    userName
                ))
            }
        }

        mBinding!!.btnHistory.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToHistoryFragment())
        }
    }





    //good practice
    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }
}