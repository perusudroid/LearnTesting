package com.androidsolution.learntesting.common

data class ResultOf<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): ResultOf<T> {
            return ResultOf(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): ResultOf<T> {
            return ResultOf(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): ResultOf<T> {
            return ResultOf(Status.LOADING, data, null)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}