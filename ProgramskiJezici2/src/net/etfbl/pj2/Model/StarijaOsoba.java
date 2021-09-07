package net.etfbl.pj2.Model;

public class StarijaOsoba extends Stanovnik {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7968869198123636423L;

	public StarijaOsoba() {
		super();
		// TODO Auto-generated constructor stub
		radijus = 3;
	}

	public StarijaOsoba(int id, String ime, String prezime, int godinaRodjenja, char pol, int idKuce) {
		super(id, ime, prezime, godinaRodjenja, pol, idKuce);
		// TODO Auto-generated constructor stub
	}

	// public static int radijus = 3;

}
