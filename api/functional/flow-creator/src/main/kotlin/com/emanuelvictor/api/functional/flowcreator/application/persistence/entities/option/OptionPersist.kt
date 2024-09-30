package com.emanuelvictor.api.functional.flowcreator.domain.entities.option

import jakarta.persistence.*


/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 25/08/2021
 */
@Entity
open class OptionPersist(
    @Column(nullable = false, unique = true)
    open var identifier: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    open var id: Long? = null
}