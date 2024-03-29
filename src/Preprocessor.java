import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

public class Preprocessor {

	public static final String NUMERICAL_FEATURE = "numerical";
	public static final String NUMERICAL_AND_OTHER_FEATURE = "numerical_and_other";
	public static final String DOT_FEATURE = "dot";
	public static final String ALL_CAPITALIZED_FEATURE = "all_capitalized";
	public static final String CAPITALIZED_FEATURE = "capitalized";
	public static final String PARENTHESIS_FEATURE = "parenthesis";

	
	public static void main(String[] args) throws IOException{
		
		Preprocessor pro = new Preprocessor();
		pro.generateBaseFromRawBase("base", "baseFile");
		
		File rawTests[] = new File("test/raw").listFiles();
		
		int i=0;
		for(File rawTest : rawTests){
			System.out.println(" lol" + rawTest.getAbsolutePath());
			ArrayList<ArrayList<String>> ar=  new ArrayList<ArrayList<String>>();
			
			Scanner in = new Scanner(rawTest);
			String line = in.nextLine();
			
			if(line.length() > 0){
				
				String words[] = line.split(" ");
				
				for(int x=0;x < words.length;x++){
					ar.add(new ArrayList<String>());
					ar.get(x).add(words[x]);
				}
				
				ar = pro.processInstance(ar);
				
				FileWriter fw = new FileWriter("test/processed/" + (i++));
				
				
				for(int x=0;x<ar.size();x++){	
					for(int y=0;y<ar.get(x).size();y++){
						if(y != 0) fw.write(" ");
						fw.write(ar.get(x).get(y));
					}
					fw.write("\n");
				}
				fw.flush();
				fw.close();
				
			}
			
		}
		
	}

	public void generateBaseFromRawBase(String rawBaseLocation, String baseFileLocation)
			throws FileNotFoundException, UnsupportedEncodingException{
		File folder = new File(rawBaseLocation);
		File files[] = folder.listFiles();
		
		ArrayList<ArrayList<ArrayList<String>>> ar = new ArrayList<ArrayList<ArrayList<String>>>();
		
		for(int x=0;x<files.length;x++){
			ar.add(processInstance(toInstance(files[x])));
		}
		
		saveBaseFile(ar,baseFileLocation);
		
	}
	
	public void saveBaseFile(ArrayList<ArrayList<ArrayList<String>>> ar, String baseFileLocation)
			throws FileNotFoundException, UnsupportedEncodingException{
		
		PrintWriter writer = new PrintWriter(baseFileLocation, "UTF-8");
		
		for(int x=0;x<ar.size();x++){
			for(int y=0;y<ar.get(x).size();y++){
				for(int z=0;z<ar.get(x).get(y).size();z++){
					if(z!=0)writer.print(" ");
					writer.print(ar.get(x).get(y).get(z).toLowerCase());
					
				
				}
					
				writer.println();	
			}
			writer.println();
		}
		
		writer.flush();
		writer.close();
		
	}
	
	public ArrayList<ArrayList<String>> toInstance(File file) throws FileNotFoundException{
		ArrayList<ArrayList<String>> ar = new ArrayList<ArrayList<String>>();
		
		Scanner in = new Scanner(new FileReader(file));
			
		String line;
		String words[];
		
		while(in.hasNextLine()){
			ar.add(new ArrayList<String>());
			
			line = in.nextLine();
			
			words = line.split(" ");
			
			for(String word : words){
				
				ar.get(ar.size() -1).add(word);
			}
		}	
		
		return ar;
	}
	
	
	public ArrayList<ArrayList<String>> processInstance(ArrayList<ArrayList<String>> ar){

		addNumericalFeature(ar);
		addNumericalAndOtherFeature(ar);
		addDotFeature(ar);
		addAllCapitalizedFeature(ar);
		addCapitalizedFeature(ar);
		addParenthesisFeature(ar);
		addSequenceOrderFeature(ar);
		
		
		return ar;
	}

	private void addSequenceOrderFeature(ArrayList<ArrayList<String>> ar) {
		
		for(int x=0;x<ar.size();x++){
			ar.get(x).add(1, Integer.toString(x + 1));
		}
		
		
	}

	private void addParenthesisFeature(ArrayList<ArrayList<String>> ar) {
		String f1;

		for(int x=0;x<ar.size();x++){
			f1 = ar.get(x).get(0);

			boolean hasCapitalized = false;
			
			for(char c : f1.toCharArray()){
				if(c == '(' || c == ')') hasCapitalized =  true;
			}

			if(hasCapitalized){
				ar.get(x).add(1, PARENTHESIS_FEATURE);
			}
		}

	}

	private void addCapitalizedFeature(ArrayList<ArrayList<String>> ar) {
		String f1;

		for(int x=0;x<ar.size();x++){
			f1 = ar.get(x).get(0);

			boolean hasCapitalized = false;
			
			for(char c : f1.toCharArray()){
				if(c >= 'A' && c <= 'Z') hasCapitalized =  true;
			}

			if(hasCapitalized){
				ar.get(x).add(1, CAPITALIZED_FEATURE);
			}
		}

	}

	private void addAllCapitalizedFeature(ArrayList<ArrayList<String>> ar) {
		String f1;

		for(int x=0;x<ar.size();x++){
			f1 = ar.get(x).get(0);

			boolean hasCapitalized = false;
			boolean hasNonCapitalized = false; 
			
			for(char c : f1.toCharArray()){
				if(c >= 'a' && c <='z') hasNonCapitalized = true;
				if(c >= 'A' && c <= 'Z') hasCapitalized =  true;
			}

			if(hasCapitalized && !hasNonCapitalized){
				ar.get(x).add(1, ALL_CAPITALIZED_FEATURE);
			}
		}
	}

	private void addDotFeature(ArrayList<ArrayList<String>> ar) {
		String f1;

		for(int x=0;x<ar.size();x++){
			f1 = ar.get(x).get(0);

			boolean hasDot = false;

			for(char c : f1.toCharArray()){
				if(c == '.') hasDot = true;
			}

			if(hasDot){
				ar.get(x).add(1, DOT_FEATURE);
			}
		}
	}

	private void addNumericalAndOtherFeature(ArrayList<ArrayList<String>> ar) {
		String f1;

		for(int x=0;x<ar.size();x++){
			f1 = ar.get(x).get(0);

			boolean hasNumerical = false;
			boolean hasOther = false;

			for(char c : f1.toCharArray()){
				if(c >= '0' && c<= '9') hasNumerical = true;
				else hasOther = true;
			}

			if(hasNumerical && hasOther){
				ar.get(x).add(1, NUMERICAL_AND_OTHER_FEATURE);
			}
		}

	}

	private void addNumericalFeature(ArrayList<ArrayList<String>> ar) {

		String f1;

		for(int x=0;x<ar.size();x++){
			f1 = ar.get(x).get(0);

			boolean hasNumerical = false;
			boolean hasOther = false;

			for(char c : f1.toCharArray()){
				if(c >= '0' && c<= '9') hasNumerical = true;
				else hasOther = true;
			}

			if(hasNumerical && !hasOther){
				ar.get(x).add(1, NUMERICAL_FEATURE);
			}
		}
	}





}
