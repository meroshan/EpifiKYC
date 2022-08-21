package com.myapp.epifikyc

import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.myapp.epifikyc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding!!
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        initListeners()
        nextButtonObserver()
        capitalizePanNumberEdittext()
    }

    private fun initListeners() {
        binding.etPanNumber.addTextChangedListener {
            val text = binding.etPanNumber.text.toString()
            mainViewModel.setPanNumber(text)
            if (mainViewModel.validatePanNumber(text)) {
                binding.etDay.requestFocus()
            }
        }

        binding.etDay.addTextChangedListener {
            val text = binding.etDay.text.toString()
            mainViewModel.setBirthDay(text)
            if (text.length == 2 && text.toInt() <= 31)
                binding.etMonth.requestFocus()
        }
        binding.etDay.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus && binding.etDay.text.length == 1) {
                binding.etDay.text.insert(0, "0")
            }
        }

        binding.etMonth.addTextChangedListener {
            val text = binding.etMonth.text.toString()
            mainViewModel.setBirthMonth(text)
            if (text.length == 2 && text.toInt() <= 12)
                binding.etYear.requestFocus()
        }
        binding.etMonth.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus && binding.etMonth.text.length == 1) {
                binding.etDay.text.insert(0, "0")
            }
        }

        binding.etYear.addTextChangedListener {
            val text = binding.etYear.text.toString()
            mainViewModel.setBirthYear(text)
        }
    }

    private fun capitalizePanNumberEdittext() {
        binding.etPanNumber.filters = arrayOf<InputFilter>(
            InputFilter.AllCaps(),
            InputFilter.LengthFilter(10)
        )
    }

    private fun nextButtonObserver() {
        binding.btnNext.isEnabled = false
        lifecycleScope.launchWhenStarted {
            mainViewModel.isButtonEnabled.collect {
                Log.d("merahulroshan1", "isPanNoValid: $it")
                binding.btnNext.isEnabled = it
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}