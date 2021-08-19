package com.google.shinyay.codelab.entity

import javax.persistence.*

@Entity
@Table(name = "employee")
data class Employee(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val employee_id: Long,
    @Column(name = "employee_name", nullable = false, length = 64)
    val name: String,
    val role: String,
    val department_id: Long
)
