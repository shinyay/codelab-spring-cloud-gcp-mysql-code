package com.google.shinyay.codelab

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TutorialApplication

fun main(args: Array<String>) {
	runApplication<TutorialApplication>(*args)
}

val Any.logger: Logger
	get() = LoggerFactory.getLogger(this::class.java)