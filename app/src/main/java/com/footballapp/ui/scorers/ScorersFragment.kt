package com.footballapp.ui.scorers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.footballapp.R
import com.footballapp.ext.stringConnector
import com.footballapp.net.ResponseCall
import com.footballapp.ui.scorers.epoxy.EpoxyScorersController
import kotlinx.android.synthetic.main.scorers_fragment.*
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
                        scorers_fragment_competition.text = it.data.competition.name
                        scorers_fragment_season.text = stringConnector(it.data.season?.startDate, it.data.season?.endDate)

                        epoxyScorersController = EpoxyScorersController(it.data)
                        epoxyScorersController.setData(true)
                        scorers_fragment_epoxy_recycler_view.setController(epoxyScorersController)
                    }

                    is ResponseCall.Error -> {
                        scorers_fragment_competition.text = it.message
                    }

                    is ResponseCall.Exception -> {
                        scorers_fragment_competition.text = it.exception.message
                    }
                }
            }
        )
        return inflate
    }
}