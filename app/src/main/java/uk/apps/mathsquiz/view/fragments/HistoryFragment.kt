package uk.apps.mathsquiz.view.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_history_row.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import uk.apps.mathsquiz.R
import uk.apps.mathsquiz.databinding.FragmentHistoryBinding
import uk.apps.mathsquiz.databinding.ItemNumberFilterListBinding
import uk.apps.mathsquiz.databinding.ItemOperatorFilterCustomListBinding
import uk.apps.mathsquiz.utils.Constants
import uk.apps.mathsquiz.view.adapters.TestResultAdapter
import uk.apps.mathsquiz.viewmodel.MathsQuizResultViewModel

class HistoryFragment : Fragment() {
    //Fragment binding Variable
    private var mBinding: FragmentHistoryBinding? = null

    //Dialog binding Variables
    private lateinit var operatorBinding: ItemOperatorFilterCustomListBinding
    private lateinit var numberBinding: ItemNumberFilterListBinding
    private lateinit var mFilterListDialog: Dialog

    //RecyclerView adapter Variables
    private lateinit var mTestResultAdapter: TestResultAdapter

    //Number and Operator variables
    private var mOperator: String? = Constants.ALL_OPERATORS
    private var mNumber: String? = Constants.ALL_NUMBERS

    //Menu Items variables
    private lateinit var menuOperator: MenuItem
    private lateinit var menuNumber: MenuItem
    private lateinit var menuClear: MenuItem


