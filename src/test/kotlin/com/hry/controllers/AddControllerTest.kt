package com.hry.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.hry.ShortApplication
import com.hry.controller.AddController
import com.hry.service.KeyMapperService
import com.hry.whenever
import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@WebAppConfiguration
@TestPropertySource(locations = ["/test.properties"])
@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [ShortApplication::class])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AddControllerTest {

    @Autowired
    lateinit var webApplicationContext: WebApplicationContext

    lateinit var mockMvc: MockMvc

    @Mock
    lateinit var service: KeyMapperService

    @Autowired
    @InjectMocks
    lateinit var controller: AddController

    private val LINK = "link"
    private val KEY = "key"

    @BeforeAll
    fun init() {

        MockitoAnnotations.openMocks(this)

        mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .build()

        whenever(service.add(LINK)).thenReturn(KEY)
    }

    @Test
    fun whenUserAddHeTakesAKey() {
        mockMvc.perform(
            MockMvcRequestBuilders
                .post("/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper().writeValueAsString(AddController.AddRequest(LINK)))
        )
            .andExpect(MockMvcResultMatchers.jsonPath("$.key", Matchers.equalTo(KEY)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.link", Matchers.equalTo(LINK)))
    }

    @Test
    fun whenUserAddLinkByFormHeTakesWebPage() {
        mockMvc.perform(
            MockMvcRequestBuilders
                .post("/addhtml")
                .param("link", LINK)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(KEY)))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(LINK)))
    }

}