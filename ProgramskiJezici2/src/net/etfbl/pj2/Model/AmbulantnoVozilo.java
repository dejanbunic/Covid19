package net.etfbl.pj2.Model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import net.etfbl.pj2.Utility.FileWatcher;

public class AmbulantnoVozilo extends Element {

	/**
	 * 
	 */
	private static final long serialVersionUID = -403951790084392709L;
	private String stranaKretanja = "GORE";
	private int idStanovnikaKojiSePrevozi = 0;
	private int idAmbulante;

	public void pomjeriAmbulantnoVozilo() {
		if (!stranaKretanja.equals("MIROVANJE")) {
			int x = this.getX();
			int y = this.getY();
			int xx = x;
			int yy = y;
			switch (stranaKretanja) {
			case "GORE":
				yy--;
				if (yy < 0)
					yy = 0; // da ne baca izuzetak map[3][-1]
				if (this.slobodnoMjestoZaAmbulantu(xx, yy))
					break;
				else {
					yy = y;
				}
			case "DOLE":
				yy++;
				if (yy > Grad.getDimenzija() - 1)
					yy = Grad.getDimenzija() - 1;
				if (this.slobodnoMjestoZaAmbulantu(xx, yy))
					break;
				else {
					yy = y;
				}
			case "LIJEVO":
				xx--;
				if (xx < 0)
					xx = 0;
				if (this.slobodnoMjestoZaAmbulantu(xx, yy))
					break;
				else {
					xx = x;
				}
			case "DESNO":
				xx++;
				if (xx > Grad.getDimenzija() - 1)
					xx = Grad.getDimenzija() - 1;
				if (this.slobodnoMjestoZaAmbulantu(xx, yy))
					break;
				else {
					xx = x;
				}
			}
			this.setX(xx);
			this.setY(yy);
			Grad.getLogStrings().clear();
			Grad.getLogStrings().add(LocalDateTime.now()+" Ambulantno vozilo se pomjerilo sa pozicije x= " + x + " y=" + y
					+ " na novu poziciju x=" + xx + " y= " + yy + "\n");
			Grad.dodajElementNaPoziciju(xx, yy, this);
			Grad.ukloniElementSaPozicije(x, y, this);
		}
	}

