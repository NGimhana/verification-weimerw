package raykernel.ml.core;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;

public class UniformClassifier extends Classifier
{
	double guess = 0;

	public void buildClassifier(Instances arg0) throws Exception
	{
		// do nothing
	}

	public double classifyInstance(Instance i)
	{
		return guess;
	}

	public double[] distributionForInstance(Instance i)
	{
		double[] ret = {guess, 1 - guess};

		return ret;
	}
	public String getRevision()
	{
		// TODO Auto-generated method stub
		return "rev 1";
	}

}