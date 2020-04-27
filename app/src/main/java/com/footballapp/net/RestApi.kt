package com.footballapp.net

import com.footballapp.model.ScorersModel
import com.footballapp.model.StandingsModel
import retrofit2.Response
import retrofit2.http.GET

interface RestApi {

    @GET("competitions/2002/scorers")
    suspend fun getScorers(): Response<ScorersModel>

    @GET("competitions/2002/standings")
    suspend fun getStandings(): Response<StandingsModel>
}