	public void pokupiZarazenogStanovnika(Stanovnik e) {

		idStanovnikaKojiSePrevozi = e.getId();
		//System.out.println("pozicija stanovnika u almbulanti x= " + e.getX() + " y= " + e.getY());
		
		
		odiDoStanovnika(e);
		odveziStanovnikaUAmbulantu(e);
		/*if (e.getStanje().equals("U AMBULANTNOM VOZILU")) {
			// e.izolacija=false;
			Ambulanta ambulanta = vratiAmbulantu(idAmbulante);

			if (ambulanta.getBrojZarazenih() < ambulanta.getKapacitet()) {
				if (x < ambulanta.getX()) {
					if (slobodnoMjestoZaAmbulantu(x + 1, y)) {
						this.stranaKretanja = "DESNO";
						e.setStranaKretanja("DESNO");
						e.setX(x + 1);
						e.setY(y);
						Grad.dodajElementNaPoziciju(x + 1, y, e);
						Grad.ukloniElementSaPozicije(x, y, e);
					}
				} else if (x > ambulanta.getX()) {
					if (slobodnoMjestoZaAmbulantu(x - 1, y)) {
						this.stranaKretanja = "LIJEVO";
						e.setStranaKretanja("LIJEVO");
						e.setX(x - 1);
						e.setY(y);
						Grad.dodajElementNaPoziciju(x - 1, y, e);
						Grad.ukloniElementSaPozicije(x, y, e);

					}
				} else if (y < ambulanta.getY()) {
					if (slobodnoMjestoZaAmbulantu(x, y + 1)) {
						this.stranaKretanja = "DOLE";
						e.setStranaKretanja("DOLE");
						e.setX(x);
						e.setY(y + 1);
						Grad.dodajElementNaPoziciju(x, y + 1, e);
						Grad.ukloniElementSaPozicije(x, y, e);
					}
				} else if (y > ambulanta.getY()) {
					if (slobodnoMjestoZaAmbulantu(x, y - 1)) {
						this.stranaKretanja = "GORE";
						e.setStranaKretanja("GORE");
						e.setX(x);
						e.setY(y - 1);
						Grad.dodajElementNaPoziciju(x, y - 1, e);
						Grad.ukloniElementSaPozicije(x, y, e);

					}
				} else {
					this.stranaKretanja = "MIROVANJE";
					e.setStanje("U AMBULANTI");
					ambulanta.getZarazeniStanovnici().add(e); // dodavanje zarazenog u ambulantu
					ambulanta.setBrojZarazenih(ambulanta.getBrojZarazenih() + 1);
					//System.out.println(ambulanta);
					FileWatcher.dodajZarazenog();
					idStanovnikaKojiSePrevozi = 0;
					//System.out.println("dodan stanovnik u ambulantu");
					Grad.setPoruka(Grad.getPoruka()+LocalDateTime.now()+" Stanovnik id="+e.getId()+" Ime="+e.getId()+" Prezime="+e.getPrezime()+" Pol="+e.getPol()+" Godiiste="+e.getGodinaRodjenja()+" sa pozicije x="+e.getX()+" y="+e.getY()+" dolazi u ambulantu id="+ambulanta.getId()+" na poziciji x="+ambulanta.getX()+" y="+ambulanta.getY()+" broj zarazenih="+ambulanta.getBrojZarazenih()+"\n");
				}
			}
		}*/
	}
	public void odveziStanovnikaUAmbulantu(Stanovnik e) {
		if (e.getStanje().equals("U AMBULANTNOM VOZILU")) {
			// e.izolacija=false;
			Ambulanta ambulanta = vratiAmbulantu(idAmbulante);

			if (ambulanta.getBrojZarazenih() < ambulanta.getKapacitet()) {
				
		
		int[] stanje = { 0, 0, 0, 0 }; // gore dole lijevo desno
		if (x < ambulanta.getX()) {
			if (slobodnoMjestoZaAmbulantu(x + 1, y)) {
				// this.stranaKretanja = "DESNO";

				stanje[3] = 1;
			}
		}
		if (x > ambulanta.getX() ) {
			if (slobodnoMjestoZaAmbulantu(x - 1, y)) {
				// this.stranaKretanja = "LIJEVO";
				stanje[2] = 1;
			}
		}
		if (y < ambulanta.getY() ) {
			if (slobodnoMjestoZaAmbulantu(x, y + 1)) {
				// this.stranaKretanja = "DOLE";
				stanje[1] = 1;
			}
		}
		if (y > ambulanta.getY() ) {
			if (slobodnoMjestoZaAmbulantu(x, y - 1)) {
				// this.stranaKretanja = "GORE";
				stanje[0] = 1;
			}
		} else {

			this.stranaKretanja = "MIROVANJE";
		}
		int max = 0;
		int pozicija = -1;
		for (int i = 0; i < 4; i++) {
			//System.out.println(stanje[i]);
			Random random = new Random();
			stanje[i] *= (random.nextInt(10) + 1);
			if (stanje[i] > max) {
				max = stanje[i];
				pozicija = i;
			}

		}
		if (pozicija == -1) {
			this.stranaKretanja = "MIROVANJE";
			e.setStanje("U AMBULANTI");
			ambulanta.getZarazeniStanovnici().add(e); // dodavanje zarazenog u ambulantu
			ambulanta.setBrojZarazenih(ambulanta.getBrojZarazenih() + 1);
			//System.out.println(ambulanta);
			FileWatcher.dodajZarazenog();
			idStanovnikaKojiSePrevozi = 0;
			//System.out.println("dodan stanovnik u ambulantu");
			Grad.setPoruka(Grad.getPoruka()+LocalDateTime.now()+" Stanovnik id="+e.getId()+" Ime="+e.getId()+" Prezime="+e.getPrezime()+" Pol="+e.getPol()+" Godiiste="+e.getGodinaRodjenja()+" sa pozicije x="+e.getX()+" y="+e.getY()+" dolazi u ambulantu id="+ambulanta.getId()+" na poziciji x="+ambulanta.getX()+" y="+ambulanta.getY()+" broj zarazenih="+ambulanta.getBrojZarazenih()+"\n");
		}
		if (pozicija == 0) {
			this.stranaKretanja = "GORE";
			e.setStranaKretanja("GORE");
			e.setX(x);
			e.setY(y - 1);
			Grad.dodajElementNaPoziciju(x, y - 1, e);
			Grad.ukloniElementSaPozicije(x, y, e);
		} else if (pozicija == 1) {
			this.stranaKretanja = "DOLE";
			e.setStranaKretanja("DOLE");
			e.setX(x);
			e.setY(y + 1);
			Grad.dodajElementNaPoziciju(x, y + 1, e);
			Grad.ukloniElementSaPozicije(x, y, e);
		} else if (pozicija == 2) {
			this.stranaKretanja = "LIJEVO";
			e.setStranaKretanja("LIJEVO");
			e.setX(x - 1);
			e.setY(y);
			Grad.dodajElementNaPoziciju(x - 1, y, e);
			Grad.ukloniElementSaPozicije(x, y, e);
		} else if (pozicija == 3) {
			this.stranaKretanja = "DESNO";
			e.setStranaKretanja("DESNO");
			e.setX(x + 1);
			e.setY(y);
			Grad.dodajElementNaPoziciju(x + 1, y, e);
			Grad.ukloniElementSaPozicije(x, y, e);
		}
			}
		}
	}
	@SuppressWarnings("null")
	public boolean slobodnoMjestoZaAmbulantu(int x, int y) {
		if (x > Grad.getDimenzija() - 1)
			x = Grad.getDimenzija() - 1;
		if (y > Grad.getDimenzija() - 1)
			y = Grad.getDimenzija() - 1;
		if (x < 0)
			x = 0;
		if (y < 0)
			y = 0;
		List<Element> elementiPolja = Grad.getMapa()[x][y].getElementi();
		if (elementiPolja != null || !elementiPolja.isEmpty()) {
			for (Element e : elementiPolja) {
				if (e instanceof Ambulanta)
					return true;
				if (e instanceof Kuca || e instanceof AmbulantnoVozilo) {
					return false;
				}

				if (e instanceof Stanovnik) {
					Stanovnik s = (Stanovnik) e;
					if (!s.getStanje().equals("U AMBULANTNOM VOZILU") || idStanovnikaKojiSePrevozi != s.getId()) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public String getStranaKretanja() {
		return stranaKretanja;
	}

	public void setStranaKretanja(String stranaKretanja) {
		this.stranaKretanja = stranaKretanja;
	}

	public int getIdStanovnikaKojiSePrevozi() {
		return idStanovnikaKojiSePrevozi;
	}

	public void setIdStanovnikaKojiSePrevozi(int idStanovnikaKojiSePrevozi) {
		this.idStanovnikaKojiSePrevozi = idStanovnikaKojiSePrevozi;
	}

	public int getIdAmbulante() {
		return idAmbulante;
	}

	public void setIdAmbulante(int idAmbulante) {
		this.idAmbulante = idAmbulante;
	}

	public Ambulanta vratiAmbulantu(int id) {
		// Ambulanta a = null;
		Ambulanta a = Grad.getAmbulante().get(0);
		//System.out.println(" id ambulante je idAmbulante=" + this.idAmbulante + "id vrati ambulantu je=" + id);
		for (Ambulanta ambulanta : Grad.getAmbulante()) {
			if (ambulanta.getId() == id) {
				a = ambulanta;
			}
		}
		if (a.getBrojZarazenih() >= a.getKapacitet()) {
			for (Ambulanta ambulanta : Grad.getAmbulante()) {
				if (ambulanta.getBrojZarazenih() < ambulanta.getKapacitet()) {
					a = ambulanta;
				}
			}
		}
		idAmbulante = a.getId();

		return a;
	}

	@Override
	public String toString() {
		return "AmbulantnoVozilo [stranaKretanja=" + stranaKretanja + ", idStanovnikaKojiSePrevozi="
				+ idStanovnikaKojiSePrevozi + ", idAmbulante=" + idAmbulante + "]";
	}

	public void odiDoStanovnika(Stanovnik e) {

		int[] stanje = { 0, 0, 0, 0 }; // gore dole lijevo desno
		if (x <= e.getX() - 1 && e.getStanje().equals("CEKA AMBULANTU")) {
			if (slobodnoMjestoZaAmbulantu(x + 1, y)) {
				// this.stranaKretanja = "DESNO";

				stanje[3] = 1;
			}
		}
		if (x >= e.getX() + 1 && e.getStanje().equals("CEKA AMBULANTU")) {
			if (slobodnoMjestoZaAmbulantu(x - 1, y)) {
				// this.stranaKretanja = "LIJEVO";
				stanje[2] = 1;
			}
		}
		if (y <= e.getY() - 1 && e.getStanje().equals("CEKA AMBULANTU")) {
			if (slobodnoMjestoZaAmbulantu(x, y + 1)) {
				// this.stranaKretanja = "DOLE";
				stanje[1] = 1;
			}
		}
		if (y >= e.getY() + 1 && e.getStanje().equals("CEKA AMBULANTU")) {
			if (slobodnoMjestoZaAmbulantu(x, y - 1)) {
				// this.stranaKretanja = "GORE";
				stanje[0] = 1;
			}
		} else {

			this.stranaKretanja = "MIROVANJE";
		}
		int max = 0;
		int pozicija = -1;
		for (int i = 0; i < 4; i++) {
			//System.out.println(stanje[i]);
			Random random = new Random();
			stanje[i] *= (random.nextInt(10) + 1);
			if (stanje[i] > max) {
				max = stanje[i];
				pozicija = i;
			}

		}
		if (pozicija == -1) {
			this.setStranaKretanja("MIROVANJE");
		}
		if (pozicija == 0) {
			this.setStranaKretanja("GORE");
		} else if (pozicija == 1) {
			this.setStranaKretanja("DOLE");
		} else if (pozicija == 2) {
			this.setStranaKretanja("LIJEVO");
		} else if (pozicija == 3) {
			this.setStranaKretanja("DESNO");
		}
	}

}
