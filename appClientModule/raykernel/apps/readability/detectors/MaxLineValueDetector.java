package raykernel.apps.readability.detectors;

import raykernel.apps.readability.code.CodeLine;
import raykernel.apps.readability.code.TrainableCodeBlock;
import raykernel.ml.feature.Feature;
import raykernel.ml.feature.FeatureDetector;
import raykernel.ml.feature.StandardValueFeature;
import raykernel.ml.feature.Trainable;
import raykernel.util.Common;

public class MaxLineValueDetector implements FeatureDetector
{

	FeatureDetector fd = null;

	public MaxLineValueDetector(FeatureDetector fd)
	{
		this.fd = fd;
	}

	public String featureName()
	{
		return "max " + fd.featureNames();
	}

	public String[] featureNames()
	{
		return Common.makeSingletonArray(featureName());
	}
	public void runDetector(Trainable b)
	{
		TrainableCodeBlock block = (TrainableCodeBlock) b;

		fd.runDetector(block);

		float max = 0;

		for (CodeLine l : block)
		{
			Feature f = l.getFeature(fd.featureNames()[0]);

			if (f != null)
			{
				if (f.value() > max)
				{
					max = f.value();
				}
			}
		}

		block.setFeature(new StandardValueFeature(featureName(), max));
	}
}
