package raykernel.apps.readability.detectors;

import java.util.regex.Pattern;

import raykernel.apps.readability.code.TrainableCodeBlock;
import raykernel.ml.feature.FeatureDetector;
import raykernel.ml.feature.Trainable;
import raykernel.util.Common;

public class JavaDocDetector implements FeatureDetector
{

	public String featureName()
	{
		return "javaDoc";
	}

	public String[] featureNames()
	{
		return Common.makeSingletonArray(featureName());
	}
	public void runDetector(Trainable b)
	{
		TrainableCodeBlock block = (TrainableCodeBlock) b;

		RegularFeatureDetector commentDetector = new RegularFeatureDetector(featureName());
		commentDetector.addPattern(Pattern.compile("/\\*\\*(?:.|[\\n\\r])*?\\*/"));

		commentDetector.runDetector(block);

	}
}
