package raykernel.apps.readability.bugdetectors;

import raykernel.apps.readability.code.CodeLine;
import raykernel.apps.readability.code.TrainableCodeBlock;
import raykernel.ml.feature.FeatureDetector;
import raykernel.ml.feature.StandardValueFeature;
import raykernel.ml.feature.Trainable;
import raykernel.util.Common;

/**
 * Simply detects the presence of the string 'bug'
 * 
 * @author rlb7g
 * 
 */
public class SimpleBugDetector implements FeatureDetector
{

	String name = "simple_bug";

	public String[] featureNames()
	{
		return Common.makeSingletonArray("simple_bug");
	}

	public void runDetector(Trainable b)
	{

		TrainableCodeBlock block = (TrainableCodeBlock) b;

		for (CodeLine l : block)
		{
			boolean bug = l.toString().contains("bug");
			if (bug)
			{
				l.setFeature(new StandardValueFeature(name, true));
			}
			else
			{
				l.setFeature(new StandardValueFeature(name, false));
			}

		}

	}

}
