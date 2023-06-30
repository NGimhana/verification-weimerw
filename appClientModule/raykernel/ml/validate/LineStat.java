package raykernel.ml.validate;

import raykernel.ml.feature.Trainable;

public class LineStat
{

	private final String scoreFeature, bugFeature; //score given by bug finder
	private final Trainable thing;

	LineStat(Trainable thing, String scoreFeature, String bugFeature)
	{
		this.scoreFeature = scoreFeature;
		this.bugFeature = bugFeature;
		this.thing = thing;
	}

	public String getBugFeature()
	{
		return this.bugFeature;
	}

	public float getScore()
	{
		return thing.getFeature(scoreFeature).value();
	}

	public String getScoreFeature()
	{
		return this.scoreFeature;
	}

	public Trainable getTrainable()
	{
		return thing;
	}

	public boolean isBug()
	{
		return thing.getFeature(bugFeature).value() > 0;
	}

	public String toString()
	{
		String name = thing.toString();

		if (isBug())
			return name + "\t" + getScore() + "\tless";

		return name + "\t" + getScore() + "\tmore";
	}

}
