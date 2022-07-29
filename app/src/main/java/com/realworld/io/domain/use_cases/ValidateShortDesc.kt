package com.realworld.io.domain.use_cases

/**
 * Use Case for validation of ShortDesc
 */
class ValidateShortDesc {
    fun execute(shortDescription :String) :ValidationResult{
        /**
         * Check if short Description is black or not
         */
        if (shortDescription.isBlank()){
            return ValidationResult(
                successful = false,
                errorMessage = "The short description cant be blank"
            )
        }

        /**
         * Check if short Description Should not have more than 100 character
         */

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