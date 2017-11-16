import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class PatternScanner {

	public Map<String,ArrayList<String>> _patterns = new HashMap<String,ArrayList<String>>();
    //private HashMap<String,ArrayList<String>> _patterns = new HashMap<String,ArrayList<String>>();
	// Outraline = bufRead.readLine(); maneira de fazer.. mais facil, vai buscar 4 linhas de cada vez..
	public void readPatterns( ) {
		String line; 
		String line1;
		String line2;
		String line3;
		
		try {
			FileReader input = new FileReader("C:\\Users\\pemol\\git\\SegSoft1718\\PHP-SliceParser\\slices\\Patterns.txt");

			BufferedReader bufRead = new BufferedReader(input);

			//String line1; // String that holds current file line
			int count = 0; // Line number of count 
			int count1 = 0;
			int count2 = 0;
			int count3 = 0;
			// Read first line
			line = bufRead.readLine();
			count++;
			count1++;
			count2++;
			// Read through file one line at time. Print line # and line
			
			while (line != null){
				System.out.println("1st Line:" + count+": "+line);
				
				line1 = bufRead.readLine();
				line2 = bufRead.readLine();
				line3 = bufRead.readLine();
				
				ArrayList<String> pattern_l1 = new ArrayList<String>();
				ArrayList<String> pattern_l2 = new ArrayList<String>();
				ArrayList<String> pattern_l3 = new ArrayList<String>();
				
				String split_1[] = line1.split(",");
				String split_2[] = line2.split(",");
				String split_3[] = line3.split(",");
				for(int i = 0; i < split_1.length ; i++){
					pattern_l1.add(split_1[i]);
				}
				for(int i = 0; i < split_2.length ; i++){
					pattern_l2.add(split_2[i]);
				}
				for(int i = 0; i < split_3.length ; i++){
					pattern_l3.add(split_3[i]);
				}
			
				System.out.println(pattern_l1);
				System.out.println(pattern_l2);
				System.out.println(pattern_l3);
				//this._patterns.put(line, pattern);
				
				line = bufRead.readLine();
				
				System.out.println("2nd Line: " +count1+": "+line1);
				System.out.println("3rd Line: " +count2+": "+line2);
				System.out.println("4rd Line: " +count3+": "+line3);
				count++;
			}
			
			bufRead.close();
			
		}catch (ArrayIndexOutOfBoundsException e){
			System.out.println("Usage: java ReadFile filename\n");          

		}catch (IOException e){
			// If another exception is generated, print a stack trace
			e.printStackTrace();
		}

	}



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

			while (scanner.hasNextLine()){
				ArrayList<String> p = processLine(scanner.nextLine());
				System.out.println("processByLine" + p);
				patterns.add(p);
			}    
		}
		System.out.println(patterns);
	}

	protected ArrayList<String> processLine(String aLine){
		//use a second Scanner to parse the content of each line 
		Scanner scanner = new Scanner(aLine);

		ArrayList<String> items = new ArrayList<String>();

		if (scanner.hasNext()){
			String s = scanner.nextLine();
			log("Full Line: " + s);
			String[] aux = s.split(",");
			for(int i = 0; i < aux.length % 4 ; i++) {
				log("Striped Line: " + aux[i]);

				items.add(aux[i]);
			}
			System.out.println(items);
			scanner.close();
		}
		return items;
	}

	protected void printPatterns() {
		System.out.println("There are "+ _patterns.size()+ " patterns");
		for(int i = 0; i < _patterns.size(); i++) {

			for(int j=0; j < _patterns.get("SQL injection").size(); j++) {
			
				System.out.println(_patterns.get("SQL injection") );
				
			}
		}

	}

}
