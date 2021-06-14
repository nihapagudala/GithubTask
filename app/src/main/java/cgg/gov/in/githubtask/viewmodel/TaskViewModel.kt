package cgg.gov.`in`.task.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cgg.gov.`in`.githubtask.ui.MainActivity
import cgg.gov.`in`.githubtask.R
import cgg.gov.`in`.githubtask.model.ServiceCallRes
import cgg.gov.`in`.githubtask.network.ServiceCall
import cgg.gov.`in`.task.`interface`.ServiceInterface
import cgg.gov.`in`.task.error_handler.ErrorHandlerInterface

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskViewModel : AndroidViewModel {


    private var resMutableLiveData: MutableLiveData<ServiceCallRes>? = null
    private var serviceInterface: ServiceInterface? = null
    private var context: Context? = null
    private var errorHandlerInterface: ErrorHandlerInterface? = null

    constructor(context: MainActivity, application: Application?) : super(application!!) {
        this.context = context
        resMutableLiveData = MutableLiveData<ServiceCallRes>()
        errorHandlerInterface = context as ErrorHandlerInterface?
        serviceInterface = context as ServiceInterface?
    }

    fun getServiceResponse(
        query: String,
        page: Int,
        items_per_page: Int
    ): LiveData<ServiceCallRes?>? {
        if (resMutableLiveData != null) {
            getServiceResponseCall(query, page, items_per_page)
        }
        return resMutableLiveData
    }

    private fun getServiceResponseCall(query: String, page: Int, items_per_page: Int) {
        val virtuoService: ServiceCall = ServiceCall.create()

        virtuoService.searchRepositories(query, page, items_per_page)
            .enqueue(object : Callback<ServiceCallRes> {
                override fun onResponse(
                    call: Call<ServiceCallRes>,
                    response: Response<ServiceCallRes>
                ) {
                    if (response.isSuccessful() && response.body() != null) {
                        serviceInterface?.getList(response.body()!!)
                    } else {
                        errorHandlerInterface!!.handleError(
                            context!!.getString(R.string.server_not),
                            context
                        )
                    }
                }

                override fun onFailure(call: Call<ServiceCallRes?>, t: Throwable) {
                    errorHandlerInterface!!.handleError(t, context)
                }
            })
    }
}



