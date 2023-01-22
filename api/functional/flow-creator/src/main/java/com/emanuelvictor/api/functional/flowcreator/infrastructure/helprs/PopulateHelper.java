package com.emanuelvictor.api.functional.flowcreator.infrastructure.helprs;

import com.emanuelvictor.api.functional.flowcreator.domain.entities.Choice;
import com.emanuelvictor.api.functional.flowcreator.domain.entities.Option;
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.AbstractAlternative;
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.IntermediaryAlternative;
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.RootAlternative;
import com.emanuelvictor.api.functional.flowcreator.domain.ports.repositories.ChoiceRepository;
import com.emanuelvictor.api.functional.flowcreator.domain.services.AlternativeService;
import com.emanuelvictor.api.functional.flowcreator.domain.services.ChoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 */
@Component
public class PopulateHelper {

    private final ChoiceService choiceService;

    private final ChoiceRepository choiceRepository;

    private final AlternativeService alternativeService;

    /**
     * @param choiceService      {@link ChoiceService}
     * @param choiceRepository   {@link ChoiceRepository}
     * @param alternativeService {@link AlternativeService}
     */
    @Autowired
    public PopulateHelper(final ChoiceService choiceService, final ChoiceRepository choiceRepository, final AlternativeService alternativeService) {
        this.choiceService = choiceService;
        this.choiceRepository = choiceRepository;
        this.alternativeService = alternativeService;
    }

    /**
     * Populate Data
     */
    public void populateData() {

        // Client
        final String client = "Bubblemix Tea";
        final RootAlternative clientSelected = new RootAlternative("Selecione a unidade?", false, new Option(client));

        alternativeService.save(clientSelected);

        // Unities
        // ---- Unity 1
        final String unity1 = "BIG - Foz do Iguaçu";
        final IntermediaryAlternative unity1Selected = new IntermediaryAlternative(clientSelected, "Por quem você foi atendido?", true, new Option(unity1));

        alternativeService.save(unity1Selected);

        // ---- Unity 2
        final String unity2 = "Catuaí Palladium - Foz do Iguaçu";
        final IntermediaryAlternative unity2Selected = new IntermediaryAlternative(clientSelected, "Por quem você foi atendido?", true, new Option(unity2));

        alternativeService.save(unity2Selected);

        // ---- Persons of Unity 1
        // Person 1 of Unity 1
        final String person1 = "Andressa";
        final IntermediaryAlternative intermediaryAlternativePerson1 = new IntermediaryAlternative(unity1Selected, "Como você avalia o atendimento?", false, new Option(person1));

        alternativeService.save(intermediaryAlternativePerson1);

        // Person 2 of Unity 1
        final String person2 = "Marta";
        final IntermediaryAlternative intermediaryAlternativePerson2 = new IntermediaryAlternative(unity1Selected, "Como você avalia o atendimento?", false, new Option(person2));

        alternativeService.save(intermediaryAlternativePerson2);

        // Person 3 of Unity 1
        final String person3 = "Roberto";
        final IntermediaryAlternative intermediaryAlternativePerson3 = new IntermediaryAlternative(unity1Selected, "Como você avalia o atendimento?", false, new Option(person3));

        alternativeService.save(intermediaryAlternativePerson3);

        // ---- Persons of Unity 1
        // Person 1 of Unity 2
        final String person4 = "Edson";
        final IntermediaryAlternative intermediaryAlternativePerson4 = new IntermediaryAlternative(unity2Selected, "Como você avalia o atendimento?", false, new Option(person4));

        alternativeService.save(intermediaryAlternativePerson4);

        // Person 2 of Unity 2
        final String person5 = "Valdir";
        final IntermediaryAlternative intermediaryAlternativePerson5 = new IntermediaryAlternative(unity2Selected, "Como você avalia o atendimento?", false, new Option(person5));

        alternativeService.save(intermediaryAlternativePerson5);

        // Person 3 of Unity 2
        final String person6 = "Vilma";
        final IntermediaryAlternative intermediaryAlternativePerson6 = new IntermediaryAlternative(unity2Selected, "Como você avalia o atendimento?", false, new Option(person6));

        alternativeService.save(intermediaryAlternativePerson6);

//        choiceService.makeChoice(intermediaryAlternativePerson1);
//        choiceService.makeChoice(intermediaryAlternativePerson2);
//        choiceService.makeChoice(intermediaryAlternativePerson2);
//        choiceService.makeChoice(intermediaryAlternativePerson3);
//        choiceService.makeChoice(intermediaryAlternativePerson3);
//        choiceService.makeChoice(intermediaryAlternativePerson3);
//        choiceService.makeChoice(intermediaryAlternativePerson4);
//        choiceService.makeChoice(intermediaryAlternativePerson4);
//        choiceService.makeChoice(intermediaryAlternativePerson4);
//        choiceService.makeChoice(intermediaryAlternativePerson4);
//        choiceService.makeChoice(intermediaryAlternativePerson5);
//        choiceService.makeChoice(intermediaryAlternativePerson5);
//        choiceService.makeChoice(intermediaryAlternativePerson5);
//        choiceService.makeChoice(intermediaryAlternativePerson5);
//        choiceService.makeChoice(intermediaryAlternativePerson5);
    }

