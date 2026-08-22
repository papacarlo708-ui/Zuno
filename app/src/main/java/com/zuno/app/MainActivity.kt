package com.zuno.app

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val textView = TextView(this).apply {
            text = "Zuno"
            textSize = 32f
            setPadding(40, 80, 40, 40)
        }

        setContentView(textView)
    }
}
