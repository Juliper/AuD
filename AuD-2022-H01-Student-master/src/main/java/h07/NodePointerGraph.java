package h07;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class NodePointerGraph<L, D> implements NodePointer<L, D> {
    private final HashMap<GraphNode<L>, NodePointerGraph<L, D>> existingNodePointers;
    private final HashMap<GraphArc<L>, ArcPointerGraph<L, D>> existingArcPointers;
    private final GraphNode<L> graphNode;
    private @Nullable D distance;
    private @Nullable NodePointer<L, D> predecessor;

    /**
     * Erzeugt einen Verweis auf einen Knoten eines Graphen.
     * @param existingNodePointers Die bereits bestehenden NodePointer.
     * @param existingArcPointers Die bereits bestehenden ArcPointer.
     * @param graphNode Der Knoten des Graphen, auf den der Verweis erzeugt werden soll.
     */
	public NodePointerGraph(HashMap<GraphNode<L>, NodePointerGraph<L, D>> existingNodePointers,
                            HashMap<GraphArc<L>, ArcPointerGraph<L, D>> existingArcPointers,
                            GraphNode<L> graphNode) {

        this.existingNodePointers = existingNodePointers;
        this.existingArcPointers = existingArcPointers;
        this.graphNode = graphNode;


        this.existingNodePointers.put(graphNode, this);
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


        for (GraphArc<L> lGraphArc : graphNode.getOutgoingArcs()) {

            if (!existingArcPointers.containsKey(lGraphArc))    new ArcPointerGraph<>(existingNodePointers, existingArcPointers, lGraphArc);

            list.add(existingArcPointers.get(lGraphArc));

        }


        return list.iterator();
	}
}
