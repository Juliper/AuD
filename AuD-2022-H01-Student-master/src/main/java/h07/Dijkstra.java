package h07;

import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class Dijkstra<L, D>  {
    private final Comparator<D> comparator;
    private final BiFunction<D, L, D> distanceFunction;
    private final IPriorityQueue<NodePointer<L, D>> queue;

    private @Nullable Predicate<NodePointer<L, D>> predicate;

    /**
     * Erzeugt eine Instanz von Dijkstra, welche den Dijkstra Algorithmus ausführt.
     * @param comparator Ein Vergleichsoperator, welcher die Priorität, in der die Knoten in Dijkstra abgearbeitet werden, vorgibt.
     * @param distanceFunction Eine Distanzfunktion, welche für eine gegebene Quellknotendistanz und eine Kantenlänge die Zielknotendistanz ermittelt.
     * @param queueFactory Erzeugt für einen gegebenen Vergleichsoperator eine PriorityQueue, welche nach diesem Vergleichskriterium die Knoten sortiert.
     */
	public Dijkstra(Comparator<D> comparator, BiFunction<D, L, D> distanceFunction,
			Function<Comparator<NodePointer<L,D>>, IPriorityQueue<NodePointer<L, D>>> queueFactory) {

        this.comparator = comparator;
        this.distanceFunction = distanceFunction;

        Comparator<NodePointer<L, D>> newCmp = new Comparator<NodePointer<L, D>>() {
            @Override
            public int compare(NodePointer<L, D> o1, NodePointer<L, D> o2) {
                return 0;
            }
        };

        this.queue = queueFactory.apply(newCmp);

	}

    /**
     * Initialisiert den Algorithmus von Dijkstra.
     * @param startNode Der Startknoten, von dem der Algorithmus aus die Suche startet.
     */
	public void initialize(NodePointer<L, D> startNode) {
        queue.clear();
        queue.add(startNode);


    }

    /**
     * Initialisiert den Algorithmus von Dijkstra, erhält zusätzlich ein Prädikat, welches beim Eintreffen die Suche vorzeitig beendet.
     * @param startNode Der Startknoten, von dem der Algorithmus aus die Suche startet.
     * @param predicate Das Prädikat, welches beim Eintreffen die Suche vorzeitig beendet.
     */
	public void initialize(NodePointer<L, D> startNode, Predicate<NodePointer<L, D>> predicate) {
        queue.clear();
        queue.add(startNode);

        this.predicate = predicate;
    }

    /**
     * Startet den Algorithms von Dijkstra.
     * @return Alle ermittelten Knoten, ausgenommen den Startknoten.
     */
	public List<NodePointer<L, D>> run() {
        List<NodePointer<L, D>> result = new LinkedList<>();
        NodePointer<L, D> start = queue.deleteFront();

        expandNode(start);


        while (!finished(queue.getFront()))    {
            NodePointer<L, D> tmp = queue.deleteFront();

            result.add(tmp);
            expandNode(tmp);

            //cleanup
            queue.delete(start);
            for (NodePointer<L, D> finish : result) queue.delete(finish);
        }


        if (queue.getFront() != null)   result.add(queue.getFront());



        return result;
	}

    /**
     * Expandiert den aktuellen Knoten, wie aus Dijkstra bekannt.
     * @param currentNode Zu expandierender Knoten.
     */
	private void expandNode(NodePointer<L, D> currentNode) {
        if (currentNode == null)    return;
        if (currentNode.getDistance() == null) return;
        Iterator<ArcPointer<L, D>> iterator = currentNode.outgoingArcs();


        while (iterator.hasNext())  {

            ArcPointer<L, D> arc = iterator.next();
            D newDistance = this.distanceFunction.apply(currentNode.getDistance(), arc.getLength());


            if (arc.destination().getDistance() == null)    {

                arc.destination().setPredecessor(currentNode);
                arc.destination().setDistance(newDistance);

            }
            else if (queue.contains(arc.destination()))  {
                queue.delete(arc.destination());

                if (comparator.compare(newDistance, arc.destination().getDistance()) < 0)   {

                    arc.destination().setPredecessor(currentNode);
                    arc.destination().setDistance(newDistance);

                }
            }


            queue.add(arc.destination());
        }

	}

    /**
     * Überprüft, ob der Algorithmus von Dijkstra terminiert.
     * @param currentNode Der Knoten, anhand dessen überprüft wird, ob der Algorithmus terminiert.
     * @return true, falls der Algorithmus terminiert.
     */
	private boolean finished(NodePointer<L, D> currentNode) {

        if (predicate != null)  return predicate.test(currentNode) || currentNode == null;

        return currentNode == null;
    }
}
