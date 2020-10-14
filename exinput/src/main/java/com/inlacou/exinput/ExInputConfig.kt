package com.inlacou.exinput

import java.util.*

object ExInputConfig {

	val toDate: (Calendar) -> String = { "${it.get(Calendar.DAY_OF_MONTH).toStringMinDigits()}/${(it.get(Calendar.MONTH)+1).toStringMinDigits()}/${it.get(Calendar.YEAR)}" }
	val toTime: (Calendar) -> String = { "${it.get(Calendar.HOUR_OF_DAY).toStringMinDigits()}:${it.get(Calendar.MINUTE).toStringMinDigits()}" }
	val toDateTime: (Calendar) -> String = { "${toDate.invoke(it)} ${toTime.invoke(it)}" }

}

private fun Int.toStringMinDigits(minDigits: Int = 2): String {
	var result = this.toString()
	while (result.length<minDigits) {
		result = "0$result"
	}
	return result
}
