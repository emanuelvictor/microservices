package com.emanuelvictor.api.functional.flowcreator.domain.repositories

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface Repository<T, ID> {

    fun save(entity: T): T

    fun deleteById(id: ID)

    fun findById(id: ID): Optional<T>

    fun findAll(): Page<T>

    fun findAll(pageable: Pageable): Page<T>
}