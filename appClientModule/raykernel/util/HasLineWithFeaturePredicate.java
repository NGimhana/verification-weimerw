package raykernel.util;

import raykernel.apps.readability.code.CodeLine;
import raykernel.apps.readability.code.TrainableCodeBlock;
import raykernel.ml.feature.Feature;

public class HasLineWithFeaturePredicate<T extends TrainableCodeBlock> implements Predicate<T>
{

	private final String featurename;

	public HasLineWithFeaturePredicate(String featurename)
	{
		this.featurename = featurename;
	}

	public boolean getBoolean(T t)
	{
		for (CodeLine l : t)
		{
			Feature f = l.getFeature(featurename);

			if (f != null && f.value() > 0)
				return true;
		}

		return false;
	}

}
