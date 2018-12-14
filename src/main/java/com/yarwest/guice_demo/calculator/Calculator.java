package com.yarwest.guice_demo.calculator;

import com.yarwest.guice_demo.calculator.operations.IOperation;

import javax.inject.Inject;
import javax.inject.Named;

public class Calculator {

	@Inject @Named("Addition Operation") private IOperation additionOperation;
	@Inject @Named("Subtraction Operation") private IOperation subtractionOperation;
	@Inject @Named("Multiplication Operation") private IOperation multiplicationOperation;
	@Inject @Named("Division Operation") private IOperation divisionOperation;

	public int addition(int firstNumberInput, int secondNumberInput) {
		return additionOperation.calculate(firstNumberInput, secondNumberInput);
	}

	public int subtraction(int firstNumberInput, int secondNumberInput) {
		return subtractionOperation.calculate(firstNumberInput, secondNumberInput);
	}

	public int multiplication(int firstNumberInput, int secondNumberInput) {
		return multiplicationOperation.calculate(firstNumberInput, secondNumberInput);
	}

	public int division(int firstNumberInput, int secondNumberInput) {
		return divisionOperation.calculate(firstNumberInput, secondNumberInput);
	}

}
