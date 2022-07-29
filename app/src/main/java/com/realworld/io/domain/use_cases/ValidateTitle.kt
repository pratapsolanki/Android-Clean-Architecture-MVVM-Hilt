package com.realworld.io.domain.use_cases

/**
 * Use Case for validation of Title
 */
class ValidateTitle {
    fun execute(title :String) :ValidationResult{
    /**
     * Check if title is black or not
     */
        if (title.isBlank()){
            return ValidationResult(
                successful = false,
                errorMessage = "The title cant be blank"
            )
        }
        /**
         * Check if title Should not have more than 100 character
         */

        if (title.length > 50){
            return ValidationResult(
                successful = false,
                errorMessage = "Title Should not have more than 50 character"
            )
        }

        return ValidationResult(
            successful = true
        )
    }

}