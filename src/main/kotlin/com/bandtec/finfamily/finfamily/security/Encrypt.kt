package com.bandtec.finfamily.finfamily.security

import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.security.crypto.password.PasswordEncoder

class Encrypt {

  @Bean
  fun customPasswordEncoder(): PasswordEncoder? {
    return object : PasswordEncoder {
      override fun encode(rawPassword: CharSequence): String {
        return BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt(4))
      }

      override fun matches(rawPassword: CharSequence, encodedPassword: String): Boolean {
        return BCrypt.checkpw(rawPassword.toString(), encodedPassword)
      }
    }
  }

}