package com.yarwest.guice_demo.calculator;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import com.yarwest.guice_demo.calculator.operations.IOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for demo app.
 */
public class CalculatorTest {
	private Calculator calc;

	@BeforeEach
	public void setup() {
		Injector injector = Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(IOperation.class).annotatedWith(Names.named("Addition Operation")).to(AdditionStub.class);
				bind(IOperation.class).annotatedWith(Names.named("Subtraction Operation")).to(OtherStub.class);
				bind(IOperation.class).annotatedWith(Names.named("Multiplication Operation")).to(OtherStub.class);
				bind(IOperation.class).annotatedWith(Names.named("Division Operation")).to(OtherStub.class);
			}
		});
		calc = injector.getInstance(Calculator.class);
	}

	@Test
	public void weirdCalculatorTest() {
		assertEquals(calc.addition(3, 9), 2);
	}
}
