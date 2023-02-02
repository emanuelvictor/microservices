package com.emanuelvictor.api.functional.flowcreator.domain.services;

import com.emanuelvictor.api.functional.flowcreator.domain.entities.option.Option;
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.Alternative;
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.IntermediaryAlternative;
import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.RootAlternative;
import com.emanuelvictor.api.functional.flowcreator.domain.ports.repositories.AlternativeRepository;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
@RequiredArgsConstructor
public class AlternativeService {

    /**
     *
     */
    private final AlternativeRepository alternativeRepository;

    /**
     * @param alternative {@link IntermediaryAlternative}
     * @return {@link IntermediaryAlternative}
     */
    public IntermediaryAlternative save(final IntermediaryAlternative alternative) {
        if (alternative.getPrevious().getNextIsMultipleChoice()) {
            final Set<IntermediaryAlternative> alternatives = this.alternativeRepository.findChildrenFromAlternativeId(alternative.getPrevious().getId()).collect(Collectors.toSet());
            alternativeRepository.saveAll(generateAlternatives(alternatives, alternative));
        }
        return alternativeRepository.save(alternative);
    }

    /**
     * @param alternative {@link RootAlternative}
     * @return {@link RootAlternative}
     */
    public RootAlternative save(final RootAlternative alternative) {
        return alternativeRepository.save(alternative);
    }

    /**
     * @return {@link Stream<RootAlternative>}
     */
    public Stream<RootAlternative> findAllRootAlternatives() {
        return alternativeRepository.findAllRootAlternatives();
    }

    /**
     * @param alternativeId {@link Integer}
     * @return {@link Optional< Alternative >}
     */
    public Optional<Alternative> findById(final Integer alternativeId) {
        return alternativeRepository.findById(alternativeId);
    }

    /**
     * @param id {@link Integer}
     * @return {@link Stream<IntermediaryAlternative>}
     */
    public Stream<IntermediaryAlternative> findChildrenFromAlternativeId(final Integer id) {
        return alternativeRepository.findChildrenFromAlternativeId(id);
    }

    /**
     *
     */
    public void eraseData() {
        alternativeRepository.eraseData();
    }

    /**
     * @param alternatives   {@link Set<IntermediaryAlternative>}
     * @param newAlternative {@link IntermediaryAlternative}
     * @return {@link Set<IntermediaryAlternative>}
     */
    public static Set<IntermediaryAlternative> generateAlternatives(final Set<IntermediaryAlternative> alternatives, final IntermediaryAlternative newAlternative) {
//        alternatives.add(newAlternative);
//
//        final Set<String> isolatedValuesFromAlternatives = extractIsolatedValuesFromAlternatives(alternatives.stream()).collect(Collectors.toSet());
//        final HashMap<Integer, String> isolatedValuesFromAlternativesMapedToIndexes = createIndexesToAlternatives(isolatedValuesFromAlternatives);
//
//        final List<int[]> combinations = generateCombinations(isolatedValuesFromAlternatives.size());
//
//        final Set<IntermediaryAlternative> newAlternativesGenerated = new HashSet<>();
//
//        for (final int[] combination : combinations) {
//            final List<String> valuesFromCombination = new ArrayList<>();
//            for (final int i : combination) {
//                valuesFromCombination.add(isolatedValuesFromAlternativesMapedToIndexes.get(i));
//            }
//
//            final IntermediaryAlternative intermediaryAlternative = new IntermediaryAlternative(
//                    newAlternative.getPrevious(),
//                    newAlternative.getMessageToNext(),
//                    newAlternative.getNextIsMultipleChoice(),
//                    valuesFromCombination.stream().map(Option::new).collect(Collectors.toList()) //TODO
//            );
//            newAlternativesGenerated.add(intermediaryAlternative);
//        }
//
//        return mergeAlternativeLists(alternatives, newAlternativesGenerated);
        return null;
    }

    /**
     * This method preserve current Alternatives and add new Alternatives.
     *
     * @param alternativesToPreserve {@link Set<IntermediaryAlternative>}
     * @param alternativesToMerge    {@link Set<IntermediaryAlternative>}
     * @return {@link Set<IntermediaryAlternative>}
     */
    static Set<IntermediaryAlternative> mergeAlternativeLists(final Set<IntermediaryAlternative> alternativesToPreserve, final Set<IntermediaryAlternative> alternativesToMerge) {

        final Set<IntermediaryAlternative> collectionToReturn = new HashSet<>();

        collectionToReturn.addAll(alternativesToPreserve);
        collectionToReturn.addAll(alternativesToMerge.stream().filter(alternativeToMerge -> alternativesToPreserve.stream().noneMatch(alternativeToMerge::compareValues)).collect(Collectors.toSet()));

        return collectionToReturn;
    }

    /**
     * TODO make tests
     * Define one index from each alternative
     *
     * @param alternatives {@link Set<String>}
     * @return {@link HashMap}
     */
    static HashMap<Integer, String> createIndexesToAlternatives(final Set<String> alternatives) {
        final HashMap<Integer, String> alternativesWithIndexes = new HashMap<>();

        final AtomicInteger i = new AtomicInteger(0);
        alternatives.forEach(alternative -> alternativesWithIndexes.put(i.getAndIncrement(), alternative));

        return alternativesWithIndexes;
    }

    /**
     * This method extract the values from all alternatives.
     * Example: Emanuel, Sarah, [Emanuel, Sarah], Jackson, [Emanuel, Jackson], [Sarah, Jackson], [Sarah, Emanuel, Jackson] to Sarah, Emanuel, Jackson
     *
     * @param alternatives {@link Stream<IntermediaryAlternative> }
     * @return {@link Stream<String> }
     */
    static Stream<String> extractIsolatedValuesFromAlternatives(final Stream<IntermediaryAlternative> alternatives) {
        return alternatives.
                map(intermediaryAlternative ->
                        Arrays.stream(intermediaryAlternative.getOptions())
                        .map(option -> Arrays.stream(option.getIdentifier().split(",")).collect(Collectors.toSet()))
                        .collect(Collectors.toSet())
                ).flatMap(sets -> sets.stream().flatMap(Collection::stream));
    }

    /**
     * @param countAlternatives int
     * @return {@link Set<int>}
     */
    public static List<int[]> generateCombinations(final int countAlternatives) {
        final List<int[]> elements = new ArrayList<>();
        for (int i = 1; i <= countAlternatives; i++) {
            elements.addAll(generateCombinations(countAlternatives, i));
        }
        return elements;
    }

    /**
     * @param countAlternatives {@link int}
     * @param groupAlternatives {@link int}
     * @return {@link Set <int>}
     */
    public static Set<int[]> generateCombinations(final int countAlternatives, final int groupAlternatives) {
        final Set<int[]> combinations = new HashSet<>();
        final int[] combination = new int[groupAlternatives];

        // initialize with lowest lexicographic combination
        for (int i = 0; i < groupAlternatives; i++) {
            combination[i] = i;
        }

        while (combination[groupAlternatives - 1] < countAlternatives) {
            combinations.add(combination.clone());

            // generate next combination in lexicographic order
            int t = groupAlternatives - 1;
            while (t != 0 && combination[t] == countAlternatives - groupAlternatives + t) {
                t--;
            }
            combination[t]++;
            for (int i = t + 1; i < groupAlternatives; i++) {
                combination[i] = combination[i - 1] + 1;
            }
        }

        return combinations;
    }
}