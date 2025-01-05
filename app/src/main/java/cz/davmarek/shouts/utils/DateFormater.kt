package cz.davmarek.shouts.utils

import java.time.format.DateTimeFormatter
import java.util.Date

fun formatDateForUI(date: Date): String {
    val zonedDateTime = date.toInstant().atZone(java.time.ZoneId.systemDefault()) // Convert to local timezone
    val formatter = DateTimeFormatter.ofPattern("HH:mm · MMM d, yyyy") // Desired format
    return zonedDateTime.format(formatter)
}