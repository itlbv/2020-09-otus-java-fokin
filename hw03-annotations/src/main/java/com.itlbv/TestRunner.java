package com.itlbv;

import com.itlbv.annotations.After;
import com.itlbv.annotations.Before;
import com.itlbv.annotations.Test;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class TestRunner {
  private static final String TEST_PASSED_STRING = "PASSED";
  private static final String TEST_FAILED_STRING = "FAILED";

  private static final Map<String, String> testResults = new TreeMap<>();
  private  static Class<?> testClass;
  private static Method beforeMethod;
  private static Method afterMethod;

  public static void run(String className) {
    testClass = getClassObject(className);

    beforeMethod = getSingleMethod(Before.class);
    afterMethod = getSingleMethod(After.class);
    List<Method> testMethods = getManyMethods(Test.class);

    testMethods.forEach(TestRunner::runTest);

    printReport();
  }

  private static Class<?> getClassObject(String className) {
    Class<?> clazz = null;
    try {
      clazz = Class.forName(className);
    } catch (ClassNotFoundException e) {
      System.out.printf("FATAL: Class [%s] was not found.%n", className);
      e.printStackTrace();
    }
    return clazz;
  }

  private static Method getSingleMethod(Class<? extends Annotation> annotationClass) {
    return Arrays.stream(testClass.getMethods())
        .filter(m -> m.isAnnotationPresent(annotationClass))
        .findFirst()
        .orElse(null);
  }

  private static List<Method> getManyMethods(Class<? extends Annotation> annotationClass) {
    return Arrays.stream(testClass.getMethods())
        .filter(m -> m.isAnnotationPresent(annotationClass))
        .collect(Collectors.toList());
  }

  private static void runTest(Method method) {
    Object testObject = getTestObject();

    if (beforeMethod != null) invokeMethod(testObject, beforeMethod);

    invokeTestMethod(testObject, method);

    if (afterMethod != null) invokeMethod(testObject, afterMethod);
  }

  private static Object getTestObject() {
    Object testObject = null;
    try {
      testObject = testClass.getConstructor().newInstance();
    } catch (NoSuchMethodException
        | IllegalAccessException
        | InvocationTargetException
        | InstantiationException e) {
      System.out.printf(
          "FATAL: Error instantiating class [%s] using default constructor.%n",
          testClass.getSimpleName());
      e.printStackTrace();
    }
    return testObject;
  }

  private static void invokeMethod(Object testObject, Method method) {
    try {
      method.invoke(testObject);
    } catch (IllegalAccessException | InvocationTargetException e) {
      System.out.printf("FATAL: Error running method [%s]%n", method.getName());
      e.printStackTrace();
    }
  }

  private static void invokeTestMethod(Object testObject, Method method) {
    boolean result = false;
    try {
      result = (boolean) method.invoke(testObject);
    } catch (ClassCastException | NullPointerException e) {
      System.out.printf(
          "FATAL: Error casting result of [%s]. Please check if the method returns boolean.%n",
          method.getName());
      e.printStackTrace();
    } catch (IllegalAccessException | InvocationTargetException e) {
      System.out.printf("FATAL: Error running test method [%s]%n", method.getName());
      e.printStackTrace();
    }
    testResults.put(method.getName(), result ? TEST_PASSED_STRING : TEST_FAILED_STRING);
  }

  private static void printReport() {

    String template = """
                
        =========== TEST REPORT ===========
        Test class: %s
        Number of test methods found: %s
        -----------------------------------
        """
        + "%s: %s \n".repeat(testResults.size()) +
        """
        ----------------------------------
        Successful tests: %s
        Failed tests: %s
        
        """
        ;

    List<String> reportValues = new ArrayList<>();
    reportValues.add(testClass.getCanonicalName());
    reportValues.add(Integer.toString(testResults.size()));

    testResults.forEach((key, value) -> {
      reportValues.add(key);
      reportValues.add(value);
    });

    reportValues.add(getCountOfTestOutcome(TEST_PASSED_STRING));
    reportValues.add(getCountOfTestOutcome(TEST_FAILED_STRING));

    String report = String.format(template, reportValues.toArray());
    System.out.println(report);
  }

  private static String getCountOfTestOutcome(String outcome){
    return Long.toString(
        testResults
            .entrySet()
            .stream()
            .filter(e -> e.getValue().equals(outcome))
            .count());
  }
}
