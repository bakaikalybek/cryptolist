package kg.bakai.cryptolist.utils

import android.content.Context
import okhttp3.Response
import retrofit2.HttpException

/**
 * Sealed class of HTTP result
 */
@Suppress("unused")
sealed class Result<out T : Any> {
    /**
     * Successful result of request without errors
     */
    class Ok<out T : Any>(
        val value: T,
        override val response: Response
    ) : Result<T>(),
        ResponseResult

    /**
     * HTTP error
     */
    class Error(
        override val exception: HttpException,
        override val response: Response
    ) : Result<Nothing>(),
        ErrorResult,
        ResponseResult

    /**
     * Network exception occurred talking to the server or when an unexpected
     * exception occurred creating the request or processing the response
     */
    class Exception(
        override val exception: Throwable
    ) : Result<Nothing>(), ErrorResult {
        fun getExceptionMessage(context: Context): String {
            return if (exception is TranslatableException) {
                exception.getTranslatableMessage(context)
            } else {
                exception.localizedMessage!!
            }
        }
    }
}

interface ResponseResult {
    val response: Response
}

/**
 * Interface for [Result] classes that contains [Throwable]: [Result.Error] and [Result.Exception]
 */
interface ErrorResult {
    val exception: Throwable
}
