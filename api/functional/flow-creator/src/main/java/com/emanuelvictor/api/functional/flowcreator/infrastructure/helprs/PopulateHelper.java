package com.emanuelvictor.api.functional.flowcreator.infrastructure.helprs;

import com.emanuelvictor.api.functional.flowcreator.domain.entity.alternative.IntermediaryAlternative;
import com.emanuelvictor.api.functional.flowcreator.domain.entity.Option;
import com.emanuelvictor.api.functional.flowcreator.domain.entity.alternative.RootAlternative;
import com.emanuelvictor.api.functional.flowcreator.domain.ports.AlternativeRepository;
import com.emanuelvictor.api.functional.flowcreator.domain.ports.OptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class PopulateHelper {

    private final OptionRepository optionRepository;
    private final AlternativeRepository alternativeRepository;

    /**
     * @param optionRepository      {@link OptionRepository}
     * @param alternativeRepository {@link AlternativeRepository}
     */
    @Autowired
    public PopulateHelper(final OptionRepository optionRepository, final AlternativeRepository alternativeRepository) {
        this.optionRepository = optionRepository;
        this.alternativeRepository = alternativeRepository;
    }

    /**
     * Populate Data
     */
    public void populateData() {

        // Client
        final Option client = new Option("Bubblemix Tea");
        final RootAlternative clientSelected = new RootAlternative(client, "Selecione a unidade?");

        optionRepository.save(client);
        alternativeRepository.save(clientSelected);

        // Unities
        // ---- Unity 1
        final Option unity1 = new Option("BIG - Foz do Iguaçu");
        final IntermediaryAlternative unity1Selected = new IntermediaryAlternative(clientSelected, unity1, "Por quem você foi atendido?");

        optionRepository.save(unity1);
        alternativeRepository.save(unity1Selected);

        // ---- Unity 2
        final Option unity2 = new Option("Catuaí Palladium - Foz do Iguaçu");
        final IntermediaryAlternative unity2Selected = new IntermediaryAlternative(clientSelected, unity2, "Por quem você foi atendido?");

        optionRepository.save(unity2);
        alternativeRepository.save(unity2Selected);

        // ---- Persons of Unity 1
        // Person 1 of Unity 1
        final Option person1 = new Option("Andressa");
        final IntermediaryAlternative intermediaryAlternativePerson1 = new IntermediaryAlternative(unity1Selected, person1, "Como você avalia o atendimento?");

        optionRepository.save(person1);
        alternativeRepository.save(intermediaryAlternativePerson1);

        // Person 2 of Unity 1
        final Option person2 = new Option("Marta");
        final IntermediaryAlternative intermediaryAlternativePerson2 = new IntermediaryAlternative(unity1Selected, person2, "Como você avalia o atendimento?");

        optionRepository.save(person2);
        alternativeRepository.save(intermediaryAlternativePerson2);

        // Person 3 of Unity 1
        final Option person3 = new Option("Roberto");
        final IntermediaryAlternative intermediaryAlternativePerson3 = new IntermediaryAlternative(unity1Selected, person3, "Como você avalia o atendimento?");

        optionRepository.save(person3);
        alternativeRepository.save(intermediaryAlternativePerson3);

        // ---- Persons of Unity 1
        // Person 1 of Unity 2
        final Option person4 = new Option("Edson");
        final IntermediaryAlternative intermediaryAlternativePerson4 = new IntermediaryAlternative(unity2Selected, person4, "Como você avalia o atendimento?");

        optionRepository.save(person4);
        alternativeRepository.save(intermediaryAlternativePerson4);

        // Person 2 of Unity 2
        final Option person5 = new Option("Valdir");
        final IntermediaryAlternative intermediaryAlternativePerson5 = new IntermediaryAlternative(unity2Selected, person5, "Como você avalia o atendimento?");

        optionRepository.save(person5);
        alternativeRepository.save(intermediaryAlternativePerson5);

        // Person 3 of Unity 2
        final Option person6 = new Option("Vilma");
        final IntermediaryAlternative intermediaryAlternativePerson6 = new IntermediaryAlternative(unity2Selected, person6, "Como você avalia o atendimento?");

        optionRepository.save(person6);
        alternativeRepository.save(intermediaryAlternativePerson6);
    }

    /**
     * Erase Data
     */
    public void eraseData() {
        alternativeRepository.eraseData();
        optionRepository.eraseData();
    }
}
