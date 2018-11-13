package com.inlacou.exinput.utils.extensions

import android.content.Context
import com.inlacou.exinput.R
import java.util.*
import timber.log.Timber
import java.text.SimpleDateFormat

/* From: http://stackoverflow.com/questions/4216745/java-string-to-date-conversion
		G       Era designator          Text                AD
		y       Year                    Year                1996; 96
		Y       Week year               Year                2009; 09
		M/L     Month in year           Month               July; Jul; 07
		w       Week in year            Number              27
		W       Week in month           Number              2
		D       Day in year             Number              189
		d       Day in month            Number              10
		F       Day of week in month    Number              2
		E       Day in week             Text                Tuesday; Tue
		u       Day number of week      Number              1
		a       Am/pm marker            Text                PM
		H       Hour in day (0-23)      Number              0
		k       Hour in day (1-24)      Number              24
		K       Hour in am/pm (0-11)    Number              0
		h       Hour in am/pm (1-12)    Number              12
		m       Minute in hour          Number              30
		s       Second in minute        Number              55
		S       Millisecond             Number              978
		z       Time zone               General time zone   Pacific Standard Time; PST; GMT-08:00
		Z       Time zone               RFC 822 time zone   -0800
		X       Time zone               ISO 8601 time zone  -08; -0800; -08:00
	 */

/* My List
	 %1$tD = date (day/month/year(2digits) format)
	 %1$td = day
	 %1$tm = month
	 %1$tM = minutes
	 %1$ty = year (2 digits)
	 %1$tY = year (4 digits)
	 %1$tI = hour 12h format
	 %1$tk = hour 24h format
	 %1$tH = hour 24h format
	 %1$Tp = PM or AM
	 %1$tp = pm or am
	 %1$tA = day as text (miércoles)
	 %1$ta = day as text (mié.)
	 %1$TA = day as text (MIÉRCOLES)
	 %1$Ta = day as text (MIË.)
	 %1$tB = month as text (julio)
	 %1$tb = month as text (jul.)
	 %1$th = month as text (jul.)
	 %1$TB = month as text (JULIO)
	 %1$Tb = month as text (JUL.)
  */

var Calendar.year: Int
	set(value) = set(Calendar.YEAR, value)
	get() = get(Calendar.YEAR)
var Calendar.month: Int
	set(value) = set(Calendar.MONTH, value)
	get() = get(Calendar.MONTH)
var Calendar.dayOfYear: Int
	set(value) = set(Calendar.DAY_OF_YEAR, value)
	get() = get(Calendar.DAY_OF_YEAR)
var Calendar.dayOfMonth: Int
	set(value) = set(Calendar.DAY_OF_MONTH, value)
	get() = get(Calendar.DAY_OF_MONTH)
var Calendar.dayOfWeek: Int
	set(value) = set(Calendar.DAY_OF_WEEK, value)
	get() = get(Calendar.DAY_OF_WEEK)
var Calendar.hour: Int
	set(value) = set(Calendar.HOUR_OF_DAY, value)
	get() = get(Calendar.HOUR_OF_DAY)
var Calendar.minute: Int
	set(value) = set(Calendar.MINUTE, value)
	get() = get(Calendar.MINUTE)
var Calendar.second: Int
	set(value) = set(Calendar.SECOND, value)
	get() = get(Calendar.SECOND)
var Calendar.millisecond: Int
	set(value) = set(Calendar.MILLISECOND, value)
	get() = get(Calendar.MILLISECOND)

fun Calendar.setYear(value: Int) : Calendar {
	set(Calendar.YEAR, value)
	return this
}

fun Calendar.setMonth(value: Int) : Calendar {
	set(Calendar.MONTH, value)
	return this
}

fun Calendar.setDayOfYear(value: Int) : Calendar {
	set(Calendar.DAY_OF_YEAR, value)
	return this
}

fun Calendar.setDayOfMonth(value: Int) : Calendar {
	set(Calendar.DAY_OF_MONTH, value)
	return this
}

fun Calendar.setDayOfWeek(value: Int) : Calendar {
	set(Calendar.DAY_OF_WEEK, value)
	return this
}

fun Calendar.setHours(value: Int) : Calendar {
	set(Calendar.HOUR_OF_DAY, value)
	return this
}

fun Calendar.setMinutes(value: Int) : Calendar {
	set(Calendar.MINUTE, value)
	return this
}

fun Calendar.setSeconds(value: Int) : Calendar {
	set(Calendar.SECOND, value)
	return this
}

fun Calendar.setMilliseconds(value: Int) : Calendar {
	set(Calendar.MILLISECOND, value)
	return this
}

fun Calendar.setTime(hour: Int, minute: Int): Calendar {
	val cal = Calendar.getInstance()
	cal.timeInMillis = this.timeInMillis
	cal.hour = hour
	cal.minute = minute
	return cal
}

