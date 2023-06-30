package raykernel.util;

import raykernel.ml.feature.Feature;
import raykernel.ml.feature.Trainable;

public class FeatureGreaterThanPredicate<T extends Trainable> implements Predicate<T>
{

	private final String featurename;
	float gt;

	public FeatureGreaterThanPredicate(String featurename, float gt)
	{
		this.featurename = featurename;
		this.gt = gt;
	}

	public boolean getBoolean(T t)
	{
		Feature f = t.getFeature(featurename);

		if (f.value() > gt)
			return true;

		return false;
	}
}
