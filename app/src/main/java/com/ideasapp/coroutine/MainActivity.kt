package com.ideasapp.coroutine

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
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

    private val viewModel by lazy {
        ViewModelProvider(this)[MyViewModel::class.java]
    }

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Set the layout file
        tvLocation = findViewById(R.id.tv_location)
        tvTemperature = findViewById(R.id.tv_temperature)
        progress = findViewById(R.id.progress)
        buttonLoad = findViewById(R.id.button_load)

        viewModel.method()
    }
}
