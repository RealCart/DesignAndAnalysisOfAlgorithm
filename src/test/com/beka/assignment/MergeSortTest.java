package test.com.beka.assignment;

import main.com.beka.assignment.features.Metrics;

public class MergeSortTest {
    public static void sort(int[] a, Metrics m) {
        int[] tmp = new int[a.length];
        m.allocations += (long)a.length * Integer.BYTES;
        sortRec(a, 0, a.length, tmp, m);
    }


    private static void sortRec(int[] a, int l, int r, int[] tmp, Metrics m) {
        int n = r - l;
        if (n <= 1) return;
        m.enter();
        int mid = l + (n >> 1);
        sortRec(a, l, mid, tmp, m);
        sortRec(a, mid, r, tmp, m);
        int i=l, j=mid, k=l;
        while (i<mid && j<r) {
            m.comparisons++;
            if (a[i] <= a[j]) tmp[k++]=a[i++]; else tmp[k++]=a[j++];
            m.moves++;
        }
        while (i<mid) { tmp[k++]=a[i++]; m.moves++; }
        while (j<r) { tmp[k++]=a[j++]; m.moves++; }
        for (k=l; k<r; k++) { a[k]=tmp[k]; m.moves++; }
        m.exit();
    }
}
