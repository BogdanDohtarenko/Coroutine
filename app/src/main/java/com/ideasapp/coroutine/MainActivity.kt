package com.ideasapp.coroutine

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    // Declare views as nullable variables
    private var tvLocation : TextView? = null
    private var tvTemperature : TextView? = null
    private var progress : ProgressBar? = null
    private var buttonLoad : Button? = null

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Set the layout file

        // Initialize views using findViewById
        tvLocation = findViewById(R.id.tv_location)
        tvTemperature = findViewById(R.id.tv_temperature)
        progress = findViewById(R.id.progress)
        buttonLoad = findViewById(R.id.button_load)

        // Set up button click listener
        buttonLoad?.setOnClickListener {
            progress?.isVisible = true
            buttonLoad?.isEnabled = false
            val jobCity = lifecycleScope.async {
                val city = loadCity()
                tvLocation?.text = city
                city
            }
            val jobTemp = lifecycleScope.async {
                val temperature = loadTemperature()
                tvTemperature?.text = temperature.toString()
                temperature
            }
            lifecycleScope.launch {
                val city = jobCity.join()
                val temperature = jobTemp.join()
                progress?.isVisible = false
                buttonLoad?.isEnabled = true
            }
        }
    }

    private suspend fun loadData() {
        Log.d("MainActivity" , "Load started: $this")
        progress?.isVisible = true
        buttonLoad?.isEnabled = false

        val city = loadCity()
        tvLocation?.text = city
        val temperature = loadTemperature()
        tvTemperature?.text = temperature.toString()
        progress?.isVisible = false
        buttonLoad?.isEnabled = true
        Log.d("MainActivity" , "Load finished: $this")
    }

    private suspend fun loadCity() : String {
        delay(2000) // Simulate network call
        return "Minsk"
    }

    private suspend fun loadTemperature() : Int {
        delay(5000) // Simulate network call
        return -17
    }
}
