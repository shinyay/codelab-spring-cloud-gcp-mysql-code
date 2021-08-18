package com.google.shinyay.codelab.controller

import com.google.shinyay.codelab.entity.Employee
import com.google.shinyay.codelab.service.EmployeeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class EmployeeController(val service: EmployeeService) {

    @GetMapping("/employees")
    fun getAllEmployees(): ResponseEntity<MutableList<Employee>> {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAllEmployee())
    }

}