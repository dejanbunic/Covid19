package net.etfbl.pj2.Model;

public class Dijete extends Stanovnik {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2707227964660106305L;

	public Dijete() {
		super();
		// TODO Auto-generated constructor stub
		radijus = Grad.getDimenzija();
	}

	public Dijete(int id, String ime, String prezime, int godinaRodjenja, char pol, int idKuce) {
		super(id, ime, prezime, godinaRodjenja, pol, idKuce);
		// TODO Auto-generated constructor stub
	}

}
