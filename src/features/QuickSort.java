package features;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuickSort {
    public static int[] sort(int[] arr) {
        if (arr == null || arr.length == 0) return arr;

        List<Integer> left = new ArrayList<Integer>();
        List<Integer> right = new ArrayList<Integer>();

        int pivotIndex = arr.length / 2;
        int pivot = arr[pivotIndex];

        for (int num : arr) {
            if (num == pivot) continue;
            if (num < pivot) {
                left.add(num);
            } else {
                right.add(num);
            }
        }

        int[] sortedLeft = sort(left.stream().mapToInt(i -> i).toArray());
        int[] sortedRight = sort(right.stream().mapToInt(i -> i).toArray());

        int index = 0;
        int[] result = new int[arr.length];

        for (int num : sortedLeft) result[index++] = num;
        result[index++] = pivot;
        for (int num : sortedRight) result[index++] = num;

        return result;
    }
}