package com.bandtec.finfamily.finfamily

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.swagger2.annotations.EnableSwagger2

@SpringBootApplication
class FinfamilyApplication

fun main(args: Array<String>) {
	runApplication<FinfamilyApplication>(*args)


}

