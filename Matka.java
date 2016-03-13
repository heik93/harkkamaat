import java.util.*;

public class Matka {
	private Date paivamaara;
	private int kesto;
	private String kohde;

	public Matka(Date paivamaara, int kesto, String kohde) {
		this.paivamaara = paivamaara;
		this.kesto = kesto;
		this.kohde = kohde;
	}
	
	@Override
	public String toString() {
		String s = paivamaara.toString() + "|" + kesto + "|" + kohde;
		return s;
	}
	
	public Date annaPaivamaara() {
		return this.paivamaara;
	}
	
	public String annaKohde() {
		return this.kohde;
	}
	
	public int annaKesto() {
		return this.kesto;
	}
}