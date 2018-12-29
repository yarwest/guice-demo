package com.yarwest.guice_demo.calculator;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.yarwest.guice_demo.calculator.operations.*;

public class BootstrapModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(IOperation.class).annotatedWith(Names.named("Addition Operation")).to(Addition.class);
		bind(IOperation.class).annotatedWith(Names.named("Subtraction Operation")).to(Subtraction.class);
		bind(IOperation.class).annotatedWith(Names.named("Multiplication Operation")).to(Multiplication.class);
		bind(IOperation.class).annotatedWith(Names.named("Division Operation")).to(Division.class);
	}
}
