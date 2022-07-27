package com.realworld.io.domain.use_cases

import android.util.Patterns

class ValidateUsername {
    fun execute(username :String) :ValidationResult{
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