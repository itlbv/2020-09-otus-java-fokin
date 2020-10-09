package com.itlbv;

import com.google.common.base.Joiner;

public class HelloOtus {
	public static void main(String[] args) {
		String[] values = { "one", "two", "three" };
		String result= Joiner.on(", ").join(values);
		System.out.println(result);
	}
}
