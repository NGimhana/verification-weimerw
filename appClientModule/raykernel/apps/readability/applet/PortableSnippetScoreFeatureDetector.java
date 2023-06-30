package raykernel.apps.readability.applet;

import raykernel.apps.readability.snippet.Snippet;
import raykernel.ml.feature.BinClassFeature;
import raykernel.ml.feature.FeatureDetector;
import raykernel.ml.feature.Trainable;
import raykernel.util.Common;

public class PortableSnippetScoreFeatureDetector implements FeatureDetector
{

	public String[] featureNames()
	{
		return Common.makeSingletonArray("snippetscore");
	}
	public void runDetector(Trainable block)
	{
		if (!(block instanceof Snippet))
			throw new IllegalArgumentException("Can only run score detector on snippets!");

		Snippet s = (Snippet) block;

		float score = (float) PortableData.scores[s.getNumber()];

		s.setFeature(new BinClassFeature(featureNames()[0], score > 0.5));
	}

}
