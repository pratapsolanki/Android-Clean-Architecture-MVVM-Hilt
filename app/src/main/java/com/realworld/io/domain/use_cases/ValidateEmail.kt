package com.realworld.io.domain.use_cases

import android.util.Patterns

class ValidateEmail {
    fun execute(email :String) :ValidationResult{
        if (email.isBlank()){
            return ValidationResult(
                successful = false,
                errorMessage = "The email cant be blank"
            )
        }
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