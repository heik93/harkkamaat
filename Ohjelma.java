import java.util.*;
import java.lang.*;
import java.io.*;

public class Ohjelma {
	static final int ASIAKKAITA = 10;
	static final int MATKOJA = 10;

	static int alkupvm_year;
	static int alkupvm_month;
	static int alkupvm_day;

	static int loppupvm_year;
	static int loppupvm_month;
	static int loppupvm_day;

	static ArrayList<Asiakas> asiakkaat;
	static ArrayList<Matka> matkat;
	static ArrayList<Varaus> varaukset;
	
	static Scanner scanner = new Scanner(System.in);

	static Asiakas kirjautunut;

	static boolean loop = true;

	public static void main(String[] args) {
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		
		alkupvm_year = today.get(Calendar.YEAR);
		alkupvm_month = today.get(Calendar.MONTH);
		alkupvm_day = today.get(Calendar.DAY_OF_MONTH);
		
		today.add(Calendar.DAY_OF_MONTH, 30);
		
		loppupvm_year = today.get(Calendar.YEAR);
		loppupvm_month = today.get(Calendar.MONTH);
		loppupvm_day = today.get(Calendar.DAY_OF_MONTH);
		
		asiakkaat = new ArrayList<Asiakas>();
		matkat = new ArrayList<Matka>();
		varaukset = new ArrayList<Varaus>();

		lueDatat();

		kirjautunut = null;

		if (asiakkaat.size() == 0) {
			generoiAsiakkaat();
		}

		if (matkat.size() == 0) {
			generoiMatkat();
		}

		kirjoitaAsiakkaat();
		kirjoitaMatkat();
		kirjoitaVaraukset();

		while (loop) {
			menuLoop();
		}
	}

	public static void menuLoop() {
		System.out.println("===================================");
		System.out.println("=== Lentobussivarausjärjestelmä ===");
		System.out.println("===================================");
		System.out.println();
		System.out.println("1. Asiakkaat");
		System.out.println("2. Matkat");
		System.out.println("0. Quit");

		String input = scanner.nextLine();

		switch (input) {
		case "1":
			asiakasMenu();
			break;
		case "2":
			matkaMenu();
			break;
		case "0":
			loop = false;
			break;
		default:
			break;
		}
		scanner.reset();
	}

	public static void asiakasMenu() {
		boolean asiakasLoop = true;

		while (asiakasLoop) {
			System.out.println("=== Asiakasmenu ===");
			System.out.println("1. Luo asiakas");
			System.out.println("2. Kirjaudu");
			System.out.println("3. Poista asiakas");
			System.out.println("0. Palaa");

			String input = scanner.nextLine();

			switch (input) {
			case "1":
				luoAsiakasMenu();
				break;
			case "2":
				kirjauduMenu();
				break;
			case "3":
				poistaAsiakasMenu();
				break;
			case "0":
				return;
			default:
				break;
			}
		}
		scanner.reset();
	}
	
	public static void luoAsiakasMenu() {
		boolean luoAsiakasLoop = true;
		
		while (luoAsiakasLoop) {
			System.out.println("Luo asiakas");
			System.out.println("Anna nimi:");
			String nimi = scanner.nextLine();
			
			if (nimi.equals("")) {
				luoAsiakasLoop = false;
				break;
			}
			
			int id = annaVapaaid();
			
			Asiakas asiakas = new Asiakas(nimi, id);
			asiakkaat.add(asiakas);
			kirjoitaAsiakkaat();
			System.out.println("Asiakas " + id + " luotu");
			luoAsiakasLoop = false;
		}
		
		scanner.reset();
	}
	
	public static void kirjauduMenu() {
		boolean kirjauduLoop = true;
		
		while (kirjauduLoop) {
			System.out.println("Kirjaudu");
			System.out.println("Anna asiakkaan id:");
			
			String id = scanner.nextLine();
			
			if (id.equals("")) {
				kirjauduLoop = false;
				break;
			}
			int id_int = 0;
			
			try {
				id_int = Integer.parseInt(id);
			} catch (NumberFormatException e) {
				System.out.println("Virheellinen syöte");
				continue;
			}
			
			for (Asiakas a : asiakkaat) {
				if (a.annaId() == id_int) {
					kirjautunut = a;
					System.out.println("Kirjautuminen asiakkaana " + id_int + " onnistui");
					kirjauduLoop = false;
					kirjautunutMenu();
					break;
				}
			}
			
			if (kirjautunut == null) {
				System.out.println("Kirjautuminen epäonnistui");
			}
		}
		
		scanner.reset();
	}
	
