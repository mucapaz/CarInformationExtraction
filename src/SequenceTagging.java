import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import cc.mallet.fst.SimpleTagger;

public class SequenceTagging {
	
	public static void main(String[] args) throws Exception{
		
		/*
		 * Create 
		 */
		
//		String ar1[] = {"--train", "true", "--model-file", "carCrf","baseFile"};
//		SimpleTagger.main(ar1);
		
		String ar2[] = {"--model-file", "carCrf",  "test/processed/1"};
		
		
		  // Create a stream to hold the output
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    PrintStream ps = new PrintStream(baos);
	    
	    
	    // IMPORTANT: Save the old System.out!
	    PrintStream old = System.out;
	    
	    
	    // Tell Java to use your special stream
	    System.setOut(ps);
	    
	    SimpleTagger.main(ar2);
		SimpleTagger.main(ar2);
	    
	    // Print some output: goes to your special stream
	    System.out.println("Foofoofoo!");
	    
	    // Put things back
	    System.out.flush();
	    System.setOut(old);
	    // Show what happened
	    System.out.println("Here: " + baos.toString());
	    
	    
		
		
	}
	
}
