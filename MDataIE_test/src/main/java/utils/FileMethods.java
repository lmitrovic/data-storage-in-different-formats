package utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileMethods {
	
	public static String fileToString(String filePath) {
		StringBuilder contentBuilder = new StringBuilder();
		File file = new File("");

		try (Stream<String> stream = Files.lines(Paths.get(file.getAbsolutePath() + filePath),
				StandardCharsets.UTF_8)) {
			stream.forEach(s -> contentBuilder.append(s).append("\n"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return contentBuilder.toString();
	}
	
	public static void stringToFile(String file, String data) {
		
		File f = new File("");
        f = new File(f.getAbsolutePath() + file);
        f.setWritable(true);

		try (FileOutputStream fos = new FileOutputStream(f);
				BufferedOutputStream bos = new BufferedOutputStream(fos)) {
			// convert string to byte array
			byte[] bytes = data.getBytes();
			// write byte array to file
			bos.write(bytes);
			bos.close();
			fos.close();
			System.out.print("Data written to file successfully.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
