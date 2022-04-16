package com.hry

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ShortApplication

fun main(args: Array<String>) {
    runApplication<ShortApplication>(*args)
}
