package com.example.heartcarelite.utils

import android.app.DatePickerDialog
import android.content.Context
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableLong
import com.google.android.material.textfield.TextInputEditText

import java.util.*


object DatePickerDataBinding {
    @JvmStatic
    @BindingAdapter(value = ["app:datePick", "app:maxDate", "app:minDate"], requireAll = false)
    fun bindTextInputEditTextDateClicks(
        textInputEditText: TextInputEditText,
        date: ObservableLong?,
        maxDate: ObservableLong?,
        minDate: ObservableLong?
    ) {
        textInputEditText.setOnClickListener { selectDate(textInputEditText.context, date, maxDate, minDate) }
    }

    @JvmStatic
    @BindingAdapter(value = ["app:textDate", "app:nullText"], requireAll = false)
    fun bindTextViewDate(
        textView: TextView,
        date: Long?,
        nullText: String?
    ) {
        if (date != null) {
            textView.text = date.toDate()
        } else if (nullText != null) {
            textView.text = nullText
        }else{
            textView.text = ""
        }
    }

    @JvmStatic
    @BindingAdapter("app:textTime")
    fun bindTextViewTime(
        textView: TextView,
        date: Long?
    ) {
        if (date != null) {
            textView.text = date.toTime()
        } else{
            textView.text = ""
        }
    }


    private fun selectDate(
        context: Context?,
        date: ObservableLong?,
        maxDate: ObservableLong?,
        minDate: ObservableLong?
    ) {
        val calBefore: Calendar = Calendar.getInstance()
        if (date?.get() != null && date.get() !=0L) calBefore.timeInMillis = date.get()
        val dialog = context?.let {
            DatePickerDialog(
                it,
                { _, year, monthOfYear, dayOfMonth ->
                    val cal: Calendar = Calendar.getInstance()
                    cal.set(Calendar.YEAR, year)
                    cal.set(Calendar.MONTH, monthOfYear)
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    date!!.set(cal.timeInMillis)
                    date.notifyChange()
                },
                calBefore.get(Calendar.YEAR),
                calBefore.get(Calendar.MONTH),
                calBefore.get(Calendar.DAY_OF_MONTH)
            )
        }
        if (minDate?.get() != null) dialog!!.datePicker.minDate =
            minDate.get()
        if (maxDate?.get() != null) {
            dialog!!.datePicker.maxDate = maxDate.get()
        }else{
            dialog!!.datePicker.maxDate = System.currentTimeMillis()
        }
        dialog?.setTitle("Select a date")
        dialog?.show()
    }
}