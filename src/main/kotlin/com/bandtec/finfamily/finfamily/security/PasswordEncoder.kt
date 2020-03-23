package com.bandtec.finfamily.finfamily.security

interface PasswordEncoder {

  fun encode(rawPassword: CharSequence?): String?

  fun matches(rawPassword: CharSequence?, encodedPassword: String?): Boolean
}