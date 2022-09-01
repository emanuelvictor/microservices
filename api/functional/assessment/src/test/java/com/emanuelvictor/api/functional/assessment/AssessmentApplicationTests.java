package com.emanuelvictor.api.functional.assessment;

import com.emanuelvictor.api.functional.assessment.domain.entities.Alternative;
import com.emanuelvictor.api.functional.assessment.domain.entities.Unity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AssessmentApplicationTests {

    @Test
    void contextLoads() {

        final Unity client = new Unity("Bubblemix Tea");
        final Alternative alternative = new Alternative("Selecione a unidade?", client);

        final Unity big = new Unity("BIG - Foz do Iguaçu");
        final Alternative atendimento = new Alternative("Selecione o nível do atendimento", big, alternative);



        final Unity unity2 = new Unity("Catuaí Palladium - Foz do Iguaçu");
        final Alternative alternative2 = new Alternative("Selecione o sabor para avaliação", big, alternative);
        final Unity unity3 = new Unity("Iguatemi - Curitiba");
        final Alternative alternative3 = new Alternative("Selecione o local para avaliação", big, alternative);

    }

}
