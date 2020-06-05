package com.footballapp.net

import com.footballapp.model.ScorersModel
import com.footballapp.model.StandingsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RestApi {

    @GET("competitions/{id}/scorers")
    suspend fun getScorers(@Path("id")leagueId: String): Response<ScorersModel>

    @GET("competitions/{id}/standings")
    suspend fun getStandings(@Path("id")leagueId: String): Response<StandingsModel>
}