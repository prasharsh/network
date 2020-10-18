import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BitStuffing {
	Path filePath;


	public void bitStreamFormText() throws IOException{
		filePath = Paths.get("sampleText.txt"); 
		byte[] content = Files.readAllBytes(filePath);
		String s = "";
		for(byte character : content){
			String temp = Integer.toBinaryString(character);
			while(temp.length()!=8){
				temp = "0" + temp;
			}
			s = s + temp;
		}
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("bitStream.txt"), "utf-8"))) {
			writer.write(s.replace("11111", "111110"));
		}
		System.out.println("bitStream.txt created successfully !");
	}

	public void bitStreamToText() throws IOException{
		filePath = Paths.get("bitStream.txt"); 
		byte[] data = Files.readAllBytes(filePath);
		String dataString = new String(data);
		//removing stuffing
		dataString = dataString.replace("111110", "11111");
		String textStr = "";
		for (int j = 0; j < dataString.length()/8; j++) {

			int a = Integer.parseInt(dataString.substring(8*j,(j+1)*8),2);
			textStr += (char)(a);
		}
		
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("RecoveredText.txt"), "utf-8"))) {
			writer.write(textStr);
		}
		System.out.println("RecoveredText.txt created successfully, with the below content");
		System.out.println("______________________________________________________________");
		System.out.println(textStr);
	}

	public static void main(String[] args) {
		try {
			BitStuffing bitStuffing = new BitStuffing();
			bitStuffing.bitStreamFormText();
			bitStuffing.bitStreamToText();
		}catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

}
