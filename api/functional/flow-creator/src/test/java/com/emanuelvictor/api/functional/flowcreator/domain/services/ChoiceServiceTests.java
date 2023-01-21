package com.emanuelvictor.api.functional.flowcreator.domain.services;

import com.emanuelvictor.api.functional.flowcreator.domain.entities.Choice;
import com.emanuelvictor.api.functional.flowcreator.domain.entities.Node;
import com.emanuelvictor.api.functional.flowcreator.domain.ports.repositories.ChoiceRepository;
import com.emanuelvictor.api.functional.flowcreator.domain.ports.repositories.EdgeRepository;
import com.emanuelvictor.api.functional.flowcreator.infrastructure.helprs.PopulateHelper;
import lombok.SneakyThrows;
import org.gephi.io.exporter.api.ExportController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openide.util.Lookup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 */
@SpringBootTest
public class ChoiceServiceTests {

    @Autowired
    private PopulateHelper populateHelper;

    @Autowired
    private ChoiceService choiceService;

    @Autowired
    private EdgeRepository edgeRepository;

    @Autowired
    private ChoiceRepository choiceRepository;

    @BeforeEach
    public void beforeEach() {
        populateHelper.eraseData();
        populateHelper.populateData();
    }

    /**
     * TODO exclude
     */
    @Test
    public void gephiTest() {

        Set<String> ssadfa= new HashSet<>();

        ssadfa.addAll(edgeRepository
                .findAll()
                .map(edge -> edge.getSource().getValue() + ";" + edge.getSource().getId())
                .collect(Collectors.toSet()));
        ssadfa.addAll(edgeRepository
                .findAll()
                .map(edge -> edge.getTarget().getValue() + ";" + edge.getTarget().getId())
                .collect(Collectors.toSet()));
        System.out.println("Label;id");
        ssadfa.forEach(System.out::println);


        System.out.println("Source;Target;Type");
        edgeRepository.findAll().forEach(edge -> System.out.println(edge.getSource().getId() + ";" + edge.getTarget().getId() + ";Directed"));
//        nodes.forEach(System.out::println);
//        // Creating graph
//        ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
//        pc.newProject();
//        Workspace workspace = pc.getCurrentWorkspace();
//
//        GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getGraphModel();
//
//        DirectedGraph directedGraph = graphModel.getDirectedGraph();
//
//        edgeRepository.findAll().forEach(edgeFromDatabase -> {
//
//            // Creating nodes
//            Node source = graphModel.factory().newNode(edgeFromDatabase.getSource().getValue());
//            source.setLabel(edgeFromDatabase.getSource().getValue());
//            if(directedGraph.getNode(edgeFromDatabase.getSource().getValue()) != null)
//                source = directedGraph.getNode(edgeFromDatabase.getSource().getValue());
//            Node target = graphModel.factory().newNode(edgeFromDatabase.getTarget().getValue());
//            target.setLabel(edgeFromDatabase.getTarget().getValue());
//            if(directedGraph.getNode(edgeFromDatabase.getTarget().getValue()) != null)
//                target = directedGraph.getNode(edgeFromDatabase.getTarget().getValue());
//
//
//            directedGraph.addAllNodes(new HashSet<>(Arrays.asList(source, target)));
//            Edge edgeToReport = graphModel.factory().newEdge(source, target, 1, true);
//            directedGraph.addEdge(edgeToReport);
//
//        });
//
//        //Get a UndirectedGraph now and count edges
//        UndirectedGraph undirectedGraph = graphModel.getUndirectedGraph();
//
//        //Layout for 1 minute
//        AutoLayout autoLayout = new AutoLayout(10, TimeUnit.SECONDS);
//        autoLayout.setGraphModel(graphModel);
//        YifanHuLayout firstLayout = new YifanHuLayout(null, new StepDisplacement(1f));
//        ForceAtlasLayout secondLayout = new ForceAtlasLayout(null);
//
//        AutoLayout.DynamicProperty adjustBySizeProperty = AutoLayout.createDynamicProperty("forceAtlas.adjustSizes.name", Boolean.TRUE, 1f);//True after 10% of layout time
//        AutoLayout.DynamicProperty repulsionProperty = AutoLayout.createDynamicProperty("forceAtlas.repulsionStrength.name", new Double(500.), 0f);//500 for the complete period
//        autoLayout.addLayout(firstLayout, 0.5f);
//        autoLayout.addLayout(secondLayout, 0.5f, new AutoLayout.DynamicProperty[]{adjustBySizeProperty,repulsionProperty});
//        autoLayout.execute();
//
//        PreviewModel model = Lookup.getDefault().lookup(PreviewController.class).getModel();
//        model.getProperties().putValue(PreviewProperty.SHOW_NODE_LABELS, Boolean.TRUE);
//        model.getProperties().putValue(PreviewProperty.EDGE_COLOR, new EdgeColor(Color.GRAY));
//        model.getProperties().putValue(PreviewProperty.EDGE_THICKNESS, new Float(0.1f));
//        model.getProperties().putValue(PreviewProperty.NODE_LABEL_FONT, model.getProperties().getFontValue(PreviewProperty.NODE_LABEL_FONT).deriveFont(18));
//
//export();
//        printChoices();
    }

    @SneakyThrows
    void export() {
        //Export
        ExportController ec = Lookup.getDefault().lookup(ExportController.class);
        try {
            ec.exportFile(new File("headless_simple.pdf"));
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
    }

    void printChoices() {
        final Map<String, List<Choice>> choicesMap = choiceRepository.findAll().collect(Collectors.groupingBy(Choice::getPath));
        choicesMap.forEach((k, v) -> System.out.println(k + " = " + v.size()));
    }

}
