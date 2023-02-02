package com.emanuelvictor.api.functional.flowcreator.infrastructure.adapters

import com.emanuelvictor.api.functional.flowcreator.domain.entities.Choice
import com.emanuelvictor.api.functional.flowcreator.domain.ports.repositories.ChoiceRepository
import com.emanuelvictor.api.functional.flowcreator.infrastructure.persistence.AbstractRepository
import org.springframework.stereotype.Repository
import java.util.function.Consumer
import java.util.stream.Collectors

/**
 *
 */
@Repository
class ChoiceRepositoryImpl : AbstractRepository<Choice, Int>(), ChoiceRepository {

    /**
     *
     */
    override fun listChoicesByOptionsValues(vararg values: String): List<Choice> {

        val retu = ArrayList<Choice>()

        stream.forEach {
            run {
                var contain = true
                values.forEach { s ->
                    run {
                        if (!it.path.contains(s))
                            contain = false
                    }
                }
                if(contain)
                    retu.add(it)
            }
        }
        return retu
//        stream.filter {
//
//            values.forEach { s ->
//                run {
//                    !it.getPath().contains(s)
//                    return@filter false;
//                }
//            }
//            return@filter true;
//        }.collect(Collectors.toList())
    }
}