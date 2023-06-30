package raykernel.ml.core;

import java.util.HashSet;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;

public class PerfectClassifier extends Classifier
{

	HashSet<Instance> classZero = new HashSet<Instance>();

	public void buildClassifier(Instances arg0) throws Exception
	{
		/*
		Enumeration e = arg0.enumerateInstances();
		
		while( e.hasMoreElements() )
		{
			Instance i = (Instance) e.nextElement();
			
			String classV = i.stringValue( i.classAttribute() );
			//io.Out.println("training on = " + classV);
		}
		*/
	}

	public double classifyInstance(Instance i)
	{
		raykernel.io.Out.println("input(classify) = " + i.classValue());

		return i.classValue();
	}

	public double[] distributionForInstance(Instance i)
	{
		//String classV = i.stringValue( i.classAttribute() );
		raykernel.io.Out.println("input = " + i.stringValue(0));

		double[] ret = {0, 0};

		ret[(int) i.classValue()] = 1;

		return ret;
	}

	public String getRevision()
	{
		// TODO Auto-generated method stub
		return "rev 1";
	}

}
