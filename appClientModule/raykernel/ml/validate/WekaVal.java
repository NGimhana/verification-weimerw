package raykernel.ml.validate;

import java.util.Collection;
import java.util.Random;

import raykernel.ml.core.Trainer;
import raykernel.ml.feature.Trainable;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;

public class WekaVal
{

	private Classifier classifier;

	private final String className;
	private final String[] otherFeatures;

	private final Collection<Trainable> trainingSet;

	public WekaVal(Collection trainingSet, Class classifier, String className, String... otherFeatures)
	{
		this.className = className;
		//this.bugName = className;
		this.otherFeatures = otherFeatures;

		this.trainingSet = trainingSet;
		//this.validationSet = validationSet;

		try
		{
			this.classifier = (Classifier) classifier.newInstance();
		}
		catch (Exception e)
		{
			System.err.println("could not instantiate classifier!");
			e.printStackTrace();
		}
	}

	public float runCrossVal()
	{
		//Weka Evaluator
		Evaluation eTest;
		Instances ins = Trainer.extractInstances(trainingSet, className, otherFeatures);

		try
		{
			eTest = new Evaluation(ins);
			eTest.crossValidateModel(classifier, ins, 10, new Random());

			//String summary = eTest.toSummaryString(); 
			//io.Out.println(summary);

			//io.Out.println("prec  = " + eTest.precision(0) );
			//io.Out.println("recal = " + eTest.recall(0) );
			//io.Out.println("fmeas = " + eTest.fMeasure(0) );

			return (float) eTest.fMeasure(1);
			//return (float) eTest.pctCorrect();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return 0;
	}

	public double runStraightVal(Collection validationSet, String bugName)
	{
		Trainer t = new Trainer(trainingSet, className, otherFeatures);

		//train
		t.trainClassifier(classifier);

		//Weka Evaluator
		Evaluation eTest;
		Instances train_ins = Trainer.extractInstances(trainingSet, className, otherFeatures);
		Instances eval_ins = Trainer.extractInstances(validationSet, bugName, otherFeatures);

		try
		{
			eTest = new Evaluation(train_ins);
			eTest.evaluateModel(classifier, eval_ins);

			return eTest.fMeasure(1);

			//String summary = eTest.toSummaryString(); 
			//io.Out.println(summary);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return 0;

	}

}
