package raykernel.ml.feature;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;

public abstract class TrainableAdapter implements Trainable
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
	/*
		public String featureValues()
		{
			StringBuffer buff = new StringBuffer();

			for (String fName : DetectorSuite.defaultFeatureNames())
			{
				buff.append(getFeature(fName).value() + "\t");
			}

			return buff.toString();
		}*/

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

		//first add bug feature
		inst.setValue(getClassAttribute(), classFeature.value());
		//inst.setValue(0, classFeature.value());

		int i = 1;

		//add the rest
		for (String feature_name : features)
		{
			Feature f = getFeature(feature_name);

			if (f == null)
			{
				f = new StandardValueFeature(class_feature, 0);
			}

			//inst.setValue(f.getAttribute(), f.value());

			inst.setValue(i++, f.value());
		}

		//io.Out.println("returning: " + inst.stringValue(inst.classAttribute()));

		return inst;
	}

	public void setFeature(Feature f)
	{
		featureMap.put(f.name(), f);

		//io.Out.println("added feature: (" + f.name() + ", " + f.value() + ")" );
	}
}
