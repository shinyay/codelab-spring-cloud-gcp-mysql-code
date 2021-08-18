package com.google.shinyay.codelab.controller

import com.google.shinyay.codelab.service.EmployeeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class EmployeeController(val service: EmployeeService) {

}