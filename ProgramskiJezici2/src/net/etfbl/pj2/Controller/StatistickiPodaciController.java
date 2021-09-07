package net.etfbl.pj2.Controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import net.etfbl.pj2.Model.Dijete;
import net.etfbl.pj2.Model.Grad;
import net.etfbl.pj2.Model.OdraslaOsoba;
import net.etfbl.pj2.Model.Stanovnik;
import net.etfbl.pj2.Model.StarijaOsoba;
import net.etfbl.pj2.Utility.FileLogger;
import net.etfbl.pj2.Utility.FileWatcher;

public class StatistickiPodaciController {

	@FXML
	private Button izvjestajButton;

	@FXML
	private Button otkaziButton;

	@FXML
	private PieChart grafikon;

	Thread initializeThread;
	boolean initializeThreadIstinitost = true;
	Thread poPoluThread;
	boolean poPoluThreadIstinitost = true;
	Thread poVrstiThread;
	boolean poVrstiThreadIstinitost = true;
	int brojIzlijecenih = 0;
	int brojZarazenih = 0;
	int brojSlucajevaMuskaraca = 0;
	int brojSlucajevaZena = 0;
	int brojSlucajevaDjece = 0;
	int brojSlucajevaOdraslih = 0;
	int brojSlucajevaStarih = 0;
	public void initialize() {
		
		initializeThread = new Thread(new Runnable() {
		
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Runnable updater = new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(brojIzlijecenih != FileWatcher.getBrojIzlijecenih() || brojZarazenih != FileWatcher.getBrojtrenutnoZarazenih()) {
							brojIzlijecenih = FileWatcher.getBrojIzlijecenih();
							brojZarazenih = FileWatcher.getBrojtrenutnoZarazenih();
							ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
								new PieChart.Data("Broj izliječenih \n"+FileWatcher.getBrojIzlijecenih(), FileWatcher.getBrojIzlijecenih()),
								new PieChart.Data("Broj trenutno zaraženih\n"+FileWatcher.getBrojtrenutnoZarazenih(), FileWatcher.getBrojtrenutnoZarazenih()));
						grafikon.setData(pieChartData);
						grafikon.setTitle("Statistika prema stanju stanovnika");
						}
						else {
							if(brojIzlijecenih == 0 && brojZarazenih == 0 ) {
								
									
								grafikon.setTitle("Nema trenutno zaraženih");
								
								
							}
						}
					}
					
				};
				while(true) {
					Platform.runLater(updater);
					try {
						//System.out.println("Initialize thread id:"+initializeThread.getId());
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						 FileLogger.log(Level.SEVERE, null, e);
						break;
						//e.printStackTrace();
					}
					if(!initializeThreadIstinitost) {
						try {
							synchronized (initializeThread) {
								initializeThread.wait();
							}
							
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							//System.out.println("initialize je prekinut");
							 FileLogger.log(Level.SEVERE, null, e);
							break;
							//e.printStackTrace();
						}
					}
				}
			}
		});
		initializeThread.start();
		//ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
		//		new PieChart.Data("Broj izlijecenih", FileWatcher.getBrojIzlijecenih()),
		//		new PieChart.Data("Broj trenutno zarazenih", FileWatcher.getBrojtrenutnoZarazenih()));
		//grafikon.setData(pieChartData);

	}

	@FXML
	void sacuvajIzvjestaj(ActionEvent event) {
		kreirajCSVFile();
	}

	@FXML
	void poPolu(ActionEvent event) {
		if(initializeThread!=null) 
			initializeThreadIstinitost=false;
		if(poVrstiThread!=null)
			poVrstiThreadIstinitost= false;
		if(poPoluThread!=null) {
			poPoluThreadIstinitost=true;
			synchronized (poPoluThread) {
				ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
						new PieChart.Data("Broj ženskih slučajeva \n"+brojSlucajevaZena,brojSlucajevaZena ),
						new PieChart.Data("Broj broj muških slučajeva \n"+brojSlucajevaMuskaraca, brojSlucajevaMuskaraca));
				grafikon.setData(pieChartData);
				grafikon.setTitle("Statistički podaci prema polu");
				poPoluThread.notify();
			}
			
		}else {
			
			
			
		poPoluThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Runnable updater = new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						int brojSlucajevaMuskaracaNovi = 0;
						int brojSlucajevaZenaNovi = 0;

						for (Stanovnik s : Grad.getStanovnici()) {

							if ((s.getStanjeZaraze().equals("BIO ZARAZEN") || s.getStanjeZaraze().equals("ZARAZEN"))
									&& s.getPol() == 'Z') {
								brojSlucajevaZenaNovi++;
							} else if ((s.getStanjeZaraze().equals("BIO ZARAZEN") || s.getStanjeZaraze().equals("ZARAZEN"))
									&& s.getPol() == 'M') {
								brojSlucajevaMuskaracaNovi++;
							}
						}
						if(brojSlucajevaMuskaraca != brojSlucajevaMuskaracaNovi || brojSlucajevaZenaNovi != brojSlucajevaZena) 
						{
							brojSlucajevaMuskaraca = brojSlucajevaMuskaracaNovi;
							brojSlucajevaZena = brojSlucajevaZenaNovi;
							ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
									new PieChart.Data("Broj ženskih slučajeva\n"+brojSlucajevaZena,brojSlucajevaZena ),
									new PieChart.Data("Broj broj muških slučajeva\n"+brojSlucajevaMuskaraca, brojSlucajevaMuskaraca));
							grafikon.setData(pieChartData);
							grafikon.setTitle("Statistički podaci prema polu");
						}
						else {
							if(brojSlucajevaMuskaraca == 0 && brojSlucajevaZena == 0) {
								grafikon.setTitle("Nema trenutno zaraženih");
							}
						}
					}
					
				};
				while(true) {
					Platform.runLater(updater);
					try {
						//System.out.println("po polu thread id:"+poPoluThread.getId());
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						//System.out.println("Po polu thread prekinut");
						 FileLogger.log(Level.SEVERE, null, e);
						break;
					}
					if(!poPoluThreadIstinitost) {
						try {
							synchronized (poPoluThread) {
								poPoluThread.wait();
							}
							
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
							 FileLogger.log(Level.SEVERE, null, e);
							break;
						}
					}
				}
			}
		});
		poPoluThread.start();
		
		
		}
		
		
		
		
		
		
		
		
		
		// PieChart pieChart = new PieChart();
		
		
		
		
		
		
		
	/*	int brojSlucajevaMuskaraca = 0;
		int brojSlucajevaZena = 0;

		for (Stanovnik s : Grad.getStanovnici()) {

			if ((s.getStanjeZaraze().equals("BIO ZARAZEN") || s.getStanjeZaraze().equals("ZARAZEN"))
					&& s.getPol() == 'Z') {
				brojSlucajevaZena++;
			} else if ((s.getStanjeZaraze().equals("BIO ZARAZEN") || s.getStanjeZaraze().equals("ZARAZEN"))
					&& s.getPol() == 'M') {
				brojSlucajevaMuskaraca++;
			}
		}
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				new PieChart.Data("Broj zenskih slucajeva", brojSlucajevaMuskaraca),
				new PieChart.Data("Broj broj muskih slucajeva", brojSlucajevaZena));
		grafikon.setData(pieChartData); */

}

	@FXML
	void poVrsti(ActionEvent event) {

		if(initializeThread!=null) 
			initializeThreadIstinitost=false;
		if(poPoluThread!=null)
			poPoluThreadIstinitost = false;
		
		if(poVrstiThread!=null) {
			poVrstiThreadIstinitost=true;
			synchronized (poVrstiThread) {
				ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
						new PieChart.Data("Broj slučajeva djece\n"+brojSlucajevaDjece, brojSlucajevaDjece),
						new PieChart.Data("Broj slučajeva odraslih\n"+brojSlucajevaOdraslih, brojSlucajevaOdraslih),
						new PieChart.Data("Broj slučajeva starih\n"+brojSlucajevaStarih, brojSlucajevaStarih));
				grafikon.setData(pieChartData);
				grafikon.setTitle("Statistički podaci prema vrsti stanovnika");
				poVrstiThread.notify();
			}
			
		}
		else {
			
		poVrstiThread= new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Runnable updater = new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						int brojSlucajevaDjeceNovi = 0;
						int brojSlucajevaOdraslihNovi = 0;
						int brojSlucajevaStarihNovi = 0;
						for (Stanovnik s : Grad.getStanovnici()) {
							if (s instanceof Dijete
									&& (s.getStanjeZaraze().equals("BIO ZARAZEN") || s.getStanjeZaraze().equals("ZARAZEN"))) {
								brojSlucajevaDjeceNovi++;
							} else if (s instanceof OdraslaOsoba
									&& (s.getStanjeZaraze().equals("BIO ZARAZEN") || s.getStanjeZaraze().equals("ZARAZEN"))) {
								brojSlucajevaOdraslihNovi++;
							} else if (s instanceof StarijaOsoba
									&& (s.getStanjeZaraze().equals("BIO ZARAZEN") || s.getStanjeZaraze().equals("ZARAZEN"))) {
								brojSlucajevaStarihNovi++;
							}
						}
						if(brojSlucajevaDjeceNovi != brojSlucajevaDjece || brojSlucajevaOdraslihNovi != brojSlucajevaOdraslih || brojSlucajevaStarihNovi != brojSlucajevaStarih)
						{	
							brojSlucajevaOdraslih = brojSlucajevaOdraslihNovi;
							brojSlucajevaDjece = brojSlucajevaDjeceNovi;
							brojSlucajevaStarih = brojSlucajevaStarihNovi;
							ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
									new PieChart.Data("Broj slučajeva djece\n"+brojSlucajevaDjece, brojSlucajevaDjece),
									new PieChart.Data("Broj slučajeva odraslih\n"+brojSlucajevaOdraslih, brojSlucajevaOdraslih),
									new PieChart.Data("Broj slučajeva starih\n"+brojSlucajevaStarih, brojSlucajevaStarih));
							grafikon.setData(pieChartData);
							grafikon.setTitle("Statistički podaci prema vrsti stanovnika");
						}else {
							if(brojSlucajevaDjece == 0 && brojSlucajevaOdraslih ==0 && brojSlucajevaStarih == 0) {
								grafikon.setTitle("Nema trenutno zaraženih");
							}
						}
					}
					
				};
				while(true) {
					Platform.runLater(updater);
					//System.out.println("po vrsti thread id:"+poVrstiThread.getId());
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						
						//e.printStackTrace();
						FileLogger.log(Level.SEVERE, null, e);
						break;
					}
					if(!poVrstiThreadIstinitost) {
						try {
							synchronized (poVrstiThread) {
								poVrstiThread.wait();
							}
							
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							//System.out.println("po vrsti thread je prekinut");
							FileLogger.log(Level.SEVERE, null, e);
							break;
							//e.printStackTrace();
						}
					}
				}
			}
		});
		poVrstiThread.start();
		
		}
		
		
		
