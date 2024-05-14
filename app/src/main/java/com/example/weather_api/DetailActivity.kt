package com.example.weather_api

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class DetailActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var locationName: TextView
    private lateinit var daysArray: JSONArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)

        listView = findViewById(R.id.list_view)
        locationName = findViewById(R.id.locationNameView)

        // Parse the JSON data
        val responseData = intent.getStringExtra("response_data")

        try {
            val jsonObject = JSONObject(responseData)
            daysArray = jsonObject.getJSONArray("days")
            locationName.text = "Address : " + jsonObject.getString("address").toString()
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        // Set the custom adapter to the ListView
        val adapter = DaysAdapter()
        listView.adapter = adapter
    }

    private inner class DaysAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return daysArray.length()
        }

        override fun getItem(position: Int): Any? {
            try {
                return daysArray.getJSONObject(position)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            return null
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var convertView = convertView
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_day, parent, false)
            }

            val datetimeTextView = convertView!!.findViewById<TextView>(R.id.datetime_text_view)
            val tempTextView = convertView.findViewById<TextView>(R.id.temp_text_view)
            val humidityTextView = convertView.findViewById<TextView>(R.id.humidity_text_view)
            val precipTextView = convertView.findViewById<TextView>(R.id.precip_text_view)
            val feelslikeTextView = convertView.findViewById<TextView>(R.id.feelslike_text_view)
            val windgustTextView = convertView.findViewById<TextView>(R.id.windgust_text_view)
            val windspeedTextView = convertView.findViewById<TextView>(R.id.windspeed_text_view)
            val winddirTextView = convertView.findViewById<TextView>(R.id.winddir_text_view)
            val pressureTextView = convertView.findViewById<TextView>(R.id.pressure_text_view)
            val cloudcoverTextView = convertView.findViewById<TextView>(R.id.cloudcover_text_view)
            val visibilityTextView = convertView.findViewById<TextView>(R.id.visibility_text_view)
            val solarradiationTextView =
                convertView.findViewById<TextView>(R.id.solarradiation_text_view)
            val solarenergyTextView = convertView.findViewById<TextView>(R.id.solarenergy_text_view)

            try {
                val dayObject = daysArray.getJSONObject(position)
                val datetime = dayObject.getString("datetime")
                val temp = dayObject.getDouble("temp")
                val humidity = dayObject.getDouble("humidity")
                val precip = dayObject.getDouble("precip")
                val feelslike = dayObject.getDouble("feelslike")
                val windgust = dayObject.getDouble("windgust")
                val windspeed = dayObject.getDouble("windspeed")
                val winddir = dayObject.getDouble("winddir")
                val pressure = dayObject.getDouble("pressure")
                val cloudcover = dayObject.getDouble("cloudcover")
                val visibility = dayObject.getDouble("visibility")
                val solarradiation = dayObject.getDouble("solarradiation")
                val solarenergy = dayObject.getDouble("solarenergy")

                datetimeTextView.text = datetime
                tempTextView.text = "Temperature: $temp°C"
                humidityTextView.text = "Humidity: $humidity%"
                precipTextView.text = "Precipitation: $precip"
                feelslikeTextView.text = "Feels Like: $feelslike°C"
                windgustTextView.text = "Wind Gust: $windgust"
                windspeedTextView.text = "Wind Speed: $windspeed"
                winddirTextView.text = "Wind Direction: $winddir"
                pressureTextView.text = "Pressure: $pressure"
                cloudcoverTextView.text = "Cloud Cover: $cloudcover"
                visibilityTextView.text = "Visibility: $visibility"
                solarradiationTextView.text = "Solar Radiation: $solarradiation"
                solarenergyTextView.text = "Solar Energy: $solarenergy"
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            return convertView
        }
    }
}