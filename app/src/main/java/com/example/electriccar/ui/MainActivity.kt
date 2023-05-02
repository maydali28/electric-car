package com.example.electriccar.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.electriccar.databinding.ActivityMainBinding
import com.example.electriccar.helpers.DBHelper
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        val dbHelper = DBHelper(this, null)
        val savedTimeStamp = getSavedTimeStamp(dbHelper)
        binding.textFieldMaintenance.isVisible = (getCurrentLocalDate() >= (savedTimeStamp + (1 * 24 * 3600)))

        binding.testClickButton.setOnClickListener {
            val intent = Intent(this, DriveActivity::class.java)
            startActivity(intent)
        }

        binding.textFieldMaintenance.setEndIconOnClickListener {
            val km = binding.textFieldMaintenance.editText?.text.toString()
            dbHelper.addKm(getCurrentLocalDate(), km)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentLocalDate(): Long {
        val localDate = LocalDate.parse(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), DateTimeFormatter.ofPattern("dd-MM-yyyy"))
         return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().epochSecond
    }

    @SuppressLint("Range")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getSavedTimeStamp(dbHelper: DBHelper): Long {
        val cursor = dbHelper.getLastSavedRecord()

        cursor!!.moveToFirst()
        val savedTimeStamp = cursor.getLong(cursor.getColumnIndex(DBHelper.TIMESTAMP_COl))
        cursor.close()

        return savedTimeStamp
    }
}