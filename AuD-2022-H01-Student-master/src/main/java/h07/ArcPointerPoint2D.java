package h07;

import javax.swing.plaf.basic.BasicDesktopIconUI;
import java.util.HashMap;
import java.util.List;

public class ArcPointerPoint2D implements ArcPointer<Double, Double> {
    private final HashMap<Point2D, NodePointerPoint2D> existingNodePointers;
    private final HashMap<Pair<Point2D, Point2D>, ArcPointerPoint2D> existingArcPointers;
    private final Point2DCollection collection;
    private final Point2D source;
    private final Point2D destination;

    /**
     * Erzeugt einen Pointer auf eine Kante, für eine gegebene Punktsammlung.
     * @param existingNodePointers Die bereits bestehenden NodePointer.
     * @param existingArcPointers Die bereits bestehenden ArcPointer.
     * @param source Die Quelle der Kante.
     * @param destination Das Ziel der Kante.
     * @param collection Die Punktsammlung.
     */
    public ArcPointerPoint2D(HashMap<Point2D, NodePointerPoint2D> existingNodePointers,
                             HashMap<Pair<Point2D, Point2D>, ArcPointerPoint2D> existingArcPointers,
                             Point2D source, Point2D destination, Point2DCollection collection) {

        this.collection = collection;
        this.source = source;
        this.destination = destination;
        this.existingArcPointers = existingArcPointers;
        this.existingNodePointers = existingNodePointers;



        this.existingArcPointers.put(new Pair<>(source, destination), this);
    }

    @Override
    public Double getLength() {
        return Math.sqrt((destination.getX() - source.getX()) * (destination.getX() - source.getX()) + (destination.getY() - source.getY()) * (destination.getY() - source.getY()));
    }

    @Override
    public NodePointer<Double, Double> destination() {
        if (!existingNodePointers.containsKey(destination)) new NodePointerPoint2D(existingNodePointers, existingArcPointers, destination, collection);
        if (!existingNodePointers.containsKey(source))  new NodePointerPoint2D(existingNodePointers, existingArcPointers, source, collection);

        existingNodePointers.get(destination).setPredecessor(existingNodePointers.get(source));

        return existingNodePointers.get(destination);

    }
}
