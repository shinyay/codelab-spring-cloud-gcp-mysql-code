package com.google.shinyay.codelab.entity

import javax.persistence.*

@Entity
@Table(name = "department")
data class Department(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val department_id: Long,
    @Column(name = "department_name", nullable = false, length = 14)
    val name: String
)
