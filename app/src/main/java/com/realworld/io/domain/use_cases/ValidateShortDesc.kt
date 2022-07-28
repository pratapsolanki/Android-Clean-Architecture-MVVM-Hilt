package com.realworld.io.domain.use_cases

class ValidateShortDesc {
    fun execute(shortDescription :String) :ValidationResult{
        if (shortDescription.isBlank()){
            return ValidationResult(
                successful = false,
                errorMessage = "The short description cant be blank"
            )
        }

        if (shortDescription.length > 100){
            return ValidationResult(
                successful = false,
                errorMessage = "Short Description Should not have more than 100 character"
            )
        }

        return ValidationResult(
            successful = true
        )
    }

}