/*		int brojSlucajevaDjece = 0;
		int brojSlucajevaOdraslih = 0;
		int brojSlucajevaStarih = 0;
		for (Stanovnik s : Grad.getStanovnici()) {
			if (s instanceof Dijete
					&& (s.getStanjeZaraze().equals("BIO ZARAZEN") || s.getStanjeZaraze().equals("ZARAZEN"))) {
				brojSlucajevaDjece++;
			} else if (s instanceof OdraslaOsoba
					&& (s.getStanjeZaraze().equals("BIO ZARAZEN") || s.getStanjeZaraze().equals("ZARAZEN"))) {
				brojSlucajevaOdraslih++;
			} else if (s instanceof StarijaOsoba
					&& (s.getStanjeZaraze().equals("BIO ZARAZEN") || s.getStanjeZaraze().equals("ZARAZEN"))) {
				brojSlucajevaStarih++;
			}
		}
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				new PieChart.Data("Broj slucajeva djece", brojSlucajevaDjece),
				new PieChart.Data("Broj slucajeva odraslih", brojSlucajevaOdraslih),
				new PieChart.Data("Broj slucajeva odraslih", brojSlucajevaStarih));
		grafikon.setData(pieChartData);*/
	}

	private void kreirajCSVFile() {
		File file = new File("src" + File.separator + "net" + File.separator + "etfbl" + File.separator + "pj2"
				+ File.separator + "Statistic" + File.separator + "FileCSV" + File.separator + "File.csv");
		try {
			file.createNewFile();
			FileOutputStream out = new FileOutputStream(file);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
			PrintWriter pw = new PrintWriter(bw);
			pw.println("Id" + "," + "Ime" + "," + "Prezime" + "," + "Godina rodjenja" + "," + "Pol" + ","
					+ "Tip stanovnika" + "," + "Stanje zaraze");

			for (Stanovnik s : Grad.getStanovnici()) {

				String tip = null;
				if (s instanceof Dijete)
					tip = "Dijete";
				else if (s instanceof OdraslaOsoba)
					tip = "Odrasla osoba";
				else if (s instanceof StarijaOsoba)
					tip = "Starija osoba";
				pw.println(s.getId() + "," + s.getIme() + "," + s.getPrezime() + "," + s.getGodinaRodjenja() + ","
						+ s.getPol() + "," + tip + "," + s.getStanjeZaraze());

			}
			pw.flush();
			pw.close();
			bw.close();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			FileLogger.log(Level.SEVERE, null, new Throwable("Greska prilikom kreiranja csv fajla"));
		}
	}

	@FXML
	void poBrojuZarazenih(ActionEvent event) {
		/*int brojZarazenih = 0;
		int brojZdravih = 0;
		for (Stanovnik s : Grad.getStanovnici()) {
			if (s.getStanjeZaraze().equals("BIO ZARAZEN") || s.getStanjeZaraze().equals("ZARAZEN")) {
				brojZarazenih++;
			} else {
				brojZdravih++;
			}
		}
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				new PieChart.Data("Stanovnici koji nisu imali virus", brojZarazenih),
				new PieChart.Data("Stanovnici koji su imali ili imaju virus", brojZdravih));
		grafikon.setData(pieChartData);*/
		
		
		if(poVrstiThread!=null) 
			poVrstiThreadIstinitost=false;
		if(poPoluThread!=null)
			poPoluThreadIstinitost = false;
		
		if(initializeThread!=null) {
			initializeThreadIstinitost=true;
			synchronized (initializeThread) {
				ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
						new PieChart.Data("Broj izliječenih\n"+FileWatcher.getBrojIzlijecenih(), FileWatcher.getBrojIzlijecenih()),
						new PieChart.Data("Broj trenutno zaraženih\n"+FileWatcher.getBrojtrenutnoZarazenih(), FileWatcher.getBrojtrenutnoZarazenih()));
				grafikon.setData(pieChartData);
				grafikon.setTitle("Statistika prema stanju stanovnika");
				initializeThread.notify();
			}
			
		}
		
	}

	@FXML
	void zatvoriProzor(ActionEvent event) {
		if(initializeThread!=null)
			initializeThread.interrupt();
		if(poPoluThread!=null)
			poPoluThread.interrupt();
		if(poVrstiThread!=null)
			poVrstiThread.interrupt();
		
		((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
		// stage.close();
	}

}