package raykernel.apps.readability.detectors;

import java.util.ListIterator;

import raykernel.apps.readability.code.CodeLine;
import raykernel.apps.readability.code.TrainableCodeBlock;
import raykernel.ml.feature.FeatureDetector;
import raykernel.ml.feature.StandardValueFeature;
import raykernel.ml.feature.Trainable;
import raykernel.util.Common;

/**
 * I belive this is theroetically impossible to do with regular expressions
 * 
 * @author rlb7g
 * 
 */
public class FunctionDetector implements FeatureDetector
{

	public static int netDepthChange(String s)
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
		return "fun";
	}

	public String[] featureNames()
	{
		return Common.makeSingletonArray(featureName());
	}
	public void runDetector(Trainable b)
	{
		TrainableCodeBlock block = (TrainableCodeBlock) b;

		float depth = 0;
		float class_len = 0;
		float fun_len = 0;

		ListIterator<CodeLine> iter = block.iterator();

		//forward
		while (iter.hasNext())
		{
			CodeLine l = iter.next();
			depth += netDepthChange(l.toString());

			if (depth == 0)
			{
				class_len = 0;
				fun_len = 0;
			}
			else if (depth == 1)
			{
				fun_len = 0;
			}

			if (depth > 0)
			{
				class_len++;

				if (depth > 1)
				{
					fun_len++;
				}
			}

			l.setFeature(new StandardValueFeature("depth", depth));
			l.setFeature(new StandardValueFeature("class", class_len));
			l.setFeature(new StandardValueFeature("fun", fun_len));
		}

		class_len = 0;
		fun_len = 0;

		//reverse
		while (iter.hasPrevious())
		{
			CodeLine l = iter.previous();

			float curr_class_len = l.getFeature("class").value();
			float curr_fun_len = l.getFeature("fun").value();

			if (curr_class_len == 0)
			{
				class_len = 0;
			}

			if (curr_fun_len == 0)
			{
				fun_len = 0;
			}

			if (curr_class_len > class_len)
			{
				class_len = curr_class_len;
			}

			if (curr_fun_len > fun_len)
			{
				fun_len = curr_fun_len;
			}

			l.setFeature(new StandardValueFeature("class", class_len));
			l.setFeature(new StandardValueFeature("fun", fun_len));

		}

	}
}
