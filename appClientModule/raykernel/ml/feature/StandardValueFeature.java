package raykernel.ml.feature;

public class StandardValueFeature extends Feature
{

	String name;
	float val;

	public StandardValueFeature(String name, boolean b)
	{

		this.name = name;

		if (b)
		{
			this.val = 1;
		}
		else
		{
			this.val = 0;
		}
	}

	public StandardValueFeature(String name, float val)
	{
		this.val = val;
		this.name = name;
	}

	public String name()
	{
		return name;
	}

	public float value()
	{
		return val;
	}

}
