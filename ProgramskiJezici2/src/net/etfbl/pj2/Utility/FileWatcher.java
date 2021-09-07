package net.etfbl.pj2.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

public class FileWatcher {
	private static int brojtrenutnoZarazenih;
	private static int ukupanBrojSlucajeva;
	private static int brojIzlijecenih;

	@SuppressWarnings("unchecked")
	public static void pokreni() {

		try {
			WatchService watcher = FileSystems.getDefault().newWatchService();
			File file = new File("src" + File.separator + "net" + File.separator + "etfbl" + File.separator + "pj2"
					+ File.separator + "Statistic");
			Path dir = Paths.get(file.getAbsolutePath());
			dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE,
					StandardWatchEventKinds.ENTRY_MODIFY);
			System.out.println("Watch Service registered for dir: " + dir.getFileName());

			for (;;) {
				WatchKey key;
				try {
					key = watcher.take();
				} catch (InterruptedException ex) {
					return;
				}

				for (WatchEvent<?> event : key.pollEvents()) {
					WatchEvent.Kind<?> kind = event.kind();
					WatchEvent<Path> ev = (WatchEvent<Path>) event;
					Path fileName = ev.context();
					System.out.println(kind.name() + ": " + fileName);
					if (fileName.toString().trim().endsWith(".txt")
							&& kind.equals(StandardWatchEventKinds.ENTRY_MODIFY)) {
						List<String> content = Files.readAllLines(dir.resolve(fileName));
						for (String s : content)
							System.out.println(s);
						System.out.println("========================");
					}
				}

				boolean valid = key.reset();
				if (!valid) {
					break;
				}
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	public static void pokupiPodatkeIzFajla() {
		Properties p = new Properties();
		try {
			InputStream ulaz = new FileInputStream("src" + File.separator + "net" + File.separator + "etfbl"
					+ File.separator + "pj2" + File.separator + "Statistic" + File.separator + "PodaciOZarazenima");
			p.load(ulaz);
			ukupanBrojSlucajeva = Integer.parseInt(p.getProperty("UKUPAN_BROJ_SLUCAJEVA"));
			brojtrenutnoZarazenih = Integer.parseInt(p.getProperty("BROJ_TRENUTNO_ZARAZENIH"));
			brojIzlijecenih = Integer.parseInt(p.getProperty("BROJ_IZLIJECENIH"));
			ulaz.close();
		} catch (IOException e) {
			// e.printStackTrace();
			FileLogger.log(Level.SEVERE, null, new Throwable("Greska prilikom kupljenja podataka iz fajla"));
		}
	}

	public static void upisiPodatkeUFajl(int brojZarazenih, int brojOporavljenih) {
		Properties p = new Properties();
		try {
			InputStream ulaz = new FileInputStream("src" + File.separator + "net" + File.separator + "etfbl"
					+ File.separator + "pj2" + File.separator + "Statistic" + File.separator + "PodaciOZarazenima");
			p.load(ulaz);
			p.setProperty("UKUPAN_BROJ_SLUCAJEVA", String.valueOf(brojZarazenih + brojOporavljenih));
			p.setProperty("BROJ_TRENUTNO_ZARAZENIH", String.valueOf(brojZarazenih));
			p.setProperty("BROJ_IZLIJECENIH", String.valueOf(brojOporavljenih));
			p.store(new FileOutputStream("src" + File.separator + "net" + File.separator + "etfbl" + File.separator
					+ "pj2" + File.separator + "Statistic" + File.separator + "PodaciOZarazenima"), null);
			ulaz.close();
		} catch (IOException e) {
			// e.printStackTrace();
			FileLogger.log(Level.SEVERE, null, new Throwable("Greska prilikom dodavanja podataka zarazenih u fajl"));
		}
	}

	public static void dodajZarazenog() {
		Properties p = new Properties();
		try {
			InputStream ulaz = new FileInputStream("src" + File.separator + "net" + File.separator + "etfbl"
					+ File.separator + "pj2" + File.separator + "Statistic" + File.separator + "PodaciOZarazenima");
			p.load(ulaz);
			p.setProperty("UKUPAN_BROJ_SLUCAJEVA", String.valueOf(ukupanBrojSlucajeva + 1));
			p.setProperty("BROJ_TRENUTNO_ZARAZENIH", String.valueOf(brojtrenutnoZarazenih + 1));
			p.setProperty("BROJ_IZLIJECENIH", String.valueOf(brojIzlijecenih));
			// System.out.println("usao u zarazene");
			// System.out.println("ukupan broj slucajeva"+(ukupanBrojSlucajeva+1));
			p.store(new FileOutputStream("src" + File.separator + "net" + File.separator + "etfbl" + File.separator
					+ "pj2" + File.separator + "Statistic" + File.separator + "PodaciOZarazenima"), null);
			ulaz.close();
		} catch (IOException e) {
			// e.printStackTrace();
			FileLogger.log(Level.SEVERE, null, new Throwable("Greska prilikom dodavanja zarazenog u fajl"));
		}
	}

	public static void dodajIzlijecenog() {
		Properties p = new Properties();
		try {
			InputStream ulaz = new FileInputStream("src" + File.separator + "net" + File.separator + "etfbl"
					+ File.separator + "pj2" + File.separator + "Statistic" + File.separator + "PodaciOZarazenima");
			p.load(ulaz);
			p.setProperty("UKUPAN_BROJ_SLUCAJEVA", String.valueOf(ukupanBrojSlucajeva));
			p.setProperty("BROJ_TRENUTNO_ZARAZENIH", String.valueOf(brojtrenutnoZarazenih - 1));
			p.setProperty("BROJ_IZLIJECENIH", String.valueOf(brojIzlijecenih + 1));
			p.store(new FileOutputStream("src" + File.separator + "net" + File.separator + "etfbl" + File.separator
					+ "pj2" + File.separator + "Statistic" + File.separator + "PodaciOZarazenima"), null);
			ulaz.close();
		} catch (IOException e) {
			// e.printStackTrace();
			FileLogger.log(Level.SEVERE, null, new Throwable("Greska prilikom dodavanja izlijecenog u fajl"));
		}
	}

	public static int getBrojtrenutnoZarazenih() {
		return brojtrenutnoZarazenih;
	}

	public static void setBrojtrenutnoZarazenih(int brojtrenutnoZarazenih) {
		FileWatcher.brojtrenutnoZarazenih = brojtrenutnoZarazenih;
	}

	public static int getUkupanBrojSlucajeva() {
		return ukupanBrojSlucajeva;
	}

	public static void setUkupanBrojSlucajeva(int ukupanBrojSlucajeva) {
		FileWatcher.ukupanBrojSlucajeva = ukupanBrojSlucajeva;
	}

	public static int getBrojIzlijecenih() {
		return brojIzlijecenih;
	}

	public static void setBrojIzlijecenih(int brojIzlijecenih) {
		FileWatcher.brojIzlijecenih = brojIzlijecenih;
	}

}
