package raykernel.apps.readability.code;

import java.util.Iterator;

import raykernel.ml.core.TrainableAdapter;
import raykernel.ml.feature.Feature;
import raykernel.ml.feature.Trainable;
import weka.core.Attribute;
import weka.core.Instance;

public abstract class TrainableCodeBlock extends CodeBlockAdapter implements Trainable
{

	TrainableAdapter train = new TrainableAdapter();

	public void addFeatureName(String feature_name)
	{
		train.addFeatureName(feature_name);
	}

	public Iterator<Feature> featureIterator()
	{
		return train.featureIterator();
	}

	public Iterator<String> featureNamesIterator()
	{
		return train.featureNamesIterator();
	}

	public Attribute getClassAttribute()
	{
		return train.getClassAttribute();
	}

	public Feature getFeature(String featurename)
	{
		return train.getFeature(featurename);
	}

	public Instance getInstance(String class_feature, String[] features)
	{
		return train.getInstance(class_feature, features);
	}

	public boolean isEmpty()
	{
		return false;
	}

	public void setFeature(Feature f)
	{
		train.setFeature(f);
	}

}
