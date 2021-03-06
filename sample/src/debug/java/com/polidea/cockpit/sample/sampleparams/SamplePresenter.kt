package com.polidea.cockpit.sample.sampleparams

import android.graphics.Color
import com.polidea.cockpit.cockpit.Cockpit
import com.polidea.cockpit.event.ActionRequestCallback
import com.polidea.cockpit.event.PropertyChangeListener
import com.polidea.cockpit.sample.BuildConfig
import com.polidea.cockpit.sample.Style
import com.polidea.cockpit.sample.model.SampleModel

class SamplePresenter(override val sampleView: SampleContract.View, override val sampleModel: SampleModel)
    : SampleBasePresenter(sampleView, sampleModel), SampleContract.Presenter {

    private lateinit var onStyleChangeListener: PropertyChangeListener<String>
    private lateinit var onTotalPriceFontSizeChangeListener: PropertyChangeListener<Int>
    private lateinit var resetCountCallback: ActionRequestCallback
    private lateinit var onHeadingTextChangeListener: PropertyChangeListener<String>
    private lateinit var onFooterColorChangeListener: PropertyChangeListener<String>
    private lateinit var onShowFooterChangeListener: PropertyChangeListener<Boolean>
    private lateinit var onFooterChangeListener: PropertyChangeListener<String>

    init {
        sampleView.presenter = this
    }

    override fun start() {
        super.start()
        setOnChangeListeners()
        setCallbacks()
        setOnValueSelectedListener()
        setReadOnlyProperties()
    }

    private fun setReadOnlyProperties() {
        Cockpit.setAppVersion(BuildConfig.VERSION_NAME)
    }

    private fun setCallbacks() {
        resetCountCallback = ActionRequestCallback {
            sampleModel.reset()
            updateAll()
        }
        Cockpit.addResetCountActionRequestCallback(resetCountCallback)
    }

    override fun stop() {
        removeOnChangeListeners()
        removeCallbacks()
        removeOnValueSelectedListeners()
    }

    private fun removeCallbacks() {
        Cockpit.removeResetCountActionRequestCallback(resetCountCallback)
    }

    private fun removeOnValueSelectedListeners() {
        Cockpit.removeOnStyleChangeListener(onStyleChangeListener)
    }

    override fun shakeDetected() {
        sampleView.showCockpitUi()
    }

    private fun setOnValueSelectedListener() {
        onStyleChangeListener = PropertyChangeListener { _, selectedValue ->
            sampleView.setStyle(Style.forValue(selectedValue))
        }
        Cockpit.addOnStyleChangeListener(onStyleChangeListener)
    }

    private fun setOnChangeListeners() {
        onTotalPriceFontSizeChangeListener = PropertyChangeListener { _, newValue ->
            sampleView.setTotalPriceFontSize(newValue.toFloat())
        }
        Cockpit.addOnTotalPriceFontSizeChangeListener(onTotalPriceFontSizeChangeListener)

        onHeadingTextChangeListener = PropertyChangeListener { _, newValue ->
            sampleView.setHeadingText(newValue)
        }
        Cockpit.addOnHeadingTextChangeListener(onHeadingTextChangeListener)

        onFooterColorChangeListener = PropertyChangeListener { _, newValue ->
            sampleView.setFooterTextColor(Color.parseColor(newValue))
        }
        Cockpit.addOnFooterFontColorChangeListener(onFooterColorChangeListener)

        onShowFooterChangeListener = PropertyChangeListener { _, newValue ->
            sampleView.showFooter(newValue)
        }
        Cockpit.addOnShowFooterChangeListener(onShowFooterChangeListener)

        onFooterChangeListener = PropertyChangeListener { _, newValue ->
            sampleView.setFooterText(newValue)
        }
        Cockpit.addOnFooterChangeListener(onFooterChangeListener)
    }

    private fun removeOnChangeListeners() {
        Cockpit.removeOnTotalPriceFontSizeChangeListener(onTotalPriceFontSizeChangeListener)
        Cockpit.removeOnHeadingTextChangeListener(onHeadingTextChangeListener)
        Cockpit.removeOnFooterFontColorChangeListener(onFooterColorChangeListener)
        Cockpit.removeOnShowFooterChangeListener(onShowFooterChangeListener)
        Cockpit.removeOnFooterChangeListener(onFooterChangeListener)
    }
}