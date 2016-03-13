import java.util.*;

public class Asiakas {
	private String nimi;
	private int id;
	private AsiakasTaso taso;
	private ArrayList<Varaus> varaukset;
	
	public Asiakas(String nimi, int id, AsiakasTaso taso) {
		this.nimi = nimi;
		this.id= id;
		this.taso = taso; 
	}
	
	public String annaNimi() {
		return nimi;
	}
	
	public int annaId() {
		return id;
	}
	
	public AsiakasTaso annaAsiakasTaso() {
		return taso;
	}
	
	public void asetaAsiakasTaso(AsiakasTaso taso) {
		this.taso = taso;
	}
	
	@Override
	public String toString() {
		String s = nimi + "|" + id + "|" + taso;
		return s;
	}
}