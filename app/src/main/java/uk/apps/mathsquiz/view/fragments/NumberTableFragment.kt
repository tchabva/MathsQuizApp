package uk.apps.mathsquiz.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import uk.apps.mathsquiz.R
import uk.apps.mathsquiz.databinding.FragmentNumberTableBinding


class NumberTableFragment : Fragment() {

    var mBinding: FragmentNumberTableBinding? = null

    var mOperation: String = ""
    var mUserName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentNumberTableBinding.inflate(inflater, container, false)
        return mBinding!!.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: NumberTableFragmentArgs by navArgs()

        mOperation = args.mOperation
        mUserName = args.mUserName


        if (mOperation == getString(R.string.multiplication)) {
            //This sets the title to Multiplication
            mBinding!!.llMultiplicationTitle.visibility = View.VISIBLE
            mBinding!!.llDivisionTitle.visibility = View.GONE

        } else if (mOperation == getString(R.string.division)) {
            //This sets the title to Division
            mBinding!!.llMultiplicationTitle.visibility = View.GONE
            mBinding!!.llDivisionTitle.visibility = View.VISIBLE
        }

        //Initiating the Quiz for the relevant number and operation
        mBinding!!.btnOne.setOnClickListener {initiateQuiz(mBinding!!.btnOne.contentDescription.toString(),mOperation)}
        mBinding!!.btnTwo.setOnClickListener {initiateQuiz(mBinding!!.btnTwo.contentDescription.toString(),mOperation)}
        mBinding!!.btnThree.setOnClickListener {initiateQuiz(mBinding!!.btnThree.contentDescription.toString(),mOperation)}
        mBinding!!.btnFour.setOnClickListener {initiateQuiz(mBinding!!.btnFour.contentDescription.toString(),mOperation)}
        mBinding!!.btnFive.setOnClickListener {initiateQuiz(mBinding!!.btnFive.contentDescription.toString(),mOperation)}
        mBinding!!.btnSix.setOnClickListener {initiateQuiz(mBinding!!.btnSix.contentDescription.toString(),mOperation)}
        mBinding!!.btnSeven.setOnClickListener {initiateQuiz(mBinding!!.btnSeven.contentDescription.toString(),mOperation)}
        mBinding!!.btnEight.setOnClickListener {initiateQuiz(mBinding!!.btnEight.contentDescription.toString(),mOperation)}
        mBinding!!.btnNine.setOnClickListener {initiateQuiz(mBinding!!.btnNine.contentDescription.toString(),mOperation)}
        mBinding!!.btnTen.setOnClickListener {initiateQuiz(mBinding!!.btnTen.contentDescription.toString(),mOperation)}
        mBinding!!.btnEleven.setOnClickListener {initiateQuiz(mBinding!!.btnEleven.contentDescription.toString(),mOperation)}
        mBinding!!.btnTwelve.setOnClickListener {initiateQuiz(mBinding!!.btnTwelve.contentDescription.toString(),mOperation)}
    }


    //This function initiates the quiz for the selected number and operation
    private fun initiateQuiz(number: String, operation: String){
        findNavController().navigate(NumberTableFragmentDirections.actionNumberTableFragmentToQuizQuestionsFragment(mUserName,number,operation))
    }

    //good practice
    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }

}