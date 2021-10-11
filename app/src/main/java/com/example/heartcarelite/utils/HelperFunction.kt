package com.example.heartcarelite.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.*

object HelperFunction {

    fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>

    fun Int.toAgeDob(): Long {
        val cal = Calendar.getInstance()
        cal.add(Calendar.YEAR, -this)
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MILLISECOND, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        return cal.timeInMillis
    }

    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }
}