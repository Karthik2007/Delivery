package com.karthik.delivery.base.util


/**
 * created by Karthik A on 11/03/19
 */
sealed class Failure {
    object NoNetworkConnection : Failure()
    object ServerError : Failure()
}