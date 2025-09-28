package main.com.beka.assignment.features;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;

public class ClosestPair {
    public static record Point(double x, double y) {}
    public static record Result(double dist, Point a, Point b) {}

    public static Result closest(Point[] pts) {
        if (pts == null || pts.length < 2) throw new IllegalArgumentException("need ≥2 points");
        Point[] Px = pts.clone();
        Arrays.sort(Px, Comparator.comparingDouble((Point p) -> p.x).thenComparingDouble(p -> p.y));
        Point[] Py = Px.clone();
        Arrays.sort(Py, Comparator.comparingDouble((Point p) -> p.y).thenComparingDouble(p -> p.x));
        return solve(Px, Py);
    }

    private static Result solve(Point[] Px, Point[] Py) {
        int n = Px.length;
        if (n <= 3) return brute(Px);

        int mid = n / 2;
        double xMid = Px[mid].x;

        Point[] Qx = Arrays.copyOfRange(Px, 0, mid);
        Point[] Rx = Arrays.copyOfRange(Px, mid, n);

        ArrayList<Point> Qy = new ArrayList<>(mid);
        ArrayList<Point> Ry = new ArrayList<>(n - mid);
        HashSet<Point> leftSet = new HashSet<>(Arrays.asList(Qx));
        for (Point p : Py) {
            if (p.x < xMid || (p.x == xMid && leftSet.contains(p))) Qy.add(p);
            else Ry.add(p);
        }

        Result left = solve(Qx, Qy.toArray(Point[]::new));
        Result right = solve(Rx, Ry.toArray(Point[]::new));
        Result best = (left.dist <= right.dist) ? left : right;
        double delta = best.dist;

        ArrayList<Point> strip = new ArrayList<>();
        double delta2 = delta * delta;
        for (Point p : Py) {
            double dx = p.x - xMid;
            if (dx*dx < delta2) strip.add(p);
        }

        int m = strip.size();
        for (int i = 0; i < m; i++) {
            Point pi = strip.get(i);
            for (int j = i + 1; j < m && j - i <= 7; j++) {
                Point pj = strip.get(j);
                double dy = pj.y - pi.y;
                if (dy*dy >= delta2) break;
                double d = dist(pi, pj);
                if (d < best.dist) {
                    best = new Result(d, pi, pj);
                    delta = d;
                    delta2 = d * d;
                }
            }
        }
        return best;
    }

    private static Result brute(Point[] a) {
        double best = Double.POSITIVE_INFINITY;
        Point u = null, v = null;
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                double d = dist(a[i], a[j]);
                if (d < best) { best = d; u = a[i]; v = a[j]; }
            }
        }
        return new Result(best, u, v);
    }

    private static double dist(Point p, Point q) {
        double dx = p.x - q.x, dy = p.y - q.y;
        return Math.hypot(dx, dy);
    }
}
