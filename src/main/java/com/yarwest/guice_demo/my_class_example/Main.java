package com.yarwest.guice_demo.my_class_example;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {
	public static void main(String[] arghs) {
		Injector injector = Guice.createInjector();
		MyClassFactory factory = injector.getInstance(MyClassFactory.class);
		factory.createMyClass(3);
		factory.printClasses();
	}
}