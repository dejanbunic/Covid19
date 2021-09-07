package net.etfbl.pj2.Controller;

import java.io.IOException;
import java.util.logging.Level;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import net.etfbl.pj2.Model.Grad;
import net.etfbl.pj2.Utility.FileLogger;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class PocetnaController {

	@FXML
	private Button startDugme;

	@FXML
	private Button OtkaziDugme;

	@FXML
	private TextField brojAmbulantnihVozila;

	@FXML
	private TextField brojKontrolnihPunktova;

	@FXML
	private TextField brojDjece;

	@FXML
	private TextField brojStarih;

	@FXML
	private TextField brojKuca;

	@FXML
	private TextField brojOdraslih;

	@FXML
	void popuni(ActionEvent event) {
		try {
			if (Integer.parseInt(brojOdraslih.getText()) <= 0 || Integer.parseInt(brojDjece.getText()) <= 0
					|| Integer.parseInt(brojStarih.getText()) <= 0
					|| Integer.parseInt(brojAmbulantnihVozila.getText()) <= 0
					|| Integer.parseInt(brojKuca.getText()) <= 0
					|| Integer.parseInt(brojKontrolnihPunktova.getText()) <= 0)
				throw new NumberFormatException();
		} catch (NumberFormatException e) {

			FileLogger.log(Level.WARNING, null, new Throwable("Unosi moraju biti pozitivne cijele vrijednosti"));
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Informacije");
			alert.setHeaderText("Sva polja na formi moraju biti popunjena");
			alert.setContentText("Sva polja na formi moraju biti popunjena poziti");
			alert.showAndWait();
			System.exit(0);
		}
			Grad.setBrojOdraslih(Integer.parseInt(brojOdraslih.getText()));
			Grad.setBrojDjece(Integer.parseInt(brojDjece.getText()));
			Grad.setBrojStarih(Integer.parseInt(brojStarih.getText()));
			Grad.setBrojAmbulantnihVozila(Integer.parseInt(brojAmbulantnihVozila.getText()));
			Grad.setBrojKuca(Integer.parseInt(brojKuca.getText()));
			Grad.setBrojKontrolnihPunktova(Integer.parseInt(brojKontrolnihPunktova.getText()));
			Grad.setBrojStanovnika(Grad.getBrojDjece() + Grad.getBrojOdraslih() + Grad.getBrojStarih());
			Grad.inicijalizujMapu();
			Grad.generisiKuce();
			Grad.generisiStanovnike();

			Grad.rasporediStanovnikePoKucama();
			Grad.rasporediKuceNaMapu();
			Grad.rasporediKontrolnePunktoveNaMapu();
			Grad.postaviAmbulante();
			try {
			Grad.postaviStanovnike();
			}catch(NumberFormatException e) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Informacije");
				alert.setHeaderText("Povecaj broj kuca ili smanji broj stanovnika");
				alert.setContentText("Povecaj broj kuca ili smanji broj stanovnika");
				alert.showAndWait();
				System.exit(0);
			}
			Grad.kreirajAmbulantanaVozila();

			Node node = (Node) event.getSource();
			Stage stage1 = (Stage) node.getScene().getWindow();
			stage1.close();

			Parent root;
			try {
				root = FXMLLoader.load(getClass().getResource("../View/Glavna.fxml"));
				Scene scene = new Scene(root);
				stage1.setScene(scene);
				stage1.setScene(scene);
				stage1.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		//} /*catch (NumberFormatException e) {

		/*	FileLogger.log(Level.WARNING, null, new Throwable("Unosi moraju biti pozitivne cijele vrijednosti"));
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Informacije");
			alert.setHeaderText("Sva polja na formi moraju biti popunjena");
			alert.setContentText("Sva polja na formi moraju biti popunjena poziti");
			alert.showAndWait();
		}*/
	}

	@FXML
	void otkazi(ActionEvent event) {
		((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
	}
}
