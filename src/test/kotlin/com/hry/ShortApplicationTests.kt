package com.hry

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension

//@WebAppConfiguration
@TestPropertySource(locations = ["/test.properties"])
@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [ShortApplication::class])
class ShortApplicationTests {

    @Test
    fun contextLoads() {
    }

}
