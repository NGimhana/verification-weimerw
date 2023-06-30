package raykernel.ml.feature;

import java.util.HashMap;

import weka.core.Attribute;

public abstract class Feature
{

	static HashMap<String, Integer> attrIntMap = new HashMap<String, Integer>();

	//every different feature has an associated weka attribute
	static HashMap<String, Attribute> attrMap = new HashMap<String, Attribute>();

	static int count = 1;

	public static Attribute getAttribute(String fName)
	{
		Attribute a = attrMap.get(fName);
		if (a != null)
			return a;

		a = new Attribute(fName);
		attrMap.put(fName, a);

		return a;
	}

	public static Integer getAttributeNumber(String fName)
	{
		Integer a = attrIntMap.get(fName);
		if (a != null)
			return a;

		a = new Integer(count++);
		attrIntMap.put(fName, a);

		return a;
	}

	/**
	 * gets the weka attribute for this feature
	 */
	public Attribute getAttribute()
	{
		Attribute a = attrMap.get(name());
		if (a == null)
		{

			a = new Attribute(name());
			attrMap.put(name(), a);
		}

		//io.Out.println("attribute: " + a.name() + " : " + a.index());

		return a;
	}

	/**
	 * gets the weka attribute for this feature
	 */
	public int getAttributeNumber()
	{

		Integer a = attrIntMap.get(name());
		if (a != null)
			return a;

		a = new Integer(count++);
		attrIntMap.put(name(), a);

		return a;
	}

	public abstract String name();

	public String toString()
	{
		return name() + "=" + value();
	}

	public abstract float value();

}
