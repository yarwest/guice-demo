package com.yarwest.guice_demo.calculator;

import com.google.inject.Guice;
import com.google.inject.Injector;
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

		final Injector injector = Guice.createInjector(new BootstrapModule());
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/calculator.fxml"));
		loader.setControllerFactory(injector::getInstance);

		primaryStage.setScene(new Scene(loader.load(), 1000, 800));
		primaryStage.show();
	}
}
