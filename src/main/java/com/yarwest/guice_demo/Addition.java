package com.yarwest.guice_demo;

public class Addition implements IOperation {

	@Override
	public int calculate(int a, int b) {
		return a + b;
	}
}
