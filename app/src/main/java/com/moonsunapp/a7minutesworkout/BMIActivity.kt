package com.moonsunapp.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import com.moonsunapp.a7minutesworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
        private const val US_UNITS_VIEW = "US_UNIT_VIEW"
    }

    private var currentVisible: String = METRIC_UNITS_VIEW


    private var binding: ActivityBmiBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBmiActivity)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "Calculate BMI"
        }
        binding?.toolbarBmiActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.btnCalculateBMI?.setOnClickListener {
           calculateUnits()
        }

        makeVisibleMetricUnitsView()

        binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId: Int ->
            if (checkedId == R.id.rbMetricUnits) {
                makeVisibleMetricUnitsView()
            } else {
                makeVisibleUSUnitsView()
            }
        }
    }

    private fun makeVisibleMetricUnitsView() {
        currentVisible = METRIC_UNITS_VIEW
        binding?.tilWeight?.visibility = View.VISIBLE
        binding?.tilHeight?.visibility = View.VISIBLE
        binding?.tilUSWeight?.visibility = View.GONE
        binding?.llFeetInchMetricUnits?.visibility = View.GONE
        binding?.llDisplayBMIResult?.visibility = View.INVISIBLE

        binding?.etHeightMetricUnit?.text!!.clear()
        binding?.etWeightMetricUnit?.text!!.clear()
    }

    private fun makeVisibleUSUnitsView() {
        currentVisible = US_UNITS_VIEW
        binding?.tilWeight?.visibility = View.GONE
        binding?.tilHeight?.visibility = View.GONE
        binding?.tilUSWeight?.visibility = View.VISIBLE
        binding?.llFeetInchMetricUnits?.visibility = View.VISIBLE
        binding?.llDisplayBMIResult?.visibility = View.INVISIBLE

        binding?.etFeetMetricUnit?.text!!.clear()
        binding?.etInchMetricUnit?.text!!.clear()
        binding?.etUSWeightMetricUnit?.text!!.clear()
    }

    private fun displayBMIResult(bmi: Float) {
        val bmiResultLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiResultLabel = "Very severely underweight"
            bmiDescription = "OOPS! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0) {
            bmiResultLabel = "Severely underweight"
            bmiDescription = "OOPS! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0) {
            bmiResultLabel = "Underweight"
            bmiDescription = "OOPS! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(20f) <= 0) {
            bmiResultLabel = "Normal"
            bmiDescription = "Congratulation! You are in a good shape!"
        } else {
            bmiResultLabel = "Obese Class"
            bmiDescription = "OMG! You are in dangerous condition!"
        }

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
        binding?.llDisplayBMIResult?.visibility = View.VISIBLE
        binding?.tvBMIValue?.text = bmiValue
        binding?.tvBMIType?.text = bmiResultLabel
        binding?.tvBMIDescription?.text = bmiDescription
    }

    private fun calculateUnits() {
        if (currentVisible == METRIC_UNITS_VIEW) {
            if (validateMetricUnits()) {
                val heightValue: Float =
                    binding?.etHeightMetricUnit?.text.toString().toFloat() / 100
                val weightValue: Float = binding?.etWeightMetricUnit?.text.toString().toFloat()
                val bmi = weightValue / (heightValue * heightValue)
                //TODO show bmi result
                displayBMIResult(bmi)
            } else {
                Toast.makeText(this@BMIActivity, "Please enter the value", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            if (validateUSUnits()) {
                val usUnitHeightValueFeet: String = binding?.etFeetMetricUnit?.text.toString()
                val usUnitHeightValueInch: String = binding?.etInchMetricUnit?.text.toString()
                val usUnitWeightValue: Float =
                    binding?.etUSWeightMetricUnit?.text.toString().toFloat()
                val heightValue =
                    usUnitHeightValueFeet.toFloat() * 12 + usUnitHeightValueInch.toFloat()

                val bmi = 703 * (usUnitWeightValue / (heightValue * heightValue))
                displayBMIResult(bmi)

            } else {
                Toast.makeText(this@BMIActivity, "Please enter the value", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun validateMetricUnits(): Boolean {
        var isValid = true
        if (binding?.etWeightMetricUnit?.text.toString().isEmpty()) {
            isValid = false
        } else if (binding?.etHeightMetricUnit?.text.toString().isEmpty()) {
            isValid = false
        }
        return isValid
    }

    private fun validateUSUnits(): Boolean {
        var isValid = true
        if (binding?.etUSWeightMetricUnit?.text.toString().isEmpty()) {
            isValid = false
        } else if (binding?.etFeetMetricUnit?.text.toString().isEmpty()) {
            isValid = false
        } else if (binding?.etInchMetricUnit?.text.toString().isEmpty()) {
            isValid = false
        }

        return isValid
    }

    override fun onDestroy() {
        super.onDestroy()
        binding=null
    }
}