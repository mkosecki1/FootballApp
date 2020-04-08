package com.footballapp.repository

import com.footballapp.model.ScorersModel
import com.footballapp.net.ResponseCall
import com.footballapp.net.RestApi
import retrofit2.Response

class Repository(private val restApi: RestApi) : BaseRepository() {

    suspend fun getAllScorers(): ResponseCall<ScorersModel> =
        call { restApi.getScorers() }
}