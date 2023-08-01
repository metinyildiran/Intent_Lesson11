package com.example.intentlesson11

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.CalendarContract
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar


class MainActivity : AppCompatActivity() {


    lateinit var btnCallNumber: Button
    lateinit var btnViewMap: Button
    lateinit var btnViewWebPage: Button
    lateinit var btnSendEmail: Button
    lateinit var btnCreateCalenderEvent: Button
    lateinit var btnOpenOtherApp: Button
    lateinit var btnOpenAnAppWithChooser: Button
    lateinit var btnSetAlarm: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCallNumber = findViewById(R.id.btnCallNumber)
        btnViewMap = findViewById(R.id.btnViewMap)
        btnViewWebPage = findViewById(R.id.btnViewWebPage)
        btnSendEmail = findViewById(R.id.btnSendEmail)
        btnCreateCalenderEvent = findViewById(R.id.btnCreateCalenderEvent)
        btnOpenOtherApp = findViewById(R.id.btnOpenOtherApp)
        btnOpenAnAppWithChooser = findViewById(R.id.btnOpenAnAppWithChooser)
        btnSetAlarm = findViewById(R.id.btnSetAlarm)

        btnCallNumber.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:5551234"))
            startActivity(callIntent)
        }

        btnViewMap.setOnClickListener {
            val mapIntent: Intent = Uri.parse(
                "geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California"
            ).let { location ->
                // Or map point based on latitude/longitude
                // val location: Uri = Uri.parse("geo:37.422219,-122.08364?z=14") // z param is zoom level
                Intent(Intent.ACTION_VIEW, location)
            }
            startActivity(mapIntent)
        }


        btnViewWebPage.setOnClickListener {
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.android.com"))
            startActivity(webIntent)
        }


        btnSendEmail.setOnClickListener {

            Intent(Intent.ACTION_SEND).apply {
                // The intent does not have a URI, so declare the "text/plain" MIME type
                type = "text/plain"
                putExtra(Intent.EXTRA_EMAIL, arrayOf("jan@example.com")) // recipients
                putExtra(Intent.EXTRA_SUBJECT, "Email subject")
                putExtra(Intent.EXTRA_TEXT, "Email message text")
                putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"))
                // You can also attach multiple items by passing an ArrayList of Uris
                startActivity(this)
            }

        }

        btnCreateCalenderEvent.setOnClickListener {
            // Event is on January 23, 2021 -- from 7:30 AM to 10:30 AM.
            Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI).apply {
                val beginTime: Calendar = Calendar.getInstance().apply {
                    set(2021, 0, 23, 7, 30)
                }
                val endTime = Calendar.getInstance().apply {
                    set(2021, 0, 23, 10, 30)
                }
                putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.timeInMillis)
                putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.timeInMillis)
                putExtra(CalendarContract.Events.TITLE, "Ninja class")
                putExtra(CalendarContract.Events.EVENT_LOCATION, "Secret dojo")
                startActivity(this)
            }
        }

        btnOpenAnAppWithChooser.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California"))
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:41.142199, 29.131750"))
            // Create intent to show chooser
            val chooser = Intent.createChooser(intent, "ilgili uygulamayi seciniz", null)
            // Try to invoke the intent.
            try {
                startActivity(chooser)
            } catch (e: ActivityNotFoundException) {
                // Define what your app should do if no activity can handle the intent.
            }
        }

        btnOpenOtherApp.setOnClickListener {
            try {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                val message = "messageadwwd awd awd wa"
                shareIntent.putExtra(Intent.EXTRA_TEXT, message)
                startActivity(shareIntent)

                //calisiyor
//                val intent = Intent(Intent.ACTION_MAIN)
//                intent.component = ComponentName("com.example.allowotherappsopenthiswithintentfilter", "com.example.allowotherappsopenthiswithintentfilter.ViewerActivity")
//                intent.putExtra("data","merhaba yalan dunya")
//                startActivity(intent)


            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "application was not found in this device", Toast.LENGTH_SHORT)
                    .show()
                startActivity(
                    //to show specific app page
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=com.findwordgame.app")
                    )
                    //to show developers page/apps
                    //Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=mbApps"))
                )
            }
        }


        btnSetAlarm.setOnClickListener {
            createAlarm("this is my alarm message", 12, 5)
        }

    }

    private fun createAlarm(message: String, hour: Int, minutes: Int) {
        val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, message)
            putExtra(AlarmClock.EXTRA_HOUR, hour)
            putExtra(AlarmClock.EXTRA_MINUTES, minutes)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }


}