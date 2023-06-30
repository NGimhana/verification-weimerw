package raykernel.apps.readability.detectors;

import raykernel.apps.readability.code.CodeLine;
import raykernel.apps.readability.code.TrainableCodeBlock;
import raykernel.ml.feature.Feature;
import raykernel.ml.feature.FeatureDetector;
import raykernel.ml.feature.StandardValueFeature;
import raykernel.ml.feature.Trainable;
import raykernel.util.Common;

/**
 * Adapts a line wise detector to operate on a whole block.
 * 
 * Avg value (not counting blank lines)
 * 
 * @author buse
 * 
 */
public class AvgLineValueDetector implements FeatureDetector
{

	FeatureDetector fd = null;
	String fName;

	boolean ignoreBlanks = true;

	public AvgLineValueDetector(FeatureDetector detector)
	{
		this.fd = detector;
		fName = fd.featureNames()[0];
	}

	public AvgLineValueDetector(FeatureDetector detector, boolean ignoreBlanks)
	{
		this.fd = detector;
		this.ignoreBlanks = ignoreBlanks;
		fName = fd.featureNames()[0];
	}

	public AvgLineValueDetector(String fName)
	{
		this.fName = fName;
		ignoreBlanks = false;
	}

	public String[] featureNames()
	{
		return Common.makeSingletonArray("avg " + fName);
	}

	public void runDetector(Trainable b)
	{
		TrainableCodeBlock block = (TrainableCodeBlock) b;

		if (fd != null)
		{
			fd.runDetector(block);
		}

		//total scores
		float total = 0;
		for (CodeLine l : block)
		{
			if (ignoreBlanks && l.toString().trim().length() == 0) //if blank, and we are ignoring them
			{
				continue;
			}

			Feature f = l.getFeature(fName);

			if (f != null)
			{
				total += f.value();
			}
		}

		//divide by lines
		float score = total / block.size();

		block.setFeature(new StandardValueFeature("avg " + fName, score));
	}

}
