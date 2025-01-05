package com.api.kotlinmon.kotlinmonapi

import com.api.kotlinmon.kotlinmonapi.exceptions.ResourceNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            message = ex.message ?: "Invalid request",
            timestamp = System.currentTimeMillis()
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }
    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleNotFoundException(ex: ResourceNotFoundException): ResponseEntity<Map<String, String>> {
        val body = mapOf("error" to (ex.message ?: "Resource not found"),
            "status" to HttpStatus.NOT_FOUND.value().toString())
        return ResponseEntity(body, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status =  HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = ex.message ?: "An unexpected error occurred",
            timestamp = System.currentTimeMillis()
        )
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
    }


}

data class ErrorResponse(
    val status: Int,
    val message: String,
    val timestamp: Long
)
