package net.etfbl.pj2.Model;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Stanovnik extends Element {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4723820681216315685L;
	int id;
	String ime;
	String prezime;
	int godinaRodjenja;
	char pol;
	int idKuce;
	double temperatura = 37;
	int radijus;
	boolean izolacija = false;
	String stanje = "NORMALNO"; // NORMALNO, KUCA, CEKA AMBULANTU, U AMBULANTNOM VOZILU, POVRATAK KUCI
	String  stanjeZaraze = "NIJE ZARAZEN"; // ZARAZEN, NIJE ZARAZEN i BIO ZARAZEN
	int []ogranicenja = {0,0,0,0}; // xMin xMax yMin yMax

	public String getStanjeZaraze() {
		return stanjeZaraze;
	}

	public void setStanjeZaraze(String stanjeZaraze) {
		this.stanjeZaraze = stanjeZaraze;
	}
	List<Double> mjerenjaTemparature = new ArrayList<Double>();

	private String stranaKretanja;


	public Stanovnik() {
		super();
	}
	public Stanovnik(int id, String ime, String prezime, int godinaRodjenja, char pol, int idKuce) {
		super();
		//tajmer.scheduleAtFixedRate(zadatak, 0, 1000);
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.godinaRodjenja = godinaRodjenja;
		this.pol = pol;
		this.idKuce = idKuce;
	}

	public void vratiSeKuci() {
		Kuca kuca = null;
		for (Kuca k : Grad.getKuce()) {
			if (k.getId() == this.idKuce) {
				kuca = k;
			}
		}
		Random random = new Random();
		int temp = random.nextInt(4);
		if (temp == 0) {
			if (x < kuca.getX()) {
				if (slobodnoMjestoZaStanovnika(x + 1, y))
					this.setStranaKretanja("DESNO");
				else {
					if (slobodnoMjestoZaStanovnika(x, y + 1)) {
						this.setStranaKretanja("DOLE");
					} else if (slobodnoMjestoZaStanovnika(x, y - 1)) {
						this.setStranaKretanja("GORE");
					} else {
						this.setStranaKretanja("LIJEVO");
					}
				}
			} else if (x > kuca.getX()) {
				if (slobodnoMjestoZaStanovnika(x - 1, y))
					this.setStranaKretanja("LIJEVO");
				else {
					if (slobodnoMjestoZaStanovnika(x, y + 1)) {
						this.setStranaKretanja("DOLE");
					} else if (slobodnoMjestoZaStanovnika(x, y - 1)) {
						this.setStranaKretanja("GORE");
					} else {
						this.setStranaKretanja("DESNO");
					}
				}
			} else if (y < kuca.getY()) {
				if (slobodnoMjestoZaStanovnika(x, y + 1))
					this.setStranaKretanja("DOLE");
				else {
					if (slobodnoMjestoZaStanovnika(x + 1, y))
						this.setStranaKretanja("DESNO");
					else if (slobodnoMjestoZaStanovnika(x - 1, y))
						this.setStranaKretanja("LIJEVO");
					else {
						this.setStranaKretanja("GORE");
					}
				}
			} else if (y > kuca.getY()) {
				if (slobodnoMjestoZaStanovnika(x, y - 1))
					this.setStranaKretanja("GORE");
				else {
					if (slobodnoMjestoZaStanovnika(x + 1, y))
						this.setStranaKretanja("DESNO");
					else if (slobodnoMjestoZaStanovnika(x - 1, y))
						this.setStranaKretanja("LIJEVO");
					else {
						this.setStranaKretanja("DOLE");
					}
				}
			} else {
				for(Kuca kuca1 : Grad.getKuce()) {
					if(kuca1.getId()==idKuce && !stanje.equals("KUCA")) {
						kuca1.setBrojUkucana(kuca1.getBrojUkucana()+1);
					}
				}
				this.setStanje("KUCA");
				this.izolacija = true;
			}

		}
		if (temp == 1) {
			if (x > kuca.getX()) {
				if (slobodnoMjestoZaStanovnika(x - 1, y))
					this.setStranaKretanja("LIJEVO");
				else {
					if (slobodnoMjestoZaStanovnika(x, y + 1)) {
						this.setStranaKretanja("DOLE");
					} else if (slobodnoMjestoZaStanovnika(x, y - 1)) {
						this.setStranaKretanja("GORE");
					} else {
						this.setStranaKretanja("DESNO");
					}
				}
			} else if (y < kuca.getY()) {
				if (slobodnoMjestoZaStanovnika(x, y + 1))
					this.setStranaKretanja("DOLE");
				else {
					if (slobodnoMjestoZaStanovnika(x + 1, y))
						this.setStranaKretanja("DESNO");
					else if (slobodnoMjestoZaStanovnika(x - 1, y))
						this.setStranaKretanja("LIJEVO");
					else {
						this.setStranaKretanja("GORE");
					}
				}
			} else if (y > kuca.getY()) {
				if (slobodnoMjestoZaStanovnika(x, y - 1))
					this.setStranaKretanja("GORE");
				else {
					if (slobodnoMjestoZaStanovnika(x + 1, y))
						this.setStranaKretanja("DESNO");
					else if (slobodnoMjestoZaStanovnika(x - 1, y))
						this.setStranaKretanja("LIJEVO");
					else {
						this.setStranaKretanja("DOLE");
					}
				}
			} else if (x < kuca.getX()) {
				if (slobodnoMjestoZaStanovnika(x + 1, y))
					this.setStranaKretanja("DESNO");
				else {
					if (slobodnoMjestoZaStanovnika(x, y + 1)) {
						this.setStranaKretanja("DOLE");
					} else if (slobodnoMjestoZaStanovnika(x, y - 1)) {
						this.setStranaKretanja("GORE");
					} else {
						this.setStranaKretanja("LIJEVO");
					}
				}
			} else {
				for(Kuca kuca1 : Grad.getKuce()) {
					if(kuca1.getId()==idKuce && !stanje.equals("KUCA")) {
						kuca1.setBrojUkucana(kuca1.getBrojUkucana()+1);
					}
				}
				this.setStanje("KUCA");
				this.izolacija = true;
			}

		}
		if (temp == 2) {
			if (y < kuca.getY()) {
				if (slobodnoMjestoZaStanovnika(x, y + 1))
					this.setStranaKretanja("DOLE");
				else {
					if (slobodnoMjestoZaStanovnika(x + 1, y))
						this.setStranaKretanja("DESNO");
					else if (slobodnoMjestoZaStanovnika(x - 1, y))
						this.setStranaKretanja("LIJEVO");
					else {
						this.setStranaKretanja("GORE");
					}
				}
			} else if (y > kuca.getY()) {
				if (slobodnoMjestoZaStanovnika(x, y - 1))
					this.setStranaKretanja("GORE");
				else {
					if (slobodnoMjestoZaStanovnika(x + 1, y))
						this.setStranaKretanja("DESNO");
					else if (slobodnoMjestoZaStanovnika(x - 1, y))
						this.setStranaKretanja("LIJEVO");
					else {
						this.setStranaKretanja("DOLE");
					}
				}
			} else if (x < kuca.getX()) {
				if (slobodnoMjestoZaStanovnika(x + 1, y))
					this.setStranaKretanja("DESNO");
				else {
					if (slobodnoMjestoZaStanovnika(x, y + 1)) {
						this.setStranaKretanja("DOLE");
					} else if (slobodnoMjestoZaStanovnika(x, y - 1)) {
						this.setStranaKretanja("GORE");
					} else {
						this.setStranaKretanja("LIJEVO");
					}
				}
			} else if (x > kuca.getX()) {
				if (slobodnoMjestoZaStanovnika(x - 1, y))
					this.setStranaKretanja("LIJEVO");
				else {
					if (slobodnoMjestoZaStanovnika(x, y + 1)) {
						this.setStranaKretanja("DOLE");
					} else if (slobodnoMjestoZaStanovnika(x, y - 1)) {
						this.setStranaKretanja("GORE");
					} else {
						this.setStranaKretanja("DESNO");
					}
				}
			} else {
				for(Kuca kuca1 : Grad.getKuce()) {
					if(kuca1.getId()==idKuce && !stanje.equals("KUCA")) {
						kuca1.setBrojUkucana(kuca1.getBrojUkucana()+1);
					}
				}
				this.setStanje("KUCA");
				this.izolacija = true;
			}

		}
		if (temp == 3) {
			if (y > kuca.getY()) {
				if (slobodnoMjestoZaStanovnika(x, y - 1))
					this.setStranaKretanja("GORE");
				else {
					if (slobodnoMjestoZaStanovnika(x + 1, y))
						this.setStranaKretanja("DESNO");
					else if (slobodnoMjestoZaStanovnika(x - 1, y))
						this.setStranaKretanja("LIJEVO");
					else {
						this.setStranaKretanja("DOLE");
					}
				}
			} else if (x < kuca.getX()) {
				if (slobodnoMjestoZaStanovnika(x + 1, y))
					this.setStranaKretanja("DESNO");
				else {
					if (slobodnoMjestoZaStanovnika(x, y + 1)) {
						this.setStranaKretanja("DOLE");
					} else if (slobodnoMjestoZaStanovnika(x, y - 1)) {
						this.setStranaKretanja("GORE");
					} else {
						this.setStranaKretanja("LIJEVO");
					}
				}
			} else if (x > kuca.getX()) {
				if (slobodnoMjestoZaStanovnika(x - 1, y))
					this.setStranaKretanja("LIJEVO");
				else {
					if (slobodnoMjestoZaStanovnika(x, y + 1)) {
						this.setStranaKretanja("DOLE");
					} else if (slobodnoMjestoZaStanovnika(x, y - 1)) {
						this.setStranaKretanja("GORE");
					} else {
						this.setStranaKretanja("DESNO");
					}
				}
			} else if (y < kuca.getY()) {
				if (slobodnoMjestoZaStanovnika(x, y + 1))
					this.setStranaKretanja("DOLE");
				else {
					if (slobodnoMjestoZaStanovnika(x + 1, y))
						this.setStranaKretanja("DESNO");
					else if (slobodnoMjestoZaStanovnika(x - 1, y))
						this.setStranaKretanja("LIJEVO");
					else {
						this.setStranaKretanja("GORE");
					}
				}
			} else {
				
				for(Kuca kuca1 : Grad.getKuce()) {
					if(kuca1.getId()==idKuce && !stanje.equals("KUCA")) {
						kuca1.setBrojUkucana(kuca1.getBrojUkucana()+1);
					}
				}
				this.setStanje("KUCA");
				this.izolacija = true;

			}

		}
		//vratiSeKuciUpdate();
	}

	public void pomjeriStanovnika() {
		if (!this.izolacija) {
			int x = this.getX();
			int y = this.getY();
			int xx = x;
			int yy = y;
			switch (stranaKretanja) {
			case "GORE":
				yy--;
				if (yy < 0)
					yy = 0; // da ne baca izuzetak map[3][-1]
				if (this.slobodnoMjestoZaStanovnika(xx, yy))
					break;
				else {
					yy = y;
				}
			case "DOLE":
				yy++;
				if (yy > Grad.getDimenzija() - 1)
					yy = Grad.getDimenzija() - 1;
				if (this.slobodnoMjestoZaStanovnika(xx, yy))
					break;
				else {
					yy = y;
				}
			case "LIJEVO":
				xx--;
				if (xx < 0)
					xx = 0;
				if (this.slobodnoMjestoZaStanovnika(xx, yy))
					break;
				else {
					xx = x;
				}
			case "DESNO":
				xx++;
				if (xx > Grad.getDimenzija() - 1)
					xx = Grad.getDimenzija() - 1;
				if (this.slobodnoMjestoZaStanovnika(xx, yy))
					break;
				else {
					xx = x;
				}
			}
			this.setX(xx);
			this.setY(yy);
			if (razmakIzmedjuStanovnika(this)) {
				Grad.getLogStrings().clear();
				Grad.getLogStrings().add("Stanovnik id= "+id+" ime= "+ime+" prezime= "+prezime+" godina rodjenja= "+godinaRodjenja+
						"prelazi sa x= "+x+" y= "+y+" na x="+xx+" y= "+y+"\n");
				Grad.dodajElementNaPoziciju(xx, yy, this);
				Grad.ukloniElementSaPozicije(x, y, this);
			} else {
				Grad.getLogStrings().clear();
				Grad.getLogStrings().add("Stanovnik id= "+id+" ime= "+ime+" prezime= "+prezime+" godina rodjenja= "+godinaRodjenja+
						"ostaje na sa x= "+x+" y= "+y+"\n");
				this.setX(x);
				this.setY(y);
			}
		}

	}

	public String prikazImenaStanovnika() {
		// return ime+id+" "+x+" "+y+" "+stranaKretanja;
		return stranaKretanja + "\n idK:" + this.idKuce + "\n";
	}

	public boolean razmakIzmedjuStanovnika(Stanovnik s) {
		for (Stanovnik stanovnik : Grad.getStanovnici()) {
			if (stanovnik.getId() != s.getId()) {
				if (stanovnik.getX() >= s.getX() - 3 && stanovnik.getX() <= s.getX() + 3
						&& stanovnik.getY() >= s.getY() - 3 && stanovnik.getY() <= s.getY() + 3) {
					if(vratiTipStanovnika(s).equals("STARIJA OSOBA") || vratiTipStanovnika(s).equals("ODRASLA OSOBA") )
					{
						if(vratiTipStanovnika(stanovnik).equals("STARIJA OSOBA") || vratiTipStanovnika(stanovnik).equals("ODRASLA OSOBA") ) 
						{
							if (!stanovnik.getStanje().equals("KUCA"))
								return false;
						}
					
					}

			 // ovdje bi isao kod ako se dijete ne moze da bude na istom rastojanju kao i odrasli
			}
		}
	 }
		return true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public int getGodinaRodjenja() {
		return godinaRodjenja;
	}

	public void setGodinaRodjenja(int godinaRodjenja) {
		this.godinaRodjenja = godinaRodjenja;
	}

	public char getPol() {
		return pol;
	}

	public void setPol(char pol) {
		this.pol = pol;
	}

	public double getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(double temperatura) {
		this.temperatura = temperatura;
	}

	public int getIdKuce() {
		return idKuce;
	}

	public void setIdKuce(int idKuce) {
		this.idKuce = idKuce;
	}

	@Override
	public String toString() {
		return "Stanovnik [id=" + id + ", ime=" + ime + ", prezime=" + prezime + ", godinaRodjenja=" + godinaRodjenja
				+ ", pol=" + pol + ", idKuce=" + idKuce + ", temperatura=" + temperatura + "]";
	}

	public int getRadijus() {
		return radijus;
	}

	public void setRadijus(int radijus) {
		this.radijus = radijus;
	}

	public String getStranaKretanja() {
		return stranaKretanja;
	}

	public void setStranaKretanja(String stranaKretanja) {
		this.stranaKretanja = stranaKretanja;
	}

	public boolean isIzolacija() {
		return izolacija;
	}

	public void setIzolacija(boolean izolacija) {
		this.izolacija = izolacija;
	}

	public List<Double> getMjerenjaTemparature() {
		return mjerenjaTemparature;
	}

	public void setMjerenjaTemparature(List<Double> mjerenjaTemparature) {
		this.mjerenjaTemparature = mjerenjaTemparature;
	}

	@SuppressWarnings("null")
	public boolean slobodnoMjestoZaStanovnika(int x, int y) {
		//if(x>= Grad.getDimenzija() || y>=Grad.getDimenzija() || x<0 || y<0)
			//return false;
		//int []ogranicenja = {0,0,0,0}; // xMin xMax yMin yMax
		if(x<0 || x> (Grad.getDimenzija()-1) || y<0 || y> (Grad.getDimenzija()-1))
			return false;
		if((x<ogranicenja[0] || x>ogranicenja[1] || y<ogranicenja[2] || y>ogranicenja[3] ) && !stanje.equals("POVRATAK KUCI"))
			return false;
		List<Element> elementiPolja = Grad.getMapa()[x][y].getElementi();
		if (elementiPolja != null || !elementiPolja.isEmpty()) {
			for (Element e : elementiPolja) {
				if (e instanceof Kuca) {
					Kuca kuca = (Kuca) e;
					if (this.getIdKuce() != kuca.getId()) {
						return false;
					}
					else if(vratiTipStanovnika(this).equals("DIJETE")) {
						if(e instanceof Kuca) {
							Kuca kuca1 = (Kuca) e;
							if(kuca1.getBrojUkucana()==0 && kuca1.getId()==this.getId())
								return false;
						}
					}
					
				//	if(kuca.getBrojUkucana()>0 && kuca.getId()==this.getIdKuce() && !stanje.equals("KUCA")) {
					//	kuca.setBrojUkucana(kuca.getBrojUkucana()+1);
						//this.setStanje("KUCA");
					//}else 
					if (kuca.getBrojUkucana()==0 && kuca.getId()==this.getIdKuce()){
						if(vratiTipStanovnika(this).equals("ODRASLA OSOBA") || vratiTipStanovnika(this).equals("STARIJA OSOBA"))
							//kuca.setBrojUkucana(kuca.getBrojUkucana()+1);
							return true;
						else {
							return false;
						}
					}
				} else if (e instanceof AmbulantnoVozilo) {
					return false;
				}
				
				
			}
		}
		elementiPolja = Grad.getMapa()[this.x][this.y].getElementi();
		if (elementiPolja != null || !elementiPolja.isEmpty()) {
			for (Element e : elementiPolja) {
				if(e instanceof Kuca &&  "KUCA".equals(this.getStanje())) { // dodan drugi uslov u slucaju da stanovnik predje samo preko kuce
					Kuca kuca = (Kuca) e;
					kuca.setBrojUkucana(kuca.getBrojUkucana()-1);
				}
			}
			
		}
		return true;
	}

	public String getStanje() {
		return stanje;
	}

	public void setStanje(String stanje) {
		this.stanje = stanje;
	}

	
	public void udjiUAmbulantnoVozilo() {
		int xOd = x - 1;
		int xDo = x + 1;
		if (xOd < 0)
			xOd = 0;
		if (xDo > Grad.getDimenzija() - 1)
			xDo = Grad.getDimenzija() - 1;
		int yOd = y - 1;
		int yDo = y + 1;
		if (yOd < 0)
			yOd = 0;
		if (yDo > Grad.getDimenzija() - 1)
			yDo = Grad.getDimenzija() - 1;
		int xx = x;
		int yy = y;
		AmbulantnoVozilo av = null;
		for (int i = xOd; i <= xDo; i++) {
			for (int j = yOd; j <= yDo; j++) {
				for (Element e : Grad.getMapa()[i][j].getElementi()) {
					if (e instanceof AmbulantnoVozilo) {
						av = (AmbulantnoVozilo) e;
						if (av.getIdStanovnikaKojiSePrevozi() == id) {
							xx = x;
							yy = y;
							x = i;
							y = j;
							Grad.setPoruka(Grad.getPoruka()+LocalDateTime.now() + " Id osoboe=" + id
							+ " sa lokacije x=" + xx + " y=" + yy + " ulazi u ambulanto vozilo na lokaciji x="+x+" y="+y+" \n");
							av.setStranaKretanja("MIROVANJE");
						}
					}
				}
			}
		}
		if (av != null) {
			Grad.dodajElementNaPoziciju(x, y, this);
			Grad.ukloniElementSaPozicije(xx, yy, this);
			this.stanje = "U AMBULANTNOM VOZILU";
		}
	}
	public static Stanovnik vratiStanovnikaNaOsnovuId(int id) {
		for(Stanovnik s:Grad.getStanovnici()) {
			if(s.getId()==id)
				return s;
		}
		return null;
	}
	public void  postaviTempStanovika() {
		Random random = new Random();

		DecimalFormat df = new DecimalFormat("#.##");
		int procenat = random.nextInt(10);
		if (procenat < 7)
			temperatura = Double.valueOf(df.format(35 + random.nextDouble() * 2));
		else {
			temperatura = Double.valueOf(df.format(37 + random.nextDouble() * 3));
		}
	}
	public  String vratiTipStanovnika(Stanovnik s) {
		int godina = LocalDateTime.now().getYear();
		if((godina-s.godinaRodjenja)<18) {
			return "DIJETE";
		}
		else if((godina- s.godinaRodjenja)<65) {
			return "STARIJA OSOBA";
		}
		return "ODRASLA OSOBA";
	}

	public int[] getOgranicenja() {
		return ogranicenja;
	}

	public void setOgranicenja(int[] ogranicenja) {
		this.ogranicenja = ogranicenja;
	}
	public void postaviOgranicenjaZaStanovnika() {
		int godina = LocalDateTime.now().getYear();
		if((godina-godinaRodjenja)<18) {
			ogranicenja[0]=0;
			ogranicenja[1]=Grad.getDimenzija()-1;
			ogranicenja[2]=0;
			ogranicenja[3]=Grad.getDimenzija()-1;
		}
		else {
			Kuca kuca = new Kuca();
			for(Kuca k:Grad.getKuce()) {
				if(k.getId()==idKuce) {
					kuca = k;
				}	
			}
			int x = kuca.getX();
			int y = kuca.getY();
			ogranicenja[0] = x - radijus;
			ogranicenja[1] = x + radijus;
			ogranicenja[2] = y - radijus;
			ogranicenja[3] = y + radijus;
			
			if((x-radijus)<0) {
				ogranicenja[0] = 0;
				ogranicenja[1]-=(x-radijus);
			}
			if((x+radijus)>(Grad.getDimenzija()-1)) {
				ogranicenja[1] = Grad.getDimenzija()-1;
				ogranicenja[0] -=(x+radijus)-(Grad.getDimenzija()-1);
			}
			if((y - radijus) < 0){
				ogranicenja[2] = 0;
				ogranicenja[3]-=(y-radijus);
			}
			if((y+radijus)>(Grad.getDimenzija()-1)) {
				ogranicenja[2] = Grad.getDimenzija()-1;
				ogranicenja[3] -=(y+radijus)-(Grad.getDimenzija()-1);
			}
		}
		
	}
	
}
