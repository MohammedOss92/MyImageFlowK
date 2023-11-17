package com.sarrawi.myimgflowk

import com.google.gson.annotations.SerializedName


data class ImgsRespone2(
    @SerializedName("ImgsModel")
    val results: List<ImgsModel>
)

