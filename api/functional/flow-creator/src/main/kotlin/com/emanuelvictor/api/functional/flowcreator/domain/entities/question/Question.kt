package com.emanuelvictor.api.functional.flowcreator.domain.entities.question

import jakarta.persistence.*

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 25/08/2021
 */
@Entity
open class Question(@Column(nullable = false) val name: String, @Column(nullable = false) val message: String) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    open var id: Long? = null

}