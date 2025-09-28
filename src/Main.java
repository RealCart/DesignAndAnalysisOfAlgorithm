import features.MergeSort;
import features.QuickSort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        final int[] array = {1, 11, 234, 312, 423, 578, 61, 765, 832, 945, 10};
//
//        System.out.println("Unsorted array:" + Arrays.toString(array));
//        final int[] sorted = MergeSort.sort(array);
//        System.out.println("Sorted array:" + Arrays.toString(sorted));

        final int[] list = {1, 11, 234, 312, 423, 578, 61, 765, 832, 945, 10};
        System.out.println(Arrays.toString(QuickSort.sort(list)));
    }
}