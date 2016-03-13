public class Ohjelma {
	
	public static void main(String[] args) {
		
	}
	
	public void tallennaTiedot(){
		try{
			PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
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
