package com.footballapp.ui.scorers.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.footballapp.model.ScorersModel

class EpoxyScorersController(
    private val myList: ScorersModel
) :
    TypedEpoxyController<Boolean>() {

    override fun buildModels(data: Boolean?) {
        myList.scorers.forEachIndexed { index, scorer ->
            scorersLayoutView {
                id(index)
                name(scorer.player.name)
                team(scorer.team.name)
                score(scorer.numberOfGoals.toString() + GOALS)
            }
        }
    }

    companion object {
        const val GOALS = " goals"
    }
}