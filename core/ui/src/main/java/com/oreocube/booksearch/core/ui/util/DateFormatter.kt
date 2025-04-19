package com.oreocube.booksearch.core.ui.util

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Long.toFormattedDate(pattern: String, locale: Locale = Locale.getDefault()): String {
    return runCatching {
        val formatter = DateTimeFormatter.ofPattern(pattern, locale)
        val instant = Instant.ofEpochMilli(this)
        val zoneId = ZoneId.systemDefault()
        val localDateTime = instant.atZone(zoneId).toLocalDateTime()
        formatter.format(localDateTime)
    }.getOrDefault("")
}
