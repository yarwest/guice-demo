package com.yarwest.guice_demo.my_class_example;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.ArrayList;
import java.util.List;

public class MyClassFactory {
	private final Provider<MyClass> myClassProvider;

	private List<MyClass> classes = new ArrayList<>();

	@Inject
	public MyClassFactory(Provider<MyClass> myClassProvider) {
		this.myClassProvider = myClassProvider;
	}

	public void createMyClass(int amount) {
		for(int i = 0; i < amount; i++) {
			classes.add(myClassProvider.get());
		}
	}

	public void printClasses() {
		for(MyClass myClass : classes) {
			System.out.println("Class");
		}
	}
}
