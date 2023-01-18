package com.krishnan.digitformattextview

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.krishnan.digitformattextview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.digitFormatTextView.initView("\u20B9")
        binding.switchOnOffLetter.setOnCheckedChangeListener { compoundButton, onOff ->
            binding.digitFormatTextView.apply {
                initView("\u20B9", onOff)
                setValue(binding.inputBox.text.toString())
            }
        }

        binding.submit.setOnClickListener {
            binding.digitFormatTextView.apply {
                setValue(binding.inputBox.text.toString())
            }
        }

        binding.inputBox.doOnTextChanged { text, start, before, count ->
            if (start < 13) {
                binding.digitFormatTextView.setValue(text.toString())
            } else {
                Toast.makeText(this, "Sorry! Digit is over limit.", Toast.LENGTH_SHORT).show()
                return@doOnTextChanged
            }
        }
    }
}