package za.co.codevue.shared.exceptions

internal class ServerException(
    val code: Int = 0,
    val errorCode: String? = "",
    val errorMessage: String?
) : Exception(errorMessage)