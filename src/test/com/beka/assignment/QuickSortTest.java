package test.com.beka.assignment;

import main.com.beka.assignment.features.Metrics;

import java.util.Random;

public class QuickSortTest {
    public static void sort(int[] a, Metrics m, Random rng) {
        if (a == null || a.length < 2) return;
        qs(a, 0, a.length, m, rng);
    }


    private static void qs(int[] a, int l, int r, Metrics m, Random rng) {
        while (r - l > 1) {
            m.enter();
            int n = r - l;
            int p = l + rng.nextInt(n);
            swap(a, l, p); m.moves += 3;
            int pivot = a[l];
            int i = l + 1, lt = l + 1, gt = r - 1;
            while (i <= gt) {
                m.comparisons++;
                if (a[i] < pivot) { swap(a, i++, lt++); m.moves += 3; }
                else if (a[i] > pivot) { swap(a, i, gt--); m.moves += 3; }
                else { i++; }
            }
            swap(a, l, --lt); m.moves += 3;
            int leftL = l, leftR = lt;
            int rightL = gt + 1, rightR = r;
            m.exit();
            if (leftR - leftL < rightR - rightL) { qs(a, leftL, leftR, m, rng); l = rightL; r = rightR; }
            else { qs(a, rightL, rightR, m, rng); l = leftL; r = leftR; }
        }
    }


    private static void swap(int[] a, int i, int j) { int t=a[i]; a[i]=a[j]; a[j]=t; }
}
