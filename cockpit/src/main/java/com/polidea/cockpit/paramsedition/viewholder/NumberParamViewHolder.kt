package com.polidea.cockpit.paramsedition.viewholder

import androidx.appcompat.widget.AppCompatEditText
import android.view.View
import com.polidea.cockpit.R
import com.polidea.cockpit.core.CockpitParam
import com.polidea.cockpit.utils.TextWatcherAdapter

internal abstract class NumberParamViewHolder<T : Number>(view: View) : ParamBaseValueWithRestoreViewHolder<T>(view) {

    private val value: AppCompatEditText = view.findViewById(R.id.cockpit_number_param_value)

    init {
        value.addTextChangedListener(object : TextWatcherAdapter() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                valueChangeListener?.invoke(convertStringToNumber(s?.toString()))
            }
        })
    }

    override fun displayParam(param: CockpitParam<T>) {
        super.displayParam(param)
        value.setText(param.value.toString())
    }

    protected abstract fun convertStringToNumber(stringValue: String?): T
}