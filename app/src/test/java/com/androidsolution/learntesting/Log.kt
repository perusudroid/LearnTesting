package com.androidsolution.learntesting

open class Log {
    companion object {
        fun d(tag: String, msg: String): Int {
            println("DEBUG: $tag: $msg")
            return 0
        }

        @JvmStatic
        fun i(tag: String, msg: String): Int {
            println("INFO: $tag: $msg")
            return 0
        }

        @JvmStatic
        fun w(tag: String, msg: String): Int {
            println("WARN: $tag: $msg")
            return 0
        }

        @JvmStatic
        fun w(tag: String, msg: String, exception: Throwable): Int {
            println("WARN: $tag: $msg , $exception")
            return 0
        }

        @JvmStatic
        fun e(tag: String, msg: String): Int {
            println("ERROR: $tag: $msg")
            return 0
        }
    }
}