	public static void kirjautunutMenu() {
		boolean kirjautunutLoop = true;

		while (kirjautunutLoop) {
			System.out.println("=== Asiakashallinta ===");
			System.out.println("=== Kirjautunut:    ===");
			System.out.println(kirjautunut.annaNimi());
			System.out.println("1. Tee uusi varaus");
			System.out.println("2. Poista varaus");
			System.out.println("0. Palaa");

			String input = scanner.nextLine();

			switch (input) {
			case "1":
				uusiVaraus();
				break;
			case "2":
				poistaVaraus();
				break;
			case "0":
				kirjautunutLoop = false;
				return;
			default:
				break;
			}
		}
		scanner.reset();
	}
	
	public static void uusiVaraus() {
		boolean uusiVarausLoop = true;

		while (uusiVarausLoop) {
			System.out.println("Mille päivälle haluaisit tehdä varauksen?");
			System.out.println("Anna vastauksesi muodossa dd-mm-yyyy");
			String datestring = scanner.nextLine();
			
			if (datestring.equals("")) {
				uusiVarausLoop = false;
				break;
			}
			
			String[] split = datestring.split("-");
			
			int dd = 0;
			int mm = 0;
			int yyyy = 0;
			
			try {
				dd = Integer.parseInt(split[0]);
				mm = Integer.parseInt(split[1]) - 1;
				yyyy = Integer.parseInt(split[2]);
			} catch (NumberFormatException e) {
				System.out.println("Virheellinen syöte");
				continue;
			}
			
			Date date = new Date(yyyy - 1900, mm, dd);
			Matka matka = null;
			
			for (Matka m : matkat) {
				int mDay = m.annaPaivamaara().getDate();
				int mMonth = m.annaPaivamaara().getMonth();
				int mYear = m.annaPaivamaara().getYear() + 1900;
				
				if (mDay == dd && mMonth == mm && mYear == yyyy) {
					matka = m;
					break;
				}
			}
			
			if (matka == null) {
				System.out.println("Antamallesi päivämäärälle ei löydy matkoja");
				continue;
			}
			
			Varaus varaus = new Varaus(matka, new Date(), kirjautunut);
			varaukset.add(varaus);
			System.out.println("Varaus tehtiin onnistuneesti");
			
			kirjoitaVaraukset();
			
			uusiVarausLoop = false;
		}
		scanner.reset();
	}
	
	public static void poistaVaraus() {
		ArrayList<Varaus> asiakkaanVaraukset = new ArrayList<Varaus>();
		
		boolean poistaVarausLoop = true;
		
		while(poistaVarausLoop) {
			for (Varaus v : varaukset) {
				if (v.annaAsiakas().annaId() == kirjautunut.annaId()) {
					asiakkaanVaraukset.add(v);
				}
			}
			
			if (asiakkaanVaraukset.size() == 0) {
				System.out.println("Ei poistettavia varauksia");
				poistaVarausLoop = false;
				break;
			}
			
			System.out.println("Minkä seuraavista varauksista haluat poistaa?");
			
			for (int i = 0; i < asiakkaanVaraukset.size(); i++) {
				Matka matka = asiakkaanVaraukset.get(i).annaMatka();
				
				System.out.println(i + ". " + matka.annaPaivamaara().toString() + " - " +
						matka.annaKohde() + " - " + matka.annaKesto() + " tuntia");
				
				if (i != 0 && i % 10 == 0) {
					System.out.println("Kirjoita q lopettaaksesi varausten listaamisen");
					System.out.println("Paina enter jatkaaksesi varausten listaamista");
					
					String input = scanner.nextLine();
					
					if (input.equals("q")) {
						break;
					} else if (input.equals("")) {
						continue;
					}
				}
			}
			
			System.out.println("Syötä valintasi");
			String valinta = scanner.nextLine();
			
			if (valinta.equals("")) {
				poistaVarausLoop = false;
				break;
			}
			
			int valinta_int = 0;
			
			try {
				valinta_int = Integer.parseInt(valinta);
			} catch (NumberFormatException e) {
				System.out.println("Virheellinen syöte");
				continue;
			}
			
			if (valinta_int >= asiakkaanVaraukset.size()) {
				System.out.println("Virheellinen syöte");
				continue;
			}

			varaukset.remove(valinta_int);
			
			poistaVarausLoop = false;
			
			kirjoitaVaraukset();
			
			System.out.println("Varaus poistettiin onnistuneesti");
		}
		
		scanner.reset();
	}
	
