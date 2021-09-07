package net.etfbl.pj2.Application;

import java.util.logging.Level;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.etfbl.pj2.Utility.FileLogger;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../View/Pocetna.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			// e.printStackTrace();
			FileLogger.log(Level.WARNING, null, new Throwable("Lokacija do Pocetna.fxml nije pravilno ucitana"));
		}

	}

	public static void main(String[] args) {
		launch(args);
	}

}
