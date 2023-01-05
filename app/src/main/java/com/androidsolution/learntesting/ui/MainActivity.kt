package com.androidsolution.learntesting.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.androidsolution.learntesting.R
import com.androidsolution.learntesting.common.ResultOf
import com.androidsolution.learntesting.common.Status
import com.androidsolution.learntesting.session.Session
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    var session = Session("")

    var TAG = "MainXX"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*session.doAggregateTest(Dispatchers.IO, 1000) //2000 - values; 500 - empty
        session.result.observe(this){
            Log.d("Session", "onCreate: status ${it.status}")
            when(it.status){
                Status.SUCCESS -> {
                    Log.d("Session", "onCreate: ${  Gson().toJson(it.data)}")
                }
                Status.ERROR -> {
                    Log.d("Session", "onCreate: ${it.message}")
                }
            }
        }*/

        craap()

    }

    private fun craap() {

        lifecycleScope.launch {

            Log.d(TAG, "craap: start")

            val j1 = launch {
                Log.d(TAG, "craap: j1")
            }

            val j2 = launch {
                Log.d(TAG, "craap: j2")
            }

            j1.join()

            Log.d(TAG, "craap: end")
        }

    }
}