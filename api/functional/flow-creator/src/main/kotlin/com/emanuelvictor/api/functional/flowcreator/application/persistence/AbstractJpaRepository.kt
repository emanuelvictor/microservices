package com.emanuelvictor.api.functional.flowcreator.application.persistence

import com.emanuelvictor.api.functional.flowcreator.domain.repositories.Repository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


abstract class AbstractJpaRepository<DomainObject : Any, DomainId : Any, PersistObject : Any, PersistId : Any>(
    private val jpaRepository: JpaRepository<PersistObject, PersistId>
) :
    Repository<DomainObject, DomainId> {

    companion object {
        const val DEFAULT_SIZE_FROM_PAGINATION = 10
    }

    override fun save(entity: DomainObject): DomainObject {
        val persistObject = domainObjectToPersistObject(entity)
        return persistObjectToDomainObject(jpaRepository.save(persistObject))
    }

    override fun deleteById(id: DomainId) {
        TODO("We need to convert domainId to persistId")
//        jpaRepository.deleteById(id)
    }

    override fun findById(id: DomainId): Optional<DomainObject> {
        TODO("We need to convert domainId to persistId")
//        return jpaRepository.findById(id)
    }

    override fun findAll(): Page<DomainObject> {
        return findAll(Pageable.ofSize(DEFAULT_SIZE_FROM_PAGINATION))
    }

    override fun findAll(pageable: Pageable): Page<DomainObject> {
        return jpaRepository.findAll(pageable).map { persistObjectToDomainObject(it) }
    }

    abstract fun getMapper(): IMapper<PersistObject, DomainObject>

//    private fun reflectClassDomainObjectType(): Class<DomainObject> {
//        return ((javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<DomainObject>)
//    }
//
//    private fun reflectClassPersistObjectType(): Class<PersistObject> {
//        return ((javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[2] as Class<PersistObject>)
//    }

    fun persistObjectToDomainObject(persistObject: PersistObject): DomainObject {
        return getMapper().persistObjectToDomainObject(persistObject)
    }

    fun domainObjectToPersistObject(persistObject: DomainObject): PersistObject {
        return getMapper().domainObjectToPersistObject(persistObject)
    }
}