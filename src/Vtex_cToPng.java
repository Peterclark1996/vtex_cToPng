import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class Vtex_cToPng {

	public static void main(String[] args) throws InterruptedException {
		if(args.length < 0) {
			return;
		}
		
		for(String s : args) {
			byte[] data = read(s);
			for(int i = 0; i < data.length - 8; i++) {
				if(data[i] == (byte)137 && data[i + 1] == (byte)80 && data[i + 2] == (byte)78 && data[i + 3] == (byte)71 && data[i + 4] == (byte)13 && data[i + 5] == (byte)10 && data[i + 6] == (byte)26 && data[i + 7] == (byte)10) {
					write(Arrays.copyOfRange(data, i, data.length - 1), s.substring(0, s.length() - 7) + ".png");
					break;
				}
			}
		}
	}
	
	private static byte[] read(String inputFileName){
		File file = new File(inputFileName);
		byte[] result = new byte[(int)file.length()];
		try {
			InputStream input = null;
			try {
				int totalBytesRead = 0;
				input = new BufferedInputStream(new FileInputStream(file));
				while(totalBytesRead < result.length){
					int bytesRemaining = result.length - totalBytesRead;
					int bytesRead = input.read(result, totalBytesRead, bytesRemaining); 
					if (bytesRead > 0){
						totalBytesRead = totalBytesRead + bytesRead;
					}
				}
			}finally {
				input.close();
			}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private static void write(byte[] input, String outputFileName){
		try {
			OutputStream output = null;
			try {
				output = new BufferedOutputStream(new FileOutputStream(outputFileName));
				output.write(input);
			}finally {
				output.close();
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
