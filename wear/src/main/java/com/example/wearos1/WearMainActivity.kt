package com.example.wearos1

import android.app.Activity
import android.os.Bundle
import com.example.wearos1.databinding.ActivityMainBinding
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import android.widget.TextView
import androidx.wear.ambient.AmbientModeSupport
import com.google.android.gms.wearable.DataMap
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable

class WearMainActivity : Activity(), SensorEventListener{

    private lateinit var binding: ActivityMainBinding
    private lateinit var sensorManager: SensorManager
    private var humiditySensor: Sensor? = null
    private lateinit var txtSensor: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        txtSensor = findViewById<TextView>(R.id.txtSensor)
        txtSensor.text = "La humedad actual es: "
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        humiditySensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)

        if(humiditySensor == null){

        }
    }

    override fun onResume() {
        super.onResume()
        humiditySensor?.let { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    private var transcriptionNodeId: String? = null

    override fun onSensorChanged(event: SensorEvent?) {
        if(event?.sensor?.type == Sensor.TYPE_RELATIVE_HUMIDITY){
            val humidityValue = event.values[0]
            txtSensor.text = "La humedad actual es: ${humidityValue}"
            sendDataToPhone("${humidityValue}")
        }
    }



    private fun sendDataToPhone(h: String) {

        val dataMap = DataMap().apply {
            putString(KEY_DATA, h)
        }

        val putDataMapRequest = PutDataMapRequest.create(PATH_DATA).apply {
            dataMap.putAll(dataMap)
        }

        val putDataTask = Wearable.getDataClient(this).putDataItem(putDataMapRequest.asPutDataRequest())

        putDataTask.addOnSuccessListener {
            // Dato enviado exitosamente
            Log.d("WEAR MANDA DATOS", h)
        }

        putDataTask.addOnFailureListener {
            Log.d("WEAR FALLO PTM", h)
            // Error al enviar el dato
        }
    }

    companion object {
        private const val KEY_DATA = "humedad"
        private const val PATH_DATA = "/data_path"
    }
}