package com.hry.service

import com.hry.model.Link
import com.hry.model.repositories.LinkRepository
import com.hry.whenever
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DefaultKeyMapperServiceTest {

    private val ID_A: Long = 10000000L
    private val ID_B: Long = 10000000L
    private val KEY_A: String = "abc"
    private val KEY_B: String = "cde"
    private val KEY: String = "bad"
    private val LINK_A: String = "http://www.savewalterwhite.com"
    private val LINK_B: String = "http://www.yahoo.com"
    private val LINK_OBJ_A: Link = Link(LINK_A, ID_A)
    private val LINK_OBJ_B: Link = Link(LINK_B, ID_B)

    @InjectMocks
    val service: KeyMapperService = DefaultKeyMapperService()

    @Mock
    lateinit var converter: KeyConverterService

    @Mock
    lateinit var repo: LinkRepository

    @BeforeAll
    fun init() {
        MockitoAnnotations.openMocks(this)

        Mockito.`when`(converter.keyToId(KEY_A)).thenReturn(ID_A)
        Mockito.`when`(converter.idToKey(ID_A)).thenReturn(KEY_A)
        Mockito.`when`(converter.keyToId(KEY_B)).thenReturn(ID_B)
        Mockito.`when`(converter.idToKey(ID_B)).thenReturn(KEY_B)

        whenever(repo.findById(Mockito.any())).thenReturn(Optional.empty())
        whenever(repo.save(Link(LINK_A))).thenReturn(LINK_OBJ_A)
        whenever(repo.save(Link(LINK_B))).thenReturn(LINK_OBJ_B)
        whenever(repo.findById(ID_A)).thenReturn(Optional.of(LINK_OBJ_A))
        whenever(repo.findById(ID_B)).thenReturn(Optional.of(LINK_OBJ_B))
    }

    @Test
    fun clientCanAddLinks() {
        val keyA = service.add(LINK_A)
        assertEquals(KeyMapperService.Get.Link(LINK_A), service.getLink(keyA))
        val keyB = service.add(LINK_B)
        assertEquals(KeyMapperService.Get.Link(LINK_B), service.getLink(keyB))
//        assertNotEquals(keyA, keyB)
    }

    @Test
    fun clientCanNotTakeLinkIfKeyNotFoundInService() {
        assertEquals(KeyMapperService.Get.NotFound(KEY), service.getLink(KEY))
    }

}