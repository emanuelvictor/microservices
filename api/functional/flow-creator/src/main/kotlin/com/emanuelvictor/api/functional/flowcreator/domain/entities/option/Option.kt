package com.emanuelvictor.api.functional.flowcreator.domain.entities.option

import io.github.emanuelvictor.commons.persistence.generic.PersistentEntity


/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 25/08/2021
 */
abstract class Option(val identifier: String) : PersistentEntity()