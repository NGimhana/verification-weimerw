package raykernel.ml.validate;

import java.util.List;
import java.util.Vector;

import raykernel.ml.feature.Trainable;

public class StraightVal
{

	String classFeature;
	String estimateFeature;

	Vector<LineStat> results = new Vector<LineStat>();

	float scoreTotal = 0;

	int totalCount = 0;
	List<Trainable> trainingSet;
	public StraightVal(List<? extends Trainable> paths, String classFeature, String estimateFeature)
	{
		trainingSet = (List<Trainable>) paths;

		this.classFeature = classFeature;
		this.estimateFeature = estimateFeature;

	}
	public Vector<LineStat> getResults()
	{
		return results;
	}

	public void printStats()
	{
		raykernel.io.Out.println("average score = " + scoreTotal / totalCount);
	}

	public void run()
	{
		//		record data from run
		for (Trainable t : trainingSet)
		{
			results.add(new LineStat(t, estimateFeature, classFeature));
		}

	}

}
