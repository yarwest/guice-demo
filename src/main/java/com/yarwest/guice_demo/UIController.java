package com.yarwest.guice_demo;

import com.yarwest.guice_demo.calculator.Calculator;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javax.inject.Inject;

public class UIController {

	@Inject
	Calculator calculator;

	@FXML TextField firstNumberField;
	@FXML TextField secondNumberField;
	@FXML TextField outcomeField;

	public void additionEvent() {
		int firstNumberInput = Integer.parseInt(firstNumberField.getText());
		int secondNumberInput = Integer.parseInt(secondNumberField.getText());
		int outcome = calculator.addition(firstNumberInput, secondNumberInput);
		outcomeField.setText(Integer.toString(outcome));
	}

	public void subtractionEvent() {
		int firstNumberInput = Integer.parseInt(firstNumberField.getText());
		int secondNumberInput = Integer.parseInt(secondNumberField.getText());
		int outcome = calculator.subtraction(firstNumberInput, secondNumberInput);
		outcomeField.setText(Integer.toString(outcome));
	}

	public void multiplicationEvent() {
		int firstNumberInput = Integer.parseInt(firstNumberField.getText());
		int secondNumberInput = Integer.parseInt(secondNumberField.getText());
		int outcome = calculator.multiplication(firstNumberInput, secondNumberInput);
		outcomeField.setText(Integer.toString(outcome));
	}

	public void divisionEvent() {
		int firstNumberInput = Integer.parseInt(firstNumberField.getText());
		int secondNumberInput = Integer.parseInt(secondNumberField.getText());
		int outcome = calculator.division(firstNumberInput, secondNumberInput);
		outcomeField.setText(Integer.toString(outcome));
	}
}
