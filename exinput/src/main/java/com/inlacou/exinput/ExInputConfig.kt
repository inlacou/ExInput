package com.inlacou.exinput

import java.util.*

object ExInputConfig {

	val toDate: (Calendar) -> String = { "${it.get(Calendar.DAY_OF_MONTH)}/${it.get(Calendar.MONTH)}/${it.get(Calendar.YEAR)}" }
	val toTime: (Calendar) -> String = { "${it.get(Calendar.HOUR_OF_DAY)}:${it.get(Calendar.MINUTE)}" }
	val toDateTime: (Calendar) -> String = { "${toDate.invoke(it)} ${toTime.invoke(it)}" }

}