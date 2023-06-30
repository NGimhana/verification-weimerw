package raykernel.apps.readability.detectors;

import java.util.List;

import raykernel.apps.readability.code.CodeLine;
import raykernel.apps.readability.code.CodeLineProcessor;
import raykernel.apps.readability.code.TrainableCodeBlock;
import raykernel.ml.feature.FeatureDetector;
import raykernel.ml.feature.StandardValueFeature;
import raykernel.ml.feature.Trainable;
import raykernel.util.Common;

public class NumNumsDetector implements FeatureDetector
{

	public String featureName()
	{
		return "numbers";
	}

	public String[] featureNames()
	{
		return Common.makeSingletonArray(featureName());
	}
	public void runDetector(Trainable b)
	{
		TrainableCodeBlock block = (TrainableCodeBlock) b;

		for (CodeLine l : block)
		{
			List<String> words = CodeLineProcessor.getWords(l);

			int count = 0;

			for (String word : words)
			{
				if (CodeLineProcessor.isNumber(word))
				{
					count++;
				}

			}

			l.setFeature(new StandardValueFeature(featureName(), count));
		}

	}
}