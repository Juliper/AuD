package h07;

import java.util.List;

public class AdjacencyMatrix<L> {
    private final L[][] matrix;

    /**
     * Initialisiert die Adjazenzmatrix über die gegebene Matrix.
     * @param matrix Die Matrix, die als Adjazenzmatrix genutzt werden soll.
     */
    public AdjacencyMatrix(L[][] matrix) {
        this.matrix = matrix;
    }

    /**
     * Erzeugt aus dem gegebenen Graph eine Adjazenzmatrix.
     * @param graph Der zu konvertierende Graph.
     */
	@SuppressWarnings("unchecked")
	public AdjacencyMatrix(Graph<L> graph) {
        List<GraphNode<L>> nodes = graph.getNodes();
        L[][] result = (L[][]) new Object[nodes.size()][nodes.size()];


        //conversion
        for (int i = 0; i < nodes.size(); i++) {
            List<GraphArc<L>> arcs = nodes.get(i).getOutgoingArcs();

            for (GraphArc<L> arc : arcs) {

                for (int j = 0; j < nodes.size(); j++)  {

                    if (nodes.get(j).equals(arc.getDestination()))  {

                        result[i][j] = arc.getLength();

                    }

                }

            }

        }



        this.matrix = result;
	}

    /**
     * Gibt die Adjazenzmatrix zurück.
     * @return Die Adjazenzmatrix.
     */
	public L[][] getMatrix() {
        return matrix;
	}
}
