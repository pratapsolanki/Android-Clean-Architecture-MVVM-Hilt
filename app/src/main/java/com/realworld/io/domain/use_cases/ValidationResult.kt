package com.realworld.io.domain.use_cases

/**
 * Use Case for validation Result
 */
data class ValidationResult(
    val successful : Boolean ,
    val errorMessage : String ?= null
)
