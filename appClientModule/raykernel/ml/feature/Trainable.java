package raykernel.ml.feature;

import java.util.Iterator;

import weka.core.Attribute;
import weka.core.Instance;

public interface Trainable
{

	public void addFeatureName(String feature_name);

	public Iterator<Feature> featureIterator();

	public Iterator<String> featureNamesIterator();

	public Attribute getClassAttribute();

	public Feature getFeature(String featurename);

	public Instance getInstance(String class_feature, String[] features);

	public void setFeature(Feature f);

}
