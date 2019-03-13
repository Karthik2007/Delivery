package com.karthik.delivery.base.network.interceptor

import com.karthik.delivery.base.network.ConnectionHandler
import okhttp3.Interceptor
import okhttp3.Response


/**
 *
 *
 * Interceptor which request to fetch result from cache if there is no internet
 *
 * created by Karthik A on 12/03/19
 */
class CacheInterceptor(var connectionHandler: ConnectionHandler) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()

        request = when (connectionHandler.isConnected) {
            true -> request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
            false ->
                request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=$CACHE_SINCE").build()
        }

        return chain.proceed(request)
    }

    private companion object {
        const val CACHE_SINCE = 60 * 60 * 24 * 7  // 7 days
    }

}

