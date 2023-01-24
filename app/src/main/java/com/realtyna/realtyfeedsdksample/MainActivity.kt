package com.realtyna.realtyfeedsdksample

import android.os.Bundle
import android.util.Log
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.appcompat.widget.AppCompatTextView
import com.realtyna.realtyfeedsdk.RealtyFeedSDK
import com.realtyna.realtyfeedsdk.API
import org.json.JSONObject
import java.util.Date

class MainActivity : AppCompatActivity() {
    lateinit var radioGroup: RadioGroup
    lateinit var rbtnListings: AppCompatRadioButton
    lateinit var rbtnProperty: AppCompatRadioButton
    lateinit var btnCheckData: AppCompatButton
    lateinit var edAPIKey: AppCompatEditText
    lateinit var tvResult: AppCompatTextView
    lateinit var prefHelper: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prefHelper = PreferenceHelper(this)

        radioGroup = findViewById(R.id.radioGroup)
        rbtnListings = findViewById(R.id.listings)
        rbtnProperty = findViewById(R.id.property)
        btnCheckData = findViewById(R.id.btnCheckData)
        edAPIKey = findViewById(R.id.edAPIKey)
        tvResult = findViewById(R.id.tvResult)

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.listings -> {
                    rbtnProperty.isChecked = false
                }
                R.id.property -> {
                    rbtnListings.isChecked = false
                }
            }
        }

        edAPIKey.setText(prefHelper.getString("X-API-KEY", ""))

        btnCheckData.setOnClickListener {
            btnCheckData.isEnabled = false
            val startDate = Date()
            RealtyFeedSDK.initial(edAPIKey.text.toString())
            prefHelper.putString(edAPIKey.text.toString(), "X-API-KEY")

            if (rbtnListings.isChecked){
                API.instance.getListings { result, error ->
                    logResult(result, startDate, tvResult)
                }
            } else if (rbtnProperty.isChecked){
                API.instance.getProperty("P_5dba1fb94aa4055b9f29691f") { result, error ->
                    logResult(result, startDate, tvResult)
                }
            }
        }
    }

    private fun logResult(
        result: JSONObject?,
        startDate: Date,
        tvResult: AppCompatTextView
    ) {
        btnCheckData.isEnabled = true

        Log.d("MainActivity", result.toString())
        val diff = Date().time - startDate.time
        val milliSeconds = diff % 1000
        val seconds = diff / 1000

        tvResult.text = "Done in $seconds:$milliSeconds seconds\n\n ${result.toString()}"
    }
}