fun Calendar.setMilliseconds(millis: Long): Calendar {
	this.timeInMillis = millis
	return this }

fun Long.toCalendar(): Calendar = Calendar.getInstance().setMilliseconds(this)

fun Calendar.isLeapYear() = ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0))

fun Calendar.compareDays(other: Calendar): Long {
	val ms = Math.abs(timeInMillis-other.timeInMillis)
	val seconds = ms/1000
	val minutes = seconds/60
	val hours = minutes/60
	return hours/24
}

fun Calendar.substractDays(number: Int): Calendar {
	this.add(Calendar.DAY_OF_YEAR, -number)
	return this }

fun Calendar.addDays(number: Int): Calendar {
	this.add(Calendar.DAY_OF_YEAR, number)
	return this }

fun Calendar.addMonths(number: Int): Calendar {
	this.add(Calendar.MONTH, number)
	return this }

fun Calendar.substractMonths(number: Int): Calendar {
	this.add(Calendar.MONTH, -number)
	return this }

fun Calendar.addYears(number: Int): Calendar {
	this.add(Calendar.YEAR, number)
	return this }

/**
 * This method sets an hour in the calendar object to 00:00:00:00
 *
 * @param calendar Calendar object which hour should be set to 00:00:00:00
 */
fun Calendar.toMidnight(): Calendar {
	set(Calendar.HOUR_OF_DAY, 0)
	set(Calendar.MINUTE, 0)
	set(Calendar.SECOND, 0)
	set(Calendar.MILLISECOND, 0)
	return this
}

/**
 * This checks if this is immediatePreviousMonth to passed calendar.
 *
 * @param calendar that must be directly posterior to this
 */
fun Calendar.immediatePreviousMonth(postCalendar: Calendar): Boolean {
	if(postCalendar.before(this)) return false
	val prevMonth = month
	var postMonth = postCalendar.month
	if(postMonth==0) postMonth += 12
	return postMonth-prevMonth==1
}

fun Calendar.sameMonth(other: Calendar): Boolean = year==other.year && month==other.month

fun Calendar.sameDay(other: Calendar): Boolean = year==other.year && month==other.month && dayOfYear==other.dayOfYear

fun Calendar.isDifferentDay(other: Calendar): Boolean = !sameDay(other)

/**
 * This method compares calendars using month and year
 *
 * @param this  First calendar object to compare
 * @param calendar Second calendar object to compare
 * @return Boolean value if first calendar is before the second one
 */
fun Calendar?.isMonthBefore(calendar: Calendar): Boolean {
	if (this == null) {
		return false
	}

	val firstDay = this.clone() as Calendar
	firstDay.toMidnight()
	firstDay.dayOfMonth = 1
	val secondDay = calendar.clone() as Calendar
	secondDay.toMidnight()
	secondDay.dayOfMonth = 1

	return firstDay.before(secondDay)
}

/**
 * This method compares calendars using month and year
 *
 * @param this  First calendar object to compare
 * @param calendar Second calendar object to compare
 * @return Boolean value if first calendar is after the second one
 */
fun Calendar?.isMonthAfter(calendar: Calendar): Boolean {
	if (this == null) {
		return false
	}

	val firstDay = this.clone() as Calendar
	firstDay.toMidnight()
	firstDay.dayOfMonth = 1
	val secondDay = calendar.clone() as Calendar
	secondDay.toMidnight()
	secondDay.dayOfMonth = 1

	return firstDay.after(secondDay)
}

// String utils

fun Long?.toTime(showSeconds: Boolean): String {
	Timber.d(".millisToTime (msg)", this.toString() + " | showSeconds " + showSeconds)
	if (this == null) {
		return ""
	}
	return if (showSeconds) {
		SimpleDateFormat("HH:mm:ss").format(Date(this))
	} else {
		val simpleFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)
		simpleFormat.timeZone = TimeZone.getTimeZone("Europe/Madrid")
		simpleFormat.format(Date(this))
	}
}

@JvmOverloads fun Long?.toDate(context: Context, monthAsText: Boolean = true): String {
	Timber.d(".millisToDate (msg)", this.toString() + "")
	if (this == null) {
		return ""
	}
	val calendar = Calendar.getInstance()
	calendar.timeInMillis = this
	return calendar.toDate(context, monthAsText)
}

fun Calendar?.toDateDebug(): String {
	if (this == null) {
		return ""
	}
	return "$year/$month/$dayOfMonth"
}

fun Calendar?.toDateTimeDebug(): String {
	if (this == null) {
		return ""
	}
	return "$year/$month/$dayOfMonth $hour:$minute"
}

