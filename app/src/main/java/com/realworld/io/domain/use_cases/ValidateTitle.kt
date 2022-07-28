package com.realworld.io.domain.use_cases

class ValidateTitle {
    fun execute(title :String) :ValidationResult{
        if (title.isBlank()){
            return ValidationResult(
                successful = false,
                errorMessage = "The title cant be blank"
            )
        }

        if (title.length > 50){
            return ValidationResult(
                successful = false,
                errorMessage = "tTtle Should not have more than 50 character"
            )
        }

        return ValidationResult(
            successful = true
        )
    }

}