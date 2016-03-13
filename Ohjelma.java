import java.io.*;

public class Ohjelma {
	
	public static void main(String[] args) {
		File f = new File("Asiakkaat.txt");
		if((f.exists() && !f.isDirectory()) == false) { 
		
			try{
				PrintWriter writer = new PrintWriter("Asiakkaat.txt", "UTF-8");
				writer.close();
			}
				catch(IOException e){
				
			}
		}
		File g = new File("Varaukset.txt");
		if((g.exists() && !g.isDirectory()) == false) { 
		
			try{
				PrintWriter writer = new PrintWriter("Varaukset.txt", "UTF-8");
				writer.close();
			}
				catch(IOException e){
				
			}
		}
		File h = new File("Matkat.txt");
		if((h.exists() && !h.isDirectory()) == false) { 
		
			try{
				PrintWriter writer = new PrintWriter("Matkat.txt", "UTF-8");
				writer.close();
			}
				catch(IOException e){
				
			}
		}
		
		
		
		
	}
	
}
