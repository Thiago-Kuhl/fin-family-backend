package com.bandtec.finfamily

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import kotlin.random.Random
import kotlin.streams.asSequence

@SpringBootTest
class FinfamilyApplicationTests {

	@Test
	fun randomString() {

		val source = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
		val output = java.util.Random().ints(5, 0, source.length)
				.asSequence()
				.map(source::get)
				.joinToString("")

		println(output)
	}

}
