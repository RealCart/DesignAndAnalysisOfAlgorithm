package test.com.beka.assignment;

import main.com.beka.assignment.features.ClosestPair;
import main.com.beka.assignment.features.DeterministicSelect;
import main.com.beka.assignment.features.Metrics;
import main.com.beka.assignment.features.QuickSort;

import java.util.*;
import java.io.*;


public class Bench {
    static final Random RNG = new Random(42);


    static int[] randArray(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = RNG.nextInt();
        return a;
    }


    static int[] nearSorted(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = i;
        for (int i = 0; i < n/100+1; i++) {
            int x = RNG.nextInt(n), y = RNG.nextInt(n);
            int t=a[x]; a[x]=a[y]; a[y]=t;
        }
        return a;
    }


    public static void main(String[] args) throws Exception {
        try (PrintWriter out = new PrintWriter(new FileWriter("bench.csv"))) {
            out.println("algorithm,n,time_ns,maxDepth,comparisons,moves,allocations");
            int[] sizes = {1_000, 2_000, 5_000, 10_000, 20_000, 50_000, 100_000};


            for (int n : sizes) {
                int[] base = randArray(n);


// QuickSort (in-place, randomized)
                {
                    int[] a = base.clone();
                    Metrics m = new Metrics();
                    long t0 = System.nanoTime();
                    QuickSort.sort(a, 3);
                    long t1 = System.nanoTime();
                    out.printf(Locale.US, "QS,%d,%d,%d,%d,%d,%d%n", n, (t1-t0), m.maxDepth, m.comparisons, m.moves, m.allocations);
                }


// MergeSort (buffered)
                {
                    int[] a = base.clone();
                    Metrics m = new Metrics();
                    long t0 = System.nanoTime();
                    MergeSortTest.sort(a, m);
                    long t1 = System.nanoTime();
                    out.printf(Locale.US, "MS,%d,%d,%d,%d,%d,%d%n", n, (t1-t0), m.maxDepth, m.comparisons, m.moves, m.allocations);
                }


// Deterministic Select (median-of-medians)
                {
                    int[] a = base.clone();
                    int k = n/2;
                    Metrics m = new Metrics();
                    long t0 = System.nanoTime();
                    int v = DeterministicSelect.selectKth(a, k); // unchanged; optional: hook metrics inside
                    long t1 = System.nanoTime();
                    out.printf(Locale.US, "SEL,%d,%d,%d,%d,%d,%d%n", n, (t1-t0), m.maxDepth, m.comparisons, m.moves, m.allocations);
                    if (v != Arrays.stream(base).sorted().toArray()[k]) throw new AssertionError();
                }


// Closest Pair (2D)
                {
                    ClosestPair.Point[] pts = new ClosestPair.Point[n];
                    for (int i = 0; i < n; i++) pts[i] = new ClosestPair.Point(RNG.nextDouble(), RNG.nextDouble());
                    long t0 = System.nanoTime();
                    ClosestPair.Result r = ClosestPair.closest(pts);
                    long t1 = System.nanoTime();
                    out.printf(Locale.US, "CP,%d,%d,%d,%d,%d,%d%n", n, (t1-t0), 0, 0, 0, 0);
                    if (r == null) throw new AssertionError();
                }
            }
        }
    }
}
