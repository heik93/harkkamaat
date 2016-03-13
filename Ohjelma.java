public class Ohjelma {
	
	public static void main(String[] args) {
		
	}
	
	public void tallennaTiedotAsiakas(){
		try{
			PrintWriter writer = new PrintWriter("Asiakkaat.txt", "UTF-8");
			writer.println("The first line");
			for(int i = 0;i<3;i++){
				
			}
			writer.println("The last line");
			writer.close();
		}
			catch(IOException e){
				
			}
	}
	public void tallennaTiedotVaraus(){
		try{
			PrintWriter writer = new PrintWriter("Varaukset", "UTF-8");
			writer.println("The first line");
			for(int i = 0;i<3;i++){
				
			}
			writer.println("The last line");
			writer.close();
		}
			catch(IOException e){
				
			}
	}
	public void tallennaTiedotMatkat(){
		try{
			PrintWriter writer = new PrintWriter("Matkat.txt", "UTF-8");
			writer.println("The first line");
			for(int i = 0;i<3;i++){
				
			}
			writer.println("The last line");
			writer.close();
		}
			catch(IOException e){
				
			}
	}
}
