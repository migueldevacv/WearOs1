package com.example.wearos1

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataItem
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.Wearable

class MainActivity : AppCompatActivity(){
    private lateinit var txtHumedad: TextView

    companion object {
        private const val KEY_DATA = "humedad"
        private const val PATH_DATA = "/data_path"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtHumedad = findViewById(R.id.txtHumedad)

    }
}