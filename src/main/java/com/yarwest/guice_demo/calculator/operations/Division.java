package com.yarwest.guice_demo.calculator.operations;

public class Division implements IOperation {
	@Override
	public int calculate(int a, int b) {
		return a / b;
	}
}
