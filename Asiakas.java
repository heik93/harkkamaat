import java.util.*;

public class Asiakas {
	private String nimi;
	private int id;
	
	public Asiakas(String nimi, int id) {
		this.nimi = nimi;
		this.id= id;
	}
	
	public String annaNimi() {
		return nimi;
	}
	
	public int annaId() {
		return id;
	}
	
	@Override
	public String toString() {
		String s = nimi + "|" + id;
		return s;
	}
}