package raykernel.apps.readability.detectors;

import java.util.Iterator;

import raykernel.apps.readability.code.CodeLine;
import raykernel.apps.readability.code.TrainableCodeBlock;
import raykernel.ml.feature.FeatureDetector;
import raykernel.ml.feature.StandardValueFeature;
import raykernel.ml.feature.Trainable;
import raykernel.util.Common;

public class LineLengthDetector implements FeatureDetector
{

	public String featureName()
	{
		return "line length";
	}

	public String[] featureNames()
	{
		return Common.makeSingletonArray(featureName());
	}
	public void runDetector(Trainable b)
	{
		TrainableCodeBlock block = (TrainableCodeBlock) b;

		Iterator<CodeLine> iter = block.iterator();
		while (iter.hasNext())
		{
			CodeLine l = iter.next();
			l.setFeature(new StandardValueFeature(featureName(), l.toString().trim().length()));
		}

	}
}
