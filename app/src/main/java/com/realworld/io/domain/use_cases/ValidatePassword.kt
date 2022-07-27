package com.realworld.io.domain.use_cases

class ValidatePassword {
    fun execute(password :String) :ValidationResult{
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