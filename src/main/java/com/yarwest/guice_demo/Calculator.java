package com.yarwest.guice_demo;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javax.inject.Inject;
import javax.inject.Named;

public class Calculator {

	@Inject @Named("Addition Operation") private IOperation additionOperation;
	@Inject @Named("Subtraction Operation") private IOperation subtractionOperation;
	@Inject @Named("Multiplication Operation") private IOperation multiplicationOperation;
	@Inject @Named("Division Operation") private IOperation divisionOperation;

	@FXML TextField firstNumberField;
	@FXML TextField secondNumberField;
	@FXML TextField outcomeField;

	public void additionEvent() {
		int firstNumberInput = Integer.parseInt(firstNumberField.getText());
		int secondNumberInput = Integer.parseInt(secondNumberField.getText());
		int outcome = addition(firstNumberInput, secondNumberInput);
		outcomeField.setText(Integer.toString(outcome));
	}

	public int addition(int firstNumberInput, int secondNumberInput) {
		return additionOperation.calculate(firstNumberInput, secondNumberInput);
	}

	public void subtractionEvent() {
		int firstNumberInput = Integer.parseInt(firstNumberField.getText());
		int secondNumberInput = Integer.parseInt(secondNumberField.getText());
		int outcome = subtraction(firstNumberInput, secondNumberInput);
		outcomeField.setText(Integer.toString(outcome));
	}

	public int subtraction(int firstNumberInput, int secondNumberInput) {
		return subtractionOperation.calculate(firstNumberInput, secondNumberInput);
	}

	public void multiplicationEvent() {
		int firstNumberInput = Integer.parseInt(firstNumberField.getText());
		int secondNumberInput = Integer.parseInt(secondNumberField.getText());
		int outcome = multiplication(firstNumberInput, secondNumberInput);
		outcomeField.setText(Integer.toString(outcome));
	}

	public int multiplication(int firstNumberInput, int secondNumberInput) {
		return multiplicationOperation.calculate(firstNumberInput, secondNumberInput);
	}

	public void divisionEvent() {
		int firstNumberInput = Integer.parseInt(firstNumberField.getText());
		int secondNumberInput = Integer.parseInt(secondNumberField.getText());
		int outcome = division(firstNumberInput, secondNumberInput);
		outcomeField.setText(Integer.toString(outcome));
	}

	public int division(int firstNumberInput, int secondNumberInput) {
		return divisionOperation.calculate(firstNumberInput, secondNumberInput);
	}

}
