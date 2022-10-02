package com.emanuelvictor.api.functional.flowcreator.infrastructure.helprs;

import com.emanuelvictor.api.functional.flowcreator.domain.entities.Alternative;
import com.emanuelvictor.api.functional.flowcreator.domain.entities.Option;
import com.emanuelvictor.api.functional.flowcreator.domain.repositories.AlternativeRepository;
import com.emanuelvictor.api.functional.flowcreator.domain.repositories.OptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class PopulateHelper {

    /**
     *
     */
    public static final Long ROOT_ID = 1L;

    /**
     * @param optionRepository      {@link OptionRepository}
     * @param alternativeRepository {@link OptionRepository}
     */
    @Autowired
    public PopulateHelper(final OptionRepository optionRepository, final AlternativeRepository alternativeRepository) {

        // Client
        final Option client = new Option("Bubblemix Tea");
        final Alternative clientSelected = new Alternative();
        clientSelected.setId(ROOT_ID); // Setting root ID, to after retrieve this value.
        clientSelected.setOption(client);
        clientSelected.setMessageToNext("Selecione a unidade?");

        optionRepository.save(client);
        alternativeRepository.save(clientSelected);

        // Unities
        // ---- Unity 1
        final Option unity1 = new Option("BIG - Foz do Iguaçu");
        final Alternative unity1Selected = new Alternative();
        unity1Selected.setPrevious(clientSelected); // Previous choice
        unity1Selected.setOption(unity1); // Option
        unity1Selected.setMessageToNext("Por quem você foi atendido?");  // Message to next

        optionRepository.save(unity1);
        alternativeRepository.save(unity1Selected);

        // ---- Unity 2
        final Option unity2 = new Option("Catuaí Palladium - Foz do Iguaçu");
        final Alternative unity2Selected = new Alternative();
        unity2Selected.setPrevious(clientSelected); // Previous choice
        unity2Selected.setOption(unity2); // Option
        unity2Selected.setMessageToNext("Por quem você foi atendido?");  // Message to next // TODO repeating, maybe make a concatenate function with option sentence

        optionRepository.save(unity2);
        alternativeRepository.save(unity2Selected);

        // ---- Persons of Unity 1
        // Person 1 of Unity 1
        final Option person1 = new Option("Andressa");
        final Alternative alternativePerson1 = new Alternative();
        alternativePerson1.setPrevious(unity1Selected); // Previous choice
        alternativePerson1.setOption(person1); // Option
        alternativePerson1.setMessageToNext("Como você avalia o atendimento?");  // Message to next // TODO repeating, maybe make a concatenate function with option sentence

        optionRepository.save(person1);
        alternativeRepository.save(alternativePerson1);

        // Person 2 of Unity 1
        final Option person2 = new Option("Marta");
        final Alternative alternativePerson2 = new Alternative();
        alternativePerson2.setPrevious(unity1Selected); // Previous choice
        alternativePerson2.setOption(person2); // Option
        alternativePerson2.setMessageToNext("Como você avalia o atendimento?");  // Message to next // TODO repeating, maybe make a concatenate function with option sentence

        optionRepository.save(person2);
        alternativeRepository.save(alternativePerson2);

        // Person 3 of Unity 1
        final Option person3 = new Option("Roberto");
        final Alternative alternativePerson3 = new Alternative();
        alternativePerson3.setPrevious(unity1Selected); // Previous choice
        alternativePerson3.setOption(person3); // Option
        alternativePerson3.setMessageToNext("Como você avalia o atendimento?");  // Message to next // TODO repeating, maybe make a concatenate function with option sentence

        optionRepository.save(person3);
        alternativeRepository.save(alternativePerson3);

        // ---- Persons of Unity 1
        // Person 1 of Unity 2
        final Option person4 = new Option("Edson");
        final Alternative alternativePerson4 = new Alternative();
        alternativePerson4.setPrevious(unity2Selected); // Previous choice
        alternativePerson4.setOption(person4); // Option
        alternativePerson4.setMessageToNext("Como você avalia o atendimento?");  // Message to next // TODO repeating, maybe make a concatenate function with option sentence

        optionRepository.save(person4);
        alternativeRepository.save(alternativePerson4);

        // Person 2 of Unity 2
        final Option person5 = new Option("Valdir");
        final Alternative alternativePerson5 = new Alternative();
        alternativePerson5.setPrevious(unity2Selected); // Previous choice
        alternativePerson5.setOption(person5); // Option
        alternativePerson5.setMessageToNext("Como você avalia o atendimento?");  // Message to next // TODO repeating, maybe make a concatenate function with option sentence

        optionRepository.save(person5);
        alternativeRepository.save(alternativePerson5);

        // Person 3 of Unity 2
        final Option person6 = new Option("Vilma");
        final Alternative alternativePerson6 = new Alternative();
        alternativePerson6.setPrevious(unity2Selected); // Previous choice
        alternativePerson6.setOption(person6); // Option
        alternativePerson6.setMessageToNext("Como você avalia o atendimento?");  // Message to next // TODO repeating, maybe make a concatenate function with option sentence

        optionRepository.save(person6);
        alternativeRepository.save(alternativePerson6);

    }
}
