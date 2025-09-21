import features.MergeSort;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        final int[] array = {1, 11, 234, 312, 423, 578, 61, 765, 832, 945, 10};

        System.out.println("Unsorted array:" + Arrays.toString(array));
        final int[] sorted = MergeSort.sort(array);
        System.out.println("Sorted array:" + Arrays.toString(sorted));
    }
}