package raykernel.ml.validate;

import java.util.List;
import java.util.Vector;

import raykernel.ml.core.Evaluator;
import raykernel.ml.core.Trainer;
import raykernel.ml.core.ValidationSet;
import raykernel.ml.feature.Trainable;
import raykernel.util.Permute;
import weka.classifiers.Classifier;

public class CrossVal
{

	Classifier classifier;

	String className;
	private final List files;

	int numFolds;
	String[] otherFeatures;
	Vector<Vector<LineStat>> results; //average scores 

	/**
	 * For K-fold cross validation, we partition the files into K groups. We
	 * select one group that we will do the evaluation on, Then we train on the
	 * other K-1. Repeat so each group is used for validation once.
	 * 
	 * Average everthing in the end.
	 * 
	 * @param files
	 * @param numFolds
	 */
	@SuppressWarnings("unchecked")
	public CrossVal(List files, int numFolds, Class classifier, String className, String... otherFeatures)
	{
		//io.Out.println("Creating " + numFolds + " fold crossVal with " + files.size() + " code blocks.  Class name = " + className);

		this.className = className;
		this.otherFeatures = otherFeatures;
		this.numFolds = numFolds;

		try
		{
			this.classifier = (Classifier) classifier.newInstance();
		}
		catch (Exception e)
		{
			System.err.println("could not instantiate classifier!");
			//e.printStackTrace();
			System.exit(0);
		}

		this.files = files;
	}

	public Vector<Vector<LineStat>> getOrderedResults()
	{
		return results;
	}

	public Vector<LineStat> getResults()
	{
		Vector<LineStat> ret = new Vector<LineStat>();

		for (Vector<LineStat> run : results)
		{
			ret.addAll(run);
		}

		return ret;
	}

	public void run()
	{
		//		setup partitions
		Vector<ValidationSet> validations = new Vector<ValidationSet>(numFolds);

		List[] partition = Permute.randomPartition(files, numFolds);

		for (int i = 0; i < numFolds; i++)
		{
			ValidationSet v = new ValidationSet();
			v.setValidationData(partition[i]);

			//add rest as training data
			for (int j = 0; j < numFolds; j++)
			{
				if (j != i)
				{
					v.addTrainingBlocks(partition[j]);
				}
			}

			validations.add(v);
		}

		results = new Vector<Vector<LineStat>>();

		for (int i = 0; i < numFolds; i++)
		{
			results.add(new Vector<LineStat>());
		}

		//for each of the K-1
		for (int run = 0; run < numFolds; run++)
		{
			List<Trainable> set = validations.get(run).getTrainingData();

			Trainer t = new Trainer(set, className, otherFeatures);

			//train
			t.trainClassifier(classifier);

			//run evaluator
			Evaluator e = new Evaluator(classifier, t.getTrainingSet(), className, otherFeatures);

			for (Trainable f : validations.get(run).getValidationData())
			{
				e.runDetector(f);
			}

			//record data fro run
			for (Trainable f : validations.get(run).getValidationData())
			{
				results.get(run).add(new LineStat(f, e.featureNames()[0], className));
			}

			//io.Out.println( "fold #" + (run+1) + " complete");
		}

	}

	/*

	public void writeClassiferProbabilities( File file )
	{
		if( results == null || results.isEmpty() )
		{
			System.err.println("no classifier data to write");
		}
		
		try{
		
		BufferedWriter br = new BufferedWriter( new FileWriter( file ));
			
		br.write( "number\tscore\tbug\n" );
		
		int i = 1;
		
		for( LineStat ls : this.results )
		{
			br.write( i + "\t" + ls.getScore() + "\t" + ls.isBug() + "\n" );
			i++;
		}
		
		br.flush();
		
		}
		catch( IOException e )
		{
			System.err.println("error writing classifier output");
		}
	}
	*/

}
