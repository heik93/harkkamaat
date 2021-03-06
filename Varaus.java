import java.util.*;

public class Varaus {
	private Matka matka;
	private Date paivamaara;
	private Asiakas asiakas;

	public Varaus(Matka matka, Date paivamaara, Asiakas asiakas) {
		this.matka = matka;
		this.paivamaara = paivamaara;
		this.asiakas = asiakas;
	}
	
	@Override
	public String toString() {
		String s = matka.toString() + "|" + paivamaara.toString() + "|" + asiakas.toString();
		return s;
	}
	
	public Asiakas annaAsiakas() {
		return this.asiakas;
	}
	
	public Matka annaMatka() {
		return this.matka;
	}
}
