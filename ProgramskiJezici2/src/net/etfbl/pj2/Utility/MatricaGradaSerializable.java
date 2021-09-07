package net.etfbl.pj2.Utility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.etfbl.pj2.Model.Grad;
import net.etfbl.pj2.Model.Polje;
import net.etfbl.pj2.Model.Stanovnik;

public class MatricaGradaSerializable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1472787591351084964L;
	Polje[][] mapa = new Polje[Grad.getDimenzija()][Grad.getDimenzija()];
	private  List<Stanovnik> stanovnici = new ArrayList<Stanovnik>();
	public MatricaGradaSerializable(Polje[][] mapa) {
		// TODO Auto-generated constructor stub
		mapa = Grad.getMapa();
		for (int i = 0; i < Grad.getDimenzija(); i++) {
			for (int j = 0; j < Grad.getDimenzija(); j++) {
				this.mapa[i][j] = mapa[i][j];
			}
			stanovnici = Grad.getStanovnici();
		}
	}

	public Polje[][] getMapa() {
		return mapa;
	}

	public void setMapa(Polje[][] mapa) {
		this.mapa = mapa;
	}

	public List<Stanovnik> getStanovnici() {
		return stanovnici;
	}

	public void setStanovnici(List<Stanovnik> stanovnici) {
		this.stanovnici = stanovnici;
	}

}
