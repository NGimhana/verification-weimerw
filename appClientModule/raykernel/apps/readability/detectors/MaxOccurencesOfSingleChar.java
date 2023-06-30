package raykernel.apps.readability.detectors;

import raykernel.apps.readability.code.CodeLine;
import raykernel.apps.readability.code.TrainableCodeBlock;
import raykernel.ml.feature.FeatureDetector;
import raykernel.ml.feature.StandardValueFeature;
import raykernel.ml.feature.Trainable;
import raykernel.util.CharCounter;
import raykernel.util.Common;

public class MaxOccurencesOfSingleChar implements FeatureDetector
{

	public String featureName()
	{
		return "max char";
	}

	public String[] featureNames()
	{
		return Common.makeSingletonArray(featureName());
	}
	public void runDetector(Trainable b)
	{
		TrainableCodeBlock block = (TrainableCodeBlock) b;

		CharCounter counter = new CharCounter();

		for (CodeLine l : block)
		{
			counter.count(l.toString());
		}

		block.setFeature(new StandardValueFeature(featureName(), counter.getMaxVal()));

		//io.Out.println("max char: " + counter.getMaxChar() + " - " + counter.getMaxVal() );
	}
}
