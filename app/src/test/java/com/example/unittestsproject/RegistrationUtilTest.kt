package com.example.unittestsproject


import com.google.common.truth.Truth.assertThat
import org.junit.Test


class RegistrationUtilTest {


    @Test
    fun  `empty username return false` () {
        val  result = RegistrationUtil.validateRegistrationInput(
            userName =  "",
            password = "123",
            confirmedPassword = "123"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun  `validate username and correctly repeated password returns true` () {
        val  result = RegistrationUtil.validateRegistrationInput(
            userName =  "Philipp",
            password = "123",
            confirmedPassword = "123"
        )
        assertThat(result).isTrue()
    }

    @Test
    fun  `username already exist returns false` () {
        val  result = RegistrationUtil.validateRegistrationInput(
            userName =  "Carl",
            password = "123",
            confirmedPassword = "123"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun  `incorrectly confirmed password returns false` () {
        val  result = RegistrationUtil.validateRegistrationInput(
            userName =  "Carl",
            password = "123456",
            confirmedPassword = "abcdef"
        )
        assertThat(result).isFalse()
    }


    @Test
    fun `empty password returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Philipp",
            "",
            ""
        )
        assertThat(result).isFalse()
    }


    @Test
    fun `less than 2 digit password returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Philipp",
            "abcdefg5",
            "abcdefg5"
        )
        assertThat(result).isFalse()
    }

}