package h07;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class NodePointerAdjacencyMatrix<L, D> implements NodePointer<L, D> {

    private final AdjacencyMatrix<L> adjacencyMatrix;
    private final int row;
    private final HashMap<Integer, NodePointerAdjacencyMatrix<L, D>> existingNodePointers;
    private final HashMap<Pair<Integer, Integer>, ArcPointerAdjacencyMatrix<L, D>> existingArcPointers;

    private @Nullable D distance;
    private @Nullable NodePointer<L, D> predecessor;

    /**
     * Erzeugt einen Verweis auf eine Kante eines Graphen, gegeben durch eine Adjazenzmatrix.
     * @param existingNodePointers Die bereits bestehenden NodePointer.
     * @param existingArcPointers Die bereits bestehenden ArcPointer.
     * @param adjacencyMatrix Die Adjazenzmatrix.
     * @param row Die Zeile der Matrix (Knoten des Graphen).
     */
	public NodePointerAdjacencyMatrix(HashMap<Integer, NodePointerAdjacencyMatrix<L, D>> existingNodePointers,
                                      HashMap<Pair<Integer, Integer>, ArcPointerAdjacencyMatrix<L, D>> existingArcPointers,
                                      AdjacencyMatrix<L> adjacencyMatrix, int row) {

        this.adjacencyMatrix = adjacencyMatrix;
        this.row = row;
        this.existingArcPointers = existingArcPointers;
        this.existingNodePointers = existingNodePointers;


        this.existingNodePointers.put(row, this);

    }

	@Override
	public @Nullable D getDistance() {
		return distance;
	}

	@Override
	public void setDistance(@NotNull D distance) {
		this.distance = distance;
	}

	@Override
	public @Nullable NodePointer<L, D> getPredecessor() {
		return predecessor;
	}

	@Override
	public void setPredecessor(@NotNull NodePointer<L, D> predecessor) {
		this.predecessor = predecessor;
	}

	@Override
	public Iterator<ArcPointer<L, D>> outgoingArcs() {

        List<ArcPointer<L, D>> list = new LinkedList<>();

        for (int i = 0; i < adjacencyMatrix.getMatrix().length; i++)    {

            if (adjacencyMatrix.getMatrix()[row][i] != null)    {

                if (!existingArcPointers.containsKey(new Pair<>(row, i)))    new ArcPointerAdjacencyMatrix<>(existingNodePointers, existingArcPointers, adjacencyMatrix, row, i);


                list.add(existingArcPointers.get(new Pair<>(row, i)));

            }

        }

        return list.iterator();
	}
}
