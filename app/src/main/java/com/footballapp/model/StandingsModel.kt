package com.footballapp.model

import com.google.gson.annotations.SerializedName

data class StandingsModel(
    @SerializedName("competition")
    val competition: Competition,
    @SerializedName("filters")
    val filters: Filters,
    @SerializedName("season")
    val season: Season,
    @SerializedName("standings")
    val standings: List<Standing>
) {
    data class Competition(
        @SerializedName("area")
        val area: Area,
        @SerializedName("code")
        val code: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("lastUpdated")
        val lastUpdated: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("plan")
        val plan: String
    ) {
        data class Area(
            @SerializedName("id")
            val id: Int,
            @SerializedName("name")
            val name: String
        )
    }

    class Filters()

    data class Season(
        @SerializedName("currentMatchday")
        val currentMatchday: Int,
        @SerializedName("endDate")
        val endDate: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("startDate")
        val startDate: String,
        @SerializedName("winner")
        val winner: Any
    )

    data class Standing(
        @SerializedName("group")
        val group: String,
        @SerializedName("stage")
        val stage: String,
        @SerializedName("table")
        val table: List<Table>,
        @SerializedName("type")
        val type: String
    ) {
        data class Table(
            @SerializedName("draw")
            val draw: Int,
            @SerializedName("goalDifference")
            val goalDifference: Int,
            @SerializedName("goalsAgainst")
            val goalsAgainst: Int,
            @SerializedName("goalsFor")
            val goalsFor: Int,
            @SerializedName("lost")
            val lost: Int,
            @SerializedName("playedGames")
            val playedGames: Int,
            @SerializedName("points")
            val points: Int,
            @SerializedName("position")
            val position: Int,
            @SerializedName("team")
            val team: Team,
            @SerializedName("won")
            val won: Int
        ) {
            data class Team(
                @SerializedName("crestUrl")
                val crestUrl: String,
                @SerializedName("id")
                val id: Int,
                @SerializedName("name")
                val name: String
            )
        }
    }
}