package raykernel.apps.readability.detectors;

import java.util.Iterator;

import raykernel.apps.readability.code.CodeLine;
import raykernel.apps.readability.code.TrainableCodeBlock;
import raykernel.ml.feature.FeatureDetector;
import raykernel.ml.feature.StandardValueFeature;
import raykernel.ml.feature.Trainable;
import raykernel.util.Common;

public class IndentDetector implements FeatureDetector
{

	public String featureName()
	{
		return "indent";
	}

	public String[] featureNames()
	{
		return Common.makeSingletonArray(featureName());
	}

	int leadingBlanks(String s)
	{
		int i = 0;
		int blanks = 0;

		while (i < s.length())
		{
			if (s.charAt(i) == ' ')
			{
				blanks++;
			}
			else if (s.charAt(i) == '\t')
			{
				blanks += 4; //might add more than just 1?
			}
			else
			{
				break;
			}

			i++;
		}

		return blanks;
	}
	public void runDetector(Trainable b)
	{
		TrainableCodeBlock block = (TrainableCodeBlock) b;

		Iterator<CodeLine> iter = block.iterator();
		while (iter.hasNext())
		{
			CodeLine l = iter.next();
			l.setFeature(new StandardValueFeature(featureName(), leadingBlanks(l.toString())));
		}

	}
}
