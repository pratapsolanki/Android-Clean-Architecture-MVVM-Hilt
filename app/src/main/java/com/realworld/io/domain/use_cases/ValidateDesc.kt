package com.realworld.io.domain.use_cases

class ValidateDesc {
    fun execute(description :String) :ValidationResult{
        if (description.isBlank()){
            return ValidationResult(
                successful = false,
                errorMessage = "The description cant be blank"
            )
        }

        if (description.length > 1000){
            return ValidationResult(
                successful = false,
                errorMessage = "Description Should not have more than 1000 character"
            )
        }

        return ValidationResult(
            successful = true
        )
    }

}