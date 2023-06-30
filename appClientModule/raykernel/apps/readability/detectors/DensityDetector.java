package raykernel.apps.readability.detectors;

import java.util.Iterator;

import raykernel.apps.readability.code.CodeLine;
import raykernel.apps.readability.code.TrainableCodeBlock;
import raykernel.ml.feature.FeatureDetector;
import raykernel.ml.feature.StandardValueFeature;
import raykernel.ml.feature.Trainable;
import raykernel.util.Common;

public class DensityDetector implements FeatureDetector
{

	public String featureName()
	{
		return "density";
	}

	public String[] featureNames()
	{
		return Common.makeSingletonArray(featureName());
	}
	public void runDetector(Trainable b)
	{
		TrainableCodeBlock block = (TrainableCodeBlock) b;

		int start = -2;
		int end = 2;

		for (CodeLine l : block)
		{
			float total = 0;

			Iterator<CodeLine> lines = block.getLines(start, end);
			while (lines.hasNext())
			{
				total += lines.next().toString().length();
			}

			l.setFeature(new StandardValueFeature(featureName(), total));
		}

	}
}