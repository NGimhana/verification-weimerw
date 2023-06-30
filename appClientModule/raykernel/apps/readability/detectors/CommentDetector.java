package raykernel.apps.readability.detectors;

import java.util.regex.Pattern;

import raykernel.apps.readability.code.TrainableCodeBlock;
import raykernel.ml.feature.FeatureDetector;
import raykernel.ml.feature.Trainable;
import raykernel.util.Common;

/**
 * Detects Comments... Regular expression for this comes from
 * <http://ostermiller.org/findcomment.html>
 * 
 */

public class CommentDetector implements FeatureDetector
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

		RegularFeatureDetector commentDetector = new RegularFeatureDetector("comment");
		commentDetector.addPattern(Pattern.compile("(/\\*(?:.|[\\n\\r])*?\\*/)|(//.*)"));

		commentDetector.runDetector(block);

	}
}