	public static void poistaAsiakasMenu() {
		boolean poistaAsiakasLoop = true;
		
		while (poistaAsiakasLoop) {
			if (asiakkaat.size() == 0) {
				System.out.println("Ei poistettavia asiakkaita");
				poistaAsiakasLoop = false;
				break;
			}
			
			System.out.println("Minkä seuraavista asiakkaista haluat poistaa?");
			
			for (int i = 0; i < asiakkaat.size(); i++) {
				Asiakas asiakas = asiakkaat.get(i);
				
				System.out.println(i + ". " + asiakas.annaNimi() + " - " +
						asiakas.annaId());
				if (i != 0 && i % 10 == 0) {
					System.out.println("Kirjoita q lopettaaksesi asiakkaiden listaamisen");
					System.out.println("Paina enter jatkaaksesi asiakkaiden listaamista");
					
					String input = scanner.nextLine();
					
					if (input.equals("q")) {
						break;
					} else if (input.equals("")) {
						continue;
					}
				}
			}
			
			System.out.println("Syötä valintasi");
			String valinta = scanner.nextLine();
			
			if (valinta.equals("")) {
				poistaAsiakasLoop = false;
				break;
			}
			
			int id_int = 0;
			
			try {
				id_int = Integer.parseInt(valinta);
			} catch (NumberFormatException e) {
				System.out.println("Virheellinen syöte");
				continue;
			}
			
			Asiakas poistettava = null;
			
			for (Asiakas a : asiakkaat) {
				if (a.annaId() == id_int) {
					poistettava = a;
					break;
				}
			}
			
			if (poistettava == null) {
				System.out.println("Asiakasta ei löytynyt");
				continue;
			}
			
			for (Varaus v : varaukset) {
				if (v.annaAsiakas().annaId() == id_int) {
					v = null;
					varaukset.remove(v);
				}
			}
			
			asiakkaat.remove(poistettava);
			
			kirjoitaAsiakkaat();
			kirjoitaVaraukset();
			
			System.out.println("Asiakas poistettu onnistuneesti");
			
			poistaAsiakasLoop = false;
		}
		
		scanner.reset();
	}

	public static void matkaMenu() {
		boolean matkaLoop = true;

		while (matkaLoop) {
			System.out.println("=== Matkamenu ===");
			System.out.println("1. Luo matka");
			System.out.println("2. Poista matka");
			System.out.println("0. Palaa");

			String input = scanner.nextLine();

			switch (input) {
			case "1":
				luoMatkaMenu();
				break;
			case "2":
				poistaMatkaMenu();
				break;
			case "0":
				return;
			default:
				break;
			}
		}
		scanner.reset();
	}
	
	public static void luoMatkaMenu() {
		boolean luoMatkaLoop = true;
		
		while (luoMatkaLoop) {
			System.out.println("Mille päivälle haluaisit luoda matkan?");
			System.out.println("Anna vastauksesi muodossa dd-mm-yyyy");
			String datestring = scanner.nextLine();
			
			if (datestring.equals("")) {
				luoMatkaLoop = false;
				break;
			}
			
			String[] split = datestring.split("-");
			int dd = 0;
			int mm = 0;
			int yyyy = 0;
			
			try {
				dd = Integer.parseInt(split[0]);
				mm = Integer.parseInt(split[1]) - 1;
				yyyy = Integer.parseInt(split[2]);
			} catch (NumberFormatException e) {
				System.out.println("Virheellinen syöte");
				continue;
			}
			Date date = new Date(yyyy - 1900, mm, dd);
			
			System.out.println("Anna matkan määränpää");
			String kohde = scanner.nextLine();
			
			if (kohde.equals("")) {
				luoMatkaLoop = false;
				break;
			}
			
			System.out.println("Anna matkan kesto tunteina");
			String kesto = scanner.nextLine();
			
			if (kesto.equals("")) {
				luoMatkaLoop = false;
				break;
			}
			
			int kesto_int = 0;
			
			try {
				kesto_int = Integer.parseInt(kesto);
			} catch (NumberFormatException e) {
				System.out.println("Virheellinen syöte");
				continue;
			}
			
			Matka matka = new Matka(date, kesto_int, kohde);
			
			for (Matka m : matkat) {
				if (m.annaPaivamaara().getDate() == dd && m.annaPaivamaara().getMonth() == mm &&
						m.annaPaivamaara().getYear() + 1900 == yyyy && m.annaKesto() == kesto_int &&
						m.annaKohde() == kohde) {
					System.out.println("Samanlainen matka on jo olemassa, matkaa ei luotu");
					continue;
				}
			}
			matkat.add(matka);
			
			kirjoitaMatkat();
			
			System.out.println("Matka luotiin onnistuneesti");
			luoMatkaLoop = false;
		}
	}

