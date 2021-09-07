package net.etfbl.pj2.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.etfbl.pj2.Model.Ambulanta;
import net.etfbl.pj2.Model.AmbulantnoVozilo;
import net.etfbl.pj2.Model.Dijete;
import net.etfbl.pj2.Model.Element;
import net.etfbl.pj2.Model.Grad;
import net.etfbl.pj2.Model.KontrolniPunkt;
import net.etfbl.pj2.Model.Kuca;
import net.etfbl.pj2.Model.OdraslaOsoba;
import net.etfbl.pj2.Model.Polje;
import net.etfbl.pj2.Model.Stanovnik;
import net.etfbl.pj2.Model.StarijaOsoba;
import net.etfbl.pj2.Utility.FileLogger;
import net.etfbl.pj2.Utility.FileWatcher;
import net.etfbl.pj2.Utility.MatricaGradaSerializable;


public class GlavnaController {
	Thread thread;
	Thread threadAmbulantnogVozila;
	@FXML
    private TextArea logTekst;	
    @FXML
    private FlowPane kontrolniPunktBoja;   
    @FXML
    private FlowPane dijeteBoja;   
    @FXML
    private FlowPane starijaOsobaBoja;   
    @FXML
    private FlowPane ambulantaBoja;  
    @FXML
    private FlowPane ambulantnoVoziloBoja;
    @FXML
    private FlowPane kucaBoja;
    @FXML
    private FlowPane odrasliBoja; 	
	@FXML
	private GridPane glavniGrid;
	@FXML
	private GridPane gridMapaKontrole;
	@FXML
	private GridPane gridMapa;
	@FXML
	private Label brojTrenutnoZarazenih;
	@FXML
	private Label brojIzlijecenih;
	@FXML
	private Label ukupanBrojSlucajeva;
	private int sirinaIVisinaPolja;
	private int vrijemeUSekundama=0;
	private boolean threadIstinitost = true;
	private int brojAmbulanti = Grad.getAmbulante().size();
	@FXML
	public void initialize() {
		//FileWatcher.dodajZarazenog();
	
		FileWatcher.upisiPodatkeUFajl(0, 0);
		
		for(String s:Grad.getLogStrings())
			logTekst.setText(logTekst.getText()+s);
		
		postaviLegendu();
		
		Thread thread2 = new Thread(new Runnable() {

			@Override
			public void run() {
				Runnable updater = new Runnable() {

					@Override
					public void run() {
						FileWatcher.pokupiPodatkeIzFajla();
					//	for(Kuca k:Grad.getKuce())
						//	System.out.println(k);
						brojTrenutnoZarazenih
								.setText("Broj trenutno zarazenih: " + FileWatcher.getBrojtrenutnoZarazenih());
						brojIzlijecenih.setText("Broj izlijecenih: " + FileWatcher.getBrojIzlijecenih());
						ukupanBrojSlucajeva.setText("Ukupan broj slucajeva: " + FileWatcher.getUkupanBrojSlucajeva());
						if(Grad.getAmbulante().size()>brojAmbulanti) {
							int i = brojAmbulanti;
							while(i<Grad.getAmbulante().size()) {
								
								Ambulanta a = Grad.getAmbulante().get(i);
								i++;
								logTekst.setText(logTekst.getText()+LocalDateTime.now() + " Ambulanta id= " + a.getId() + " ima kapacitet=" + a.getKapacitet()
								+ " je postavljena na poziciju x= " + a.getX() + " y= " + a.getY() + "\n");
							}
							brojAmbulanti = Grad.getAmbulante().size();
							
						}
						if(!Grad.getPoruka().equals("")) {
							logTekst.setText(logTekst.getText()+Grad.getPoruka());
							Grad.setPoruka("");
						}
					}
				};

				while (true) {
					// UI update is run on the Application thread
					Platform.runLater(updater);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException ex) {
						FileLogger.log(Level.SEVERE, null, ex);
					}
				}
			}

		});
		 
