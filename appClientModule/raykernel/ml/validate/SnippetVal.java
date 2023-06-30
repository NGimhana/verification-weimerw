package raykernel.ml.validate;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import raykernel.ml.core.Evaluator;
import raykernel.ml.core.Trainer;
import raykernel.ml.feature.Trainable;
import weka.classifiers.Classifier;

public class SnippetVal
{

	Classifier classifier;

	String className;

	int numFolds;
	String[] otherFeatures;
	Map<Trainable, Double> results; //average scores 

	private final List<? extends Trainable> snippets;

	public SnippetVal(List<? extends Trainable> snippets, Class classifier, String className, String... otherFeatures)
	{
		this.className = className;
		this.otherFeatures = otherFeatures;
		this.snippets = snippets;

		try
		{
			this.classifier = (Classifier) classifier.newInstance();
		}
		catch (Exception e)
		{
			System.err.println("could not instantiate classifier!");
			System.exit(0);
		}

		results = new HashMap<Trainable, Double>();
		//results.setSize(101);

		//for( int i=0; i<results.size(); i++ )
		//results.set(i, -1.0);

	}

	private List<Trainable> getAllBut(List<Trainable> trainingSnippets, Trainable leaveOut)
	{
		LinkedList<Trainable> ret = new LinkedList<Trainable>();

		for (Trainable s : trainingSnippets)
		{
			if (s != leaveOut)
			{
				ret.add(s);
			}
		}

		return ret;
	}

	private List<Trainable> getTrainingSet()
	{
		LinkedList<Trainable> ret = new LinkedList<Trainable>();

		for (Trainable s : snippets)
		{
			if (s.getFeature(className).value() != -1)
			{
				ret.add(s);
			}
		}

		return ret;
	}

	public void printResults()
	{
		raykernel.io.Out.println("actual\tguess");

		for (Trainable t : results.keySet())
		{
			double guess = results.get(t);
			float actual = t.getFeature(className).value();

			raykernel.io.Out.println(actual + "\t" + guess);
		}
	}

	public void run()
	{
		List<Trainable> trainingSnippets = getTrainingSet();

		int i = 0;

		//for each of the snippets
		for (Trainable snippet : snippets)
		{
			List<Trainable> train = getAllBut(trainingSnippets, snippet);

			Trainer t = new Trainer(train, className, otherFeatures);

			//train
			t.trainClassifier(classifier);

			//run evaluator
			Evaluator e = new Evaluator(classifier, t.getTrainingSet(), className, otherFeatures);

			e.runDetector(snippet);

			results.put(snippet, (double) e.getScoreFor(snippet));
		}

	}

}
