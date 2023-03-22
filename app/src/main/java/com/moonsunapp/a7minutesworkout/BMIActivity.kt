package com.moonsunapp.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.moonsunapp.a7minutesworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    private var binding:ActivityBmiBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBmiActivity)
        if (supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title="Calculate BMI"
        }
        binding?.toolbarBmiActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.btnCalculateBMI?.setOnClickListener{
            if (validateMetricUnits()){
                val heightValue:Float=binding?.etHeightMetricUnit?.text.toString().toFloat() / 100
                val weightValue:Float=binding?.etWeightMetricUnit?.text.toString().toFloat()
                val bmi=weightValue/(heightValue*heightValue)
                //TODO show bmi result
                displayBMIResult(bmi)
            }else{
                Toast.makeText(this@BMIActivity,"Please enter the value",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displayBMIResult(bmi:Float){
        val bmiResultLabel:String
        val bmiDescription:String

        if (bmi.compareTo(15f)<=0){
            bmiResultLabel="Very severely underweight"
            bmiDescription="OOPS! You really need to take better care of yourself! Eat more!"
        }
        else if (bmi.compareTo(15f)>0 && bmi.compareTo(16f)<=0){
            bmiResultLabel="Severely underweight"
            bmiDescription="OOPS! You really need to take better care of yourself! Eat more!"
        }
        else if (bmi.compareTo(16f)>0 && bmi.compareTo(18.5f)<=0){
            bmiResultLabel="Underweight"
            bmiDescription="OOPS! You really need to take better care of yourself! Eat more!"
        }
        else if (bmi.compareTo(18.5f)>0 && bmi.compareTo(20f)<=0){
            bmiResultLabel="Normal"
            bmiDescription="Congratulation! You are in a good shape!"
        }
        else{
            bmiResultLabel="Obese Class"
            bmiDescription="OMG! You are in dangerous condition!"
        }

        val bmiValue=BigDecimal(bmi.toDouble()).setScale(2,RoundingMode.HALF_EVEN).toString()
        binding?.llDisplayBMIResult?.visibility=View.VISIBLE
        binding?.tvBMIValue?.text= bmiValue
        binding?.tvBMIType?.text=bmiResultLabel
        binding?.tvBMIDescription?.text=bmiDescription
    }
    private fun validateMetricUnits():Boolean{
        var isValid=true
        if (binding?.etWeightMetricUnit?.text.toString().isEmpty()){
            isValid= false
        }else if(binding?.etHeightMetricUnit?.text.toString().isEmpty()){
            isValid=false
        }
        return isValid
    }
}