package h07;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class PathFinder<L, D> implements Function<NodePointer<L, D>, List<NodePointer<L, D>>> {

    /**
     * Rekonstruiert den Pfad vom Endkonten zum Startknoten.
     * @param endNode Der Endkonten.
     * @return Den Pfad vom Endknoten zum Startknoten.
     */
	@Override
	public List<NodePointer<L, D>> apply(NodePointer<L, D> endNode) {
        List<NodePointer<L, D>> tmp = new LinkedList<>();


        NodePointer<L, D> current = endNode;
        while (current != null) {
            tmp.add(current);
            current = current.getPredecessor();
        }



        List<NodePointer<L, D>> result = new LinkedList<>();
        for (int i = tmp.size() - 1; i >= 0; i--)  {
            result.add(tmp.get(i));
        }

        return result;
    }
}
