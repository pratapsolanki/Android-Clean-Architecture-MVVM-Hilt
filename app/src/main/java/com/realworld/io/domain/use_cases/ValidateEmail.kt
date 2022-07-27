package com.realworld.io.domain.use_cases

class ValidateEmail {
    fun execute(email :String) :ValidationResult{
        if (email.isBlank()){
            return ValidationResult(
                successful = false,
                errorMessage = "The email cant be blank"
            )
        }
        return ValidationResult(
            successful = true
        )
    }

}