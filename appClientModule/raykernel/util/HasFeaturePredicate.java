package raykernel.util;

import raykernel.ml.feature.Feature;
import raykernel.ml.feature.Trainable;

public class HasFeaturePredicate<T extends Trainable> implements Predicate<T>
{

	private final String featurename;

	public HasFeaturePredicate(String featurename)
	{
		this.featurename = featurename;
	}

	public boolean getBoolean(T t)
	{
		Feature f = t.getFeature(featurename);

		if (f != null && f.value() > 0)
			return true;

		return false;
	}

}
