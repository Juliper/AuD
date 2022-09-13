package h07;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class NodePointerPoint2D implements NodePointer<Double, Double> {
    private final HashMap<Point2D, NodePointerPoint2D> existingNodePointers;
    private final HashMap<Pair<Point2D, Point2D>, ArcPointerPoint2D> existingArcPointers;

    private final Point2DCollection collection;
    private final Point2D point;

    private @Nullable NodePointer<Double, Double> predecessor;
    private @Nullable Double distance;

    /**
     * Erzeugt einen Verweis auf einen Punkt einer Punktsammlung.
     * @param existingNodePointers Die bereits bestehenden NodePointer.
     * @param existingArcPointers Die bereits bestehenden ArcPointer.
     * @param point Der Punkt, auf den verwiesen wird.
     * @param collection Die Punktsammlung, die den Punkt enthält.
     */
    public NodePointerPoint2D(HashMap<Point2D, NodePointerPoint2D> existingNodePointers,
                              HashMap<Pair<Point2D, Point2D>, ArcPointerPoint2D> existingArcPointers,
                              Point2D point, Point2DCollection collection) {

        this.collection = collection;
        this.point = point;
        this.existingNodePointers = existingNodePointers;
        this.existingArcPointers = existingArcPointers;


        this.existingNodePointers.put(point, this);

    }

    @Override
    public @Nullable Double getDistance() {
        return distance;
    }

    @Override
    public void setDistance(@NotNull Double distance) {
        this.distance = distance;
    }

    @Override
    public @Nullable NodePointer<Double, Double> getPredecessor() {
        return predecessor;
    }

    @Override
    public void setPredecessor(@NotNull NodePointer<Double, Double> predecessor) {
        this.predecessor = predecessor;
    }

    @Override
    public Iterator<ArcPointer<Double, Double>> outgoingArcs() {
        List<ArcPointer<Double, Double>> result = new LinkedList<>();



        for (Point2D destination : collection.getPoints())  {

            double distance = Math.sqrt((destination.getX() - point.getX()) * (destination.getX() - point.getX()) + (destination.getY() - point.getY()) * (destination.getY() - point.getY()));

            if (distance != 0 && distance <= collection.getMaxArcLength())   {

                if (!existingArcPointers.containsKey(new Pair<>(point, destination)))   new ArcPointerPoint2D(existingNodePointers, existingArcPointers, point, destination, collection);

                result.add(existingArcPointers.get(new Pair<>(point, destination)));

            }

        }


        return result.iterator();
    }
}
