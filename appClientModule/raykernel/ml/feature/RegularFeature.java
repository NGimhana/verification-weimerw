package raykernel.ml.feature;

public class RegularFeature extends Feature
{

	boolean hasFeature;
	String name;

	public RegularFeature(String regex, boolean hasFeature)
	{
		name = regex;
		this.hasFeature = hasFeature;
	}

	public String name()
	{
		return name;
	}

	public float value()
	{
		if (hasFeature)
			return 1;
		return 0;
	}

}
