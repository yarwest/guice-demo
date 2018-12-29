package com.yarwest.guice_demo.calculator;

import com.yarwest.guice_demo.calculator.operations.IOperation;

public class OtherStub implements IOperation {
	@Override
	public int calculate(int a, int b) {
		return 0;
	}
}
