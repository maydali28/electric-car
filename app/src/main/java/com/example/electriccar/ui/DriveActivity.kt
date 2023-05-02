package com.example.electriccar.ui

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.ui.AppBarConfiguration
import com.example.electriccar.databinding.ActivityDriveBinding


class DriveActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDriveBinding
    private var batteryCapacity = 5000

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityDriveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.checkDrivingDistance.setOnClickListener {
            val distance = binding.textFieldDistance.editText?.text.toString()
            val batteryLevel = binding.textFieldBatteryLevel.editText?.text.toString()
            drive(Integer.parseInt(distance), Integer.parseInt(batteryLevel))
        }
    }

    fun drive(distance: Int, batteryLevel: Int) {
        val range = batteryLevel * batteryCapacity / 100
        if (distance <= range) {
            Toast.makeText(this, "Vous avez suffisamment de batterie pour parcourir cette distance", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Vous n'avez pas assez de batterie pour parcourir cette distance.", Toast.LENGTH_LONG).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish() // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }
}