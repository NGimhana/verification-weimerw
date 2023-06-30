package raykernel.ml.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import raykernel.config.GlobalStats;
import raykernel.ml.feature.DetectorSuite;
import raykernel.ml.feature.Feature;
import raykernel.ml.feature.FeatureDetector;
import raykernel.ml.feature.StandardValueFeature;
import raykernel.ml.feature.Trainable;
import raykernel.util.Common;
import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Evaluate how well a feature suite works relative to a bug detector
 * Unfortunatelly this is a bit hackish
 */
public class Evaluator implements FeatureDetector, Serializable
{

	public static void writeVector(Vector v, String fName)
	{
		File f = new File(fName);

		try
		{
			BufferedWriter br = new BufferedWriter(new FileWriter(f));

			for (Object o : v)
			{
				br.write(o + "\n");
			}

			br.flush();

		}
		catch (IOException e)
		{
			System.err.println("could not write to: " + fName);
			e.printStackTrace();
		}

	}
	Vector<Float> bug_scores = new Vector<Float>();

	Classifier classifier;
	private String className;

	float maxScore = 0;
	float minScore = 1;

	Vector<Float> nonbug_scores = new Vector<Float>();
	private String[] otherFeatures;
	HashMap<Trainable, Float> scoreMap = new HashMap<Trainable, Float>();

	Instances trainingSet;

	public Evaluator(Classifier classifier, DetectorSuite featureSuite, DetectorSuite bugSuite, Instances trainingSet)
	{
		this.classifier = classifier;
		this.trainingSet = trainingSet;
	}

	/**
	 * uses default features
	 */
	public Evaluator(Classifier classifier, Instances trainingSet, String className, String... otherFeatures)
	{
		this.classifier = classifier;
		this.trainingSet = trainingSet;
		this.className = className;
		this.otherFeatures = otherFeatures;
	}
	public String[] featureNames()
	{
		return Common.makeSingletonArray(classifier.getClass().getSimpleName());
	}

	public int getClassFor(Trainable f)
	{
		return scoreMap.get(f).intValue();
	}

	public float getScoreFor(Trainable f)
	{
		return scoreMap.get(f);
	}

	public HashMap<Trainable, Float> getScoreMap()
	{
		return scoreMap;
	}

	public void printEvaluation(Collection<Trainable> files)
	{

		//normalizeScores();

		int bugCount = 0;
		float sumValuesBugLines = 0;

		int nonBugCount = 0;
		float sumVauesNonBugLines = 0;

		//My Evauator
		for (Trainable f : files)
		{
			float bug = f.getFeature(className).value(); //does it contain bug?
			float score = scoreMap.get(f); //score it was given

			if (bug > 0)
			{
				bugCount++;
				sumValuesBugLines += score;
			}
			else
			{
				nonBugCount++;
				sumVauesNonBugLines += score;
			}
		}

		float avgBugScore = sumValuesBugLines / bugCount;
		float avgNonBugScore = sumVauesNonBugLines / nonBugCount;

		float ratio = avgBugScore / avgNonBugScore;
		raykernel.io.Out.println("ratio  : " + ratio + "\n");

	}

	/*
	public double getProbFor( Trainable block, int classNum )
	{
		Instance i = block.getInstance( this.className, otherFeatures );
		
		i.setDataset( trainingSet );
		
		double[] prob = { -1, -1 };
		
		try{ prob = classifier.distributionForInstance( i ); }
		catch( Exception e ) {  System.err.println("classification failed");  e.printStackTrace();  }
		
		return prob[classNum];
	}
	*/
	public void runDetector(Trainable block)
	{
		double[] prob;
		try
		{
			Instance i = block.getInstance(this.className, otherFeatures);

			i.setDataset(trainingSet);

			//io.Out.println("to classify: " + i);

			float score;

			//if (Config.classifyMode)
			//{
			prob = classifier.distributionForInstance(i);
			score = GlobalStats.minimiseScore((float) prob[0]);
			//}
			//else
			//{
			//	score = (float) classifier.classifyInstance(i);
			//}

			Feature classFet = block.getFeature(className);

			if (classFet != null && classFet.value() > 0)
			{
				bug_scores.add(score);
			}
			else
			{
				nonbug_scores.add(score);
			}

			//record min / max
			if (score < minScore)
			{
				minScore = score;
			}
			else if (score > maxScore)
			{
				maxScore = score;
			}

			scoreMap.put(block, score);
			block.setFeature(new StandardValueFeature(featureNames()[0], score));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public void writeScores(String fName_bug, String fName_nobug)
	{
		writeVector(this.bug_scores, fName_bug);
		writeVector(this.nonbug_scores, fName_nobug);
	}

	/*
	public void showScores(int limit)
	{
		int c=0;
		
		io.Out.println(scores);
		
		for( float s : scores)
		{
			io.Out.println(s);
			c++;
			if( c > limit )
				break;
		}
	}
	*/

}