    /**
     * Erase Data
     */
    public void eraseData() {
        alternativeService.eraseData();
    }

    public void startProgram() {
        final RootAlternative rootAlternative = alternativeService.findAllRootAlternatives().findFirst().orElseThrow();
        startProgram(rootAlternative.getId());
    }

    private void startProgram(final Long alternativeId) {
        final AbstractAlternative abstractAlternative = alternativeService.findById(alternativeId).orElseThrow();
        final List<IntermediaryAlternative> intermediaryAlternatives = alternativeService.findChildrenFromAlternativeId(abstractAlternative.getId()).collect(Collectors.toList());
        if (intermediaryAlternatives.size() == 0) {
            choiceService.makeChoice((IntermediaryAlternative) abstractAlternative);
            final Map<String, List<Choice>> choicesMap = choiceRepository.findAll().collect(Collectors.groupingBy(Choice::getPath));
            choicesMap.forEach((k, v) -> System.out.println(k + " = " + v.size()));
            System.out.println("------------------------------------------------");
            startProgram();
        }

        System.out.println(abstractAlternative.getMessageToNext());
        final HashMap<Integer, Long> alternativesMap = showAndReturnOptions(intermediaryAlternatives);

        choice(alternativesMap, abstractAlternative.getNextIsMultipleChoice());
    }

    private void startProgram(final Long... alternativeIds) {
        System.out.println(alternativeIds);
    }

    public void choice(final HashMap<Integer, Long> alternativesMap, final boolean isMultipleChoice) {
//        if (isMultipleChoice) {
//            final Scanner myObj = new Scanner(System.in);
//            final int choice = Integer.parseInt(myObj.nextLine());
//            closeIfExit(choice);
//
//            final String[] choices = String.valueOf(choice).split("");
//
//            final List<Long> listAlternatives = Arrays
//                    .stream(choices)
//                    .map(choiceInt -> alternativesMap.get(Integer.valueOf(choiceInt))).collect(Collectors.toList());
//            final Long[] alternatives = Utils.getArrayFromList(listAlternatives);
//
//            startProgram(alternatives);
//        }
        final Scanner myObj = new Scanner(System.in);
        final int choice = Integer.parseInt(myObj.nextLine());
        closeIfExit(choice);

        startProgram(alternativesMap.get(choice));
    }

    private static void closeIfExit(int choice) {
        if (choice == 0)
            System.exit(-1);
    }

    private static HashMap<Integer, Long> showAndReturnOptions(final List<IntermediaryAlternative> intermediaryAlternatives) {
        final HashMap<Integer, Long> alternativesMap = new HashMap<>();
        for (int i = 0; i < intermediaryAlternatives.size(); i++) {
            System.out.println(i + 1 + " - " + intermediaryAlternatives.get(i).getPath());
            alternativesMap.put(i + 1, intermediaryAlternatives.get(i).getId());
        }
        System.out.println("0 - exit");
        return alternativesMap;
    }

}
