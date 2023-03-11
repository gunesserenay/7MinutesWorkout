package com.moonsunapp.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.moonsunapp.a7minutesworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding:ActivityMainBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        //val flStartButton:FrameLayout=findViewById(R.id.flStart)

        binding?.flStart?.setOnClickListener{

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding=null
    }
}