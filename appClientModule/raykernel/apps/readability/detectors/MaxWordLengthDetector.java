package raykernel.apps.readability.detectors;

import java.util.List;

import raykernel.apps.readability.code.CodeLine;
import raykernel.apps.readability.code.CodeLineProcessor;
import raykernel.apps.readability.code.TrainableCodeBlock;
import raykernel.ml.feature.FeatureDetector;
import raykernel.ml.feature.StandardValueFeature;
import raykernel.ml.feature.Trainable;
import raykernel.util.Common;

public class MaxWordLengthDetector implements FeatureDetector
{

	public String featureName()
	{
		return "maxwordlength";
	}

	public String[] featureNames()
	{
		return Common.makeSingletonArray(featureName());
	}
	public void runDetector(Trainable b)
	{
		TrainableCodeBlock block = (TrainableCodeBlock) b;

		String maxWord = ""; //keep track of this for fun
		int maxLength = 0;

		for (CodeLine l : block)
		{
			List<String> words = CodeLineProcessor.getWords(l);

			for (String word : words)
			{
				if (CodeLineProcessor.isNumber(word) || CodeLineProcessor.isKeyWord(word))
				{
					continue;
				}

				int len = word.length();

				if (len > maxLength)
				{
					maxLength = len;
					maxWord = word;
				}
			}
		}

		block.setFeature(new StandardValueFeature(featureName(), maxLength));

		//io.Out.println("max word = " + maxWord + " - " + maxLength);
	}
}