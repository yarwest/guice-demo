package com.yarwest.guice_demo;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		final Injector injector = Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(IOperation.class).annotatedWith(Names.named("Addition Operation")).to(Addition.class);
				bind(IOperation.class).annotatedWith(Names.named("Subtraction Operation")).to(Subtraction.class);
				bind(IOperation.class).annotatedWith(Names.named("Multiplication Operation")).to(Multiplication.class);
				bind(IOperation.class).annotatedWith(Names.named("Division Operation")).to(Division.class);
			}
		});
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Calculator.fxml"));
		loader.setControllerFactory(injector::getInstance);

		primaryStage.setScene(new Scene(loader.load(), 1000, 800));
		primaryStage.show();
	}
}
