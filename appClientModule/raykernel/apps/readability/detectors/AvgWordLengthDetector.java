package raykernel.apps.readability.detectors;

import java.util.List;

import raykernel.apps.readability.code.CodeLine;
import raykernel.apps.readability.code.CodeLineProcessor;
import raykernel.apps.readability.code.TrainableCodeBlock;
import raykernel.ml.feature.FeatureDetector;
import raykernel.ml.feature.StandardValueFeature;
import raykernel.ml.feature.Trainable;
import raykernel.util.Common;

public class AvgWordLengthDetector implements FeatureDetector
{

	public String featureName()
	{
		return "avg identifier length";
	}

	public String[] featureNames()
	{
		return Common.makeSingletonArray(featureName());
	}
	public void runDetector(Trainable b)
	{
		TrainableCodeBlock block = (TrainableCodeBlock) b;

		float totalWords = 0;
		float totalLength = 0;

		for (CodeLine l : block)
		{
			List<String> words = CodeLineProcessor.getWords(l);

			for (String word : words)
			{
				if (CodeLineProcessor.isNumber(word) || CodeLineProcessor.isKeyWord(word))
				{
					continue;
				}

				totalWords++;
				totalLength += word.length();
			}
		}

		block.setFeature(new StandardValueFeature(featureName(), totalLength / totalWords));

		//io.Out.println("avg word len = " + totalLength/totalWords);
	}
}
