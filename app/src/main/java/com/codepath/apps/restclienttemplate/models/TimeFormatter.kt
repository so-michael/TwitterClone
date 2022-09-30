package com.codepath.apps.restclienttemplate.models

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class TimeFormatter {
	fun getTimeDifference(rawJsonDate: String?): String? {
		var time = ""
		val twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy"
		val format = SimpleDateFormat(twitterFormat, Locale.ENGLISH)
		format.setLenient(true)
		try {
			val diff: Long =
				(System.currentTimeMillis() - format.parse(rawJsonDate).getTime()) / 1000
			time =
				if (diff < 5) "Just now" else if (diff < 60) String.format(
					Locale.ENGLISH,
					"%ds",
					diff
				) else if (diff < 60 * 60) String.format(
					Locale.ENGLISH,
					"%dm",
					diff / 60
				) else if (diff < 60 * 60 * 24) String.format(
					Locale.ENGLISH,
					"%dh",
					diff / (60 * 60)
				) else if (diff < 60 * 60 * 24 * 30) String.format(
					Locale.ENGLISH,
					"%dd",
					diff / (60 * 60 * 24)
				) else {
					val now: Calendar = Calendar.getInstance()
					val then: Calendar = Calendar.getInstance()
					then.setTime(format.parse(rawJsonDate))
					if (now.get(Calendar.YEAR) === then.get(Calendar.YEAR)) {
						(java.lang.String.valueOf(then.get(Calendar.DAY_OF_MONTH)).toString() + " "
								+ then.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US))
					} else {
						(java.lang.String.valueOf(then.get(Calendar.DAY_OF_MONTH)).toString() + " "
								+ then.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US)
								+ " " + java.lang.String.valueOf(then.get(Calendar.YEAR) - 2000))
					}
				}
		} catch (e: ParseException) {
			e.printStackTrace()
		}
		return time
	}
}