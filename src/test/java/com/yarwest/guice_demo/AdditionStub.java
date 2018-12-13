package com.yarwest.guice_demo;

public class AdditionStub implements IOperation {
	@Override
	public int calculate(int a, int b) {
		return 2;
	}
}