		thread2.setDaemon(true);
		thread2.start();
		postaviMapu();
	}

	@FXML
	void omoguciKretanje(ActionEvent event) {
		if(thread == null) {
		thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Runnable updater = new Runnable() {
					@Override
					public void run() {

						Random random = new Random();
						

						for (Stanovnik s : Grad.getStanovnici()) {
							int stranaInt = random.nextInt(4);
							if (stranaInt == 0) {
								s.setStranaKretanja("LIJEVO");

							} else if (stranaInt == 1) {
								s.setStranaKretanja("DESNO");

							} else if (stranaInt == 2) {
								s.setStranaKretanja("GORE");

							} else if (stranaInt == 3) {
								s.setStranaKretanja("DOLE");
							}
							for (Stanovnik stanovnik : Grad.getStanovnici()) {
								if(vrijemeUSekundama % 30 == 0) { //broj sekundi
									stanovnik.postaviTempStanovika();
								}
								if (stanovnik.getStanje().equals("CEKA AMBULANTU") || stanovnik.getStanje().equals("U AMBULANTNOM VOZILU")) {
									for (Stanovnik st : Grad.getStanovnici()) {
										if (st.getId() != stanovnik.getId()
												&& st.getIdKuce() == stanovnik.getIdKuce()) {
											//st.vratiSeKuci();
											if("NORMALNO".equals(st.getStanje()))
												st.setStanje("POVRATAK KUCI");
										}
									}
								}
								if(stanovnik.getStanje().equals("POVRATAK KUCI")) {
									stanovnik.vratiSeKuci();
								}
							}
							s.pomjeriStanovnika();
							logTekst.setText(logTekst.getText()+Grad.getLogStrings().get(0));
						}
						for (KontrolniPunkt k : Grad.getKontrolniPunktovi()) {
							k.ocitajTempStanovnikaURadijusuDjelovanja();
						}
						for(Ambulanta a : Grad.getAmbulante()) {
							a.izmjeriTemperaturuStanovnicima();
							a.provjeriTempStanovnikaUAmbulanti();
						}
						prikaziTrenutnoStanjeMape(sirinaIVisinaPolja);
					}
				};
				while (true) {
					// UI update is run on the Application thread
					
					Platform.runLater(updater);
					try {
						Thread.sleep(1000);
						vrijemeUSekundama++;
						//System.out.println("tred"+thread.getId()+" se izvrsava");
						if(!threadIstinitost) {
							synchronized (thread) {
								thread.wait();
								}
						}

					} catch (InterruptedException ex) {
						FileLogger.log(Level.SEVERE, null, ex);
					}
				}
			}
		});
		thread.setDaemon(true);
		thread.start();
		}
	}

	@FXML
	void posaljiAmbulantnoVozilo(ActionEvent event) {
		threadAmbulantnogVozila = new Thread(new Runnable() {

			@Override
			public void run() {
				Runnable updater = new Runnable() {
					@Override
					public void run() {
						for(AmbulantnoVozilo av: Grad.getAmbulantnaVozila()) {
							if(av.getIdStanovnikaKojiSePrevozi() == 0) {
								//uslov da stek nije prazan
								if(!Grad.getStekStanovnika().empty()) {
								Stanovnik stanovnik = Grad.getStekStanovnika().pop();
								av.pokupiZarazenogStanovnika(stanovnik);
								av.pomjeriAmbulantnoVozilo();
								logTekst.setText(logTekst.getText()+Grad.getLogStrings().get(0));
								}
							}else {
								av.pokupiZarazenogStanovnika(Stanovnik.vratiStanovnikaNaOsnovuId(av.getIdStanovnikaKojiSePrevozi()));
								av.pomjeriAmbulantnoVozilo();
								logTekst.setText(logTekst.getText()+Grad.getLogStrings().get(0));			
							}
						}
						for (Stanovnik st : Grad.getStanovnici()) {
							if (st.isIzolacija() && st.getStanje().equals("CEKA AMBULANTU")) {
								st.udjiUAmbulantnoVozilo();
							}
						}
					}
				};
				while (true) {
					Platform.runLater(updater);
					try {
						Thread.sleep(1000);
						if(!threadIstinitost) {
							synchronized (threadAmbulantnogVozila) {
								threadAmbulantnogVozila.wait();
							}	
						}
					} catch (InterruptedException ex) {
						FileLogger.log(Level.SEVERE, null, ex);
					}
				}
			}
		});
		threadAmbulantnogVozila.setDaemon(true);
		threadAmbulantnogVozila.start();
	}

	@FXML
	void pregledajStanjeAmbulanti(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("../View/StanjeAmbulanti.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			FileLogger.log(Level.SEVERE, null, new Throwable("Nije moguce ucitati stranicu StanjeAmbulanti.fxml"));
			e.printStackTrace();
		}
	}

	@FXML
	void pogledajStatistickePodatke(ActionEvent event) {

		//
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("../View/StatistickiPodaci.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			FileLogger.log(Level.SEVERE, null, new Throwable("Nije moguce ucitati stranicu StatickiPodaci.fxml"));
			e.printStackTrace();
		}
		
	}
	
	
	@FXML
	void zaustaviSimulaciju(ActionEvent event) {
		
			
		
		if(thread!=null || threadAmbulantnogVozila!=null) {
			threadIstinitost=false;
			try {
				synchronized (Grad.getStanovnici()) {
				String filename = "src" + File.separator + "net" + File.separator + "etfbl" + File.separator + "pj2"
						+ File.separator + "Statistic" + File.separator + "SerijalizacijaMatrice";
				FileOutputStream file = new FileOutputStream(filename);
				ObjectOutputStream out = new ObjectOutputStream(file);
				MatricaGradaSerializable matricaGrada = new MatricaGradaSerializable(Grad.getMapa());
				//Grad.setMapa( new Polje[Grad.getDimenzija()-1][Grad.getDimenzija()-1]);
				//prikaziTrenutnoStanjeMape(sirinaIVisinaPolja);
				Grad.setMapa( new Polje[Grad.getDimenzija()][Grad.getDimenzija()]);
				Grad.getStanovnici().clear();
				Grad.inicijalizujMapu();
				//prikaziTrenutnoStanjeMape(sirinaIVisinaPolja);
				out.writeObject(matricaGrada);
				out.close();
				file.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				FileLogger.log(Level.SEVERE, null, new Throwable("Greska priliko serijalizacije matrice"));
				e.printStackTrace();
			}
		}
		//}	
	}

	@FXML
	void pokreniSimulacijuPonovo(ActionEvent event) throws InterruptedException {
		
		if(thread!=null || threadAmbulantnogVozila!=null) {
		  try {
			  synchronized (Grad.getStanovnici()) {
			 // System.out.println("stanje treda"+thread.getState());
	        	 String filename = "src" + File.separator + "net" + File.separator + "etfbl" + File.separator + "pj2"
	     				+ File.separator + "Statistic" + File.separator + "SerijalizacijaMatrice";
	     		 FileInputStream file = new FileInputStream(filename); 
	              ObjectInputStream in = new ObjectInputStream(file); 
			
			//	Grad.setMapa( new Polje[Grad.getDimenzija()][Grad.getDimenzija()]);
			//	Grad.inicijalizujMapu();
				MatricaGradaSerializable matricaGrada = (MatricaGradaSerializable)in.readObject();
				Grad.setMapa(matricaGrada.getMapa());
				Grad.dodajStanovnikeiAmbulanteIzMape();
				//Grad.setStanovnici(matricaGrada.getStanovnici());
				in.close(); 
		         file.close();
			  }
			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				FileLogger.log(Level.SEVERE, null, new Throwable("Greska priliko deserijalizacije matrice"));
			} 
		  threadIstinitost=true;
		  if(threadAmbulantnogVozila!=null)
		  synchronized (threadAmbulantnogVozila) {
			threadAmbulantnogVozila.notify();
		}
		  if(thread!=null)
		  synchronized (thread) {
			thread.notify();
		  }
		}
	}

	@FXML
	void zavrsiSimulaciju(ActionEvent event) {
		//

		LocalDateTime datum = LocalDateTime.now();
		String vrijemeIDatum = "SIM-JavaKov-20-"+datum.getHour()+"_"+datum.getMinute()+"_"+datum.getSecond()+" "+datum.getDayOfMonth()+"."+datum.getMonth()+"."+datum.getYear()+".txt";
		File file = new File("src"+File.separator+"net"+File.separator+"etfbl"+File.separator+"pj2"+File.separator+"SimulationData"+File.separator+vrijemeIDatum);
		
		try {
			file.createNewFile();
			FileInputStream in = new FileInputStream(file);
			Properties p = new Properties();
			Properties p1 = new Properties();
			InputStream ulaz = new FileInputStream("src"+File.separator+"net"+File.separator+"etfbl"+File.separator+"pj2"+File.separator+"Statistic"+File.separator+"PodaciOZarazenima");
			p1.load(ulaz);
			
			p.setProperty("Ukupan broj slucajeva", p1.getProperty("UKUPAN_BROJ_SLUCAJEVA"));
			p.setProperty("Broj trenutno zarazenih", p1.getProperty("BROJ_TRENUTNO_ZARAZENIH"));
			p.setProperty("Broj izlijecenih", p1.getProperty("BROJ_IZLIJECENIH"));
			
			int sati = vrijemeUSekundama/3600;
			int minute = (vrijemeUSekundama % 3600) / 60;
			int sekunde = (vrijemeUSekundama % 60);
			p.load(in);
			p.setProperty("Vrijeme trajanja simulacije", String.format("%02d:%02d:%02d", sati, minute, sekunde));
			p.setProperty("Broj ambulanti",String.valueOf(Grad.getAmbulante().size()));
			p.setProperty("Broj ambulantnih vozila", String.valueOf(Grad.getAmbulantnaVozila().size()));
			p.setProperty("Broj kuca", String.valueOf(Grad.getBrojKuca()));
			p.setProperty("Broj kontrolnih punktova", String.valueOf(Grad.getBrojKontrolnihPunktova()));
			p.setProperty("Broj djece",String.valueOf(Grad.getBrojDjece()));
			p.setProperty("Broj odraslih osoba", String.valueOf(Grad.getBrojOdraslih()));
			p.setProperty("Broj starijih osoba", String.valueOf(Grad.getBrojStarih()));
			
			p.store(new FileOutputStream(file), null);
			in.close();		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			FileLogger.log(Level.SEVERE, null, new Throwable("Greska prilikom kreiranje zavrsnog fajla"));
		}
		//System.out.println(vrijemeIDatum);
		  System.exit(0);
	}

	private Background getPodzadinaElementa(Element element) {
	
		Color c = Color.WHITE;
		if (element instanceof Ambulanta)
			c = Color.RED;
		else if (element instanceof KontrolniPunkt)
			c = Color.BLUE;
		else if (element instanceof Kuca)
			c = Color.GREEN;
		else if (element instanceof AmbulantnoVozilo)
			c = Color.BLUEVIOLET;
		else if (element instanceof OdraslaOsoba)
			c = Color.BROWN;
		else if (element instanceof StarijaOsoba)
			c = Color.PINK;
		else if (element instanceof Dijete)
			c = Color.YELLOW;

		else {
			c = Color.WHITE;
		}
		return new Background(new BackgroundFill(c, CornerRadii.EMPTY, Insets.EMPTY));
	}

	private Node getPoljeGridPane(GridPane gridPane, int col, int row) {
		for (Node node : gridPane.getChildren()) {
			if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
				return node;
			}
		}
		return null;
	}

	public void prikaziTrenutnoStanjeMape(int sirinaIVisinaPolja) {
		for (int i = 0; i < Grad.getDimenzija(); i++) {
			for (int j = 0; j < Grad.getDimenzija(); j++) {
				if (Grad.getMapa()[i][j].getElementi() != null && !Grad.getMapa()[i][j].getElementi().isEmpty()) {
					for (Element e : Grad.getMapa()[i][j].getElementi()) {
						if (!((FlowPane) getPoljeGridPane(gridMapa, i, j)).getChildren().isEmpty())
							((FlowPane) getPoljeGridPane(gridMapa, i, j)).getChildren().remove(0);
						if (e instanceof OdraslaOsoba) {
							OdraslaOsoba osoba = (OdraslaOsoba) e;
							Text text = new Text(osoba.prikazImenaStanovnika());
							text.setFont(new Font(8));
							text.autosize();
							text.resize(sirinaIVisinaPolja, sirinaIVisinaPolja);
							((FlowPane) getPoljeGridPane(gridMapa, i, j)).getChildren().add(text);

						} else if (e instanceof StarijaOsoba) {
							StarijaOsoba osoba = (StarijaOsoba) e;
							Text text = new Text(osoba.prikazImenaStanovnika());
							text.setFont(new Font(8));
							text.autosize();
							text.resize(sirinaIVisinaPolja, sirinaIVisinaPolja);
							((FlowPane) getPoljeGridPane(gridMapa, i, j)).getChildren().add(text);

						} else if (e instanceof Dijete) {
							Dijete dijete = (Dijete) e;
							Text text = new Text(dijete.prikazImenaStanovnika());
							text.setFont(new Font(8));
							text.autosize();
							text.resize(sirinaIVisinaPolja, sirinaIVisinaPolja);
							((FlowPane) getPoljeGridPane(gridMapa, i, j)).getChildren().add(text);

						} else if (e instanceof Kuca) {
							Kuca kuca = (Kuca) e;
							Text text = new Text(kuca.kucaIspis());
							text.setFont(new Font(8));
							text.autosize();
							text.resize(sirinaIVisinaPolja, sirinaIVisinaPolja);
							((FlowPane) getPoljeGridPane(gridMapa, i, j)).getChildren().add(text);
						}
					}

					Element tempElement = null;
					int temp = 0;
					for (Element element : Grad.getMapa()[i][j].getElementi()) {
						if (element instanceof AmbulantnoVozilo) {
							tempElement = element;

						} else if (element instanceof Ambulanta) {
							temp = 1;
						}
					}
					if (tempElement == null || temp == 1)
						((FlowPane) getPoljeGridPane(gridMapa, i, j))
								.setBackground(getPodzadinaElementa(Grad.getMapa()[i][j].getElementi().get(0)));
					else {
						((FlowPane) getPoljeGridPane(gridMapa, i, j)).setBackground(getPodzadinaElementa(tempElement));
					}
				} else {
					Color color = Color.rgb(255, 255, 255);
					BackgroundFill fill = new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY);
					((FlowPane) getPoljeGridPane(gridMapa, i, j)).setBackground(new Background(fill));
					if (!((FlowPane) getPoljeGridPane(gridMapa, i, j)).getChildren().isEmpty())
						((FlowPane) getPoljeGridPane(gridMapa, i, j)).getChildren().remove(0);
				}
			}
		}
	}
	public void postaviLegendu() {
		BackgroundFill fill1 = new BackgroundFill(Color.BLUEVIOLET, CornerRadii.EMPTY, Insets.EMPTY);
		ambulantnoVoziloBoja.setBackground(new Background(fill1));
		
		BackgroundFill fill2 = new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY);
		ambulantaBoja.setBackground(new Background(fill2));
		
		BackgroundFill fill3 = new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY);
		kucaBoja.setBackground(new Background(fill3));
		
		BackgroundFill fill4 = new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY);
		kontrolniPunktBoja.setBackground(new Background(fill4));
		
		BackgroundFill fill5 = new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY);
		dijeteBoja.setBackground(new Background(fill5));
		
		BackgroundFill fill6 = new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY);
		starijaOsobaBoja.setBackground(new Background(fill6));
		
		BackgroundFill fill7 = new BackgroundFill(Color.BROWN, CornerRadii.EMPTY, Insets.EMPTY);
		odrasliBoja.setBackground(new Background(fill7));
	}
	public void postaviMapu() {
		sirinaIVisinaPolja = 20;
		int sirinaIVisinaMape = sirinaIVisinaPolja * Grad.getDimenzija();
		gridMapa.setPrefWidth(sirinaIVisinaMape);
		gridMapa.setMaxWidth(sirinaIVisinaMape);
		gridMapa.setMinWidth(sirinaIVisinaMape);
		gridMapa.setPrefHeight(sirinaIVisinaMape);
		gridMapa.setMinHeight(sirinaIVisinaMape);
		gridMapa.setMaxHeight(sirinaIVisinaMape);
		for (int i = 0; i < Grad.getDimenzija(); i++) {
			for (int j = 0; j < Grad.getDimenzija(); j++) {
				FlowPane field = new FlowPane();
				// Text text = new Text("T");
				field.setPrefHeight(sirinaIVisinaPolja);
				field.setMaxHeight(sirinaIVisinaPolja);
				field.setMinHeight(sirinaIVisinaPolja);
				field.setPrefWidth(sirinaIVisinaPolja);
				field.setMaxWidth(sirinaIVisinaPolja);
				field.setMinWidth(sirinaIVisinaPolja);
				Color color = Color.rgb(255, 255, 255);
				BackgroundFill fill = new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY);
				// field.getChildren().add(text);
				field.setBackground(new Background(fill));
				gridMapa.add(field, i, j);
			}

		}
		prikaziTrenutnoStanjeMape(sirinaIVisinaPolja);
	}
}
