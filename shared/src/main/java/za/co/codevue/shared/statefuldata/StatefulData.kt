package za.co.codevue.shared.domain.statefuldata

import za.co.codevue.shared.extensions.valueOrDefault

sealed class StatefulData<out T> {
    data class Success<T>(val data: T) : StatefulData<T>()
    data class Error(val message: String) : StatefulData<Nothing>()
    object Loading : StatefulData<Nothing>()
}

/**
 * `true` if [StatefulData] is of type [StatefulData.Error]
 */
val StatefulData<*>.error
    get() = this is StatefulData.Error

/**
 * `true` if [StatefulData] is of type [StatefulData.Loading].
 */
val StatefulData<*>.loading
    get() = this is StatefulData.Loading

/**
 * `true` if [StatefulData] is of type [StatefulData.Success] & holds non-null [StatefulData.Success.data].
 */
val StatefulData<*>.success
    get() = this is StatefulData.Success && data != null

/**
 * Returns the data of [StatefulData.Success].
 */
val <T> StatefulData<T>.data: T?
    get() = (this as? StatefulData.Success)?.data


/**
 * Returns the message of [StatefulData.Error].
 */
val <T> StatefulData<T>.message: String
    get() = (this as? StatefulData.Error)?.message.valueOrDefault()

/**
 * Returns the data of [StatefulData.Success] or the supplied [value] as the result.
 */
fun <T> StatefulData<T>.dataOr(value: T): T {
    return (this as? StatefulData.Success)?.data ?: value
}

