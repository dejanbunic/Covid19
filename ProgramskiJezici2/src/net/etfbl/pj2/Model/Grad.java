package net.etfbl.pj2.Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class Grad implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4114788945988865884L;
	private static int dimenzija = (int) Math.round(15+15*Math.random());
	private static int brojStanovnika = 0;
	private static int brojOdraslih;
	private static int brojDjece;
	private static int brojStarih;
	private static int brojAmbulantnihVozila;
	private static int brojKuca;
	private static int brojKontrolnihPunktova;
	private static String poruka="";
	private static Polje[][] mapa = new Polje[dimenzija][dimenzija];
	private static List<Kuca> kuce = new ArrayList<Kuca>();
	private static List<KontrolniPunkt> kontrolniPunktovi = new ArrayList<KontrolniPunkt>();
	private static List<Stanovnik> stanovnici = new ArrayList<Stanovnik>();
	private static List<AmbulantnoVozilo> ambulantnaVozila = new ArrayList<AmbulantnoVozilo>();
	private static Stack<Stanovnik> stekStanovnika = new Stack<Stanovnik>();
	private static List<Ambulanta> ambulante = new ArrayList<Ambulanta>();
	private static List<String> logStrings = new ArrayList<String>();
	public static Object lock = new Object();

	public Grad() {
		super();
		// TODO Auto-generated constructor stub
		setDimenzija((int) Math.round(15 + Math.random() * 15));
	}

	public static void dodajElementNaPoziciju(int x, int y, Element e) {
		mapa[x][y].elementi.add(e);
	}

	public static void ukloniElementSaPozicije(int x, int y, Element e) {
		mapa[x][y].elementi.remove(e);
	}

	public static int getDimenzija() {
		return dimenzija;
	}

	public static void setDimenzija(int dimenzija) {
		Grad.dimenzija = dimenzija;
	}

	public static int getBrojStanovnika() {
		return brojStanovnika;
	}

	public static void setBrojStanovnika(int brojStanovnika) {
		Grad.brojStanovnika = brojStanovnika;
	}

	public static int getBrojAmbulantnihVozila() {
		return brojAmbulantnihVozila;
	}

	public static void setBrojAmbulantnihVozila(int brojAmbulantnihVozila) {
		Grad.brojAmbulantnihVozila = brojAmbulantnihVozila;
	}

	public static int getBrojKuca() {
		return brojKuca;
	}

	public static void setBrojKuca(int brojKuca) {
		Grad.brojKuca = brojKuca;
	}

	public static int getBrojKontrolnihPunktova() {
		return brojKontrolnihPunktova;
	}

	public static void setBrojKontrolnihPunktova(int brojKontrolnihPunktova) {
		Grad.brojKontrolnihPunktova = brojKontrolnihPunktova;
	}

	public static int getBrojOdraslih() {
		return brojOdraslih;
	}

	public static void setBrojOdraslih(int brojOdraslih) {
		Grad.brojOdraslih = brojOdraslih;
	}

	public static int getBrojDjece() {
		return brojDjece;
	}

	public static void setBrojDjece(int brojDjece) {
		Grad.brojDjece = brojDjece;
	}

	public static int getBrojStarih() {
		return brojStarih;
	}

	public static void setBrojStarih(int brojStarih) {
		Grad.brojStarih = brojStarih;
	}

	public static List<Kuca> getKuce() {
		return kuce;
	}

	public static void setKuce(List<Kuca> kuce) {
		Grad.kuce = kuce;
	}

	public static List<Stanovnik> getStanovnici() {
		return stanovnici;
	}

	public static void setStanovnici(List<Stanovnik> stanovnici) {
		Grad.stanovnici = stanovnici;
	}

	public static Polje[][] getMapa() {
		return mapa;
	}

	public static void setMapa(Polje[][] mapa) {
		Grad.mapa = mapa;
	}

	public static List<KontrolniPunkt> getKontrolniPunktovi() {
		return kontrolniPunktovi;
	}

	public static void setKontrolniPunktovi(List<KontrolniPunkt> kontrolniPunktovi) {
		Grad.kontrolniPunktovi = kontrolniPunktovi;
	}

	public static List<AmbulantnoVozilo> getAmbulantnaVozila() {
		return ambulantnaVozila;
	}

	public static void setAmbulantnaVozila(List<AmbulantnoVozilo> ambulantnaVozila) {
		Grad.ambulantnaVozila = ambulantnaVozila;
	}

	public static void kreirajAmbulantanaVozila() {
		for (int i = 0; i < brojAmbulantnihVozila; i++) {
			ambulantnaVozila.add(new AmbulantnoVozilo());
			// logStrings.add(LocalDateTime.now()+" Ambulantno vozilo je kreirano");
		}
		int i = 0;
		/*
		 * mapa[0][0].getElementi().add(Grad.getAmbulante().get(0)); mapa[dimenzija -
		 * 1][0].getElementi().add(Grad.getAmbulante().get(1)); mapa[0][dimenzija -
		 * 1].getElementi().add(Grad.getAmbulante().get(2)); mapa[dimenzija -
		 * 1][dimenzija - 1].getElementi().add(Grad.getAmbulante().get(3));
		 */
		for (AmbulantnoVozilo av : Grad.getAmbulantnaVozila()) {
			if (i % 4 == 0) {
				Grad.getMapa()[0][0].getElementi().add(av);
				av.setX(0);
				av.setY(0);
				av.setIdAmbulante(1);
			} else if (i % 3 == 0) {
				Grad.getMapa()[Grad.getDimenzija() - 1][0].getElementi().add(av);
				av.setX(Grad.getDimenzija() - 1);
				av.setY(0);
				av.setIdAmbulante(2);
			} else if (i % 2 == 0) {
				Grad.getMapa()[0][Grad.getDimenzija() - 1].getElementi().add(av);
				av.setX(0);
				av.setY(Grad.getDimenzija() - 1);
				av.setIdAmbulante(3);
			} else {
				Grad.getMapa()[Grad.getDimenzija() - 1][Grad.getDimenzija() - 1].getElementi().add(av);
				av.setX(Grad.getDimenzija() - 1);
				av.setY(Grad.getDimenzija() - 1);
				av.setIdAmbulante(4);
			}
			i++;
		}
	}

	public static void generisiKuce() {
		for (int i = 0; i < brojKuca; i++) {
			// Kuca kuca = new Kuca(i+1,0);
			kuce.add(new Kuca(i + 1, 0));
			// logStrings.add(LocalDateTime.now()+" Kreirana je kuca sa id="+k);
		}
	}

	public static void generisiStanovnike() {
		// List<Stanovnik> stanovnici = new ArrayList<Stanovnik>();
		int ukupno = brojDjece + brojOdraslih + brojStarih;
		// System.out.println("ukupno je"+ukupno);
		LocalDateTime localData = LocalDateTime.now();
		for (int i = 0; i < ukupno; i++) {
			if (i < brojDjece) {
				Stanovnik s = new Dijete();
				s.setId(i + 1);
				s.setIme(randomString());
				s.setPrezime(randomString());
				s.setGodinaRodjenja(localData.getYear() - (int) (18 * Math.random()));
				if (Math.random() <= 0.5)
					s.setPol('M');
				else {
					s.setPol('Ž');
				}
				stanovnici.add(s);
			} else if (i >= brojDjece && i < (brojDjece + brojOdraslih)) {
				Stanovnik s = new OdraslaOsoba();
				s.setId(i + 1);
				s.setIme(randomString());
				s.setPrezime(randomString());
				s.setGodinaRodjenja(localData.getYear() - (int) (18 + Math.random() * 47));
				if (Math.random() <= 0.5)
					s.setPol('M');
				else {
					s.setPol('Ž');
				}
				stanovnici.add(s);
			} else if (i >= (brojDjece + brojOdraslih) && i < (brojDjece + brojOdraslih + brojStarih)) {
				Stanovnik s = new StarijaOsoba();
				s.setId(i + 1);
				s.setIme(randomString());
				s.setPrezime(randomString());
				s.setGodinaRodjenja(localData.getYear() - (int) (65 + 35 * Math.random()));
				if (Math.random() <= 0.5)
					s.setPol('M');
				else {
					s.setPol('Ž');
				}
				stanovnici.add(s);
			}
		}
	}

	private static String randomString() {
		int leftLimit = 41; // slovo 'a'
		int rightLimit = 122; // slovo 'Z'
		int targetStringLength = 2;
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}
		String generatedString = buffer.toString();
		return generatedString;
	}

	public static void rasporediStanovnikePoKucama() {
		int i = 1;
		int max = 1;
		for (Stanovnik s : stanovnici) {
			if (!(s instanceof Dijete)) {
				if (i > brojKuca) {
					max = brojKuca;
					i = 1;
				}
				s.setIdKuce(i);
				i++;
			}
		}
		i--;
		if (i > max && i < brojKuca)
			max = i;
		i = 1;
		for (Stanovnik s : stanovnici) {
			if (s instanceof Dijete) {
				if (i > max) {
					i = 1;
				}
				s.setIdKuce(i);
				i++;
			}
		}
	}

	public static void rasporediKuceNaMapu() {
		// Polje [][]mapa = Grad.getMapa();
		for (Kuca k : kuce) {
			int i = 1;
			int x = 0;
			int y = 0;
			while (i > 0) {
				x = (int) (dimenzija * Math.random());
				y = (int) (dimenzija * Math.random());
				// System.out.println("x= "+x+" y="+y);
				/*if (x == 0 && y == 0)
					i = 1;
				else if (x == dimenzija && y == dimenzija)
					i = 1;
				else if (x == dimenzija && y == 0)
					i = 1;
				else if (x == 0 && y == dimenzija)
					i = 1;*/
				if(x==0 || y==0 || x==dimenzija-1 || y==dimenzija-1)
					i=1;
				else {
					List<Element> elementi = null;
					if (mapa[x][y].getElementi() != null)
						elementi = mapa[x][y].getElementi();

					if (elementi != null && elementi.isEmpty()) {
						i = 0;
					}
					k.setX(x);
					k.setY(y);
					logStrings.add(LocalDateTime.now() + " Kuca sa id=" + k.getId() + " je postavljana na lokaciju x="
							+ k.getX() + " y=" + k.getY() + "\n");
					mapa[x][y].getElementi().add(k);
				}
			}
	/*		k.setX(x);
			k.setY(y);
			logStrings.add(LocalDateTime.now() + " Kuca sa id=" + k.getId() + " je postavljana na lokaciju x="
					+ k.getX() + " y=" + k.getY() + "\n");
			mapa[x][y].getElementi().add(k);*/ // kopirano unutar else
		}
	}

	public static void rasporediKontrolnePunktoveNaMapu() {
		for (int i = 0; i < brojKontrolnihPunktova; i++) {
			kontrolniPunktovi.add(new KontrolniPunkt());
		}
		for (KontrolniPunkt k : kontrolniPunktovi) {
			int i = 1;
			int x = 0;
			int y = 0;
			while (i > 0) {
				x = (int) (dimenzija * Math.random());
				y = (int) (dimenzija * Math.random());
			/*	if (x == 0 && y == 0)
					i = 1;
				else if (x == dimenzija && y == dimenzija)
					i = 1;
				else if (x == dimenzija && y == 0)
					i = 1;
				else if (x == 0 && y == dimenzija)
					i = 1;*/
				if(x==0 || y==0 || x==dimenzija-1 || y==dimenzija-1)
					i=1;
				else {
					List<Element> elementi = null;
					if (mapa[x][y].getElementi() != null)
						elementi = mapa[x][y].getElementi();
					if (elementi != null && elementi.isEmpty()) {
						i = 0;
					}
					k.setX(x);
					k.setY(y);
					logStrings.add(LocalDateTime.now() + " Kontrolni punkt je postavljan na lokaciju x=" + k.getX() + " y="
							+ k.getY() + "\n");
					mapa[x][y].getElementi().add(k);
				}
			}
		/*	k.setX(x);
			k.setY(y);
			logStrings.add(LocalDateTime.now() + " Kontrolni punkt je postavljan na lokaciju x=" + k.getX() + " y="
					+ k.getY() + "\n");
			mapa[x][y].getElementi().add(k);*/ // kopirano unutrar else 
		}
	}

	public static void inicijalizujMapu() {
		for (int i = 0; i < dimenzija; i++) {
			for (int j = 0; j < dimenzija; j++) {
				mapa[i][j] = new Polje();
			}
		}
	}

	public static void postaviAmbulante() {
		Grad.getAmbulante().add(new Ambulanta(0, 0, 1));
		Grad.getAmbulante().add(new Ambulanta(dimenzija - 1, 0, 2));
		Grad.getAmbulante().add(new Ambulanta(0, dimenzija - 1, 3));
		Grad.getAmbulante().add(new Ambulanta(dimenzija - 1, dimenzija - 1, 4));
		for (Ambulanta a : Grad.getAmbulante())
			logStrings.add(LocalDateTime.now() + " Ambulanta id= " + a.getId() + " ima kapacitet=" + a.getKapacitet()
					+ " je postavljena na poziciju x= " + a.getX() + " y= " + a.getY() + "\n");

		mapa[0][0].getElementi().add(Grad.getAmbulante().get(0));
		mapa[dimenzija - 1][0].getElementi().add(Grad.getAmbulante().get(1));
		mapa[0][dimenzija - 1].getElementi().add(Grad.getAmbulante().get(2));
		mapa[dimenzija - 1][dimenzija - 1].getElementi().add(Grad.getAmbulante().get(3));
	}

	public static void postaviStanovnike() throws NumberFormatException {
		// int x = (int)
		// Math.round(ogranicenja[0]+(ogranicenja[1]-ogranicenja[0])*Math.random());
		// int y = (int)
		// Math.round(ogranicenja[2]+(ogranicenja[3]-ogranicenja[2])*Math.random());
		Collections.reverse(Grad.getStanovnici());
		for (Stanovnik s : Grad.getStanovnici()) {
			int i = 1;
			int brojac=0;
			s.postaviOgranicenjaZaStanovnika();
			while (i > 0) {
				brojac++;
				// s.setX((int) (dimenzija * Math.random()));
				// s.setY((int) (dimenzija * Math.random()));
				s.setX((int) Math.round(
						s.getOgranicenja()[0] + (s.getOgranicenja()[1] - s.getOgranicenja()[0]) * Math.random()));
				s.setY((int) Math.round(
						s.getOgranicenja()[2] + (s.getOgranicenja()[3] - s.getOgranicenja()[2]) * Math.random()));
				if (s.razmakIzmedjuStanovnika(s)) {
					i = 0;
				}
				if(brojac>10000)
					throw new NumberFormatException();
			}
			if (s instanceof Dijete) {
				logStrings.add(LocalDateTime.now() + " Dijete sa id osobe=" + s.getId()
						+ " je postavljana na lokaciju x=" + s.getX() + " y=" + s.getY() + "\n");

			} else if (s instanceof OdraslaOsoba) {
				logStrings.add(LocalDateTime.now() + " Odrasla osoba sa id osobe=" + s.getId()
						+ " je postavljana na lokaciju x=" + s.getX() + " y=" + s.getY() + "\n");
			} else if (s instanceof StarijaOsoba) {
				logStrings.add(LocalDateTime.now() + " Starija osoba sa id osobe=" + s.getId()
						+ " je postavljana na lokaciju x=" + s.getX() + " y=" + s.getY() + "\n");

			}
			mapa[s.getX()][s.getY()].getElementi().add(s);
		}
	}

	// public int getIdGrada() {
	// return idGrada;
	// }

	// public void setIdGrada(int idGrada) {
	// this.idGrada = idGrada;
	// }

	public static Stack<Stanovnik> getStekStanovnika() {
		return stekStanovnika;
	}

	public static void setStekStanovnika(Stack<Stanovnik> stekStanovnika) {
		Grad.stekStanovnika = stekStanovnika;
	}

	public static List<Ambulanta> getAmbulante() {
		return ambulante;
	}

	public static void setAmbulante(List<Ambulanta> ambulante) {
		Grad.ambulante = ambulante;
	}

	public static List<String> getLogStrings() {
		return logStrings;
	}

	public static void setLogStrings(List<String> logStrings) {
		Grad.logStrings = logStrings;
	}
	public static void dodajStanovnikeiAmbulanteIzMape() {
		stanovnici.clear();
		ambulantnaVozila.clear();
		for(int i = 0 ; i<Grad.getDimenzija();i++) {
			for(int j = 0 ; j<Grad.getDimenzija();j++) {
				for(Element e : mapa[i][j].getElementi()) {
					if(e instanceof Stanovnik){
						stanovnici.add((Stanovnik) e);
					}
					if(e instanceof AmbulantnoVozilo) {
						ambulantnaVozila.add((AmbulantnoVozilo) e);
					}
				}
			}
		}
	}

	public static String getPoruka() {
		return poruka;
	}

	public static void setPoruka(String poruka) {
		Grad.poruka = poruka;
	}
}
