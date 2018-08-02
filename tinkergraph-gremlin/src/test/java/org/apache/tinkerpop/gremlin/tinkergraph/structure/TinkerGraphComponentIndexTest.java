//package org.apache.tinkerpop.gremlin.tinkergraph.structure;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TinkerGraphComponentIndexTest {

        public static  ArrayList<TinkerVertex> testArray = new ArrayList<>();

    public static void  main (String [] args) throws Exception {
        int counter = 0;

        FileReader fileStream = new FileReader("/home/eakillio/Projects/Huge Files/twitter_combined.txt"); // a stream created
        Scanner input = new Scanner(fileStream);

        TinkerGraph graph = TinkerGraph.open();
        graph.disableComponentIndex();

        int n1 = -1;
        int n2 = -1;

        long start = System.currentTimeMillis(); //Chronometer starts
        while (input.hasNext()) {
            counter++;

            n1 = Integer.parseInt(input.next());//vertex listte var mu varsa ekle yoksa yarat
            n2 = Integer.parseInt(input.next());
            TinkerVertex v1, v2;

            GraphTraversal<Vertex, Vertex> traversal = graph.traversal().V(n1);
            if (traversal.hasNext()) {
                v1 = (TinkerVertex) traversal.next();
            } else {
                v1 = (TinkerVertex) graph.addVertex(T.id, n1);
                testArray.add(v1);
            }

            traversal = graph.traversal().V(n2);
            if (traversal.hasNext()) {
                v2 = (TinkerVertex)traversal.next();
            } else {// add to array
                v2 = (TinkerVertex) graph.addVertex(T.id, n2);
                testArray.add(v2);
            }

            v1.addEdge("connected", v2);


            if (counter % 10000 == 0) {
                System.out.println("Number of lines processed: " + counter);
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("Process Took : " + ((end - start)) + " ms"); //Chronometer ends


        start= System.currentTimeMillis(); //Chronometer starts

        Random r =new Random();
        for (int x=0; x<1000; x++) {
            Integer i= r.nextInt(testArray.size());
            Integer j= r.nextInt(testArray.size());

            TinkerVertex v =  testArray.get(i);
            TinkerVertex w =  testArray.get(j);

            System.out.println(v.canReach(w));



        }
        end = System.currentTimeMillis();
        System.out.println("Process Took : " + ((end - start)) + " ms"); //Chronometer ends

        //graph loadded


    }

}
