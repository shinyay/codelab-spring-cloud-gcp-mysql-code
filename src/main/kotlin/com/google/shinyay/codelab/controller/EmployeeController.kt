package com.google.shinyay.codelab.controller

import com.google.shinyay.codelab.entity.Employee
import com.google.shinyay.codelab.service.EmployeeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class EmployeeController(val service: EmployeeService) {

    @GetMapping("/employees")
    fun getAllEmployees(): ResponseEntity<MutableList<Employee>> {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAllEmployee())
    }

    @PostMapping("/employees")
    fun postEmployee(@RequestBody employee: Employee): ResponseEntity<Employee> {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registerEmployee(employee))
    }

}