package com.example.learningmanager.fragments.setgoals.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.learningmanager.databinding.SetGoalsRvItemsLayoutBinding
import com.example.learningmanager.fragments.setgoals.data.SetGoalsData
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*
import kotlin.math.exp
import kotlin.math.roundToInt

class SetGoalsAdapter(
    private val onItemDeleteClicked: (Int) -> Unit,
    private val onItemRootClicked: (Int) -> Unit
) : RecyclerView.Adapter<SetGoalsAdapter.SetGoalsItemViewHolder>()
{
    var items = emptyList<SetGoalsData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetGoalsItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SetGoalsRvItemsLayoutBinding.inflate(inflater, parent, false)
            .let(::SetGoalsItemViewHolder)
        setListData(items)


    }


    override fun onBindViewHolder(holder: SetGoalsItemViewHolder, position: Int): Unit=
        with(holder) {
            bind(items[position])
        }


    inner class SetGoalsItemViewHolder(
        private val layout: SetGoalsRvItemsLayoutBinding
    ) : RecyclerView.ViewHolder(layout.root) {
        fun bind(item: SetGoalsData) = with(layout) {
            val daysLeft = getDaysLeft(item.timeGoal, item.initialDate)
            val percentageValue = getProgressPercentages(item.timeGoal, item.initialDate)
            expandableLayout.visibility = View.GONE
            idTvItemNameMain.setOnClickListener {
                item.expanded = !item.expanded
                val isExpanded = item.expanded
                expandableLayout.visibility = if (isExpanded) View.VISIBLE else View.GONE
            }

            idTvItemNameMain.text = item.goal
            idTvItemIntense.text = "${item.intenseGoal} min/day"
            idTvItemTimeGoal.text = "$daysLeft days left"
            idTvStatusBar.isIndeterminate = false
            idProgressText.text = percentageValue.roundToInt().toString() + " %"
            idTvStatusBar.progress = percentageValue.roundToInt()

        }

    }


    override fun getItemCount(): Int {
        return items.size
    }

    //fix?
    fun getDaysLeftSecond(daysLeft: String): String {
        //Calendar set to the current date
        //Calendar set to the current date
        val calendar: Calendar = Calendar.getInstance()
//rollback 90 days
//rollback 90 days
        calendar.add(Calendar.DAY_OF_YEAR, -daysLeft.toInt())
//now the date is 90 days back
//now the date is 90 days back
        Log.d("MyApp", "90 days ago:" + calendar.time.toString())

        return calendar.time.toString()
    }

    fun getDaysLeft(timeGoalDays: String, initData: String): String {

        val oneDayEpoch = 86400.toDouble()
        val now = Calendar.getInstance().timeInMillis / 1000
        val endData = (initData.toDouble() + timeGoalDays.toDouble()*oneDayEpoch)
        val days = ((endData - now) / (oneDayEpoch))
//        val currentData = SimpleDateFormat.getInstance().format(Date())
//        val date = SimpleDateFormat("dd-MM")
//        val daysRoundOff = ((days*100).roundToInt() ) / 100

        val df = DecimalFormat("#.##")
//        df.roundingMode = RoundingMode.UP
        val daysRoundOff = df.format(days)


        Log.d("days", "now: $now - endData: $endData -- days: $days and roundoff: $daysRoundOff --- timeGoalDays: $timeGoalDays")

        return if ((days.toInt()) >= timeGoalDays.toInt() || days.toInt() <= 0) {
            "0"
        } else {
            Log.d("days", "VLAUE: $daysRoundOff and $days")
            daysRoundOff.toString()
        }
    }

    fun setListData(list: List<SetGoalsData>) {
        this.items = emptyList()
        this.items = list
        notifyDataSetChanged()
    }

    fun getProgressPercentages(timeGoalDays: String, initData: String) : Double {
        val oneDayEpoch = 86400.toDouble()
        val now = Calendar.getInstance().timeInMillis / 1000
        val endData = (initData.toDouble() + timeGoalDays.toDouble()*oneDayEpoch)
        val days = ((endData - now) / (oneDayEpoch))

        val percentageValue = ((timeGoalDays.toDouble() - days.toDouble())/timeGoalDays.toDouble())*100
        Log.d("value", "percantage $percentageValue")
        Log.d("value", "END DATA $endData")
        Log.d("value", "now $now")
        // epoch

        if (percentageValue >= 100) {
            return 100.toDouble()
        }

        return percentageValue
    }

}