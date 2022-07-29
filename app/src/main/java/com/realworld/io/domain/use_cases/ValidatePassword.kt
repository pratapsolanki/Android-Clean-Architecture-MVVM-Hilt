package com.realworld.io.domain.use_cases

/**
 * Use Case for validation of PassWord
 */
class ValidatePassword {
    fun execute(password :String) :ValidationResult{
        /**
         * Use Case for password should not less than 8 digit
         */
        if (password.length < 8){
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to consists of at least 8 letter"
            )
        }
        return ValidationResult(
            successful = true ,
        )
    }

}