package net.etfbl.pj2.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Polje implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4480017980263157519L;
	int x;
	int y;
	List<Element> elementi = new ArrayList<Element>();

	public Polje() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Polje(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "Polje [x=" + x + ", y=" + y + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Polje other = (Polje) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	public List<Element> getElementi() {
		return elementi;
	}

	public void setElementi(List<Element> elementi) {
		this.elementi = elementi;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		Polje polje = (Polje) super.clone();
		List<Element> kopija = new ArrayList<Element>();
		for (Element e : elementi) {
			kopija.add(new Element(e.getX(), e.getY()));
		}
		polje.setElementi(kopija);
		return super.clone();
	}

}
