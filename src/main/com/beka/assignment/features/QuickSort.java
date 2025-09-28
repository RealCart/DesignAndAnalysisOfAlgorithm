package main.com.beka.assignment.features;

import java.util.ArrayList;
import java.util.List;

public class QuickSort {
    public static int[] sort(int[] arr, int pIndex) {
        if (arr == null || arr.length < 2) return arr;

        List<Integer> left = new ArrayList<Integer>();
        List<Integer> right = new ArrayList<Integer>();

        int pivot = arr[pIndex];

        for (int num : arr) {
            if (num == pivot) continue;
            if (num < pivot) left.add(num);
            else right.add(num);
        }

        int[] sortedLeft = sort(left.stream().mapToInt(i -> i).toArray(), left.size() / 2);
        int[] sortedRight = sort(right.stream().mapToInt(i -> i).toArray(), right.size() / 2);

        int index = 0;
        int[] result = new int[arr.length];

        for (int num : sortedLeft) result[index++] = num;
        result[index++] = pivot;
        for (int num : sortedRight) result[index++] = num;

        return result;
    }
}