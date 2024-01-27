package com.example.politicalspeech

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class PoliticalSpeechApplication

fun main(args: Array<String>) {
	runApplication<PoliticalSpeechApplication>(*args)
}

@RestController
class HelloWorldContainer {
	@GetMapping("/")
	fun sayHello() = "Hello World"
}
