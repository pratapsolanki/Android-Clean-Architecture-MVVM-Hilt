package com.realworld.io.domain.use_cases

/**
 * Use Case for validation of Username
 */
class ValidateUsername {
    fun execute(username :String) :ValidationResult{
        /**
         * Check if Username should more than 5 digit
         */
        if (username.length < 5){
            return ValidationResult(
                successful = false,
                errorMessage = "Username needs at least 5 character"
            )
        }

        return ValidationResult(
            successful = true
        )
    }

}