package com.emanuelvictor.api.functional.flowcreator.domain.entities.choice

import com.emanuelvictor.api.functional.flowcreator.domain.entities.generic.UniqueIdentifier
import jakarta.persistence.Embeddable

@Embeddable
class ChoiceId() : UniqueIdentifier<String>() {

    override var id: String? = null

    constructor(id: String?) : this() {
        this.id = id
    }
}