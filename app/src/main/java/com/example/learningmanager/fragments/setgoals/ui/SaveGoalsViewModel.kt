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
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewModelScope
import com.example.learningmanager.MainActivity
import com.example.learningmanager.R
import com.example.learningmanager.base.ui.BaseViewModel
import com.example.learningmanager.fragments.setgoals.data.SetGoalsDataDetailsResponse
import com.example.learningmanager.fragments.setgoals.domain.AddGoalsItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SaveGoalsViewModel @Inject constructor(
    @ApplicationContext contextApp: Context,
    private val application: Application,
    private val addGoalsItemsUseCase: AddGoalsItemsUseCase
) : BaseViewModel() {
    val calendar = Calendar.getInstance()
    val context = contextApp

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
                textView.text =
                    ((TimeUnit.MILLISECONDS.toHours(countDaysFromNow(calendar.timeInMillis)) + oneHourLong) / hoursLong).toString()
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

    fun saveInGoogleCalendar(
        activity: FragmentActivity,
        goalTitle: String,
//        contentResolver: ContentResolver
    ) {
//        val intent = Intent(activity: MainActivity, MainActivity::class.java)
//        activity as? MainActivity)?.let{
//            val intent = Intent (it, Main::class.java)
//            it.startActivity(intent)
//        }

//        activity.run {

//         WORKS
//        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        val intent = Intent(Intent.ACTION_EDIT)
        intent.type = "vnd.android.cursor.item/event"
        intent.putExtra("endTime", calendar.timeInMillis)
        intent.putExtra("time", true)
//        intent.putExtra("rrule", "FREQ=MONTHLY;COUNT=24;WKST=SU")
        intent.putExtra("allDay", true)
        intent.putExtra("title", goalTitle)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        MainActivity.addGoogleResultCode = true
        MainActivity.addGoogleSaveAndNavigate = true
        activity.startActivity(intent)
//        activity.finish()

//        MainActivity.addGoogleResultCode = true
//        MainActivity.addGoogleSaveAndNavigate = true
//        context!!.startActivity(intent)

        // SILENT ale nie wysyła
        // TODO
//        val calID: Long = 3
//        val values = ContentValues().apply {
//            put(CalendarContract.Events.DTSTART, Calendar.getInstance().timeInMillis)
//            put(CalendarContract.Events.DTEND, calendar.timeInMillis)
//            put(CalendarContract.Events.TITLE, goalTitle)
//            put(CalendarContract.Events.DESCRIPTION, "TestDescirption")
//            put(CalendarContract.Events.ALL_DAY, "true")
//            put(CalendarContract.Events.CALENDAR_ID, calID)
//            put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles")
//        }
//        val uri: Uri? = contentResolver.insert(CalendarContract.Events.CONTENT_URI, values)
//
//// get the event ID that is the last element in the Uri
//        val eventID: Long = uri!!.lastPathSegment!!.toLong()


        //
//        val cr: ContentResolver = activity.contentResolver
//        val values = ContentValues()
//
//        values.put(CalendarContract.Events.DTSTART, dtstart)
//        values.put(CalendarContract.Events.TITLE, title)
//        values.put(CalendarContract.Events.DESCRIPTION, comment)
//
//        val timeZone = TimeZone.getDefault()
//        values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.id)
//
//// Default calendar
//
//// Default calendar
//        values.put(CalendarContract.Events.CALENDAR_ID, 1)
//
//        values.put(
//            CalendarContract.Events.RRULE, "FREQ=DAILY;UNTIL="
//                    + dtUntill
//        )
//// Set Period for 1 Hour
//// Set Period for 1 Hour
//        values.put(CalendarContract.Events.DURATION, "+P1H")
//
//        values.put(CalendarContract.Events.HAS_ALARM, 1)
//
//// Insert event to calendar
//
//// Insert event to calendar
//        val uri2 = cr.insert(CalendarContract.Events.CONTENT_URI, values)

    }

    fun sendTaskToEmail(activity: FragmentActivity, email: String, subject: String, message: String) {
//        val adresses = email.split(",".toRegex()).toTypedArray()
//        val sendMail = Intent(Intent.ACTION_SENDTO)
//            Intent(Intent.ACTION_SENDTO).apply {
//            data = Uri.parse("mailto:")
//            putExtra(Intent.EXTRA_EMAIL, email)
//            putExtra(Intent.EXTRA_SUBJECT, subject)
//            putExtra(Intent.EXTRA_TEXT, message)
//            intent.setSelector( sendMail )
//        }
        val sendMail = Intent(Intent.ACTION_SENDTO)
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, email)
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, message)
        intent.selector = sendMail
        activity.startActivity(Intent.createChooser(intent, "Choose an Email Client"));
    }
}