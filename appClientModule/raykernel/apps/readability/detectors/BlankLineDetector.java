package raykernel.apps.readability.detectors;

import raykernel.apps.readability.code.CodeLine;
import raykernel.apps.readability.code.TrainableCodeBlock;
import raykernel.ml.feature.FeatureDetector;
import raykernel.ml.feature.StandardValueFeature;
import raykernel.ml.feature.Trainable;
import raykernel.util.Common;

public class BlankLineDetector implements FeatureDetector
{

	public String featureName()
	{
		return "blank lines";
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
			boolean blank = l.toString().trim().length() == 0;
			l.setFeature(new StandardValueFeature(featureName(), blank));
		}

	}
}
