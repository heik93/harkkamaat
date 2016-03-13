import java.util.*;
import java.lang.*;
import java.io.*;

public class Ohjelma {
	static final int ASIAKKAITA = 10;
	static final int MATKOJA = 10;
	
	static final int alkupvm_year = 2016;
	static final int alkupvm_month = 3;
	static final int alkupvm_day = 15;
	
	static final int loppupvm_year = 2016;
	static final int loppupvm_month = 4;
	static final int loppupvm_day = 1;
	
	static ArrayList<Asiakas> asiakkaat;
	static ArrayList<Matka> matkat;
	static ArrayList<Varaus> varaukset;
	
	public static void main(String[] args) {
		
		lueDatat();

		asiakkaat = new ArrayList<Asiakas>();
		matkat = new ArrayList<Matka>();
		varaukset = new ArrayList<Varaus>();
		
		if (asiakkaat.size() == 0) {
			generoiAsiakkaat();
		}
		
		if (matkat.size() == 0) {
			generoiMatkat();
		}
		
		kirjoitaAsiakkaat();
		kirjoitaMatkat();
		kirjoitaVaraukset();
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
			try	{
				PrintWriter writer = new PrintWriter("Asiakkaat.txt", "UTF-8");
				writer.close();
			} catch(IOException e) {
				
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
			} catch(IOException e) {
				
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
			} catch(IOException e) {
				
			}
		}
	}

	
	private static void kasitteleAsiakas(String line) {
		String[] split = line.split("|"); // Splitin pituuden tulisi olla 3
		
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
			try	{
				PrintWriter writer = new PrintWriter("Asiakkaat.txt", "UTF-8");
				writer.close();
			} catch(IOException e) {
				
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
		String[] split = line.split("|"); // Splitin pituuden 
										  // tulisi olla 3
		
		String paivamaara = split[0]; // Matkan päivämäärä
		String kesto = split[1]; // Matkan kesto
		int kesto_int = Integer.parseInt(kesto);
		String kohde = split[2]; // Matkan kohde
		
		Matka matka = new Matka(new Date(paivamaara), kesto_int, 
				kohde);
		
		matkat.add(matka);
	}
	
	private static void kirjoitaMatkat() {
		File f = new File("Matkat.txt");
		if ((f.exists() && !f.isDirectory()) == false) { 
			try	{
				PrintWriter writer = new PrintWriter("Matkat.txt", "UTF-8");
				writer.close();
			} catch(IOException e) {
				
			}
		}
		
		try {
			PrintWriter writer = new PrintWriter("Matkat.txt", "UTF-8");
			
			for (Matka m : matkat){
				writer.println(m.toString());
			}
			
			writer.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private static void kasitteleVaraus(String line) {
		String[] split = line.split("|"); // Splitin pituuden tulisi olla 7
		
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
		
		Matka matka = new Matka(new Date(paivamaara), kesto_int, 
				kohde);
		Date varauspaivamaara = new Date(varauspvm);
		Asiakas asiakas = new Asiakas(nimi, id_int, taso_enum);
		
		Varaus varaus = new Varaus(matka, varauspaivamaara, asiakas);
		
		varaukset.add(varaus);
		
	}

	private static void kirjoitaVaraukset() {
		File f = new File("Varaukset.txt");
		if ((f.exists() && !f.isDirectory()) == false) { 
			try	{
				PrintWriter writer = new PrintWriter("Varaukset.txt", "UTF-8");
				writer.close();
			} catch(IOException e) {
				
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
}
