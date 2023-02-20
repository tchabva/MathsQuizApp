package uk.apps.mathsquiz.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uk.apps.mathsquiz.R
import uk.apps.mathsquiz.databinding.ItemHistoryRowBinding
import uk.apps.mathsquiz.engine.model.entities.TestResult

class TestResultAdapter(private val fragment: Fragment): RecyclerView.Adapter<TestResultAdapter.ViewHolder>() {

    //Pass list here, as we use an observer that will notify the UI
    private var results: List<TestResult> = listOf()

    //ViewHolder describes the item view and metadata about its place within the RecyclerView
    class ViewHolder(view: ItemHistoryRowBinding): RecyclerView.ViewHolder(view.root) {
        //Holds the View that we will add each item to
        val ivDivisionLogo = view.ivDivisionLogo
        val ivMultiplyLogo = view.ivMultiplicationLogo
        val ivNumber = view.ivNumber
        val tvName = view.tvName
        val tvScore = view.tvResult
        val tvDate = view.tvDate
    }

    //This inflates the item views that is designed in the XML layout file
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemHistoryRowBinding = ItemHistoryRowBinding.inflate(LayoutInflater.from
            (fragment.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    //This binds each item in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = results[position]

        //This sets the visibility the logo based on the operator
        when(result.operator){
            holder.itemView.context.getString(R.string.division)->{
                holder.ivDivisionLogo.visibility = View.VISIBLE
                holder.ivMultiplyLogo.visibility = View.GONE
            }
            holder.itemView.context.getString(R.string.multiplication) ->{
                holder.ivDivisionLogo.visibility = View.GONE
                holder.ivMultiplyLogo.visibility = View.VISIBLE
            }
        }

        holder.ivNumber.visibility = View.VISIBLE

        //For the number
        when(result.number){
            holder.itemView.context.getString(R.string.number_1) ->{
                Glide.with(fragment)
                    .load(R.drawable.ic_number_1)
                    .into(holder.ivNumber)
            }
            holder.itemView.context.getString(R.string.number_2) ->{
                Glide.with(fragment)
                    .load(R.drawable.ic_number_2)
                    .into(holder.ivNumber)
            }
            holder.itemView.context.getString(R.string.number_3) ->{
                Glide.with(fragment)
                    .load(R.drawable.ic_number_3)
                    .into(holder.ivNumber)
            }
            holder.itemView.context.getString(R.string.number_4) ->{
                Glide.with(fragment)
                    .load(R.drawable.ic_number_4)
                    .into(holder.ivNumber)
            }
            holder.itemView.context.getString(R.string.number_5) ->{
                Glide.with(fragment)
                    .load(R.drawable.ic_number_5)
                    .into(holder.ivNumber)
            }
            holder.itemView.context.getString(R.string.number_6) ->{
                Glide.with(fragment)
                    .load(R.drawable.ic_number_6)
                    .into(holder.ivNumber)
            }
            holder.itemView.context.getString(R.string.number_7) ->{
                Glide.with(fragment)
                    .load(R.drawable.ic_number_7)
                    .into(holder.ivNumber)
            }
            holder.itemView.context.getString(R.string.number_8) ->{
                Glide.with(fragment)
                    .load(R.drawable.ic_number_8)
                    .into(holder.ivNumber)
            }
            holder.itemView.context.getString(R.string.number_9) ->{
                Glide.with(fragment)
                    .load(R.drawable.ic_number_9)
                    .into(holder.ivNumber)
            }
            holder.itemView.context.getString(R.string.number_10) ->{
                Glide.with(fragment)
                    .load(R.drawable.ic_number_10)
                    .into(holder.ivNumber)
            }
            holder.itemView.context.getString(R.string.number_11) ->{
                Glide.with(fragment)
                    .load(R.drawable.ic_number_11)
                    .into(holder.ivNumber)
            }
            holder.itemView.context.getString(R.string.number_12) ->{
                Glide.with(fragment)
                    .load(R.drawable.ic_number_12)
                    .into(holder.ivNumber)
            }
        }

        //Sets the name of user
        holder.tvName.text = result.name

        //Sets the score
        holder.tvScore.text = holder.itemView.context.getString(R.string.adapter_score, result.score)

        //Sets the date
        holder.tvDate.text = result.date
    }

    //Gets all of items in the list and therefore DB
    override fun getItemCount(): Int {
        return results.size
    }

    fun resultsList(list: List<TestResult>){
        results = list
        notifyDataSetChanged() //This will notify any observers that the data set has changed
    }
}