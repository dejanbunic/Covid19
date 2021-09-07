package net.etfbl.pj2.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import net.etfbl.pj2.Utility.FileWatcher;

public class Ambulanta extends Element {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2639923299589644690L;
	int kapacitet = (int) ((Math.round((float) Grad.getBrojStanovnika() / 10))
			+ Math.random() * (Math.round((float) Grad.getBrojStanovnika() / 20))) + 1;
	int brojZarazenih = 0;
	int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	List<Stanovnik> zarazeniStanovnici = new ArrayList<Stanovnik>();

	public List<Stanovnik> getZarazeniStanovnici() {
		return zarazeniStanovnici;
	}

	public void setZarazeniStanovnici(List<Stanovnik> zarazeniStanovnici) {
		this.zarazeniStanovnici = zarazeniStanovnici;
	}

	public Ambulanta() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Ambulanta(int kapacitet) {
		super();
		this.kapacitet = kapacitet;
	}

	public Ambulanta(int x, int y, int id) {
		super(x, y);
		this.id = id;
	}

	public int getBrojZarazenih() {
		return brojZarazenih;
	}

	public void setBrojZarazenih(int brojZarazenih) {
		this.brojZarazenih = brojZarazenih;
	}

	public int getKapacitet() {
		return kapacitet;
	}

	public void setKapacitet(int kapacitet) {
		this.kapacitet = kapacitet;
	}

	public void dodajStanovnikaUAmbulantu(Stanovnik s) {
		zarazeniStanovnici.add(s);
	}

	public void otpustiStanovnikaIzAmbulante(Stanovnik s) {
		zarazeniStanovnici.remove(s);
	}

	public boolean oporavakStanovnika(Stanovnik s) {
		s.getMjerenjaTemparature().add(s.getTemperatura());
		int n = s.getMjerenjaTemparature().size();
		if (n >= 3) {
			double aritmetickaSredina = ((s.getMjerenjaTemparature().get(n - 1) + s.getMjerenjaTemparature().get(n - 2)
					+ s.getMjerenjaTemparature().get(n - 3)) / 3);
			//System.out.println("aritmeticka sredina temparature" + aritmetickaSredina);
			if ((aritmetickaSredina) < 37) {
				return true;
			}
		}
		return false;
	}

	public void provjeriTempStanovnikaUAmbulanti() {
		Stanovnik stanovnik = null;
		for (Stanovnik s : zarazeniStanovnici) {
			if (oporavakStanovnika(s)) {
				id = s.getId();
				stanovnik = s;
				// zarazeniStanovnici.remove(s);
				FileWatcher.dodajIzlijecenog();
				s.setStanje("POVRATAK KUCI");
				s.izolacija = false;
				s.setStanjeZaraze("BIO ZARAZEN");
				Grad.setPoruka(Grad.getPoruka()+LocalDateTime.now()+" Stanovnik id="+s.getId()+" Ime="+s.getId()+" Prezime="+s.getPrezime()+" Pol="+s.getPol()+" Godiiste="+s.getGodinaRodjenja()+" sa pozicije x="+s.getX()+" y="+s.getY()+"\n"); 
				s.getMjerenjaTemparature().clear();
			}
		}
		if (stanovnik != null) {
			zarazeniStanovnici.remove(stanovnik);
			// Grad.getAmbulante().get(stanovnik.getId)
			brojZarazenih--;
		}
	}

	public void izmjeriTemperaturuStanovnicima() {
		for (Stanovnik s : zarazeniStanovnici) {
			s.getMjerenjaTemparature().add(s.getTemperatura());
		}
	}

	@Override
	public String toString() {
		return "Ambulanta [kapacitet=" + kapacitet + ", brojZarazenih=" + brojZarazenih + ", id=" + id
				+ ", zarazeniStanovnici=" + zarazeniStanovnici + "]";
	}

}
