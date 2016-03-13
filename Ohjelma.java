import java.util.*;
import java.lang.*;

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
	
	public static void main(String[] args) {
		asiakkaat = new ArrayList<Asiakas>();
		matkat = new ArrayList<Matka>();
		
		lueDatat();
		
		if (asiakkaat.size() == 0) {
			generoiAsiakkaat();
		}
		
		if (matkat.size() == 0) {
			generoiMatkat();
		}
		
		while (true) {
			
		}
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
		
	}
}
