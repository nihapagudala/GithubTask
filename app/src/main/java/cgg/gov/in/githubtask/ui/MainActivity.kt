package cgg.gov.`in`.githubtask.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import cgg.gov.`in`.githubtask.R
import cgg.gov.`in`.githubtask.adapter.MyRecyclerViewAdapter
import cgg.gov.`in`.githubtask.databinding.ActivityMainBinding
import cgg.gov.`in`.githubtask.model.ServiceCallRes
import cgg.gov.`in`.task.`interface`.ServiceInterface
import cgg.gov.`in`.task.error_handler.ErrorHandler
import cgg.gov.`in`.task.error_handler.ErrorHandlerInterface
import cgg.gov.`in`.task.utils.CustomProgressDialog
import cgg.gov.`in`.task.utils.Utils
import cgg.gov.`in`.task.viewmodel.TaskViewModel

class MainActivity : AppCompatActivity(), ErrorHandlerInterface, ServiceInterface {
    private var myAttendanceViewModel: TaskViewModel? = null
    private var customProgressDialog: CustomProgressDialog? = null
    private lateinit var binding: ActivityMainBinding
    private var page = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this, R.layout.activity_main
        )
        customProgressDialog = CustomProgressDialog(this)
        callService()
    }

    private fun callService() {
        myAttendanceViewModel = TaskViewModel(this, application)
        if (Utils.checkInternetConnection(this)) {
            customProgressDialog?.show()
            myAttendanceViewModel?.getServiceResponse("id", page, PAGE_PER_ITEM)
        } else {
            Toast.makeText(this, getString(R.string.plz_check_int), Toast.LENGTH_LONG).show()
        }
    }

    override fun handleError(e: Throwable?, context: Context?) {
        val errMsg: String = ErrorHandler.handleError(e, context).toString()
        Toast.makeText(applicationContext, errMsg, Toast.LENGTH_SHORT)
            .show()
    }

    override fun handleError(e: String?, context: Context?) {
        Toast.makeText(applicationContext, "" + e, Toast.LENGTH_SHORT)
            .show()
    }

    override fun getList(response: ServiceCallRes) {
        customProgressDialog?.dismiss()
        if (response != null) {
            if (response.items != null && response.items.size > 0) {
                binding.rv.visibility = View.VISIBLE
                binding.tvEmpty.visibility = View.GONE

                val adapter = MyRecyclerViewAdapter(response.items, this)
                binding.rv.adapter = adapter
                binding.rv.layoutManager = LinearLayoutManager(this)

            } else {
                binding.rv.visibility = View.GONE
                binding.tvEmpty.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        private const val PAGE_PER_ITEM = 10
    }
}