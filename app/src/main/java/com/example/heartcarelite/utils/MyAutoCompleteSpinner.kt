package com.example.heartcarelite.utils

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent

class MyAutoCompleteSpinner : androidx.appcompat.widget.AppCompatAutoCompleteTextView {

    var position = -1
    constructor(context: Context) : this(context, null)

    constructor(arg0: Context, arg1: AttributeSet?) : super(arg0, arg1)

    constructor(arg0: Context, arg1: AttributeSet, arg2: Int) : super(arg0, arg1, arg2)

    init {
        isCursorVisible = false
        setEnableSpinner(false)

        setTextColor(Color.BLACK)
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)

        setEnableSpinner(false)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d("spinner",event?.action.toString())
        when (event?.action) {
            MotionEvent.ACTION_MOVE -> {
                setEnableSpinner(true)
            }
            MotionEvent.ACTION_UP -> {
                setEnableSpinner(true)
            }
            MotionEvent.ACTION_DOWN -> {
                setEnableSpinner(true)
            }
        }

        if(event?.action == MotionEvent.ACTION_UP) {
            if(event.rawX <= totalPaddingLeft) {
                setEnableSpinner(true)
                return true
            }
        }

        return super.onTouchEvent(event)
    }

    private fun setEnableSpinner(enable: Boolean){
        this.isEnabled = enable

    }

    override fun performFiltering(text: CharSequence?, keyCode: Int) {
        super.performFiltering(null, keyCode)
    }
}