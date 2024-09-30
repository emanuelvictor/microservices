package com.emanuelvictor.api.functional.flowcreator.application.persistence

interface IMapper<PersistObject, DomainObject> {
    fun persistObjectToDomainObject(persistObject: PersistObject): DomainObject

    fun domainObjectToPersistObject(domainObject: DomainObject): PersistObject
}
