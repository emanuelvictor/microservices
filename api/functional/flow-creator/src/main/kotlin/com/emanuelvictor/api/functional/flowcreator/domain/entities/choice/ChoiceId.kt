package com.emanuelvictor.api.functional.flowcreator.domain.entities.choice

import com.emanuelvictor.api.functional.flowcreator.domain.entities.generic.UniqueIdentifierJava
import jakarta.persistence.Embeddable

@Embeddable
class ChoiceId(id: String?) : UniqueIdentifierJava<String>(id) {

}