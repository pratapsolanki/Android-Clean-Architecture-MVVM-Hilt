package com.realworld.io.domain.use_cases

import android.util.Patterns

/**
 * Use Case for validation of Email
 */
class ValidateEmail {
    fun execute(email :String) :ValidationResult{
        /**
         * if email is blank
         */
        if (email.isBlank()){
            return ValidationResult(
                successful = false,
                errorMessage = "The email cant be blank"
            )
        }

        /**
         * if email is valid or Not
         */
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return ValidationResult(
                successful = false,
                errorMessage = "Please Provide valid email Address"
            )
        }

        return ValidationResult(
            successful = true
        )
    }

}