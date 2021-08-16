package com.google.shinyay.codelab.entity

import javax.persistence.*

@Entity
@Table(name = "employee")
data class Employee(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String,
    val role: String,
    val department_id: Long
)
