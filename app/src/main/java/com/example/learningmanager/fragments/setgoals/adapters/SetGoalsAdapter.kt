package com.example.learningmanager.fragments.setgoals.adapters

import android.animation.ObjectAnimator
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.learningmanager.databinding.SetGoalsRvItemsLayoutBinding
import com.example.learningmanager.fragments.setgoals.data.SetGoalsData
import java.text.DecimalFormat
import java.util.*
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
            cardViewSetGoals.setOnClickListener {
                item.expanded = !item.expanded
                val isExpanded = item.expanded
                expandableLayout.visibility = if (isExpanded) View.VISIBLE else View.GONE
                idTvStatusBarMain.visibility = if (isExpanded) View.GONE else View.VISIBLE
                if (isExpanded) {
                    expandableLayout.visibility = View.VISIBLE
                    idTvStatusBarMain.visibility = View.GONE
                    idTvItemNameMain.setGravity(Gravity.CENTER_HORIZONTAL)
                } else {
                    expandableLayout.visibility = View.GONE
                    idTvStatusBarMain.visibility = View.VISIBLE
                    idTvItemNameMain.setGravity(Gravity.NO_GRAVITY)
                }

            }

            idTvItemNameMain.text = item.goal
            idTvItemIntense.text = "${item.intenseGoal} min/day"
            idTvItemTimeGoal.text = "$daysLeft days left"
            idTvStatusBar.isIndeterminate = false
            idProgressText.text = percentageValue.roundToInt().toString() + " %"
            idTvStatusBar.progress = percentageValue.roundToInt()
            idTvStatusBarMain.progress = percentageValue.roundToInt()
            idTvStatusBarMain.isIndeterminate = false
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

    //TODO

//    private fun expandItem(holder: SetGoalsItemViewHolder, expand: Boolean, animate: Boolean) {
//
//        if (animate) {
//            val valueAnimator = ValueAnimator()
//
//
//
//            val animator = valueAnimator. getValueAnimator(
//                expand, listItemExpandDuration, AccelerateDecelerateInterpolator()
//            ) { progress -> setExpandProgress(holder, progress) }
//
//            if (expand) animator.doOnStart { holder.expandView.isVisible = true }
//            else animator.doOnEnd { holder.expandView.isVisible = false }
//
//            animator.start()
//        } else {
//
//            // show expandView only if we have expandedHeight (onViewAttached)
//            holder.expandView.isVisible = expand && expandedHeight >= 0
//            setExpandProgress(holder, if (expand) 1f else 0f)
//        }
//    }

//    ValueAnimator getValueAnimator() {
//    ValueAnimator anim = ValueAnimator.ofFloat(0, 1);
//    anim.addUpdateListener(new AnimatorUpdateListener() {
//        @Override
//        public void onAnimationUpdate(ValueAnimator animation) {
//            setShapeY((int) (animation.getAnimatedFraction() * (getHeight() - mShapeH)));
//        }
//    });
//    return anim;
}

