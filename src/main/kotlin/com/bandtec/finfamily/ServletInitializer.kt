package com.bandtec.finfamily

import com.bandtec.finfamily.finfamily.FinfamilyApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

class ServletInitializer : SpringBootServletInitializer() {

	override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder {
		return application.sources(FinfamilyApplication::class.java)
	}

}
