package raykernel.apps.readability.detectors;

import java.util.List;

import raykernel.apps.readability.code.CodeLine;
import raykernel.apps.readability.code.CodeLineProcessor;
import raykernel.apps.readability.code.TrainableCodeBlock;
import raykernel.ml.feature.FeatureDetector;
import raykernel.ml.feature.StandardValueFeature;
import raykernel.ml.feature.Trainable;
import raykernel.util.Common;
import raykernel.util.WordCounter;

public class MaxOccurencesOfSingleWord implements FeatureDetector
{

	public String featureName()
	{
		return "max word";
	}

	public String[] featureNames()
	{
		return Common.makeSingletonArray(featureName());
	}
	public void runDetector(Trainable b)
	{
		TrainableCodeBlock block = (TrainableCodeBlock) b;

		WordCounter counter = new WordCounter();

		for (CodeLine l : block)
		{
			List<String> words = CodeLineProcessor.getWords(l);

			for (String w : words)
			{
				counter.countWord(w);
			}
		}

		block.setFeature(new StandardValueFeature(featureName(), counter.getMaxVal()));

		//io.Out.println("max word: " + counter.getMaxWord() + " - " + counter.getMaxVal() );

	}
}