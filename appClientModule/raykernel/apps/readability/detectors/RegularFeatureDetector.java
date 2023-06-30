package raykernel.apps.readability.detectors;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Pattern;

import raykernel.apps.readability.code.CodeLine;
import raykernel.apps.readability.code.TrainableCodeBlock;
import raykernel.ml.feature.FeatureDetector;
import raykernel.ml.feature.RegularFeature;
import raykernel.ml.feature.Trainable;
import raykernel.util.Common;

public class RegularFeatureDetector implements FeatureDetector
{

	//Name of this set of patterns
	String name;

	//the pattern thid detector is looking for
	LinkedList<Pattern> patterns;

	RegularFeatureDetector(Pattern p)
	{
		patterns = new LinkedList<Pattern>();
		addPattern(p);
		name = p.toString();
	}

	public RegularFeatureDetector(String name)
	{
		patterns = new LinkedList<Pattern>();
		this.name = name;
	}

	public void addPattern(Pattern p)
	{
		patterns.add(p);
	}

	public String featureName()
	{
		return name;
	}

	public String[] featureNames()
	{
		return Common.makeSingletonArray(featureName());
	}

	public void runDetector(Trainable b)
	{
		TrainableCodeBlock block = (TrainableCodeBlock) b;
		//first set all to false
		Iterator<CodeLine> iter = block.iterator();
		while (iter.hasNext())
		{
			CodeLine l = iter.next();
			l.setFeature(new RegularFeature(name, false));
		}

		//for each pattern
		Iterator<Pattern> patern_iter = patterns.iterator();
		while (patern_iter.hasNext())
		{
			Pattern p = patern_iter.next();

			iter = block.linesMatching(p);
			while (iter.hasNext())
			{
				CodeLine l = iter.next();
				l.setFeature(new RegularFeature(name, true));
			}
		}

	}

}
