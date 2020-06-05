package com.footballapp.ext

import android.content.Context
import android.widget.AbsSpinner
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModel
import com.footballapp.R

fun setupSpinner(context: Context, spinner: AbsSpinner, adapterView: AdapterView.OnItemSelectedListener) {
    val adapter = ArrayAdapter.createFromResource(context, R.array.league_list, android.R.layout.simple_spinner_item)
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    spinner.adapter = adapter
    spinner.onItemSelectedListener = adapterView
}

enum class LeagueId(val value: String) {
    BUNDESLIGA("2002"),
    PREMIER_LEAGUE("2021"),
    PREMIERA_DIVISION("2014"),
    SERIE_A("2019"),
    LIGUE_1("2015"),
    PRIMEIRA_LIGA("2017")
}