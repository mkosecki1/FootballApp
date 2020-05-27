package com.footballapp.ui.scorers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.firebase.ui.auth.AuthUI
import com.footballapp.R
import com.footballapp.ext.runDestination
import com.footballapp.ext.setVisible
import com.footballapp.ext.showSnackBar
import com.footballapp.ext.stringConnector
import com.footballapp.model.ScorersModel
import com.footballapp.net.ResponseCall
import com.footballapp.ui.scorers.epoxy.EpoxyScorersController
import kotlinx.android.synthetic.main.scorers_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScorersFragment : Fragment() {

    private val scorersViewModel by viewModel<ScorersViewModel>()
    private lateinit var epoxyScorersController: EpoxyScorersController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.scorers_fragment, container, false)
        scorersViewModel.loadScorers()

        scorersViewModel.scorersStatus.observe(
            viewLifecycleOwner, Observer {
                when (it) {
                    is ResponseCall.Success -> {
                        onSuccess(
                            it.data.competition.name,
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

        goToStandingsButtonScorersFragment.setOnClickListener {
            runDestination(this, R.id.standingsFragment)
        }

        logoutButtonScorersFragment.setOnClickListener {
            logoutUser()
        }
    }

    private fun runEpoxyController(scorersModel: ScorersModel) {
        epoxyScorersController = EpoxyScorersController(scorersModel)
        epoxyScorersController.setData(true)
        recyclerViewScorersFragment.setController(epoxyScorersController)
    }

    private fun onSuccess(
        name: String?,
        startDate: String?,
        endDate: String?,
        scorersModel: ScorersModel
    ) {
        competitionScorersFragment.stringConnector(
            name,
            getString(R.string.scorers_title_text),
            null
        )
        seasonScorersFragment.stringConnector(
            getString(R.string.season_title_text),
            startDate,
            endDate
        )
        runEpoxyController(scorersModel)
        progressBarScorersFragment.hide()
        logoScorersFragment.setVisible()
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