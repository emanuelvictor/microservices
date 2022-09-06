package com.emanuelvictor.api.functional.assessment;

import com.emanuelvictor.api.functional.assessment.domain.entities.Alternative;
import com.emanuelvictor.api.functional.assessment.domain.entities.Option;
import org.junit.jupiter.api.Test;

class AssessmentApplicationTests {

    @Test
    void contextLoads() {

        // Client
        final Option client1 = new Option("Bubblemix Tea");
        final Alternative clientSelected = new Alternative("Selecione a unidade?", client1);


        // Unities
        final Option unity1 = new Option("BIG - Foz do Iguaçu");
        final Alternative unity1Selected = new Alternative("Por quem você foi atendimento?", unity1, clientSelected);
        final Option unity2 = new Option("Catuaí Palladium - Foz do Iguaçu");
        final Alternative unity2Selected = new Alternative("Por quem você foi atendimento?", unity2, clientSelected);

        // Options
        final Option person1 = new Option("Andressa");
        final Alternative alternativePerson1 = new Alternative("Como você avalia o atendimento?", person1, unity1Selected);
        final Option person2 = new Option("Marta");
        final Alternative alternativePerson2 = new Alternative("Como você avalia o atendimento?", person2, unity1Selected);
        final Option person3 = new Option("Roberto");
        final Alternative alternativePerson3 = new Alternative("Como você avalia o atendimento?", person3, unity1Selected);

        final Option person4 = new Option("Edson");
        final Alternative alternativePerson4 = new Alternative("Como você avalia o atendimento?", person4, unity2Selected);
        final Option person5 = new Option("Valdir");
        final Alternative alternativePerson5 = new Alternative("Como você avalia o atendimento?", person5, unity2Selected);
        final Option person6 = new Option("Vilma");
        final Alternative alternativePerson6 = new Alternative("Como você avalia o atendimento?", person6, unity2Selected);




    }

}
