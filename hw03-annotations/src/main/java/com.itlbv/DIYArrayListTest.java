package com.itlbv;

import com.itlbv.annotations.After;
import com.itlbv.annotations.Before;
import com.itlbv.annotations.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DIYArrayListTest {

  private static final int TEST_DATA_LIST_SIZE = 25;
  private DIYArrayList<Integer> diyArrayList;
  private List<Integer> testDataList;

  private static List<Integer> getListOfInteger() {
    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < TEST_DATA_LIST_SIZE; i++) {
      list.add(i);
    }
    return list;
  }

  @Before
  public void setup() {
    diyArrayList = new DIYArrayList<>();
    testDataList = getListOfInteger();
  }

  @After
  public void tearDown() {}

  @Test
  public boolean shouldAddAllWithCollections() {
    Integer[] testArray = new Integer[testDataList.size()];
    testDataList.toArray(testArray);
    Collections.addAll(diyArrayList, testArray);
    return diyArrayList.size() == testDataList.size();
  }

  @Test
  public boolean shouldCopyWithCollections() {
    diyArrayList = new DIYArrayList<>(testDataList.size());
    Collections.copy(diyArrayList, testDataList);
    return diyArrayList.size() == testDataList.size();
  }

  @Test
  public boolean shouldFail() {
    Collections.copy(diyArrayList, testDataList);
    return diyArrayList.size() == testDataList.size();
  }

  @Test
  public int shouldThrowClassCastException() {
    return 0; // returns int therefore should fail miserably and show an error message
  }
}
