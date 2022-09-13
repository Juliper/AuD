package h07;

import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class PriorityQueueList<T> implements IPriorityQueue<T> {
	private final Comparator<T> priorityComparator;
	private final List<T> queue;

	/**
	 * Erstellt eine Priority Queue basierend auf einer Liste, mit durch priorityComparator induzierter Ordnung.
	 * @param priorityComparator Die auf die Priority Queue induzierte Ordnung.
	 */
	public PriorityQueueList(Comparator<T> priorityComparator) {
        this.priorityComparator = priorityComparator;
        this.queue = new LinkedList<>();
	}

	@Override
	public void add(T item) {

        int index = 0;
        for (T items : this.queue)   {

            int cmp = this.priorityComparator.compare(item, items);

            if (cmp == 0 || cmp > 0)    {
                this.queue.add(index, item);
                return;
            }

            index++;
        }

        this.queue.add(item);

	}

	@Override
	public @Nullable T delete(T item) {

        int index = 0;
        for (T items : this.queue)   {
            int cmp = this.priorityComparator.compare(item, items);
            if (cmp == 0)   return this.queue.remove(index);

            index++;
        }


        return null;
	}

	@Override
	public @Nullable T getFront() {

        if (!this.queue.isEmpty()) return this.queue.get(0);

        return null;
	}

	@Override
	public @Nullable T deleteFront() {

        if (!this.queue.isEmpty())  return this.queue.remove(0);

        return null;
	}

	@Override
	public int getPosition(T item) {

        int index = 1;
        for (T items : this.queue)   {

            int cmp = this.priorityComparator.compare(item, items);
            if (cmp == 0)   return index;

            index++;
        }

        return -1;
	}

    @Override
    public boolean contains(T item) {

        for (T items : this.queue)   {

            int cmp = this.priorityComparator.compare(item, items);
            if (cmp == 0)   return true;

        }

        return false;
    }

    @Override
	public Comparator<T> getPriorityComparator() {
        return priorityComparator;
	}

	@Override
	public void clear() {

        this.queue.clear();

	}

	/**
	 * Gibt die zur Realisierung der Priority Queue genutzte Liste zurück.
	 * @return Die Liste, die zur Realisierung der Priority Queue genutzt wird.
	 */
	public List<T> getInternalList() {
        return queue;
	}
}
