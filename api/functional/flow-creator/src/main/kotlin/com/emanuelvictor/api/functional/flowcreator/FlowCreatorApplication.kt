package com.emanuelvictor.api.functional.flowcreator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

/**
 *
 */
@SpringBootApplication
class FlowCreatorApplication

/**
 * @param args Array<String>
 */
fun main(args: Array<String>) {
    runApplication<FlowCreatorApplication>(*args)
}