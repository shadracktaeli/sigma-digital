package za.co.codevue.shared.extensions

/**
 * Returns the value of [String] if not null or a blank [String]
 */
fun String?.valueOrDefault() = this ?: ""