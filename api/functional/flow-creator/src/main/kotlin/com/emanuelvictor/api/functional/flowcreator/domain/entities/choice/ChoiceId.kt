package com.emanuelvictor.api.functional.flowcreator.domain.entities.choice

import com.emanuelvictor.api.functional.flowcreator.domain.entities.generic.UniqueIdentifier
import jakarta.persistence.Embeddable

@Embeddable
class ChoiceId(id: String?) : UniqueIdentifier<String>(id) {

}