package uk.apps.mathsquiz.view.fragments

import android.app.Dialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.icu.util.ULocale
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import uk.apps.mathsquiz.R
import uk.apps.mathsquiz.databinding.FragmentQuizQuestionsBinding
import uk.apps.mathsquiz.databinding.QuizQuestionDialogBinding
import uk.apps.mathsquiz.engine.model.entities.NewQuestion
import uk.apps.mathsquiz.viewmodel.MathsQuizResultViewModel


class QuizQuestionsFragment : Fragment() {

    //ViewBinding variable
    private var mBinding: FragmentQuizQuestionsBinding? = null

    //Dialog Binding Variable
    private lateinit var dialogBinding: QuizQuestionDialogBinding
    private lateinit var mCustonDialog: Dialog

    //Quiz Variables
    private var mOperation: String = ""
    private var mUserName: String = ""
    private var mNumber: Int = 0
    private var mCurrentPosition: Int = 1
    lateinit var mQuestionList: ArrayList<NewQuestion>
    private var mCorrectAnswers:Int = 0
    private var mDate: String = ""

    //Initializing the ViewModel
    private val mMathsQuizResultViewModel: MathsQuizResultViewModel by viewModel<MathsQuizResultViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentQuizQuestionsBinding.inflate(inflater, container, false)
        return mBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Initiates the get date function
        getDate()

        //Retrieving the arguments from the NumberTableFragment
        val args: QuizQuestionsFragmentArgs by navArgs()

        mUserName = args.mUserName
        mOperation = args.mOperation
        mNumber = args.number.toInt()

        //This generates the question list based on the number and the operation
        mQuestionList = generateQuestion(mNumber, mOperation)

        //This sets the first question of the quiz
        setQuestion()

