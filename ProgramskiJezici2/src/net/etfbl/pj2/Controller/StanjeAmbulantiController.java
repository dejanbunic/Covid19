package net.etfbl.pj2.Controller;

import java.util.Random;
import java.util.logging.Level;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import net.etfbl.pj2.Model.Ambulanta;
import net.etfbl.pj2.Model.Grad;
import net.etfbl.pj2.Utility.FileLogger;
public class StanjeAmbulantiController {

	@FXML
	private GridPane gridPane;

	@FXML
	private GridPane gridContent;

	boolean tredSePokrece = true;

	Thread thread;
	private final int MAX_BROJ_AMBULANTI = 8;

	public void initialize() {

		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				Runnable updater = new Runnable() {

					@Override
					public void run() {
						prikaziStanjeAmbulanti();
					}
				};

				while (true) {
					//System.out.println("thread"+thread.getId()+"running");

					Platform.runLater(updater);
					try {
						Thread.sleep(1000);
						if(Thread.currentThread().isInterrupted())
							throw new InterruptedException();
					} catch (InterruptedException ex) {
						 // System.out.println("Tred je prekinut");
						  FileLogger.log(Level.SEVERE, null, new Throwable("Tred statistika ambulanti je prekinut"));
						  break;
					}
				}
			}

		});
		//thread.setDaemon(true);
		thread.start();
	}

	private void prikaziStanjeAmbulanti() {
		// gridContent=null;

		gridContent.getColumnConstraints().clear();
		gridContent.getRowConstraints().clear();
		// gridPane.getChildren().clear();
		// gridContent.getChildren().retainAll(gridContent.getChildren().get(0));
		gridContent.getChildren().clear();
		ColumnConstraints column1 = new ColumnConstraints();
		ColumnConstraints column2 = new ColumnConstraints();
		ColumnConstraints column3 = new ColumnConstraints();
		column1.setHalignment(HPos.CENTER);
		column2.setHalignment(HPos.CENTER);
		column3.setHalignment(HPos.CENTER);
		if (Grad.getAmbulante().size() < 5) {
			column1.setPercentWidth(10);
			column2.setPercentWidth(80);
			column3.setPercentWidth(10);
			// System.out.println("fdsa"+column1.getPercentWidth());
			// System.out.println("fdsa"+column2.getPercentWidth());
			// System.out.println("fdsa"+column3.getPercentWidth());

			gridContent.getColumnConstraints().clear();
			gridContent.getColumnConstraints().addAll(column1, column2, column3);
		} else {
			ColumnConstraints column4 = new ColumnConstraints();
			column1.setPercentWidth(10);
			column2.setPercentWidth(40);
			column3.setPercentWidth(40);
			column4.setPercentWidth(10);
			column4.setHalignment(HPos.CENTER);
			gridContent.getColumnConstraints().clear();
			gridContent.getColumnConstraints().addAll(column1, column2, column3, column4);
		}

		RowConstraints row1 = new RowConstraints();
		RowConstraints row2 = new RowConstraints();
		RowConstraints row3 = new RowConstraints();
		RowConstraints row4 = new RowConstraints();
		RowConstraints row5 = new RowConstraints();
		row1.setValignment(VPos.CENTER);
		row2.setValignment(VPos.CENTER);
		row3.setValignment(VPos.CENTER);
		row4.setValignment(VPos.CENTER);

		row1.setPercentHeight(20);
		row2.setPercentHeight(20);
		row3.setPercentHeight(20);
		row4.setPercentHeight(20);
		row5.setPercentHeight(20);

		// String labela ="Broj zarazenih:
		// "+Grad.getAmbulante().get(0).getBrojZarazenih()+"\n Kapacitet:
		// "+Grad.getAmbulante().get(0).getKapacitet()+"\n";
		// Label label1 = new Label(labela);
		// labela ="Broj zarazenih: "+Grad.getAmbulante().get(1).getBrojZarazenih()+"\n
		// Kapacitet: "+Grad.getAmbulante().get(1).getKapacitet()+"\n";
		// Label label2 = new Label(labela);
		// labela ="Broj zarazenih: "+Grad.getAmbulante().get(2).getBrojZarazenih()+"\n
		// Kapacitet: "+Grad.getAmbulante().get(2).getKapacitet()+"\n";
		// Label label3 = new Label(labela);
		// labela ="Broj zarazenih: "+Grad.getAmbulante().get(3).getBrojZarazenih()+"\n
		// Kapacitet: "+Grad.getAmbulante().get(3).getKapacitet()+"\n";
		// Label label4 = new Label(labela);

		// gridContent.setHalignment(label1, HPos.CENTER);
		// gridContent.setHalignment(label2, HPos.CENTER);
		// gridContent.setHalignment(label3, HPos.CENTER);
		// gridContent.setHalignment(label4, HPos.CENTER);
		gridContent.getRowConstraints().clear();
		gridContent.getRowConstraints().addAll(row1, row2, row3, row4, row5);

		for (int i = 0; i < 4; i++) {
			Ambulanta a = new Ambulanta();
			a = Grad.getAmbulante().get(i);
			String labela1 = "id: " + a.getId() + "\nx= " + a.getX() + " y= " + a.getY() + "\nBroj zarazenih: "
					+ a.getBrojZarazenih() + "\nKapacitet: " + a.getKapacitet() + "\n";
			Label novaLabela = new Label(labela1);
			gridContent.add(novaLabela, 1, i);
		}
		// gridContent.add(label1, 1, 0);
		// gridContent.add(label2, 1, 1);
		// gridContent.add(label3, 1, 2);
		// gridContent.add(label4, 1, 3);
		/*
		if (Grad.getAmbulante().size() >= 5) {
			Label label5 = new Label("Broj zarazenih: " + Grad.getAmbulante().get(4).getBrojZarazenih()
					+ "\n Kapacitet: " + Grad.getAmbulante().get(4).getKapacitet() + "\n");
			gridContent.add(label5, 2, 0);

		}
		if (Grad.getAmbulante().size() >= 6) {
			Label label6 = new Label("Broj zarazenih: " + Grad.getAmbulante().get(5).getBrojZarazenih()
					+ "\n Kapacitet: " + Grad.getAmbulante().get(5).getKapacitet() + "\n");
			gridContent.add(label6, 2, 1);
		}
		if (Grad.getAmbulante().size() >= 7) {
			Label label7 = new Label("Broj zarazenih: " + Grad.getAmbulante().get(6).getBrojZarazenih()
					+ "\n Kapacitet: " + Grad.getAmbulante().get(6).getKapacitet() + "\n");
			gridContent.add(label7, 2, 2);
		}
		if (Grad.getAmbulante().size() >= 8) {
			Label label8 = new Label("Broj zarazenih: " + Grad.getAmbulante().get(7).getBrojZarazenih()
					+ "\n Kapacitet: " + Grad.getAmbulante().get(7).getKapacitet() + "\n");
			gridContent.add(label8, 2, 3);
		}*/
		
		  if(Grad.getAmbulante().size()>=5) { 
			  for(int i=0;i<(Grad.getAmbulante().size()-4);i++) 
			  { 	Ambulanta a = new Ambulanta(); 
			  		a= Grad.getAmbulante().get(i+4);
			  		String labela1 ="id: "+a.getId()+"\nx= "+a.getX()+" y= "+a.getY()+"\nBroj zarazenih: "+a.getBrojZarazenih()+"\nKapacitet: "+a.getKapacitet()+"\n"; 
			  		Label novaLabela =new Label(labela1); gridContent.add(novaLabela, 2, i);
			  } 
		  }		 
	}

	@FXML
	void dodajAmbulantu(ActionEvent event) {
		int i = 0;
		Ambulanta ambulanta = null;
		while (i == 0) {
			if (Grad.getAmbulante().size() < MAX_BROJ_AMBULANTI) {
				Random random = new Random();
				int x = random.nextInt(Grad.getDimenzija());
				int y = random.nextInt(Grad.getDimenzija()); // mora u granicama okvira biti ambulanta 
				
				int temp = random.nextInt(4);
				if (temp==0)
					x=0;
				if(temp==1)
					x=Grad.getDimenzija()-1;
				if(temp==2)
					y=0;
				if(temp==3)
					y=Grad.getDimenzija()-1;
				
				if (Grad.getMapa()[x][y].getElementi().isEmpty()) {
					ambulanta = new Ambulanta();

					Grad.getMapa()[x][y].getElementi().add(ambulanta);
					Grad.getAmbulante().add(ambulanta);
					ambulanta.setId(Grad.getAmbulante().size()); 
					i = 1;
					ambulanta.setX(x);
					ambulanta.setY(y);
					
				}
			} else {
				i = 1;
			}

		}
		//String tempString = Grad.getLogStrings()+"Nova ambulanta je dodana na poziciju x= "+ambulanta.getX()+" y="+ambulanta.getY();
		if(ambulanta!=null)
		Grad.getLogStrings().add("Nova ambulanta je dodana na poziciju x= "+ambulanta.getX()+" y="+ambulanta.getY());
	}

	@FXML
	void otkazi(ActionEvent event) {
		// tredSePokrece = false;
		if(thread!=null)
		 thread.interrupt();
		// thread.
		// Window window = gridPane.getScene().getWindow();
		((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
	}

}
