package raykernel.apps.readability.detectors;

import java.util.ListIterator;
import java.util.StringTokenizer;

import raykernel.apps.readability.code.CodeLine;
import raykernel.apps.readability.code.TrainableCodeBlock;
import raykernel.ml.feature.FeatureDetector;
import raykernel.ml.feature.StandardValueFeature;
import raykernel.ml.feature.Trainable;
import raykernel.util.Common;

public class FunctionReturnTypeDetector implements FeatureDetector
{

	static float getFloatForReturnType(String type)
	{
		if (type.equals("void"))
			return 0;

		if (type.equals("boolean") || type.equals("int") || type.equals("float") || type.equals("char")
				|| type.equals("double"))
			return 1;

		return 3;
	}

	//this uses tokenizers, but it would be better (probably) to go
	//ahead and use regexps
	public static String getRetType(String def)
	{
		StringTokenizer tokenizer = new StringTokenizer(def, " \t()/\\*");
		String token = null;

		while (tokenizer.hasMoreTokens())
		{
			token = tokenizer.nextToken();

			if (!token.equals("public") && !token.equals("private") && !token.equals("protected")
					&& !token.equals("static"))
			{
				break;
			}
		}

		if (token == null)
			return null;

		return token;
	}

	static int netDepthChange(String s)
	{
		int ret = 0;

		for (int i = 0; i < s.length(); i++)
		{
			if (s.charAt(i) == '{')
			{
				ret++;
			}

			if (s.charAt(i) == '}')
			{
				ret--;
			}
		}

		return ret;
	}

	public String featureName()
	{
		return "ret_type";
	}

	public String[] featureNames()
	{
		return Common.makeSingletonArray(featureName());
	}
	public void runDetector(Trainable b)
	{
		TrainableCodeBlock block = (TrainableCodeBlock) b;

		int depth = 0;
		String curr_ret = null;

		ListIterator<CodeLine> iter = block.iterator();

		while (iter.hasNext())
		{
			CodeLine l = iter.next();

			if (depth == 1 && l.length() > 3) //inside a class
			{
				//look for a function declairation
				String tmp = getRetType(l.toString());
				if (tmp != null)
				{
					curr_ret = tmp;
				}
			}

			depth += netDepthChange(l.toString());

			//depth >= 2 => we are inside a function
			if (depth >= 2)
			{
				l.setFeature(new StandardValueFeature(featureName(), getFloatForReturnType(curr_ret)));
			}
			else
			{
				l.setFeature(new StandardValueFeature(featureName(), 0));
			}
		}

	}
}
