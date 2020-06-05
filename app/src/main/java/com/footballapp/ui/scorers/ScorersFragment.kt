package com.footballapp.ui.scorers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.firebase.ui.auth.AuthUI
import com.footballapp.R
import com.footballapp.ext.*
import com.footballapp.ext.LeagueId.*
import com.footballapp.model.ScorersModel
import com.footballapp.net.ResponseCall
import com.footballapp.ui.scorers.epoxy.EpoxyScorersController
import kotlinx.android.synthetic.main.scorers_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScorersFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private val scorersViewModel by viewModel<ScorersViewModel>()
    private lateinit var epoxyScorersController: EpoxyScorersController
    private var selectLeague: ((Int) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.scorers_fragment, container, false)

        scorersViewModel.scorersStatus.observe(
            viewLifecycleOwner, Observer {
                when (it) {
                    is ResponseCall.Success -> {
                        onSuccess(
                            it.data.season?.startDate,
                            it.data.season?.endDate,
                            it.data
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

        setupSpinner(requireContext(), competitionSpinnerScorersFragment, this)
        choseLeague()

        goToStandingsButtonScorersFragment.setOnClickListener {
            runDestination(this, R.id.standingsFragment)
        }

        logoutButtonScorersFragment.setOnClickListener {
            logoutUser()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        scorersViewModel.loadScorers(BUNDESLIGA.value)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectLeague?.invoke(position)
        progressBarScorersFragment.show()
    }

    private fun runEpoxyController(scorersModel: ScorersModel) {
        epoxyScorersController = EpoxyScorersController(scorersModel)
        epoxyScorersController.setData(true)
        recyclerViewScorersFragment.setController(epoxyScorersController)
    }

    private fun choseLeague() {
        selectLeague = {
            when (it) {
                0 -> scorersViewModel.loadScorers(BUNDESLIGA.value)
                1 -> scorersViewModel.loadScorers(PREMIER_LEAGUE.value)
                2 -> scorersViewModel.loadScorers(PREMIERA_DIVISION.value)
                3 -> scorersViewModel.loadScorers(SERIE_A.value)
                4 -> scorersViewModel.loadScorers(LIGUE_1.value)
            }
        }
    }

    private fun onSuccess(
        startDate: String?,
        endDate: String?,
        scorersModel: ScorersModel
    ) {
        seasonScorersFragment.stringConnector(
            getString(R.string.scorers_title_text),
            startDate,
            endDate
        )
        runEpoxyController(scorersModel)
        competitionSpinnerScorersFragment.setVisible()
        progressBarScorersFragment.hide()
    }

    private fun onError(error: String) {
        constraintLayoutScorersFragment.showSnackBar(error)
    }

    private fun onException(exception: String) {
        constraintLayoutScorersFragment.showSnackBar(exception)
    }

    private fun logoutUser() {
        AuthUI.getInstance().signOut(requireContext())
        GlobalScope.launch {
            delay(100)
            runDestination(this@ScorersFragment, R.id.loginFragment)
        }
    }
}