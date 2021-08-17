package com.google.shinyay.codelab.service

import com.google.shinyay.codelab.entity.Employee
import com.google.shinyay.codelab.repository.EmployeeRepository
import org.springframework.stereotype.Service

@Service
class EmployeeService(val repository: EmployeeRepository) {

    fun findAllEmployee(): MutableList<Employee> {
        return repository.findAll()
    }
}