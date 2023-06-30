package raykernel.ml.core;

import java.util.Random;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;

public class RandomClassifier extends Classifier
{
	Random r = new Random();

	public void buildClassifier(Instances instances) throws Exception
	{

	}

	public double classifyInstance(Instance i)
	{
		return r.nextInt(2);
	}

	public double[] distributionForInstance(Instance i)
	{
		double d = r.nextDouble();
		double[] ret = {d, 1 - d};

		return ret;
	}
	public String getRevision()
	{
		return "rev 1";
	}

}
