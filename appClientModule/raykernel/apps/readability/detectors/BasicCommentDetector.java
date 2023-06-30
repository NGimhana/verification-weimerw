package raykernel.apps.readability.detectors;

import raykernel.apps.readability.code.CodeLine;
import raykernel.apps.readability.code.TrainableCodeBlock;
import raykernel.ml.feature.FeatureDetector;
import raykernel.ml.feature.StandardValueFeature;
import raykernel.ml.feature.Trainable;
import raykernel.util.Common;

public class BasicCommentDetector implements FeatureDetector
{

	public String featureName()
	{
		return "comment";
	}

	public String[] featureNames()
	{
		return Common.makeSingletonArray(featureName());
	}

	public void runDetector(Trainable b)
	{
		TrainableCodeBlock block = (TrainableCodeBlock) b;

		boolean inComment = false;

		for (CodeLine l : block)
		{
			inComment = updateCommentStatus(l.toString(), inComment);

			boolean comment = l.toString().trim().startsWith("//") || inComment || l.toString().contains("*/");
			l.setFeature(new StandardValueFeature(featureName(), comment));
		}

	}

	boolean updateCommentStatus(String s, boolean current)
	{
		boolean start = s.contains("/*");
		boolean end = s.contains("*/");

		if (start && !end)
			return true;

		if (!start && end)
			return false;

		return current;
	}

}