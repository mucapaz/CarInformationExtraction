import cc.mallet.fst.SimpleTagger;

public class SequenceTagging {
	
	public static void main(String[] args) throws Exception{
		String ar1[] = {"--train", "true", "--model-file", "carCrf","baseFile"};
	
		SimpleTagger.main(ar1);
		
		String ar2[] = {"--model-file", "carCrf",  "1"};
		
		SimpleTagger.main(ar2);
	}
	
}
