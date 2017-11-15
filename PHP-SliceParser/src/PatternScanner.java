import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class PatternScanner {

	// PRIVATE 

	private final Path fFilePath ;
	private final static Charset ENCODING = StandardCharsets.UTF_8;  
	private  ArrayList<ArrayList<String>> patterns;

	private static void log(Object aObject){
		System.out.println(String.valueOf(aObject));
	}


	public PatternScanner(String filePath){
		fFilePath = Paths.get(filePath);
		patterns = new ArrayList<ArrayList<String>>();
	}


	/** Template method that calls {@link #processLine(String)}.  */
	public final void processLineByLine() throws IOException {
		try (Scanner scanner =  new Scanner(fFilePath,ENCODING.name())){
			int line = 0;

			while (scanner.hasNextLine()  ){

				ArrayList<String> p = processLine(scanner.nextLine());

				patterns.add(p);
				line++;
			}    
		}
	}

	protected ArrayList<String> processLine(String aLine){
		//use a second Scanner to parse the content of each line 
		Scanner scanner = new Scanner(aLine);

		ArrayList<String> items = new ArrayList<String>();

		if (scanner.hasNext()){
			String s = scanner.next();
			log("Full Line: " + s);
			String[] aux = s.split(",");
			for(int i = 0; i < aux.length ; i++) {
				log("Striped Line: " + aux[i]);
				items.add(aux[i]);
			}
			
		}
		return items;
	}

	protected void printPatterns() {
		System.out.println("There are "+ patterns.size()+ "patterns");
		for(int i = 0; i < patterns.size(); i++) {
		
			for(int j=0; j < patterns.get(i).size(); j++) {
				System.out.println("Elementos do padrão " + i );
				System.out.println("Elemento["+j+ "]: " + patterns.get(i).get(j));
			}
		}

	}

}
