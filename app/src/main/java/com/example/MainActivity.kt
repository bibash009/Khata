package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.ui.ExpenseViewModel
import com.example.ui.ViewModelFactory
import com.example.ui.MainAppScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Retrieve ViewModel using encapsulated ViewModelFactory with Application context
        val factory = ViewModelFactory(application)
        val viewModel: ExpenseViewModel by viewModels { factory }

        enableEdgeToEdge()
        setContent {
            MainAppScreen(viewModel = viewModel)
        }
    }
}
