package com.bandtec.finfamily.finfamily.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class PasswordEncoderConfig {
  @Bean
  fun passwordEncoder(): PasswordEncoder? {
    return object : PasswordEncoder {
      override fun encode(rawPassword: CharSequence): String {
        return rawPassword.toString()
      }

      override fun matches(rawPassword: CharSequence, encodedPassword: String): Boolean {
        return rawPassword.toString() == encodedPassword
      }
    }
  }
}