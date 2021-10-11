package com.example.heartcarelite.utils


import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener


object MyAutoCompleteSpinnerBinding {
    @JvmStatic
    @BindingAdapter("valueAttrChanged")
    fun MyAutoCompleteSpinner.setListener(listener: InverseBindingListener?) {
        this.onItemClickListener = if (listener != null) {
            AdapterView.OnItemClickListener { _, _, _, _ -> listener.onChange() }
        } else {
            null
        }
    }


    @JvmStatic
    @get:InverseBindingAdapter(attribute = "value")
    @set:BindingAdapter("value")
    var MyAutoCompleteSpinner.selectedValue: String?
        get() {
            return if (!text.isNullOrBlank()) {
                text.toString()
            } else {
                null
            }
        }
        set(value) {
            val newValue = value ?: adapter.getItem(0).toString()
            setText(newValue, false)
            if (adapter is ArrayAdapter<*>) {
                val position = (adapter as ArrayAdapter<String?>).getPosition(newValue)
                listSelection = position
                this.position =position
            }
        }


    @JvmStatic
    @BindingAdapter("entries", "itemLayout", "textViewId", requireAll = false)
    fun MyAutoCompleteSpinner.bindAdapter(entries: Array<String>, @LayoutRes itemLayout: Int?, @IdRes textViewId: Int?) {
        val adapter = when {
            itemLayout == null -> {
                ArrayAdapter(context, android.R.layout.simple_list_item_1, android.R.id.text1, entries)
            }
            textViewId == null -> {
                ArrayAdapter(context, itemLayout, entries)
            }
            else -> {
                ArrayAdapter(context, itemLayout, textViewId, entries)
            }
        }
        setAdapter(adapter)
    }

}