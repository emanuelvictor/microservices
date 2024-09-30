package com.emanuelvictor.api.functional.flowcreator.application.persistence.adapters.choice

import com.emanuelvictor.api.functional.flowcreator.application.persistence.IMapper
import com.emanuelvictor.api.functional.flowcreator.application.persistence.entities.choice.ChoicePersist
import com.emanuelvictor.api.functional.flowcreator.domain.entities.choice.Choice
import org.modelmapper.Converter
import org.modelmapper.ModelMapper
import org.modelmapper.Provider
import org.modelmapper.spi.MappingContext

open class ChoiceMapper : IMapper<ChoicePersist, Choice> {

    private val modelMapper = ModelMapper()

    init {
    }

    override fun persistObjectToDomainObject(persistObject: ChoicePersist): Choice {
        return modelMapper.map(persistObject, Choice::class.java)
    }

    override fun domainObjectToPersistObject(domainObject: Choice): ChoicePersist {
        return modelMapper.map(domainObject, ChoicePersist::class.java)
    }
}
