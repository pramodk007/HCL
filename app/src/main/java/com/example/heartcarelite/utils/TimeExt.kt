package com.example.heartcarelite.utils

import java.text.SimpleDateFormat
import java.util.*

fun Long.toAge(): Int {
    val dob = Calendar.getInstance()
    val today = Calendar.getInstance()

    dob.timeInMillis = this

    var age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)

    if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
        age--
    }
    return age
}

fun Long.toAge(from:Long): Int {
    val dob = Calendar.getInstance()
    val today = Calendar.getInstance()
    today.timeInMillis = from
    dob.timeInMillis = this

    var age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)

    if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
        age--
    }
    return age
}
fun Long.toDate(): String {
    return if (this != 0.toLong()) {
        val formatter = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = this
        formatter.format(calendar.time)
    } else {
        ""
    }
}

fun Long.toPatientDate(): String {
    return if (this != 0.toLong()) {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = this
        formatter.format(calendar.time)
    } else {
        ""
    }
}

fun Long.toGraphDate(): String {
    return if (this != 0.toLong()) {
        val formatter = SimpleDateFormat("dd-MMM-yy", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = this
        formatter.format(calendar.time)
    } else {
        ""
    }
}

fun Long.toVisitDate(): String {
    return if (this != 0.toLong()) {
        val formatter = SimpleDateFormat("dd-MMM-yyyy, HH:mm", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = this
        formatter.format(calendar.time)
    } else {
        ""
    }
}
fun Long.toFileDate(): String {
    return if (this != 0.toLong()) {
        val formatter = SimpleDateFormat("dd_MMM_yyyy_HH_mm", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = this
        formatter.format(calendar.time)
    } else {
        ""
    }
}
fun Long.toSettingDate(): String {
    return if (this != 0.toLong()) {
        val formatter = SimpleDateFormat("MMM dd, yyyy, HH:mm", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = this
        formatter.format(calendar.time)
    } else {
        ""
    }
}

fun Long.toTime(): String {
    return if (this > 0) {
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = this
        val time = formatter.format(calendar.time)
        if(time=="00:00") "" else time
    } else {
        ""
    }
}


fun Long.toScheduleTime(): String {
    if (this > 0) {
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = this
        return formatter.format(calendar.time)
    } else {
        return ""
    }
}

fun Long.toDayInMonth(): String {
    if (this > 0) {
        val formatter = SimpleDateFormat("dd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = this
        return formatter.format(calendar.time)
    } else {
        return ""
    }
}

fun Long.toDayNameInWeek(): String {
    if (this > 0) {
        val formatter = SimpleDateFormat("EEE", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = this
        return formatter.format(calendar.time)
    } else {
        return ""
    }
}

fun getTodayDateInMillis(): Long {
    val now = Calendar.getInstance()
    now[Calendar.HOUR_OF_DAY] = 0
    now[Calendar.MINUTE] = 0
    now[Calendar.SECOND] = 0
    now[Calendar.MILLISECOND] =0
    return now.timeInMillis
}

fun Long.milliWithoutTime(): Long {
    val now = Calendar.getInstance()
    now.timeInMillis = this
    now[Calendar.HOUR_OF_DAY] = 0
    now[Calendar.MINUTE] = 0
    now[Calendar.SECOND] = 0
    now[Calendar.MILLISECOND] =0
    return now.timeInMillis
}

fun String.toTimeInMilli(): Long {
    val myDate = this
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val date = sdf.parse(myDate)
    return date.time
}