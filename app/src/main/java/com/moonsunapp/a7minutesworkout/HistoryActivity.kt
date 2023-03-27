package com.moonsunapp.a7minutesworkout

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.moonsunapp.a7minutesworkout.databinding.ActivityHistoryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    private var binding: ActivityHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarHistory)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "History"
        }
        binding?.toolbarHistory?.setNavigationOnClickListener {
            onBackPressed()
        }

        val dao= (application as WorkOutApp).db.historyDao()
        getAllCompletedWorkOutDates(dao)
    }

    private fun getAllCompletedWorkOutDates(historyDao: HistoryDao) {
        lifecycleScope.launch {
            historyDao.fetchAllDates().collect { allCompletedDateList ->
                if (allCompletedDateList.isNotEmpty()){
                    binding?.rvDate?.visibility= View.VISIBLE
                    binding?.tvCompleted?.visibility= View.VISIBLE
                    binding?.tvNoData?.visibility= View.INVISIBLE

                    binding?.rvDate?.layoutManager=LinearLayoutManager(this@HistoryActivity)
                    val dates=ArrayList<String>()
                    for (date in allCompletedDateList){
                        dates.add(date.date)
                    }
                    val historyAdapter= HistoryAdapter(dates)
                    binding?.rvDate?.adapter=historyAdapter

                }else{
                    binding?.rvDate?.visibility= View.GONE
                    binding?.tvCompleted?.visibility= View.GONE
                    binding?.tvNoData?.visibility= View.VISIBLE
                }

//                for (i in allCompletedDateList) {
//                    Log.e("History fetch Date", "" + i.date)
//                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null

    }
}