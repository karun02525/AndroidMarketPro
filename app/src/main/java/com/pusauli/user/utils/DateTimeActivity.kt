package com.example.vicky.multilanguageexample.splash

/*import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.vicky.multilanguageexample.R
import com.pusauli.user.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class DateTimeActivity : AppCompatActivity() {

    private val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.US)
    private val timeFormat = SimpleDateFormat("hh:mm a", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val now = Calendar.getInstance()
        btn_date.text = dateFormat.format(now.time)
        btn_time.text = timeFormat.format(now.time)


        btn_date.setOnClickListener {
            val datePicker = DatePickerDialog(
                this, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    now.set(Calendar.YEAR, year)
                    now.set(Calendar.MONTH, month)
                    now.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    btn_date.text = dateFormat.format(now.time)
                },
                now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }

        btn_time.setOnClickListener {
            val timePicker = TimePickerDialog(
                this, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    now.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    now.set(Calendar.MINUTE, minute)
                    btn_time.text = timeFormat.format(now.time)
                },
                now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), false
            )
            timePicker.show()
        }

    }
}*/

//Layout-----------------
/*
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:tools="http://schemas.android.com/tools"
android:layout_width="match_parent"
android:layout_height="match_parent"
tools:context=".MainActivity">


<LinearLayout
android:layout_centerInParent="true"
android:layout_width="match_parent"
android:layout_height="wrap_content"
android:padding="20dp"
android:orientation="vertical">

<Button
android:id="@+id/btn_date"
android:layout_width="match_parent"
android:layout_height="wrap_content"
android:padding="16dp"
/>
<Button
android:id="@+id/btn_time"
android:layout_width="match_parent"
android:layout_height="wrap_content"
android:padding="16dp"
/>

</LinearLayout>

</RelativeLayout>*/