	public static void poistaMatkaMenu() {
		boolean poistaMatkaLoop = true;
		
		while(poistaMatkaLoop) {
			
			if (matkat.size() == 0) {
				System.out.println("Ei poistettavia matkoja");
				poistaMatkaLoop = false;
				break;
			}
			
			System.out.println("Minkä seuraavista matkoista haluat poistaa?");
			
			for (int i = 0; i < matkat.size(); i++) {
				Matka matka = matkat.get(i);
				
				System.out.println(i + ". " + matka.annaPaivamaara().toString() + " - " +
						matka.annaKohde() + " - " + matka.annaKesto() + " tuntia");
				if (i != 0 && i % 10 == 0) {
					System.out.println("Kirjoita q lopettaaksesi matkojen listaamisen");
					System.out.println("Paina enter jatkaaksesi matkojen listaamista");
					
					String input = scanner.nextLine();
					
					if (input.equals("q")) {
						break;
					} else if (input.equals("")) {
						continue;
					}
				}
			}
			
			System.out.println("Syötä valintasi");
			String valinta = scanner.nextLine();
			
			if (valinta.equals("")) {
				poistaMatkaLoop = false;
				break;
			}
			
			int valinta_int = 0;
			try {
				valinta_int = Integer.parseInt(valinta);
			} catch (NumberFormatException e) {
				System.out.println("Virheellinen syöte");
				continue;
			}
			
			if (valinta_int >= matkat.size()) {
				System.out.println("Virheellinen syöte");
				continue;
			}

			matkat.remove(valinta_int);
			
			poistaMatkaLoop = false;
			
			kirjoitaMatkat();
			
			System.out.println("Varaus poistettiin onnistuneesti");
		}
		
		scanner.reset();
	}

	public static void generoiAsiakkaat() {
		for (int i = 0; i < ASIAKKAITA; i++) {
			Asiakas asiakas = new Asiakas("Testi Asiakas " + i, i);
			asiakkaat.add(asiakas);
		}
	}

	public static void generoiMatkat() {
		boolean kaikki = false;
		Calendar cal = Calendar.getInstance();
		cal.set(alkupvm_year, alkupvm_month, alkupvm_day);
		Calendar loppu = Calendar.getInstance();
		loppu.set(loppupvm_year, loppupvm_month, loppupvm_day);
		Date loppudate = loppu.getTime();

		while (!kaikki) {
			Date paivamaara = cal.getTime();
			Matka matka = new Matka(paivamaara, 8, "Testimaa");
			matkat.add(matka);
			if (paivamaara.getDate() != loppudate.getDate() || 
					paivamaara.getMonth() != loppudate.getMonth() ||
					paivamaara.getYear() != loppudate.getYear()) {
				cal.add(Calendar.DAY_OF_MONTH, 1);
			} else {
				kaikki = true;
			}
		}
	}

	public static void lueDatat() {
		File f = new File("Asiakkaat.txt");
		if ((f.exists() && !f.isDirectory()) == false) {
			try {
				PrintWriter writer = new PrintWriter("Asiakkaat.txt", "UTF-8");
				writer.close();
			} catch (IOException e) {

			}
		}

		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();

				kasitteleAsiakas(line);
			}

