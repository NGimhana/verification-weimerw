package raykernel.ml.feature;

public class BinClassFeature extends Feature
{

	float classNum;
	boolean hasClass = false;
	String name = "bin_class";

	public BinClassFeature()
	{
		classNum = -1;
	}

	public BinClassFeature(boolean classOne)
	{
		if (classOne)
		{
			classNum = 1;
		}
		else
		{
			classNum = 0;
		}
	}

	public BinClassFeature(String string, boolean classOne)
	{
		name = string;

		if (classOne)
		{
			classNum = 1;
		}
		else
		{
			classNum = 0;
		}
	}

	public boolean hasClass()
	{
		return hasClass;
	}

	public String name()
	{
		return name;
	}

	public float value()
	{
		return classNum;
	}

}
