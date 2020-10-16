package com.itlbv;

import com.google.common.base.Joiner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Main {
  public static void main(String[] args) {

    Integer[] testArray = getArrayRandomInt();
    List<Integer> testList = getListSequentialInt();

    DIYArrayList<Integer> diyArrayList = new DIYArrayList<>();

    Collections.addAll(diyArrayList, testArray);
    printList("After adding an array of random values with Collections.addAll():", diyArrayList);

    Collections.sort(diyArrayList, Comparator.comparingInt(o -> o));
    printList("After sorting with Collections.sort():", diyArrayList);

    Collections.copy(diyArrayList, testList);
    printList("After copying a sorted list with Collections.copy():", diyArrayList);
  }

  private static void printList(String message, List<?> list) {
    System.out.println("\n" + message);
    System.out.println("{" + Joiner.on(", ").join(list) + "}");
  }

  private static Integer[] getArrayRandomInt() {
    Integer[] arr = new Integer[25];
    Random r = new Random();
    for (int i = 0; i < arr.length; i++) {
      arr[i] = r.nextInt();
    }
    return arr;
  }

  private static List<Integer> getListSequentialInt() {
    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < 25; i++) {
      list.add(i);
    }
    return list;
  }
}
