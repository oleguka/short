package com.hry.controllers

import com.hry.ShortApplication
import com.hry.controller.RedirectController
import com.hry.service.KeyMapperService
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

//@WebAppConfiguration
@TestPropertySource(locations = ["/test.properties"])
@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [ShortApplication::class])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RedirectControllerTest {

    @Autowired
    lateinit var webApplicationContext: WebApplicationContext

    lateinit var mockMvc: MockMvc

    @Mock
    lateinit var service: KeyMapperService

    @Autowired
    @InjectMocks
    lateinit var controller: RedirectController

    private val HEADER_VALUE: String = "http://www.savewalterwhite.com"
    private val HEADER_NAME: String = "Location"
    private val PATH: String = "aAbB"
    private val REDIRECT_STATUS: Int = 302
    private val BAD_PATH = "bad"
    private val NOT_FOUND: Int = 404

    @BeforeAll
    fun init() {

        MockitoAnnotations.openMocks(this)

        mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .build()

        Mockito.`when`(service.getLink(PATH)).thenReturn(KeyMapperService.Get.Link(HEADER_VALUE))
        Mockito.`when`(service.getLink(BAD_PATH)).thenReturn(KeyMapperService.Get.NotFound(BAD_PATH))
    }

    @Test()
    fun controllerMustRedirectUsWhenRequestIsSuccessful() {
        mockMvc.perform(get("/$PATH"))
            .andExpect(status().`is`(REDIRECT_STATUS))
            .andExpect(header().string(HEADER_NAME, HEADER_VALUE))
    }

    @Test
    fun controllerMustReturn404IfBadKey() {
        mockMvc.perform(get("/$BAD_PATH"))
            .andExpect(status().`is`(NOT_FOUND))
    }

    @Test
    fun homeWorksFine() {
        mockMvc.perform(get("/"))
            .andExpect(MockMvcResultMatchers.view().name("home"))
    }

}