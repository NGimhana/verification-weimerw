package raykernel.apps.readability.detectors;

import raykernel.apps.readability.code.CodeLine;
import raykernel.apps.readability.code.TrainableCodeBlock;
import raykernel.ml.feature.Feature;
import raykernel.ml.feature.FeatureDetector;
import raykernel.ml.feature.StandardValueFeature;
import raykernel.ml.feature.Trainable;
import raykernel.util.Common;
import raykernel.util.Predicate;

/**
 * Percent of lines in the block satisfying the predicate or with feature
 * 
 * @author buse
 * 
 */
public class PercentOfLinesDetector implements FeatureDetector
{

	FeatureDetector fd = null;
	boolean ignoreBlanks = false;
	Predicate<CodeLine> predicate = null;

	public PercentOfLinesDetector(FeatureDetector fd, boolean ignoreBlanks)
	{
		this.fd = fd;
		this.ignoreBlanks = ignoreBlanks;
	}

	public PercentOfLinesDetector(Predicate<CodeLine> predicate, boolean ignoreBlanks)
	{
		this.predicate = predicate;
		this.ignoreBlanks = ignoreBlanks;
	}

	public String featureName()
	{
		return "percent wtih " + fd.featureNames()[0];
	}

	public String[] featureNames()
	{
		return Common.makeSingletonArray(featureName());
	}
	public void runDetector(Trainable b)
	{
		TrainableCodeBlock block = (TrainableCodeBlock) b;

		fd.runDetector(block);

		int countAll = 0;
		int countHasFeature = 0;

		for (CodeLine l : block)
		{
			if (ignoreBlanks && l.toString().trim().length() == 0) //if blank, and we are ignoring them
			{
				continue;
			}

			Feature f = l.getFeature(fd.featureNames()[0]);

			if (f != null && fd != null && f.value() > 0)
			{
				countHasFeature++;
			}
			else if (predicate != null && predicate.getBoolean(l))
			{
				countHasFeature++;
			}

			countAll++;
		}

		float percent = (float) countHasFeature / (float) countAll;

		block.setFeature(new StandardValueFeature(featureName(), percent));
	}
}
