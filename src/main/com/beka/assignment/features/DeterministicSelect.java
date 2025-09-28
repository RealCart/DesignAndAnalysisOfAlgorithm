package main.com.beka.assignment.features;

public class DeterministicSelect {
    public static int selectKth(int[] a, int k) {
        if (k < 0 || k >= a.length) throw new IllegalArgumentException("k out of range");
        return selectRange(a, 0, a.length, k);
    }

    private static int selectRange(int[] a, int l, int r, int k) {
        while (true) {
            int n = r - l;
            if (n <= 50) {
                insertionSort(a, l, r);
                return a[l + k];
            }

            int groups = (n + 4) / 5;
            int[] medians = new int[groups];
            for (int i = 0, gi = 0; i < n; i += 5, gi++) {
                int start = l + i;
                int end = Math.min(start + 5, r);
                insertionSort(a, start, end);
                medians[gi] = a[start + (end - start - 1) / 2];
            }

            int pivot = (groups == 1) ? medians[0] : selectRangeCopy(medians, 0, groups, groups / 2);

            int lt = l, i = l, gt = r;
            while (i < gt) {
                if (a[i] < pivot) { swap(a, lt, i); lt++; i++; }
                else if (a[i] > pivot) { gt--; swap(a, i, gt); }
                else { i++; }
            }

            if (l + k < lt) {
                r = lt;
            } else if (l + k >= gt) {
                k -= (gt - l);
                l = gt;
            } else {
                return pivot;
            }
        }
    }

    private static int selectRangeCopy(int[] a, int l, int r, int k) {
        return selectRange(a.clone(), l, r, k);
    }

    private static void insertionSort(int[] a, int l, int r) {
        for (int i = l + 1; i < r; i++) {
            int x = a[i], j = i - 1;
            while (j >= l && a[j] > x) { a[j + 1] = a[j]; j--; }
            a[j + 1] = x;
        }
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }
}
