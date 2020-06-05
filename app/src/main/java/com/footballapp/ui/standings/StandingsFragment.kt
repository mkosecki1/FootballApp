package com.footballapp.ui.standings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.auth.AuthUI
import com.footballapp.R
import com.footballapp.ext.*
import com.footballapp.ext.LeagueId.*
import com.footballapp.model.StandingsModel.Standing.Table
import com.footballapp.net.ResponseCall
import com.footballapp.ui.standings.adapters.StandingsRecyclerViewAdapter
import kotlinx.android.synthetic.main.standings_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class StandingsFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private val standingsViewModel by viewModel<StandingsViewModel>()
    private lateinit var standingsAdapter: StandingsRecyclerViewAdapter
    private var selectLeague: ((Int) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.standings_fragment, container, false)

        standingsAdapter = StandingsRecyclerViewAdapter(arrayListOf(), requireActivity())

        standingsViewModel.standingsStatus.observe(
            viewLifecycleOwner, Observer {
                when (it) {
                    is ResponseCall.Success -> {
                        onSuccess(
                            it.data.season.startDate,
                            it.data.season.endDate,
                            it.data.standings[0].table
                        )
                    }

                    is ResponseCall.Error -> {
                        onError(getString(R.string.server_error_text))
                    }

                    is ResponseCall.Exception -> {
                        onException(getString(R.string.internet_unavailable_text))
                    }
                }
            }
        )

        return inflate
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinner(requireContext(), competitionSpinnerStandingsFragment, this)
        choseLeague()
        runRecyclerView()

        goToScorersButtonStandingsFragment.setOnClickListener {
            runDestination(this, R.id.scorersFragment)
        }

        logoutButtonStandingsFragment.setOnClickListener {
            logoutUser()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        standingsViewModel.loadStandings(BUNDESLIGA.value)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectLeague?.invoke(position)
        progressBarStandingsFragment.show()
    }

    private fun choseLeague() {
        selectLeague = {
            when (it) {
                0 -> standingsViewModel.loadStandings(BUNDESLIGA.value)
                1 -> standingsViewModel.loadStandings(PREMIER_LEAGUE.value)
                2 -> standingsViewModel.loadStandings(PREMIERA_DIVISION.value)
                3 -> standingsViewModel.loadStandings(SERIE_A.value)
                4 -> standingsViewModel.loadStandings(LIGUE_1.value)
            }
        }
    }

    private fun onSuccess(startDate: String?, endDate: String?, table: List<Table>) {
        seasonStandingsFragment.stringConnector(
            getString(R.string.standings_title_text),
            startDate,
            endDate
        )
        standingsAdapter.updateTable(table)
        progressBarStandingsFragment.hide()
        recyclerBarStandingsFragment.setVisible()
        competitionSpinnerStandingsFragment.setVisible()
    }

    private fun onError(error: String) {
        constraintLayoutStandingsFragment.showSnackBar(error)
    }

    private fun onException(exception: String) {
        constraintLayoutStandingsFragment.showSnackBar(exception)
    }

    private fun runRecyclerView() {
        recyclerViewStandingsFragment.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = standingsAdapter
        }
    }

    private fun logoutUser() {
        AuthUI.getInstance().signOut(requireContext())
        GlobalScope.launch {
            delay(100)
            runDestination(this@StandingsFragment, R.id.loginFragment)
        }
    }
}