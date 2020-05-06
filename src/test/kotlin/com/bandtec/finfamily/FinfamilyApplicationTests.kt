package com.bandtec.finfamily

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate
import java.util.*
import kotlin.random.Random
import kotlin.streams.asSequence

@SpringBootTest
class FinfamilyApplicationTests {

	@Test
	fun randomString() {

		var date = LocalDate.now()
		println(date)
	}

}
