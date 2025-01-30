package com.ideasapp.coroutine

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    // Declare views as nullable variables
    private var tvLocation: TextView? = null
    private var tvTemperature: TextView? = null
    private var progress: ProgressBar? = null
    private var buttonLoad: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Set the layout file

        // Initialize views using findViewById
        tvLocation = findViewById(R.id.tv_location)
        tvTemperature = findViewById(R.id.tv_temperature)
        progress = findViewById(R.id.progress)
        buttonLoad = findViewById(R.id.button_load)

        // Set up button click listener
        buttonLoad?.setOnClickListener {
            loadData()
        }
    }

    private fun loadData() {
        Log.d("MainActivity", "Load started: $this")
        progress?.isVisible = true
        buttonLoad?.isEnabled = false

        loadCity { city ->
            tvLocation?.text = city
            loadTemperature(city) { temperature ->
                tvTemperature?.text = temperature.toString()
                progress?.isVisible = false
                buttonLoad?.isEnabled = true
                Log.d("MainActivity", "Load finished: $this")
            }
        }
    }

    private fun loadCity(callback: (String) -> Unit) {
        thread {
            Thread.sleep(5000) // Simulate network call
            runOnUiThread {
                callback.invoke("Moscow")
            }
        }
    }

    private fun loadTemperature(city: String, callback: (Int) -> Unit) {
        thread {
            runOnUiThread {
                Toast.makeText(
                    this,
                    getString(R.string.loading_temperature_toast, city),
                    Toast.LENGTH_SHORT
                ).show()
            }
            Thread.sleep(5000) // Simulate network call
            runOnUiThread {
                callback.invoke(17)
            }
        }
    }
}