package za.co.codevue.shared.extensions

import timber.log.Timber
import za.co.codevue.shared.exceptions.ServerException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Exception mapper
 */
fun Throwable.mapExceptionMessage(): String {
    logException(this.javaClass.name, message, this)
    val defaultErrorMessage = "An unexpected error has occurred, please try again."
    return when (this) {
        is ServerException -> errorMessage ?: defaultErrorMessage
        is ConnectException,
        is UnknownHostException -> "Please check your internet settings and try again."
        is SocketTimeoutException -> "Request timed out, please try again."
        else -> defaultErrorMessage
    }
}

/** Logs an exception with Timber */
private fun logException(tag: String, message: String?, throwable: Throwable?) {
    Timber.tag(tag).e(throwable, message)
}