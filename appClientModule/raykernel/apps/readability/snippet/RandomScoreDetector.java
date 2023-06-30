package raykernel.apps.readability.snippet;

import raykernel.apps.readability.code.TrainableCodeBlock;
import raykernel.ml.feature.FeatureDetector;
import raykernel.ml.feature.StandardValueFeature;
import raykernel.ml.feature.Trainable;
import raykernel.util.Common;

public class RandomScoreDetector implements FeatureDetector
{

	int numOne;
	int total;

	public RandomScoreDetector(int numOne)
	{
		this.numOne = numOne;
	}

	public String featureName()
	{
		return "randscore";
	}

	public String[] featureNames()
	{
		return Common.makeSingletonArray(featureName());
	}
	public void runDetector(Trainable b)
	{
		TrainableCodeBlock block = (TrainableCodeBlock) b;

		float score = 0;

		if (total < numOne)
		{
			score = 1;
		}

		block.setFeature(new StandardValueFeature(featureName(), score));

		total++;
	}

}