			br.close();
		} catch (Exception e) {

		}

		File g = new File("Varaukset.txt");
		if ((g.exists() && !g.isDirectory()) == false) {
			try {
				PrintWriter writer = new PrintWriter("Varaukset.txt", "UTF-8");
				writer.close();
			} catch (IOException e) {

			}
		}

		try {
			BufferedReader br = new BufferedReader(new FileReader(g));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();

				kasitteleVaraus(line);
			}

			br.close();
		} catch (Exception e) {

		}

		File h = new File("Matkat.txt");
		if ((h.exists() && !h.isDirectory()) == false) {
			try {
				PrintWriter writer = new PrintWriter("Matkat.txt", "UTF-8");
				writer.close();
			} catch (IOException e) {

			}
		}
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(h));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();

				kasitteleMatka(line);
			}

			br.close();
		} catch (Exception e) {

		}
	}

	public static void kasitteleAsiakas(String line) {
		String[] split = line.split("\\|"); // Splitin pituuden tulisi olla 2

		String nimi = split[0]; // nimi
		String id = split[1]; // id
		
		int id_int = 0;
		try {
			id_int = Integer.parseInt(id);
		} catch (NumberFormatException e) {
			System.out.println("Virheellinen id asiakkaan " + nimi + " " + id + " kohdalla");
			return;
		}
		Asiakas asiakas = new Asiakas(nimi, id_int);
		asiakkaat.add(asiakas);
	}

	public static void kirjoitaAsiakkaat() {
		File f = new File("Asiakkaat.txt");
		if ((f.exists() && !f.isDirectory()) == false) {
			try {
				PrintWriter writer = new PrintWriter("Asiakkaat.txt", "UTF-8");
				writer.close();
			} catch (IOException e) {

			}
		}

		try {
			PrintWriter writer = new PrintWriter("Asiakkaat.txt", "UTF-8");

			for (Asiakas a : asiakkaat) {
				writer.println(a.toString());
			}

			writer.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void kasitteleMatka(String line) {
		String[] split = line.split("\\|"); // Splitin pituuden
											// tulisi olla 3

		String paivamaara = split[0]; // Matkan päivämäärä
		Date pvm = new Date(paivamaara);
		String kesto = split[1]; // Matkan kesto
		int kesto_int = 0;
		
		try {
			kesto_int = Integer.parseInt(kesto);
		} catch (NumberFormatException e) {
			System.out.println("Virheellinen kesto matkan " + 
				paivamaara + " " + split[2] + " " + kesto + " kohdalla");
			return;
		}
		
		String kohde = split[2]; // Matkan kohde

		Matka matka = new Matka(pvm, kesto_int, kohde);

		matkat.add(matka);
	}

	public static void kirjoitaMatkat() {
		File f = new File("Matkat.txt");
		if ((f.exists() && !f.isDirectory()) == false) {
			try {
				PrintWriter writer = new PrintWriter("Matkat.txt", "UTF-8");
				writer.close();
			} catch (IOException e) {

			}
		}

		try {
			PrintWriter writer = new PrintWriter("Matkat.txt", "UTF-8");

			for (Matka m : matkat) {
				writer.println(m.toString());
			}

			writer.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void kasitteleVaraus(String line) {
		String[] split = line.split("\\|"); // Splitin pituuden tulisi olla 6

		String paivamaara = split[0]; // Matkan päivämäärä
		String kesto = split[1]; // Matkan kesto
		int kesto_int = 0;
		
		try {
			kesto_int = Integer.parseInt(kesto);
		} catch (NumberFormatException e) {
			System.out.println("Virheellinen kesto varauksen " + paivamaara + " " +
				kesto + " " + split[2] + " " + split[3] + " " + split[4] + " " + 
					split[5] + " kohdalla");
			return;
		}
		String kohde = split[2]; // Matkan kohde

		String varauspvm = split[3]; // Varauksen pvm

		String nimi = split[4]; // Asiakkaan nimi
		String id = split[5]; // Asiakkaan id
		int id_int = 0;
		try {
			id_int = Integer.parseInt(id);
		} catch (NumberFormatException e) {
			System.out.println("Virheellinen id varauksen " + paivamaara + " " +
				kesto + " " + split[2] + " " + split[3] + " " + split[4] + " " + 
					split[5] + " kohdalla");
			return;
		}

		Matka matka = new Matka(new Date(paivamaara), kesto_int, kohde);
		Date varauspaivamaara = new Date(varauspvm);
		Asiakas asiakas = new Asiakas(nimi, id_int);

		Varaus varaus = new Varaus(matka, varauspaivamaara, asiakas);

		varaukset.add(varaus);

	}

	public static void kirjoitaVaraukset() {
		File f = new File("Varaukset.txt");
		if ((f.exists() && !f.isDirectory()) == false) {
			try {
				PrintWriter writer = new PrintWriter("Varaukset.txt", "UTF-8");
				writer.close();
			} catch (IOException e) {

			}
		}

		try {
			PrintWriter writer = new PrintWriter("Varaukset.txt", "UTF-8");

			for (Varaus v : varaukset) {
				writer.println(v.toString());
			}

			writer.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static int annaVapaaid() {
		int id = 0;
		
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			boolean vapaa = true;
			for (Asiakas a : asiakkaat) {
				if (a.annaId() == i) {
					vapaa = false;
					break;
				}
			}
			
			if (vapaa) {
				id = i;
				break;
			}
		}
		
		return id;
	}
	
}
