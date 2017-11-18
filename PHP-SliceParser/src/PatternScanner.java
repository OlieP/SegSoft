import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.SingleSelectionModel;

import patterns.VulnPattern;


public class PatternScanner {

	private final Path fFilePath= null ;
	private ArrayList<VulnPattern> _patterns = new ArrayList<VulnPattern>();

	public PatternScanner(){
		_patterns = new ArrayList<VulnPattern>();
	}

	
	public ArrayList<VulnPattern> get_patterns() {
		return _patterns;
	}

	public void set_patterns(ArrayList<VulnPattern> _patterns) {
		this._patterns = _patterns;
	}

	
	public void readPatterns( ) {
		String line; 
		String line1;
		String line2;
		String line3;

		try {
			FileReader input = new FileReader("C:\\Users\\pemol\\git\\SegSoft1718\\PHP-SliceParser\\slices\\Patterns.txt");

			BufferedReader bufRead = new BufferedReader(input);

			VulnPattern pattern = new VulnPattern();

			//String_node line1; // String_node that holds current file line
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
				pattern = new VulnPattern();
				//System.out.println("1st Line:" + count+": "+line);

				line1 = bufRead.readLine();
				line2 = bufRead.readLine();
				line3 = bufRead.readLine();

				String split_1[] = line1.split(",");
				String split_2[] = line2.split(",");
				String split_3[] = line3.split(",");

				for(int i = 0,j=0,k=0; i < split_1.length && j<split_2.length && k < split_3.length; i++,j++,k++){
					pattern.add_entryPoint(split_1[i].substring(1));
					pattern.add_sanitization(split_2[1]);
					pattern.add_sensitiveSink(split_3[i]);
				}
				//pattern.set_entryPoints(pattern_l1);
				for(int i = 0; i < split_2.length ; i++){
					
				}
				//pattern.set_saninitizationFuncs(pattern_l2);
				for(int i = 0; i < split_3.length ; i++){
					
				}


				_patterns.add(pattern);

				line = bufRead.readLine();

				//System.out.println("2nd Line: " +count1+": "+line1);
				//System.out.println("3rd Line: " +count2+": "+line2);
				//System.out.println("4rd Line: " +count3+": "+line3);
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


	protected void printPatterns() {
		System.out.println("There are "+ _patterns.size()+ " patterns");

		//List<String_node> coll = _patterns.get("SQL injection");
		//System.out.println(coll);
		for(int i= 0; i< _patterns.size();i++) {
			VulnPattern vuln = _patterns.get(i);
			System.out.println(vuln.get_entryPoints());
			System.out.println(vuln.get_saninitizationFuncs());
			System.out.println(vuln.get_sensitiveSinks());
			
			System.out.println("");

		}



	}

}
