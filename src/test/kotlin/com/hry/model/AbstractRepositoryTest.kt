package com.hry.model

import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.hry.ShortApplication
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.TestPropertySource

@TestPropertySource(locations = ["/test.properties"])
@TestExecutionListeners(DbUnitTestExecutionListener::class)
@SpringBootTest(classes = [ShortApplication::class])
@DirtiesContext
abstract class AbstractRepositoryTest: AbstractTransactionalJUnit4SpringContextTests() {
}