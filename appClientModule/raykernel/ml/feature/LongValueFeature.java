package raykernel.ml.feature;

public class LongValueFeature extends Feature
{

	private final String name;
	private final long value;

	public LongValueFeature(String name, long value)
	{
		this.name = name;
		this.value = value;
	}

	public long longValue()
	{
		// TODO Auto-generated method stub
		return value;
	}

	public String name()
	{
		return name;
	}

	public float value()
	{
		// TODO Auto-generated method stub
		return value;
	}

}
