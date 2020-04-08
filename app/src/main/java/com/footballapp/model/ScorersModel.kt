package com.footballapp.model

import com.google.gson.annotations.SerializedName

data class ScorersModel(
    @SerializedName("competition")
    val competition: Competition,
    @SerializedName("count")
    val count: Int,
    @SerializedName("filters")
    val filters: Filters?,
    @SerializedName("scorers")
    val scorers: List<Scorer>,
    @SerializedName("season")
    val season: Season?
) {
    data class Competition(
        @SerializedName("area")
        val area: Area?,
        @SerializedName("code")
        val code: String?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("lastUpdated")
        val lastUpdated: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("plan")
        val plan: String?
    ) {
        data class Area(
            @SerializedName("id")
            val id: Int?,
            @SerializedName("name")
            val name: String?
        )
    }

    data class Filters(
        @SerializedName("limit")
        val limit: Int?
    )

    data class Scorer(
        @SerializedName("numberOfGoals")
        val numberOfGoals: Int,
        @SerializedName("player")
        val player: Player,
        @SerializedName("team")
        val team: Team
    ) {
        data class Player(
            @SerializedName("countryOfBirth")
            val countryOfBirth: String?,
            @SerializedName("dateOfBirth")
            val dateOfBirth: String?,
            @SerializedName("firstName")
            val firstName: String?,
            @SerializedName("id")
            val id: Int?,
            @SerializedName("lastName")
            val lastName: Any?,
            @SerializedName("lastUpdated")
            val lastUpdated: String?,
            @SerializedName("name")
            val name: String,
            @SerializedName("nationality")
            val nationality: String?,
            @SerializedName("position")
            val position: String?,
            @SerializedName("shirtNumber")
            val shirtNumber: Any?
        )

        data class Team(
            @SerializedName("id")
            val id: Int?,
            @SerializedName("name")
            val name: String
        )
    }

    data class Season(
        @SerializedName("currentMatchday")
        val currentMatchday: Int?,
        @SerializedName("endDate")
        val endDate: String?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("startDate")
        val startDate: String?,
        @SerializedName("winner")
        val winner: Any?
    )
}