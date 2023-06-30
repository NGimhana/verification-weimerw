package raykernel.apps.readability.detectors;

import java.util.Iterator;

import raykernel.apps.readability.code.CodeLine;
import raykernel.apps.readability.code.TrainableCodeBlock;
import raykernel.ml.feature.FeatureDetector;
import raykernel.ml.feature.StandardValueFeature;
import raykernel.ml.feature.Trainable;
import raykernel.util.Common;

/**
 * Count the number of occurences of some character in each line We could extend
 * this to strings. Done without too much overhead like you would get using
 * regular expressions.
 * 
 * @author rlb7g
 * 
 */
public class CharCountDetector implements FeatureDetector
{

	char[] chars;
	String name;

	public CharCountDetector(char... chars)
	{
		this.chars = chars;

		//only figure out the name once
		StringBuffer tmpname = new StringBuffer("");

		for (char c : chars)
		{
			tmpname.append(" '");
			tmpname.append(c);
			tmpname.append("'");
		}

		name = tmpname.toString();

		name = tmpname.toString();

	}

	int count(String s)
	{
		int i = 0;
		int count = 0;

		while (i < s.length())
		{
			for (char c : chars)
			{
				if (s.charAt(i) == c)
				{
					count++;
					break; //found a match for this char
				}
			}

			i++;
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
