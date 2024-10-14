package ru.otus.otuskotlin.marketplace.common.models.order

import kotlin.jvm.JvmInline

@JvmInline
value class MkplOrderLock(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = MkplOrderLock("")
    }
}
