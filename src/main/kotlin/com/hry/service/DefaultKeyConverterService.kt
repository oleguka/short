package com.hry.service

import org.springframework.stereotype.Component

@Component
class DefaultKeyConverterService : KeyConverterService {

    private val chars = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890-_".toCharArray()
    val charToLong = (chars.indices).associate { i -> Pair(chars[i], i.toLong()) }

    override fun idToKey(id: Long): String {
        var n = id
        val builder = StringBuilder()
        while (n != 0L) {
            builder.append(chars[(n % chars.size).toInt()])
            n /= chars.size
        }
        return builder.reverse().toString()
    }

    override fun keyToId(key: String) = key
        .map { c -> charToLong[c]!! }
        .fold(0L) { a, b -> a * chars.size + b }
}