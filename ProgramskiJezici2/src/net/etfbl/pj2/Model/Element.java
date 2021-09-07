package net.etfbl.pj2.Model;

import java.io.Serializable;

public class Element implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7157598027924530114L;
	int x;
	int y;

	public Element() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Element(int x, int y) {
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
		Element other = (Element) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	public int putnaUdaljenostIzmedjuDvaElementa(Element e) {
		int putnaUdaljenost = 0;
		if (this.getX() <= e.getX())
			putnaUdaljenost += e.getX() - this.getY();
		else {
			putnaUdaljenost += this.getX() - e.getX();
		}
		if (this.getY() <= e.getY())
			putnaUdaljenost += e.getY() - this.getY();
		else {
			putnaUdaljenost += this.getY() - e.getY();
		}
		return putnaUdaljenost;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		Element element = (Element) super.clone();
		return element;
	}

}
