package ru.otus.otuskotlin.marketplace.common.models.item

import kotlin.jvm.JvmInline

@JvmInline
value class MkplItemId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = MkplItemId("")
    }
}
