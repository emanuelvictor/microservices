package com.emanuelvictor.api.functional.flowcreator.domain.entities

import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.AbstractAlternative
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.IntermediaryAlternative
import com.emanuelvictor.api.functional.flowcreator.infrastructure.persistence.generic.PersistentEntity
import java.util.function.Consumer

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 25/08/2021
 */
class Choice(val alternative: IntermediaryAlternative) : PersistentEntity() {

    /**
     *
     */
    fun getPath(): String {
        return alternative.path
    }

    fun getAllOrderedNodes(): List<Node> {
        val nodes = ArrayList<Node>()
        extractAlternativesFromAlternative(alternative, nodes)
        return nodes
    }

    private fun extractAlternativesFromAlternative(alternative: AbstractAlternative, nodes: ArrayList<Node>) {
        if (alternative is IntermediaryAlternative) {
            extractAlternativesFromAlternative(alternative.previous, nodes)
        }
        alternative.values.forEach(Consumer {
            nodes.add(Node(it))
        })
    }

//    fun getNodes(): Set<Node> {
//        return getNodesFromAlternative(alternative, HashSet())
//    }

//    fun getNodesFromAlternative(alternative: AbstractAlternative, nodes: HashSet<Node>): Set<Node> {
//        if (alternative is IntermediaryAlternative) {
//            alternative.values.forEach(Consumer {
//                nodes.add(Node(it))
//            })
//            getNodesFromAlternative(alternative.previous, nodes)
//        }
//        return nodes
//    }

    /**
     * TODO maybe it is not necessary
     */
    fun getEdges(): Set<Edge> {
        return getEdgeFromAlternative(alternative, HashSet())
    }

    /**
     * TODO maybe it is not necessary
     */
    fun getEdgeFromAlternative(alternative: AbstractAlternative, edges: HashSet<Edge>): Set<Edge> {
        if (alternative is IntermediaryAlternative) {
            edges.addAll(alternative.getEdges())
            getEdgeFromAlternative(alternative.previous, edges)
        }
        return edges
    }

}
