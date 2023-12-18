package kz.just_code.devmuscles.utilities

import okhttp3.Interceptor
import okhttp3.Response

import okhttp3.*
import java.io.IOException
import java.net.SocketTimeoutException

class RetryInterceptor : Interceptor {
    private val maxRetries = 3
    private var tryCount = 0

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var response = execute(chain, request)
        tryCount = 0

        while (!response.isSuccessful && tryCount < maxRetries) {
            tryCount++
            response.close()

            request = chain.request()
            response = execute(chain, request)
        }

        return response
    }

    @Throws(IOException::class)
    private fun execute(chain: Interceptor.Chain, request: Request): Response {
        try {
            return chain.proceed(request)
        } catch (e: IOException) {
            if (e is SocketTimeoutException && tryCount < maxRetries) {
                return execute(chain, request)
            }
            throw e
        }
    }
}