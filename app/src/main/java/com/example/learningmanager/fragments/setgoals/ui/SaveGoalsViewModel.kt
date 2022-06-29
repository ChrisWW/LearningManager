package com.example.learningmanager.fragments.setgoals.ui

import android.app.Application
import android.app.DatePickerDialog
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.viewModelScope
import com.example.learningmanager.MainActivity
import com.example.learningmanager.R
import com.example.learningmanager.base.ui.BaseViewModel
import com.example.learningmanager.fragments.setgoals.data.SetGoalsDataDetailsResponse
import com.example.learningmanager.fragments.setgoals.domain.AddGoalsItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SaveGoalsViewModel @Inject constructor(
    private val application: Application,
    private val addGoalsItemsUseCase: AddGoalsItemsUseCase
) : BaseViewModel() {
    val calendar = Calendar.getInstance()

    fun onBackNavigate() {
        navigateBack()
    }

    fun saveGoal(newSetGoalsData: SetGoalsDataDetailsResponse) {
        viewModelScope.launch {
            addGoalsItemsUseCase.create(newSetGoalsData)
        }
    }

    fun calendarGoal(textView: TextView, context: Context?) {
        textView.setOnClickListener {
            val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                
                val hoursLong: Long = 24
                val oneHourLong: Long = 2
                textView.text = ((TimeUnit.MILLISECONDS.toHours(countDaysFromNow(calendar.timeInMillis))+oneHourLong)/hoursLong).toString()
//                textView.text = SimpleDateFormat("dd-MM-yyyy").format(calendar.time)
            }
            DatePickerDialog(
                context!!,
                datePicker,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    fun showInfo(context: Context?) {
            val inflater: LayoutInflater = LayoutInflater.from(context)
            val view: View = inflater.inflate(R.layout.info_information, null)
            val builder = AlertDialog.Builder(context!!)
                .setView(view)
            val alert = builder.create()
            alert.show()
    }

    fun countDaysFromNow(goalDate: Long): Long {
        return goalDate - Calendar.getInstance().timeInMillis
    }

    fun saveInGoogleCalendar(goalTitle: String, contentResolver: ContentResolver) {
//        val intent = Intent(Intent.ACTION_EDIT, MainActivity::class.java)
//        val intent = Intent(context, MainActivity::class.java)
//        intent.type = "vnd.android.cursor.item/event"
//        intent.putExtra("endTime", calendar.timeInMillis)
//        intent.putExtra("time", true)
////        intent.putExtra("rrule", "FREQ=MONTHLY;COUNT=24;WKST=SU")
//        intent.putExtra("allDay", true)
//        intent.putExtra("title", goalTitle)
//        context!!.startActivity(intent)
        val calID: Long = 3
        val values = ContentValues().apply {
            put(CalendarContract.Events.DTSTART, Calendar.getInstance().timeInMillis)
            put(CalendarContract.Events.DTEND, calendar.timeInMillis)
            put(CalendarContract.Events.TITLE, "TestGOAL")
            put(CalendarContract.Events.DESCRIPTION, "TestDescirption")
            put(CalendarContract.Events.ALL_DAY, "true")
            put(CalendarContract.Events.CALENDAR_ID, calID)
            put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles")
        }
        val uri: Uri? = contentResolver.insert(CalendarContract.Events.CONTENT_URI, values)

// get the event ID that is the last element in the Uri
        val eventID: Long = uri!!.lastPathSegment!!.toLong()

    }

    fun sendTaskToEmail() {
//        val adresses = email.split(",".toRegex()).toTypedArray()

//        val intent = Intent(Intent.ACTION_SENDTO).apply {
//            data = Uri.parse("mailto:")
//            putExtra(Intent.EXTRA_EMAIL, adresses)
//            putExtra(Intent.EXTRA_SUBJECT, subject)
//            putExtra(Intent.EXTRA_TEXT, message)
//        }
    }
}