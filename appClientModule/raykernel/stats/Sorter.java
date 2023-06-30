package raykernel.stats;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import raykernel.ml.feature.Trainable;

public class Sorter
{
	static class FeatureComparator implements Comparator
	{

		String feature;

		FeatureComparator(String feature)
		{
			this.feature = feature;
		}

		public int compare(Object o1, Object o2)
		{
			Trainable t1 = (Trainable) o1;
			Trainable t2 = (Trainable) o2;

			return (int) ((t1.getFeature(feature).value() * 10000) - (t2.getFeature(feature).value() * 10000));
		}

	}

	static class ReverseFeatureComparator implements Comparator
	{

		String feature;

		ReverseFeatureComparator(String feature)
		{
			this.feature = feature;
		}

		public int compare(Object o1, Object o2)
		{
			Trainable t1 = (Trainable) o1;
			Trainable t2 = (Trainable) o2;

			return (int) ((t2.getFeature(feature).value() * 10000) - (t1.getFeature(feature).value() * 10000));
		}

	}

	public static void sort(List<Float> values)
	{
		Collections.sort(values);

	}

	@SuppressWarnings("unchecked")
	public static void sortByFeature(List<? extends Trainable> toSort, String feature)
	{
		Collections.sort(toSort, new FeatureComparator(feature));
	}

	@SuppressWarnings("unchecked")
	public static void sortByFeatureReverse(List<? extends Trainable> toSort, String feature)
	{
		Collections.sort(toSort, new ReverseFeatureComparator(feature));
	}
}
