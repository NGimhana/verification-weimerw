package raykernel.apps.readability.detectors;

import java.util.Iterator;

import raykernel.apps.readability.code.CodeLine;
import raykernel.apps.readability.code.TrainableCodeBlock;
import raykernel.ml.feature.FeatureDetector;
import raykernel.ml.feature.StandardValueFeature;
import raykernel.ml.feature.Trainable;
import raykernel.util.Common;

public class SubstrCountDetector implements FeatureDetector
{

	String name;
	String[] strs;

	public SubstrCountDetector(String... strs)
	{
		this.strs = strs;

		//only figure out the name once
		StringBuffer tmpname = new StringBuffer("");

		for (String s : strs)
		{
			tmpname.append(" '");
			tmpname.append(s);
			tmpname.append("'");
		}

		name = tmpname.toString();

	}

	int count(String s)
	{
		int count = 0;

		for (String str : strs) //for each string we're looking for
		{
			int lastFound = 0;

			while (true)
			{
				lastFound = s.indexOf(str, lastFound); //find next occurance

				if (lastFound < 0) //keep going til we dont find another
				{
					break;
				}
				else
				{
					count++; //found one
					lastFound += str.length();
				}
			}
		}

		return count;
	}

	public String featureName()
	{
		return name;
	}

	public String[] featureNames()
	{
		return Common.makeSingletonArray(featureName());
	}
	public void runDetector(Trainable b)
	{
		TrainableCodeBlock block = (TrainableCodeBlock) b;

		Iterator<CodeLine> iter = block.iterator();
		while (iter.hasNext())
		{
			CodeLine l = iter.next();
			l.setFeature(new StandardValueFeature(name, count(l.toString())));
		}

	}
}
