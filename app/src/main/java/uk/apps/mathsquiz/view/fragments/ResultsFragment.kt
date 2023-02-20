package uk.apps.mathsquiz.view.fragments

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.icu.util.ULocale
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import uk.apps.mathsquiz.R
import uk.apps.mathsquiz.databinding.FragmentResultsBinding
import uk.apps.mathsquiz.viewmodel.MathsQuizResultViewModel

class ResultsFragment : Fragment() {
    var mBinding: FragmentResultsBinding? = null

    private val mMathsQuizResultViewModel: MathsQuizResultViewModel by viewModel<MathsQuizResultViewModel>()

    //Quiz Variables
    private var mUserName: String = ""
    private var mCorrectAnswers: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentResultsBinding.inflate(inflater, container, false)
        return mBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //allocates the args to variables within the Fragment
        val args: ResultsFragmentArgs by navArgs()
        mUserName = args.mUserName
        mCorrectAnswers = args.mCorrectAnswers

        //This activates the result activity text function
        resultActivityText()


        //Sends the User back to the landing screen with the same username
        mBinding!!.btnPlayAgain.setOnClickListener {
            findNavController().navigate(ResultsFragmentDirections.actionResultsFragmentToLandingPageFragment(mUserName))
        }

        //This sends the user back to the home, to enter a new username
        mBinding!!.btnHome.setOnClickListener {
            findNavController().navigate(ResultsFragmentDirections.actionResultsFragmentToHomeFragment())
        }

        //Sends the user to the History fragment
        mBinding!!.btnHistory.setOnClickListener {
            findNavController().navigate(ResultsFragmentDirections.actionResultsFragmentToHistoryFragment())
        }
    }

    //The function dictates the content of the results textView based on the user's score
    private fun resultActivityText(){
        //This sets the text and the ImageView on the results fragment based on the results
        when(mCorrectAnswers.toInt()){
            0 -> {mBinding!!.tvCongratsText.text = getString(R.string.results_0_text)
                Glide.with(this)
                    .load(R.drawable.ic_results_0)
                    .into(mBinding!!.ivResult)
            }
            in 1..4 ->{mBinding!!.tvCongratsText.text = getString(R.string.results_1_4_text)
                Glide.with(this)
                    .load(R.drawable.ic_results_1_4)
                    .into(mBinding!!.ivResult)
            }
            in 5..7 ->{mBinding!!.tvCongratsText.text = getString(R.string.results_5_7_text)
                Glide.with(this)
                    .load(R.drawable.ic_results_5_7)
                    .into(mBinding!!.ivResult)
            }
            in 8..9 ->{mBinding!!.tvCongratsText.text = getString(R.string.results_8_9_text)
                Glide.with(this)
                    .load(R.drawable.ic_results_8_9)
                    .into(mBinding!!.ivResult)
            }
            in 10..11 ->{mBinding!!.tvCongratsText.text = getString(R.string.results_10_11_text)
                Glide.with(this)
                    .load(R.drawable.ic_results_10_11)
                    .into(mBinding!!.ivResult)
            }
            12 -> {mBinding!!.tvCongratsText.text = getString(R.string.results_12_text)
                Glide.with(this)
                    .load(R.drawable.ic_trophy)
                    .into(mBinding!!.ivResult)
            }
            else -> {mBinding!!.tvCongratsText.text = ""
                Glide.with(this)
                    .load(R.drawable.ic_results_1_4)
                    .into(mBinding!!.ivResult)
            }
        }
        //This sets the user's entered name to the TextView
        mBinding!!.tvUsername.text = mUserName
        //This provides the user with there score result
        mBinding!!.tvQuizScore.text = getString(R.string.result_score, mCorrectAnswers)
    }

    //good practice
    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }

}