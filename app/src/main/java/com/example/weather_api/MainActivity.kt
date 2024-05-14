package com.example.weather_api

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var startBtn: Button
    private lateinit var url: EditText
    private lateinit var loadingPanel: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        startBtn = findViewById(R.id.start)
        startBtn.setOnClickListener(this)

        url = findViewById(R.id.url)
    }

    override fun onClick(view: View) {
        val id = view.id
        if (id == R.id.start) {
            val asyncTask = MyAsyncTask()
            asyncTask.wa = this
            asyncTask.execute()
        }
    }

    fun nextActivity(responseData: String) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("response_data", responseData)
        startActivity(intent)
    }

    inner class MyAsyncTask : AsyncTask<String, Int, String>() {
        lateinit var wa: MainActivity
        lateinit var progressDialog: ProgressDialog

        override fun onPreExecute() {
            progressDialog = ProgressDialog(this@MainActivity)
            progressDialog.setMessage("Wait a second...")
            progressDialog.setCancelable(false)
            progressDialog.show()
        }

        override fun onProgressUpdate(vararg progress: Int?) {
            progressDialog.progress = progress[0]!!
        }

        override fun doInBackground(vararg strings: String): String? {
            return try {
                val client = OkHttpClient().newBuilder().build()
                val location = url.text.toString()
                val request = Request.Builder()
                    .url("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/$location?unitGroup=us&key=3C8TRCWYPKSPU83H6U8CJ5CUR&contentType=json")
                    .method("GET", null)
                    .build()
                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val jsonData = response.body!!.string()
                    wa.nextActivity(jsonData)
                    jsonData
                } else {
                    response.message
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            progressDialog.dismiss()
            if (result != null) {
                // Process the response data
                // Update UI or do any other required tasks
            } else {
                // Handle the error scenario
            }
        }
    }

    fun showToast(text: String) {
        runOnUiThread {
            Toast.makeText(this@MainActivity, text, Toast.LENGTH_SHORT).show()
        }
    }
}