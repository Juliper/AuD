package h07;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

public class Point2DCollection {
    private final List<Point2D> points;
    private final double maxArcLength;

    /**
     * Erzeugt eine Sammlung aus 2D Punkten mit impliziten Kanten zwischen den Punkten,
     * falls der euklidische Abstand kleiner als maxArcLength ist und die Punkte nicht gleich sind.
     * @param points Die Punkte der Sammlung.
     * @param maxArcLength Die maximale Kantenlänge.
     */
    public Point2DCollection(List<Point2D> points, double maxArcLength) {
        this.points = points;
        this.maxArcLength = maxArcLength;
    }

    /**
     * Erzeugt eine Punktsammlung der Größe pointCount im Intervall from bis to,
     * mit impliziten Kanten zwischen den Punkten,
     * falls der euklidische Abstand kleiner als maxArcLength ist und die Punkte nicht gleich sind.
     * @param pointCount Die Anzahl der Punkte die generiert werden sollen.
     * @param from Die untere Grenze des Punktwerteintervalls.
     * @param to Die obere Grenze des Punktwerteintervalls.
     * @param maxArcLength Die maximale Kantenlänge.
     */
    public Point2DCollection(int pointCount, Point2D from, Point2D to, double maxArcLength) {
        List<Point2D> points = new LinkedList<>();

        Iterator<Double> xs = ThreadLocalRandom.current().doubles(from.getX(), to.getX()).iterator();
        Iterator<Double> ys = ThreadLocalRandom.current().doubles(from.getY(), to.getY()).iterator();

        for (int i = 0; i < pointCount; i++)    points.add(new Point2D(xs.next(), ys.next()));


        this.maxArcLength = maxArcLength;
        this.points = points;
    }

    /**
     * Gib die Punkte der Punktesammlung zurück.
     * @return Die Punkte der Punktesammlung.
     */
    public List<Point2D> getPoints() {
        return points;
    }

    /**
     * Gibt die maximale Länge der Kanten zurück.
     * @return Die maximale Länge der Kanten.
     */
    public double getMaxArcLength() {
        return maxArcLength;
    }
}