    //ViewModel
    private val mMathsQuizResultViewModel: MathsQuizResultViewModel by viewModel<MathsQuizResultViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentHistoryBinding.inflate(inflater, container, false)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Toolbar initializer
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_history)
        val navHostFragment = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController(toolbar, navHostFragment)

        //For the MenuOptions
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { view -> view.findNavController().navigateUp() }

        //Setup the Layout Manager
        mBinding!!.rvTestResults.layoutManager = LinearLayoutManager(requireActivity())

        //Setup the Adapter
        mTestResultAdapter = TestResultAdapter(this@HistoryFragment)

        //Associate the recyclerView to the test result
        mBinding!!.rvTestResults.adapter = mTestResultAdapter

        //If there is data in the DB the the view made visible
        mMathsQuizResultViewModel.allResultsList.observe(viewLifecycleOwner){
                results ->
            results?.let {
                if(it.isNotEmpty()){
                    //This will populate the RecyclerView if the DB is not empty
                    mBinding!!.rvTestResults.visibility = View.VISIBLE
                    mBinding!!.tvNoData.visibility = View.GONE
                    //Assigns our RecyclerView
                    mTestResultAdapter.resultsList(it)
                }else{
                    //Will Display the TV indicating that the DB is empty
                    mBinding!!.rvTestResults.visibility = View.GONE
                    mBinding!!.tvNoData.visibility = View.VISIBLE
                }
            }
            clearFilterVisibility()
        }
    }
    //Ensure the onCreate/onViewCreated function have setHasOptionsMenu(true)
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_history_filters, menu)

        menuNumber = menu.findItem(R.id.action_filter_number)
        menuClear = menu.findItem(R.id.action_filter_clear)
        menuOperator = menu.findItem(R.id.action_filter_operators)

        clearFilterVisibility()
    }

    //Function allows us to click on the items in the toolbar
    @SuppressLint("ResourceType")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            menuOperator.itemId ->{
                filterOperationDialog(item) //Pass the item so it can changed
                return true
            }
            R.id.action_filter_number->{
                filterNumberDialog(item)  //Pass the item so it can changed
                return true
            }
            menuClear.itemId ->{
                activity?.invalidateOptionsMenu()
                clearFilters(item)
                return true
            }
        }
        return super.onOptionsItemSelected(item)  //Pass the item so it can changed
    }

    //Dialog for the number Filter
    private fun filterNumberDialog(item: MenuItem) {
        //Initiate the Dialog for the filter
        mFilterListDialog = Dialog(requireActivity())
        //Inflate the dialog XML
        numberBinding = ItemNumberFilterListBinding.inflate(layoutInflater)
        mFilterListDialog.setContentView(numberBinding.root)

//        activity?.invalidateOptionsMenu()

        //The All Numbers button
        numberBinding.tvAllNumbers.setOnClickListener {
            filterSelectedNumber(Constants.ALL_NUMBERS, item)
        }
        //Number 1
        numberBinding.ivNumberOne.setOnClickListener {
            mNumber = numberBinding.ivNumberOne.contentDescription.toString()
            mOperator = getString(R.string.multiplication)
            filterSelectedNumber(mNumber, item)
        }
        //Number 2
        numberBinding.ivNumberTwo.setOnClickListener {
            mNumber = numberBinding.ivNumberTwo.contentDescription.toString()
            filterSelectedNumber(mNumber, item)
        }
        //Number 3

        numberBinding.ivNumberThree.setOnClickListener {
            mNumber = numberBinding.ivNumberThree.contentDescription.toString()
            filterSelectedNumber(mNumber, item)
        }
        //Number 4
        numberBinding.ivNumberFour.setOnClickListener {
            mNumber = numberBinding.ivNumberFour.contentDescription.toString()
            filterSelectedNumber(mNumber, item)
        }
        //Number 5
        numberBinding.ivNumberFive.setOnClickListener {
            mNumber = numberBinding.ivNumberFive.contentDescription.toString()
            filterSelectedNumber(mNumber, item)
        }
        //Number 6
        numberBinding.ivNumberSix.setOnClickListener {
            mNumber = numberBinding.ivNumberSix.contentDescription.toString()
            filterSelectedNumber(mNumber, item)
        }
        //Number 7
        numberBinding.ivNumberSeven.setOnClickListener {
            mNumber = numberBinding.ivNumberSeven.contentDescription.toString()
            filterSelectedNumber(mNumber, item)
        }
        //Number 8
        numberBinding.ivNumberEight.setOnClickListener {
            mNumber = numberBinding.ivNumberEight.contentDescription.toString()
            filterSelectedNumber(mNumber, item)
        }
        //Number 9
        numberBinding.ivNumberNine.setOnClickListener {
            mNumber = numberBinding.ivNumberNine.contentDescription.toString()
            filterSelectedNumber(mNumber, item)
        }
        //Number 10
        numberBinding.ivNumberTen.setOnClickListener {
            mNumber = numberBinding.ivNumberTen.contentDescription.toString()
            filterSelectedNumber(mNumber, item)
        }
        //Number 11
        numberBinding.ivNumberEleven.setOnClickListener {
            mNumber = numberBinding.ivNumberEleven.contentDescription.toString()
            filterSelectedNumber(mNumber, item)
        }
        //Number 12
        numberBinding.ivNumberTwelve.setOnClickListener {
            mNumber = numberBinding.ivNumberTwelve.contentDescription.toString()
            filterSelectedNumber(mNumber, item)
        }

        //Necessary for the dialog to show when run
        mFilterListDialog.show()
    }

    //Function for selecting the number
    private fun filterSelectedNumber(filterNumber: String?, item: MenuItem) {
        //Dismiss the dialog
        mFilterListDialog.dismiss()
        //Log cat for verification
        Log.i("Filter Operation", filterNumber.toString())

        //If there is no filter applied all the data in the DB will show
        if (filterNumber == Constants.ALL_NUMBERS) {
            mMathsQuizResultViewModel.getFilterNumberList(null)
            //This selects the number filter icon to the default icon
            menuNumber.setIcon(R.drawable.ic_filter_alt)
            clearFilterVisibility()
        } else {
            //For when we select a Filter Number
            mMathsQuizResultViewModel.getFilterNumberList(filterNumber)
            //This selects the number filter icon to the selected number
            when(filterNumber){
                getString(R.string.number_1) ->{
                    menuNumber.setIcon(R.drawable.ic_number_1)
                }
                getString(R.string.number_2) ->{
                    item.setIcon(R.drawable.ic_number_2)
                }
                getString(R.string.number_3) ->{
                    item.setIcon(R.drawable.ic_number_3)
                }
                getString(R.string.number_4) ->{
                    item.setIcon(R.drawable.ic_number_4)
                }
                getString(R.string.number_5) ->{
                    item.setIcon(R.drawable.ic_number_5)
                }
                getString(R.string.number_6) ->{
                    item.setIcon(R.drawable.ic_number_6)
                }
                getString(R.string.number_7) ->{
                    item.setIcon(R.drawable.ic_number_7)
                }
                getString(R.string.number_8) ->{
                    item.setIcon(R.drawable.ic_number_8)
                }
                getString(R.string.number_9) ->{
                    item.setIcon(R.drawable.ic_number_9)
                }
                getString(R.string.number_10) ->{
                    item.setIcon(R.drawable.ic_number_10)
                }
                getString(R.string.number_11) ->{
                    item.setIcon(R.drawable.ic_number_11)
                }
                getString(R.string.number_12) ->{
                    item.setIcon(R.drawable.ic_number_12)
                }
            }
            //This makes the clear filters cross appear when any filter is selected
//            menuClear.isVisible = true
            clearFilterVisibility()
        }
    }

    //Function for the filter list dialog
    private fun filterOperationDialog(item: MenuItem) {
        //Initiate the Dialog for the filter
        mFilterListDialog = Dialog(requireActivity())
        //Inflate the dialog XML
        operatorBinding = ItemOperatorFilterCustomListBinding.inflate(layoutInflater)
        mFilterListDialog.setContentView(operatorBinding.root)

//        activity?.invalidateOptionsMenu()

        //The all operators button
        operatorBinding.tvAllOperators.setOnClickListener {
            filterSelectedOperation(Constants.ALL_OPERATORS, item)
        }

        //filters the test results by divisions
        operatorBinding.ivDivisionLogo.setOnClickListener {

            mOperator = operatorBinding.ivDivisionLogo.contentDescription.toString()
            filterSelectedOperation(mOperator!!, item)
        }

        //Filters the test results by multiplications
        operatorBinding.ivMultiplicationLogo.setOnClickListener {
            mOperator = operatorBinding.ivMultiplicationLogo.contentDescription.toString()
            filterSelectedOperation(mOperator!!,item)
            R.id.action_filter_operators.toDrawable()
        }

        //Otherwise the Dialog does not show
        mFilterListDialog.show()
    }

    //This function runs the filter operation method
    private fun filterSelectedOperation(filterOperation: String?, item: MenuItem) {
        //Dismiss the dialog
        mFilterListDialog.dismiss()

        //Log cat for verification
        Log.i("Filter Operation", filterOperation.toString())

        //If there is no filter applied all the data in the DB will show
        if (filterOperation == Constants.ALL_OPERATORS) {
            mMathsQuizResultViewModel.getFilterOperatorList(null)
            //If the
            item.setIcon(R.drawable.ic_filter_list)
            clearFilterVisibility()
        } else {
            //For when we a select a Filter Operation
            mMathsQuizResultViewModel.getFilterOperatorList(filterOperation)

            //This will set the OptionsMenu item icon based on the selected Operator
            when (filterOperation) {
                getString(R.string.multiplication) -> {
                    item.setIcon(R.drawable.ic_multiplication_logo)
                }
                getString(R.string.division) -> {
                    item.setIcon(R.drawable.ic_division_logo)
                }
                else -> {
                    item.setIcon(R.drawable.ic_filter_list)
                }
            }
            //This makes the clear filters cross appear when any filter is selected
//            menuClear.isVisible = true
            clearFilterVisibility()
        }

    }

    private fun clearFilterVisibility(){
        mOperator
        mNumber
        //This will remove the filter if there are no filters selected
        menuClear.isVisible =
            !(mOperator == Constants.ALL_OPERATORS && mNumber == Constants.ALL_NUMBERS)

    }

    //This fun will clear the filters
    private fun clearFilters(item: MenuItem) {
        mOperator = Constants.ALL_OPERATORS
        mNumber = Constants.ALL_NUMBERS
        filterSelectedNumber(mNumber, item)
        filterSelectedOperation(mOperator, item)
        item.isVisible = false

    }

    //good practice
    override fun onDestroy() {
        //On the fragment being destroyed the variables are set back to null
        super.onDestroy()
        mBinding = null
        mNumber = null
        mOperator = null
    }
}