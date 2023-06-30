package raykernel.ml.feature;

import java.util.LinkedList;
import java.util.List;

import weka.core.Attribute;
import weka.core.FastVector;

public class BugFeature extends Feature
{

	static Attribute attr;

	LinkedList<String> bugs = new LinkedList<String>();

	public BugFeature()
	{
		//by default no bug
	}

	public List<String> bugNames()
	{
		return this.bugs;
	}

	public Attribute getAttribute()
	{
		if (attr != null)
			return attr;

		FastVector classVal = new FastVector(2);
		classVal.addElement("negative");
		classVal.addElement("positive");

		attr = new Attribute("bug", classVal);
		return attr;

	}

	public String name()
	{
		return "bug";
	}

	public void recordBug(String name)
	{
		bugs.add(name);
	}

	public float value()
	{

		return bugs.size();

	}

}
