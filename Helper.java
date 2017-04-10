import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Ahmet Faruk Bayrak
 *Helper class contain some helping methods.
 */
public class Helper {

	/**
	 * @param path path for read the file.
	 * @return a String collection as lines of text file.
	 */
	public static String[] readFile(String path) {
		try{
			int i = 0;
			int lenght = Files.readAllLines(Paths.get(path)).size();
			String[] results = new String[lenght];
			for(String line : Files.readAllLines(Paths.get(path))){
				results[i++] = line;
			}
			return results;
		}
		catch(IOException e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * We need a method like this for compare date comprehension.
	 * @param dataasstring sting format date 
	 * @return date format date
	 */
	public static Date dateParse(String dataasstring) {
		try {
			DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
			Date d;
			d = formatter.parse(dataasstring);
			return d;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	
	
}
