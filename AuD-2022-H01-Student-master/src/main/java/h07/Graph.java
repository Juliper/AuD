package h07;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Graph<L> {
    private final List<GraphNode<L>> nodes;

    /**
     * Speichert die gegebenen Knoten als Graphen ab.
     * @param nodes Die Knoten des Graphen.
     */
	public Graph(List<GraphNode<L>> nodes) {
        this.nodes = nodes;
	}

    /**
     * Erzeugt anhand einer Adjazenzmatrix einen Graphen.
     * @param adjacencyMatrix Die Adjazenzmatrix, die zu einem Graphen konvertiert werden soll.
     */
	public Graph(AdjacencyMatrix<L> adjacencyMatrix) {

        L[][] matrix = adjacencyMatrix.getMatrix();
        List<GraphNode<L>> result = new LinkedList<>();


        for (L[] nodes : matrix)    result.add(new GraphNode<>());


        for (int i = 0; i < matrix.length; i++) {

            List<GraphArc<L>> outgoingArcs = new LinkedList<>();

            for (int j = 0; j < matrix[i].length; j++) {

                if (matrix[i][j] != null)   {
                    outgoingArcs.add(new GraphArc<>(matrix[i][j], result.get(j)));
                }
            }


            result.get(i).setOutgoingArcs(outgoingArcs);
        }



        this.nodes = result;
	}

    /**
     * Gibt die Knoten des Graphen zurück.
     * @return Die Knoten des Graphen.
     */
	public List<GraphNode<L>> getNodes() {
        return nodes;
	}
}
