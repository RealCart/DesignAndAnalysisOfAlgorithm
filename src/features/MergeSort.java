package features;

import java.util.Arrays;

public class MergeSort {
    public static int[] sort(int[] arr) {
        if (arr == null || arr.length < 2) return arr;

        int mid = arr.length / 2;
        int[] left  = Arrays.copyOfRange(arr, 0, mid);
        int[] right = Arrays.copyOfRange(arr, mid, arr.length);

        return merge(sort(left), sort(right));
    }

    private static int[] merge(int[] left, int[] right) {
        int[] merged = new int[left.length + right.length];
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) merged[k++] = left[i++];
            else                      merged[k++] = right[j++];
        }
        while (i < left.length)  merged[k++] = left[i++];
        while (j < right.length) merged[k++] = right[j++];

        return merged;
    }
}

