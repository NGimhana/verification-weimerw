package raykernel.apps.readability.snippet;

import java.io.File;

import raykernel.ml.feature.BinClassFeature;
import raykernel.ml.feature.FeatureDetector;
import raykernel.ml.feature.Trainable;
import raykernel.util.Common;

public class SnippetScoreFeatureDetector implements FeatureDetector
{

	File scoreFile;
	ScoreLog scoreLog;
	float threshold;

	public SnippetScoreFeatureDetector(File scoreFile, float cutoff)
	{
		this.scoreFile = scoreFile;
		this.threshold = cutoff;

		scoreLog = new ScoreLog();
		scoreLog.readScores(scoreFile);
	}

	public String featureName()
	{
		return "snippetscore";
	}

	public String[] featureNames()
	{
		return Common.makeSingletonArray("snippetscore");
	}
	public void runDetector(Trainable block)
	{

		if (!(block instanceof Snippet))
			throw new IllegalArgumentException("Can only run score detector on snippets!");

		Snippet s = (Snippet) block;

		Score score = scoreLog.getScore(s.getNumber());

		//io.Out.println("score for snippet #" + s.getNumber() + ":" + score.getScore());

		if (score == null)
		{
			//s.setFeature( new BinClassFeature(  ));
		}
		else
		{
			s.setFeature(new BinClassFeature("snippetscore", score.getScore() > threshold));
		}

		//io.Out.println("snippet #" + s.getNumber() + " class = " + s.getFeature(featureName()) );

		//System.out.println(s.codeableString() + ",\n");
		//System.out.println((int) score.getScore() + ",\n");
	}

}
