package raykernel.apps.readability.detectors;

import raykernel.ml.feature.DetectorSuite;

/**
 * A Wrapper around a set of FeatureDetectors
 * 
 * @author rlb7g
 * 
 */
public class ReadabilityDetectorSuite
{

	private static DetectorSuite dsuite = null;

	public static DetectorSuite getDefaultSuite()
	{

		if (dsuite != null)
			return dsuite;

		DetectorSuite dsuite = new DetectorSuite();

		dsuite.add(new AvgLineValueDetector(new LineLengthDetector())); //avg line length (chars)
		dsuite.add(new AvgLineValueDetector(new WordCountDetector())); //avg line length (words)
		dsuite.add(new AvgLineValueDetector(new NumKeyWordsDetector()));
		dsuite.add(new AvgLineValueDetector(new NumNumsDetector()));
		dsuite.add(new AvgLineValueDetector(new IndentDetector())); //average indentation 
		dsuite.add(new AvgWordLengthDetector()); //avg word length
		dsuite.add(new AvgLineValueDetector(new BasicCommentDetector())); //comment density ***
		dsuite.add(new AvgLineValueDetector(new BlankLineDetector(), false));

		dsuite.add(new MaxLineValueDetector(new LineLengthDetector())); //max line length (chars)
		dsuite.add(new MaxLineValueDetector(new WordCountDetector())); //max line length (words)

		dsuite.add(new MaxLineValueDetector(new NumKeyWordsDetector())); //***
		dsuite.add(new MaxLineValueDetector(new NumNumsDetector())); //***

		dsuite.add(new MaxLineValueDetector(new IndentDetector())); //max indentation ***
		dsuite.add(new MaxWordLengthDetector()); //max word length ***

		dsuite.add(new AvgLineValueDetector(new CharCountDetector('.')));
		dsuite.add(new AvgLineValueDetector(new CharCountDetector(',')));

		//dsuite.add( new AvgLineValueDetector( new CharCountDetector( '.',',' )));

		dsuite.add(new AvgLineValueDetector(new CharCountDetector('(', '{'))); // punct
		dsuite.add(new AvgLineValueDetector(new CharCountDetector(' '))); // spaces
		dsuite.add(new AvgLineValueDetector(new CharCountDetector('+', '*', '%', '/', '-'))); // math
		dsuite.add(new AvgLineValueDetector(new CharCountDetector('='))); // assign ***
		dsuite.add(new AvgLineValueDetector(new SubstrCountDetector("==", "<", ">"))); // compare
		dsuite.add(new AvgLineValueDetector(new SubstrCountDetector("if"))); // compare
		dsuite.add(new AvgLineValueDetector(new SubstrCountDetector("for", "while"))); // compare ***

		dsuite.add(new MaxOccurencesOfSingleChar()); //max single char ***
		dsuite.add(new MaxOccurencesOfSingleWord()); //max single word

		return dsuite;
	}

}
