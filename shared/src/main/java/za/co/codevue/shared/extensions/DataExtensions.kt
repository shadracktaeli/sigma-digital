package za.co.codevue.shared.extensions

import org.ocpsoft.prettytime.PrettyTime
import java.text.SimpleDateFormat
import java.util.*

/**
 * Returns the value of [String] if not null or a blank [String]
 */
fun String?.valueOrDefault() = this ?: ""

/**
 * Converts a datetime string to [Date] with timezone
 */
fun String.toDate(): Date {
    val dateFormat = SimpleDateFormat(
        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
        Locale.getDefault()
    )
    dateFormat.timeZone = TimeZone.getTimeZone("GMT")
    return try {
        dateFormat.parse(this) ?: Calendar.getInstance().time
    } catch (e: Exception) {
        // default to now
        Calendar.getInstance().time
    }
}

/**
 * Formats a [Date] to pretty time format i.e `10 minutes ago`
 */
fun Date.toPrettyDate(): String = PrettyTime().format(this)
