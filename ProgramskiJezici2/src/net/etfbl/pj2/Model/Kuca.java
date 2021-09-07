package net.etfbl.pj2.Model;

public class Kuca extends Element {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1764701506888642966L;
	int id;
	int brojUkucana = 0;

	public Kuca() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Kuca(int id, int brojUkucana) {
		super();
		this.id = id;
		this.brojUkucana = brojUkucana;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBrojUkucana() {
		return brojUkucana;
	}

	public void setBrojUkucana(int brojUkucana) {
		this.brojUkucana = brojUkucana;
	}

	@Override
	public String toString() {
		return "Kuca [id=" + id + ", brojUkucana=" + brojUkucana + "]";
	}

	public String kucaIspis() {
		return "id=" + id;
	}
}
