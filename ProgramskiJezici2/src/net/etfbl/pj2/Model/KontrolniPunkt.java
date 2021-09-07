package net.etfbl.pj2.Model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class KontrolniPunkt extends Element {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6344709423731176642L;

	public void ocitajTempStanovnikaURadijusuDjelovanja() {

		int xOd = this.getX() - 2;
		int xDo = this.getX() + 2;
		if (xOd < 0)
			xOd = 0;
		if (xDo > Grad.getDimenzija() - 1)
			xDo = Grad.getDimenzija() - 1;
		int yOd = this.getY() - 2;
		int yDo = this.getY() + 2;
		if (yOd < 0)
			yOd = 0;
		if (yDo > Grad.getDimenzija() - 1)
			yDo = Grad.getDimenzija() - 1;
		for (int i = xOd; i <= xDo; i++) {
			for (int j = yOd; j <= yDo; j++) {
				for (Element e : Grad.getMapa()[i][j].getElementi()) {
					if (e instanceof Stanovnik) {
						Stanovnik s = (Stanovnik) e;
						if (!s.izolacija) {
							s.getTemperatura();
							if (s.getTemperatura() > 37 && !s.getStanje().equals("CEKA AMBULANTU")) {
								Alert alert = new Alert(null, "Pozicija: x= " + s.getX() + " y= " + s.getY()
										+ "\n Id kuce je: " + s.getIdKuce(), ButtonType.OK);
								alert.show();
								s.izolacija = true;
								Grad.getStekStanovnika().push(s);
								// System.out.println("pusovano na stek =====================");
								// for(Stanovnik stanovnik:Grad.getStanovnici()) {
								// if(s.getId()!=stanovnik.getId() && s.getIdKuce()==stanovnik.getIdKuce()) {
								s.stanje = "CEKA AMBULANTU";
								s.setStanjeZaraze("ZARAZEN");
								// }
								// }
							}
						}
					}
				}
			}
		}
	}

}
