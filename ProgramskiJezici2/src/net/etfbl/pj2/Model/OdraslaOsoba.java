package net.etfbl.pj2.Model;

public class OdraslaOsoba extends Stanovnik {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8365862095112775151L;

	public OdraslaOsoba() {
		super();
		// TODO Auto-generated constructor stub
		radijus = Math.round((float) Grad.getDimenzija() / 4);
	}

	public OdraslaOsoba(int id, String ime, String prezime, int godinaRodjenja, char pol, int idKuce) {
		super(id, ime, prezime, godinaRodjenja, pol, idKuce);
		// TODO Auto-generated constructor stub
	}
	// public static int radijus = Math.round((float)Grad.getDimenzija()/4);

}
