package za.co.codevue.shared.extensions

import org.ocpsoft.prettytime.PrettyTime
import java.text.SimpleDateFormat
import java.util.*

private const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

/**
 * Returns the value of [String] if not null or a blank [String]
 */
fun String?.valueOrDefault() = this ?: ""

/**
 * Converts a datetime string to [Date] with timezone
 */
fun String.toDate(): Date {
    val dateFormat = SimpleDateFormat(
        DATE_FORMAT,
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

/**
 * Adds an hour to a datetime [String]
 */
internal fun String.modifyDate(): String {
    val dateFormat = SimpleDateFormat(
        DATE_FORMAT,
        Locale.getDefault()
    )

    return try {
        val date = Calendar.getInstance().apply {
            time = this@modifyDate.toDate()
            add(Calendar.HOUR, 1)
        }.time
        dateFormat.format(date)
    } catch (e: Exception) {
        this
    }
}