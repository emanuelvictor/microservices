package com.emanuelvictor.api.functional.flowcreator.domain.entities.generic

import java.io.Serializable

open class UniqueIdentifier<ID> : Serializable {

    open var id: ID? = null
}