
package cgg.gov.`in`.githubtask.model

import com.google.gson.annotations.SerializedName


data class ServiceCallRes(

    @SerializedName("total_count") val total: Int ,
    @SerializedName("items") val items: List<ItemsRes> ,
    @SerializedName("incomplete_results") val incompleteResults: Boolean

)
