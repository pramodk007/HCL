package com.example.heartcarelite.utils

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.example.heartcarelite.utils.DigitsInputFilter


@BindingAdapter("app:errorText")
fun setErrorMessage(view: TextInputLayout, errorMessage: String?) {
    view.error = errorMessage
}

@BindingAdapter("app:errorText")
fun setErrorMessage(view: TextInputLayout, errorMessage: Int?) {
    view.error = errorMessage?.let { view.context.getString(errorMessage) }
}


@BindingAdapter(
    value = ["app:digit_before_decimal", "app:digit_after_decimal", "app:max_value"],
    requireAll = false
)
fun bindTextInputEditTextDateClicks(
    textInputEditText: TextInputEditText,
    mMaxIntegerDigitsLength: Int?,
    mMaxDigitsAfterLength: Int?,
    mMax: Number?
) {
    textInputEditText.filters = arrayOf(
        DigitsInputFilter(
            mMaxIntegerDigitsLength ?: 0,
            mMaxDigitsAfterLength ?: 0,
            mMax?.toDouble() ?: 0.0
        )
    )
}


@BindingAdapter("app:helperText")
fun setHelperMessage(view: TextInputLayout, helperText: Int?) {
    view.helperText = helperText?.let { view.context.getString(helperText) }
}