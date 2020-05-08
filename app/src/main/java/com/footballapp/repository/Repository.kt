package com.footballapp.repository

import android.net.ConnectivityManager
import com.footballapp.model.ScorersModel
import com.footballapp.model.StandingsModel
import com.footballapp.net.ConnectionManager
import com.footballapp.net.ResponseCall
import com.footballapp.net.RestApi

class Repository(
    private val restApi: RestApi,
    private val connectionManager: ConnectionManager
) : BaseRepository() {

    suspend fun getAllScorers(): ResponseCall<ScorersModel> =
        call { restApi.getScorers() }

    suspend fun getAllStandings() : ResponseCall<StandingsModel> =
        call { restApi.getStandings() }

    fun hasNetworkConnection(connectivityManager: ConnectivityManager) =
        connectionManager.hasNetworkConnection(connectivityManager)
}