import java.util.*;
import java.lang.*;
import java.io.*;

public class Ohjelma {
	static final int ASIAKKAITA = 10;
	static final int MATKOJA = 10;

	static final int alkupvm_year = 2016;
	static final int alkupvm_month = 2;
	static final int alkupvm_day = 15;

	static final int loppupvm_year = 2016;
	static final int loppupvm_month = 4;
	static final int loppupvm_day = 1;

	static ArrayList<Asiakas> asiakkaat;
	static ArrayList<Matka> matkat;
	static ArrayList<Varaus> varaukset;
	
	static Scanner scanner = new Scanner(System.in);

	static Asiakas kirjautunut;

	static boolean loop = true;

	public static void main(String[] args) {
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
		System.out.println("Testimenu:");
		System.out.println("1. Asiakkaat");
		System.out.println("2. Matkat");
		System.out.println("3. Varaukset");
		System.out.println("0. Quit");

		String input = scanner.nextLine();

		switch (input) {
		case "1":
			asiakasMenu();
			break;
		case "2":
			matkaMenu();
			break;
		case "3":
			varausMenu();
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
			System.out.println("Asiakasmenu:");
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
			int id = annaVapaaid();
			System.out.println("Anna taso 1. BASIC 2. GOLD 3. PLATINUM");
			String taso = scanner.nextLine();
			AsiakasTaso taso_enum = null;
			
			switch (taso) {
			case "1":
				taso_enum = AsiakasTaso.BASIC;
				break;
			case "2":
				taso_enum = AsiakasTaso.GOLD;
				break;
			case "3":
				taso_enum = AsiakasTaso.PLATINUM;
				break;
			default:
				taso_enum = AsiakasTaso.BASIC;
				break;
			}
			
			Asiakas asiakas = new Asiakas(nimi, id, taso_enum);
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
			
			int id_int = Integer.parseInt(id);
			
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
			System.out.println("Asiakashallinta:");
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
			String[] split = datestring.split("-");
			int dd = Integer.parseInt(split[0]);
			int mm = Integer.parseInt(split[1]);
			int yyyy = Integer.parseInt(split[2]);
			Date date = new Date(dd, mm, yyyy);
			Matka matka = null;
			
			for (Matka m : matkat) {
				int mDay = m.annaPaivamaara().getDate();
				int mMonth = m.annaPaivamaara().getMonth() + 1;
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
			}
			
			String valinta = scanner.nextLine();
			int valinta_int = Integer.parseInt(valinta);
			
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
			System.out.println("Poista asiakas");
			System.out.println("Anna asiakkaan id:");
			String id = scanner.nextLine();
			
			if (id.equals("")) {
				poistaAsiakasLoop = false;
				break;
			}
			
			int id_int = Integer.parseInt(id);
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
		
	}

	public static void varausMenu() {

	}

	public static void generoiAsiakkaat() {
		for (int i = 0; i < ASIAKKAITA; i++) {
			Asiakas asiakas = new Asiakas("Testi Asiakas " + i, i, AsiakasTaso.BASIC);
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
			if (!paivamaara.toGMTString().equals(loppudate.toGMTString())) {
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
			BufferedReader br = new BufferedReader(new FileReader(f));
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
	}

	private static void kasitteleAsiakas(String line) {
		String[] split = line.split("\\|"); // Splitin pituuden tulisi olla 3

		String nimi = split[0]; // nimi
		String id = split[1]; // id
		int id_int = Integer.parseInt(id);
		String taso = split[2]; // taso
		AsiakasTaso taso_enum = null;

		switch (taso) {
		case "BASIC":
			taso_enum = AsiakasTaso.BASIC;
			break;
		case "GOLD":
			taso_enum = AsiakasTaso.GOLD;
			break;
		case "PLATINUM":
			taso_enum = AsiakasTaso.PLATINUM;
			break;
		default:
			break;
		}

		Asiakas asiakas = new Asiakas(nimi, id_int, taso_enum);
		asiakkaat.add(asiakas);
	}

	private static void kirjoitaAsiakkaat() {
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

	private static void kasitteleMatka(String line) {
		String[] split = line.split("\\|"); // Splitin pituuden
											// tulisi olla 3

		String paivamaara = split[0]; // Matkan päivämäärä
		String kesto = split[1]; // Matkan kesto
		int kesto_int = Integer.parseInt(kesto);
		String kohde = split[2]; // Matkan kohde

		Matka matka = new Matka(new Date(paivamaara), kesto_int, kohde);

		matkat.add(matka);
	}

	private static void kirjoitaMatkat() {
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

	private static void kasitteleVaraus(String line) {
		String[] split = line.split("\\|"); // Splitin pituuden tulisi olla 7

		String paivamaara = split[0]; // Matkan päivämäärä
		String kesto = split[1]; // Matkan kesto
		int kesto_int = Integer.parseInt(kesto);
		String kohde = split[2]; // Matkan kohde

		String varauspvm = split[3]; // Varauksen pvm

		String nimi = split[4]; // Asiakkaan nimi
		String id = split[5]; // Asiakkaan id
		int id_int = Integer.parseInt(id);
		String taso = split[6]; // Asiakkaan taso
		AsiakasTaso taso_enum = null;

		switch (taso) {
		case "BASIC":
			taso_enum = AsiakasTaso.BASIC;
			break;
		case "GOLD":
			taso_enum = AsiakasTaso.GOLD;
			break;
		case "PLATINUM":
			taso_enum = AsiakasTaso.PLATINUM;
			break;
		default:
			break;
		}

		Matka matka = new Matka(new Date(paivamaara), kesto_int, kohde);
		Date varauspaivamaara = new Date(varauspvm);
		Asiakas asiakas = new Asiakas(nimi, id_int, taso_enum);

		Varaus varaus = new Varaus(matka, varauspaivamaara, asiakas);

		varaukset.add(varaus);

	}

	private static void kirjoitaVaraukset() {
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