fun Long?.toDateBigshowi(): String {
	if (this == null) {
		return ""
	}
	val cal = Calendar.getInstance()
	cal.timeInMillis = this
	//CAUTION IF I GET THE LOGS OFF IT DOES NOT APPLY THE TIMEZONE
	Timber.d("preTimeZone: ${cal.toDateTimeDebug()}")
	cal.timeZone = TimeZone.getTimeZone("UTC")
	//CAUTION IF I GET THE LOGS OFF IT DOES NOT APPLY THE TIMEZONE
	Timber.d("postTimeZone: ${cal.toDateTimeDebug()}")
	//CAUTION IF I GET THE LOGS OFF IT DOES NOT APPLY THE TIMEZONE
	Timber.d("postTimeZone | returning: ${"$cal.year-${cal.month+1}-${cal.dayOfMonth}T${cal.hour}:${cal.minute}:${cal.second}.${cal.millisecond.toStringMinDigits(3)}"}}")
	return "${cal.year}-${cal.month+1}-${cal.dayOfMonth}T${cal.hour}:${cal.minute}:${cal.second}.${cal.millisecond.toStringMinDigits(3)}"
}

fun Long?.toDateBigshowiShort(): String {
	if (this == null) {
		return ""
	}
	val cal = Calendar.getInstance()
	cal.timeInMillis = this
	return "${cal.year}-${cal.month+1}-${cal.dayOfMonth}"
}

fun Calendar?.toDateBigshowi(): String {
	if(this==null){
		return ""
	}
	//CAUTION IF I GET THE LOGS OFF IT DOES NOT APPLY THE TIMEZONE
	Timber.d("preTimeZone: ${toDateTimeDebug()}")
	timeZone = TimeZone.getTimeZone("UTC")
	//CAUTION IF I GET THE LOGS OFF IT DOES NOT APPLY THE TIMEZONE
	Timber.d("postTimeZone: ${toDateTimeDebug()}")
	//CAUTION IF I GET THE LOGS OFF IT DOES NOT APPLY THE TIMEZONE
	Timber.d("postTimeZone | returning: ${"$year-${month+1}-${dayOfMonth}T$hour:$minute:$second.${millisecond.toStringMinDigits(3)}"}}")
	return "$year-${month+1}-${dayOfMonth}T$hour:$minute:$second.${millisecond.toStringMinDigits(3)}"
}

fun Calendar?.toDateBigshowiShort(): String {
	if(this==null){
		return ""
	}
	val month = month+1
	return "$year-${month.toStringMinDigits(2)}-${dayOfMonth.toStringMinDigits(2)}"
}

@JvmOverloads fun Calendar?.toDate(context: Context, monthAsText: Boolean = true): String {
	if (this == null) {
		return ""
	}
	return if (monthAsText) {
		String.format(context.resources.getString(R.string.expinput_date), this)
	} else {
		String.format(context.resources.getString(R.string.expinput_date_month_as_number), this)
	}
}

fun Long?.toDateTime(context: Context, separator: String = ", ", dayOfWeek: Boolean = false, monthAsNumber: Boolean = true): String {
	if (this == null) {
		return ""
	}
	val calendar = Calendar.getInstance()
	calendar.timeInMillis = this
	return calendar.toDateTime(context, separator, dayOfWeek, monthAsNumber)
}

fun Calendar?.toTime(showSeconds: Boolean): String {
	if (this == null) {
		return ""
	}
	return timeInMillis.toTime(showSeconds)
}

fun Calendar?.toDateTime(context: Context, separator: String = ", ", dayOfWeek: Boolean = false, monthAsNumber: Boolean = true): String {
	if (this == null) {
		return ""
	}
	return if(dayOfWeek) {
		if(monthAsNumber){
			String.format(context.resources.getString(R.string.expinput_datetime_day_of_week_month_as_number), this, separator)
		}else{
			String.format(context.resources.getString(R.string.expinput_datetime_day_of_week), this, separator)
		}
	}else if(monthAsNumber) {
		String.format(context.resources.getString(R.string.expinput_datetime_month_as_number), this, separator)
	}else{
		String.format(context.resources.getString(R.string.expinput_datetime), this, separator)
	}
}

/**
 * WARNING! Use Calendar.MONDAY, Calendar.FRIDAY, etc.
 * If you do not want to, MONDAY is 2 and SUNDAY is 1. The week starts on sunday
 */
fun Calendar.numOfDaysOfWeeks(positionOnWeek: Int): Int {
	Calendar.MONDAY
	this.dayOfMonth = 1
	var acc = 0
	val initialMonth = month
	do {
		if(dayOfWeek == positionOnWeek) {
			Timber.d("$positionOnWeek ding: ${toDateDebug()}")
			acc += 1
		}else {
			Timber.d("$positionOnWeek current: ${toDateDebug()}")
		}
		dayOfMonth += 1
	}while (month==initialMonth)
	Timber.d("$positionOnWeek finally: ${toDateDebug()}")
	return acc
}