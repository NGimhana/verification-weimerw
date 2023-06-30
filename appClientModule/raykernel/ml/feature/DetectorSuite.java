package raykernel.ml.feature;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class DetectorSuite implements Iterable<FeatureDetector>
{
	LinkedList<FeatureDetector> detectors = new LinkedList<FeatureDetector>();

	public void add(FeatureDetector fd)
	{
		detectors.add(fd);
	}

	public String[] featureNames()
	{
		List<String> names = new LinkedList<String>();

		for (FeatureDetector d : detectors)
		{
			for (String s : d.featureNames())
			{
				names.add(s);
			}
		}

		String[] ret = new String[names.size()];

		return names.toArray(ret);
	}

	public Iterator<FeatureDetector> iterator()
	{
		return this.detectors.iterator();
	}

	public void runAll(List<Trainable> files)
	{
		for (Trainable f : files)
		{
			runAll(f);
		}
	}

	public void runAll(Trainable b)
	{
		Iterator<FeatureDetector> iter = detectors.iterator();

		while (iter.hasNext())
		{
			iter.next().runDetector(b);
		}
	}

}