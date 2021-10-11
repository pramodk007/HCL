package com.example.heartcarelite.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.chip.ChipGroup

object ChipGroupBindingAdapter {
    @JvmStatic
    @BindingAdapter(
        value = ["android:onCheckedChanged", "android:checkedButtonAttrChanged"],
        requireAll = false
    )
    fun setChipsListeners(
        view: ChipGroup?, listener: ChipGroup.OnCheckedChangeListener?,
        attrChange: InverseBindingListener?
    ) {
        view?.setOnCheckedChangeListener { group, checkedId ->
            view.context.hideKeyboard(view)
            listener?.onCheckedChanged(group, checkedId)
            attrChange?.onChange()
        }
    }
}

fun Context.hideKeyboard(view: View) {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}