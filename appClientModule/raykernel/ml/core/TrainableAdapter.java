package raykernel.ml.core;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import raykernel.ml.feature.Feature;
import raykernel.ml.feature.StandardValueFeature;
import raykernel.ml.feature.Trainable;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;

public class TrainableAdapter implements Trainable
{

	static Attribute attr;

	Map<String, Feature> featureMap = new TreeMap<String, Feature>();
	Set<String> features = new TreeSet<String>();

	public void addFeatureName(String feature_name)
	{
		features.add(feature_name);
	}

	public Iterator<Feature> featureIterator()
	{
		return featureMap.values().iterator();
	}

	public Iterator<String> featureNamesIterator()
	{
		return features.iterator();
	}

	public Attribute getClassAttribute()
	{
		if (attr != null)
			return attr;

		FastVector classVal = new FastVector(2);
		classVal.addElement("less");
		classVal.addElement("more");

		attr = new Attribute("bug", classVal);

		return attr;
	}

	public Feature getFeature(String featurename)
	{
		return featureMap.get(featurename);
	}

	public Instance getInstance(String class_feature, String[] features)
	{
		Instance inst = new Instance(features.length + 1);

		Feature classFeature = getFeature(class_feature);

		if (classFeature == null)
		{
			classFeature = new StandardValueFeature(class_feature, 0);
		}

		int currFeature = 0;

		//first add bug feature
		inst.setValue(getClassAttribute(), classFeature.value());
		//inst.setValue(currFeature++, classFeature.value());

		//io.Out.println("class feature: " + getClassAttribute() + " " + classFeature.value());

		//add the rest
		for (String feature_name : features)
		{
			if (feature_name.contains("java.lang.String"))
			{
				continue;
			}

			//io.Out.println("adding: " + feature_name);

			Feature f = getFeature(feature_name);

			if (f == null)
			{
				f = new StandardValueFeature(class_feature, 0);
			}

			//io.Out.println("index: " + f.getAttribute().index());

			inst.setValue(f.getAttribute(), f.value());
			//inst.setValue(currFeature++, f.value());

		}

		return inst;
	}

	public void setFeature(Feature f)
	{
		featureMap.put(f.name(), f);

		//io.Out.println("added feature: (" + f.name() + ", " + f.value() + ")" );
	}

}
