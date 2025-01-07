package com.api.kotlinmon.kotlinmonapi

import jakarta.servlet.http.HttpServletRequest
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class CustomErrorController: ErrorController {
    @RequestMapping("/error")
    fun handleError(request: HttpServletRequest): ResponseEntity<Map<String, Any>> {
        val status = request.getAttribute("javax.servlet.error.status_code") as Int? ?: 500
        val errorMessage = request.getAttribute("javax.servlet.error.message") as String? ?: "An unexpected error occurred"

        val responseBody = mapOf(
            "status" to status,
            "error" to HttpStatus.valueOf(status).reasonPhrase,
            "message" to errorMessage,
            "timestamp" to System.currentTimeMillis()
        )

        return ResponseEntity.status(status).body(responseBody)
    }
}