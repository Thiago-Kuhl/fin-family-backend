package com.bandtec.finfamily.finfamily;

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [JacksonAutoConfiguration::class])
class FinfamilyApplication

fun main(args: Array<String>) {
    runApplication<FinfamilyApplication>(*args)
}
