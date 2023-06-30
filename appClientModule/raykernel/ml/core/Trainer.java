package raykernel.ml.core;

import java.util.Collection;
import java.util.LinkedList;

import raykernel.ml.feature.Feature;
import raykernel.ml.feature.Trainable;
import weka.classifiers.Classifier;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Used to train a classifier to recognise bugs found by a bug detector from
 * featurws found by a suite of featureDetectors
 * 
 */
public class Trainer
{

	public static Instances extractInstances(Collection<Trainable> fs, String classFeature, String[] otherFeatures)
	{
		//*** Setup Attriibutes

		//Iterator<Feature> features = fs.iterator().next().featureIterator();

		//create attribute vector
		FastVector vec = new FastVector();

		//add bug feature first
		vec.addElement(fs.iterator().next().getClassAttribute());

		//now add the others
		for (String fName : otherFeatures)
		{
			vec.addElement(Feature.getAttribute(fName));
		}

		//	*** Get Instances

		Instances ins = new Instances("some_name", vec, fs.size() * 20);
		ins.setClassIndex(0);

		for (Trainable t : fs)
		{
			Instance inst = t.getInstance(classFeature, otherFeatures);

			//io.Out.println("instance: " + inst);

			ins.add(inst);
		}

		return ins;
	}

	Collection<Trainable> blocks;

	String classFeature;
	String[] otherFeatures;

	Instances trainingSet;

	@SuppressWarnings("unchecked")
	public Trainer(Collection blocks, String classification_feature, String[] otherFeatures)
	{
		classFeature = classification_feature;
		this.otherFeatures = otherFeatures;
		this.blocks = blocks;
	}

	public Trainer(String classification_feature, String[] otherFeatures)
	{
		classFeature = classification_feature;
		this.otherFeatures = otherFeatures;
	}

	public void addTrainable(Trainable t)
	{
		if (blocks == null)
		{
			blocks = new LinkedList<Trainable>();
		}

		blocks.add(t);
	}

	public Instances getTrainingSet()
	{
		if (trainingSet == null)
		{
			trainingSet = extractInstances(blocks, classFeature, otherFeatures);
		}

		return trainingSet;
	}

	public void trainClassifier(Classifier c)
	{
		Instances ins = getTrainingSet();

		//io.Out.println("*** Training Set ***\n" + ins + "\n**********\n" );

		try
		{
			c.buildClassifier(ins);
		}
		catch (Exception e)
		{
			System.err.println("unknown weka error!");
			e.printStackTrace();
			System.exit(0);
		}

	}

}