        mBinding!!.btnSubmit.setOnClickListener{onClickSubmit()}

    }

    //Using the variables from the number table this function will generate the list of questions
    private fun generateQuestion(number: Int, operation: String): ArrayList<NewQuestion>{

        val questionsList = ArrayList<NewQuestion>()

        if (operation == getString(R.string.multiplication)){
            //This generates questions for times tables
            for (i in 1..12){
                val generatedQuestion = "${number}×$i = ?"
                val correctAnswer = number*i
                val generatedAnswer = "${number}×$i = $correctAnswer"

                questionsList.add(NewQuestion(i,generatedQuestion,correctAnswer,generatedAnswer))
            }


        }else if (operation == getString(R.string.division)){
            //This generates questions for the divisions
            for (i in 1..12){
                val multipliedNumber = number*i
                val generatedQuestion = "$multipliedNumber÷$number = ?"
                val generatedAnswer = "$multipliedNumber÷$number = $i"

                questionsList.add(NewQuestion(i,generatedQuestion,i,generatedAnswer))
            }

        }
        questionsList.shuffle()
        return questionsList
    }

    //This function sets the question
    private fun setQuestion(){

        //This sets Answer Box Edit Text to focusable
        mBinding?.etAnswerBox?.isFocusable = true
        mBinding?.etAnswerBox?.isFocusableInTouchMode = true
        mBinding?.etAnswerBox?.requestFocus()

        //Because Arrays start counting at 0, the position in the Array and the actual position
        //Are off by one
        val question = mQuestionList[mCurrentPosition-1]

        //This set the progress Bar number and text to match the current question
        mBinding!!.progressBar.progress = mCurrentPosition
        mBinding!!.tvProgress.text = "$mCurrentPosition/${mBinding!!.progressBar.max}"

        //This sets the question textView to the relevant question
        mBinding!!.tvQuestion.text = question.question
    }

    //Controls the actions when the Submit button is pressed
    private fun onClickSubmit() {

        if (mBinding!!.btnSubmit.text.toString() == getString(R.string.submit_btn_text)){

            val question = mQuestionList[mCurrentPosition-1]

            //The code for when a correct answer is submitted
            if (question.correctAnswer == mBinding!!.etAnswerBox.text.toString().toIntOrNull()){
                //This makes the Answer Box Green when a correct answer is submitted
                answerBoxView(R.drawable.correct_option_border)
                //This changes the Quiz question to the correct answer
                mBinding!!.tvQuestion.text = question.generatedAnswer
                //This increases the correct Answers variable by one
                mCorrectAnswers++

                //This sets Answer Box Edit Text to non focusable
                mBinding?.etAnswerBox?.isFocusable = false
                mBinding?.etAnswerBox?.isFocusableInTouchMode = false
            }else {
                //This makes the Answer Box red when an incorrect answer is submitted
                answerBoxView(R.drawable.wrong_option_border)
                //This changes the Quiz question to the correct answer
                mBinding!!.tvQuestion.text = question.generatedAnswer

                //This sets Answer Box Edit Text to non focusable
                mBinding?.etAnswerBox?.isFocusable = false
                mBinding?.etAnswerBox?.isFocusableInTouchMode = false

            }

            //Checks on the current position of quiz relative to the total amount of questions
            if (mCurrentPosition < mQuestionList.size){
                //If the are still questions left the text is changed "Next Question"
                mBinding!!.btnSubmit.text = getString(R.string.btn_next_question_txt)
            }else {
                //If there are no questions left the submit button text will change to Finish
                mBinding!!.btnSubmit.text = getString(R.string.btn_finish_txt)
            }
            //The logic when the submit btn text is "Next Question"
        } else if (mBinding!!.btnSubmit.text == getString(R.string.btn_next_question_txt)){

            //This moves our quiz position onto the next question
            mCurrentPosition++

            //This sets the next question
            setQuestion()
            mBinding!!.etAnswerBox.text = null
            //Resets the answer box colour from reg/green
            answerBoxView(R.drawable.default_option_border_bg)
            //Changes the btn text back to submit
            mBinding!!.btnSubmit.text = getString(R.string.submit_btn_text)
        } else{

            //The allows the data to be inserted into the DB
            insertTestResult()

            //This moves over to the results page and transfers over the args
            findNavController()
                .navigate(QuizQuestionsFragmentDirections
                    .actionQuizQuestionsFragmentToResultsFragment(
                        mUserName,
                        mCorrectAnswers.toString()
                    )
                )
        }
    }

    //This function controls the colour of the answer box
    private fun answerBoxView(drawableView: Int){
        mBinding!!.etAnswerBox.setBackgroundResource(drawableView)
    }

    //Gets the date of the system
    private fun getDate() {

        //Create a DateFormatter for displaying date in specified format.
        val formatter = SimpleDateFormat("dd/MM/YYYY", ULocale.getDefault())

        val calendar = Calendar.getInstance() //Get the current instance from the calendar
        val dateTime = calendar.time //get the date and time of the system

        mDate = formatter.format(dateTime)
    }

    //This function inserts the Test result and its appropriate data in to the DB
    private fun insertTestResult(){

        val id = 0

        mMathsQuizResultViewModel.saveTestResult(
            mNumber.toString(),
            mOperation,
            mUserName,
            mCorrectAnswers.toString(),
            mDate,
            id
        )
        Log.i("Insertion","success $mUserName")
    }

    //Quiz Question Fragment Dialog
    private fun quizQuestionDialog(){

        //Binding the view
        dialogBinding = QuizQuestionDialogBinding.inflate(layoutInflater)

        mCustonDialog = Dialog(requireActivity())
        //This inflates the XML
        mCustonDialog.setContentView(dialogBinding.root)

        //If you click NO, the Dialog will be dismissed and you can continue with the quiz
        dialogBinding.btnNo.setOnClickListener {
            mCustonDialog.dismiss()
        }

        //Will take you back to the LandingPage Fragment with your username
        dialogBinding.btnYes.setOnClickListener {
            findNavController().navigate(QuizQuestionsFragmentDirections.actionQuizQuestionsFragmentToLandingPageFragment(mUserName))
            mCustonDialog.dismiss()
        }

        //Ensure the dialog shows when the function is
        mCustonDialog.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This callback will only be called when MyFragment is at least Started.
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
           quizQuestionDialog()
        }
        callback.isEnabled
    }

    //good practice
    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }

}

