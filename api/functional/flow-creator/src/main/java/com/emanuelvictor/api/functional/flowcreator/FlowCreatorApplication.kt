package com.emanuelvictor.api.functional.flowcreator

import com.emanuelvictor.api.functional.flowcreator.domain.services.AlternativeService
import com.emanuelvictor.api.functional.flowcreator.domain.services.ChoiceService
import com.emanuelvictor.api.functional.flowcreator.infrastructure.adapters.AlternativeRepositoryImpl
import com.emanuelvictor.api.functional.flowcreator.infrastructure.adapters.ChoiceRepositoryImpl
import com.emanuelvictor.api.functional.flowcreator.infrastructure.adapters.OptionRepositoryImpl
import com.emanuelvictor.api.functional.flowcreator.infrastructure.helprs.PopulateHelper

/**
 *
 */
object FlowCreatorApplication {

    /**
     * @param args String[]
     */
    @JvmStatic
    fun main(args: Array<String>) {

        val optionRepository = OptionRepositoryImpl();
        val choiceRepository = ChoiceRepositoryImpl();
        val choiceService = ChoiceService(choiceRepository)
        val alternativeRepository = AlternativeRepositoryImpl()
        val alternativeService = AlternativeService(alternativeRepository);
        val populateHelper = PopulateHelper(choiceService, optionRepository, choiceRepository, alternativeService)

        populateHelper.populateData();
        populateHelper.startProgram();
    }
}