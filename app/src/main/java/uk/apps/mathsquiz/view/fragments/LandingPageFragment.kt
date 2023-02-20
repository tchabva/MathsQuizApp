package uk.apps.mathsquiz.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import uk.apps.mathsquiz.R
import uk.apps.mathsquiz.databinding.FragmentLandingPageBinding
import uk.apps.mathsquiz.utils.Constants


class LandingPageFragment : Fragment() {
    var mBinding: FragmentLandingPageBinding? = null

    var mOperation: String = ""
    var mUserName: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        mBinding = FragmentLandingPageBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return mBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //The args that transfer the username from the HomeFragment
        val args: LandingPageFragmentArgs by navArgs()
        mUserName = args.userName

        //Sets the text on the landing page including the entered username
        mBinding!!.tvLandingGreeting.text = getString(R.string.landing_page_txt, mUserName)

        //This enables the multiplication btn to take to the number table with multiplication rules
        mBinding!!.flMultiplication.setOnClickListener {
            mOperation = getString(R.string.multiplication)
            findNavController().navigate(
                LandingPageFragmentDirections
                    .actionLandingPageFragmentToNumberTableFragment(mUserName, mOperation)
            )
        }



        //This enables the division btn to take us to the number table with division rules
        mBinding!!.flDivision.setOnClickListener {
            mOperation = getString(R.string.division)
            findNavController().navigate(
                LandingPageFragmentDirections
                    .actionLandingPageFragmentToNumberTableFragment(mUserName, mOperation)
            )
        }
    }


    //good practice
    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }

}