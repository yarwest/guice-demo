package com.yarwest.guice_demo;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.ArrayList;
import java.util.List;

public class Controller {
	private final Provider<POJO> pojoProvider;

	private List<POJO> pojos = new ArrayList<>();

	@Inject
	public Controller(Provider<POJO> pojoProvider) {
		this.pojoProvider = pojoProvider;
	}

	public void createPOJOs(int amount) {
		for(int i = 0; i < amount; i++) {
			pojos.add(pojoProvider.get());
		}
	}

	public void printPOJOs() {
		for(POJO pojo : pojos) {
			System.out.println("POJO");
		}
	}
}
