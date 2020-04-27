package com.footballapp.ui.scorers.epoxy

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.footballapp.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class ScorersLayoutView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {
    private val rootLayout: ConstraintLayout
    private val name: TextView
    private val team: TextView
    private val score: TextView
    private val placeNumber: TextView

    init {
        View.inflate(context, R.layout.scorers_layout, this)
        rootLayout = findViewById(R.id.scorersLayout)
        name = findViewById(R.id.nameScorersLayout)
        team = findViewById(R.id.teamScorersLayout)
        score = findViewById(R.id.goalsScorersLayout)
        placeNumber = findViewById(R.id.placeNumberScorersLayout)
    }

    @TextProp
    fun setName(name: CharSequence) {
        this.name.text = name
    }

    @TextProp
    fun setTeam(team: CharSequence) {
        this.team.text = team
    }

    @TextProp
    fun setScore(score: CharSequence) {
        this.score.text = score
    }

    @TextProp
    fun setPlaceNumber(placeNumber: CharSequence) {
        this.placeNumber.text = placeNumber
    }

    @CallbackProp
    fun setItemClickListener(listener: OnClickListener?) {
        rootLayout.setOnClickListener(listener)
    }
}