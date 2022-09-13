package h07;

import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.HashMap;

public class PriorityQueueHeap<T> implements IPriorityQueue<T> {
    private final Comparator<T> priorityComparator;
    private final HashMap<T, Integer> indexMap;
    private T[] heap;
    private int size;

    /**
     * Erstellt eine Priority Queue basierend auf einem Heap, mit durch priorityComparator induzierter Ordnung.
     * @param priorityComparator Die auf die Priority Queue induzierte Ordnung.
     * @param capacity Die Kapazität der Queue.
     */
	public PriorityQueueHeap(Comparator<T> priorityComparator, int capacity) {
        this.priorityComparator = priorityComparator;
        this.indexMap = new HashMap<>();
        this.heap = (T[]) new Object[capacity];
        this.size = 0;
	}



    @Override
	public void add(T item) {

        this.heap[size] = item;
        this.indexMap.put(item, size);

        if (size == 0)  {
            size++;
            return;
        }

        //parent = (i % 2 == 1) ? (i - 1) / 2 : (i - 2) / 2
        //left kid = i * 2 + 1
        //right kid = i * 2 + 2

        int parent = (size % 2 == 1) ? (size - 1) / 2 : (size - 2) / 2;
        int current = size++;


        while (current > 0 && priorityComparator.compare(heap[current], heap[parent]) > 0) {

            swap(parent, current);

            current = parent;
            parent = (current % 2 == 1) ? (current - 1) / 2 : (current - 2) / 2;

        }

	}

	@Override
	public @Nullable T delete(T item) {

        if (!contains(item))    return null;

        int current = indexMap.get(item);
        T result = heap[current];

        //if end
        if (current == size - 1)  {
            heap[current] = null;
            indexMap.remove(result);

            size--;
            return result;
        }


        //delete
        heap[current] = heap[--size];
        heap[size] = null;

        indexMap.remove(result);
        indexMap.put(heap[current], current);




        //which way?
        boolean up = false;
        boolean down = false;
        int parent = (current % 2 == 1) ? (current - 1) / 2 : (current - 2) / 2;
        int leftKid = current * 2 + 1;
        int rightKid = current * 2 + 2;

        if (current != 0)   {
            int cmp = priorityComparator.compare(heap[current], heap[parent]);

            if (cmp > 0)    up = true;

        }

        if (leftKid < heap.length && heap[leftKid] != null)    {
            down = priorityComparator.compare(heap[current], heap[leftKid]) < 0;

            if (rightKid < heap.length && heap[rightKid] != null)   {
                down = down || priorityComparator.compare(heap[current], heap[rightKid]) < 0;
            }
        }


        //up
        if (up) {

            while (current > 0 && priorityComparator.compare(heap[current], heap[parent]) > 0) {

                swap(parent, current);

                current = parent;
                parent = (current % 2 == 1) ? (current - 1) / 2 : (current - 2) / 2;

            }

        }



        //down
        if (down)   {

            while ((leftKid < heap.length && heap[leftKid] != null) || rightKid < heap.length && heap[rightKid] != null)    {
                boolean swapped = false;

                if (rightKid < heap.length && heap[rightKid] != null)   {

                    if (priorityComparator.compare(heap[leftKid], heap[rightKid]) > 0)  {

                        if (priorityComparator.compare(heap[current], heap[leftKid]) < 0)   {
                            swap(current, leftKid);

                            current = leftKid;
                            swapped = true;
                        }

                    }
                    else {

                        if (priorityComparator.compare(heap[current], heap[rightKid]) < 0)   {
                            swap(current, rightKid);

                            current = rightKid;
                            swapped = true;
                        }

                    }

                }
                else {
                    if (priorityComparator.compare(heap[current], heap[leftKid]) < 0)   {
                        swap(current, leftKid);

                        current = leftKid;
                        swapped = true;
                    }
                }


                leftKid = current * 2 + 1;
                rightKid = current * 2 + 2;


                if (!swapped)   break;

            }

        }


        return result;
	}

    /**
     * Tauscht die Position der Elemente
     * @param index0 Die Position des ersten Elements.
     * @param index1 Die Position des zweiten Elements.
     */
	private void swap(int index0, int index1) {

        indexMap.put(heap[index0], index1);
        indexMap.put(heap[index1], index0);

        T tmp = heap[index0];

        heap[index0] = heap[index1];
        heap[index1] = tmp;
	}


	@Override
	public @Nullable T getFront() {
        if (heap.length == 0)   return null;

        return heap[0];
	}

	@Override
	public @Nullable T deleteFront() {
        T tmp = getFront();

        if (tmp != null)    delete(tmp);


        return tmp;
	}

	@Override
	public int getPosition(T item) {
        if (!contains(item))    return -1;

        return indexMap.get(item) + 1;
	}

    @Override
    public boolean contains(T item) {

        return this.indexMap.get(item) != null;
    }

    @Override
	public void clear() {
        this.heap = (T[]) new Object[this.heap.length];
	}

	@Override
	public Comparator<T> getPriorityComparator() {
        return priorityComparator;
	}

    /**
     * Gibt die zugrundeliegende Heapstruktur zurück.
     * @return Die zugrundeliegende Heapstruktur.
     */
    public Object[] getInternalHeap() {
        return heap;
    }
}
