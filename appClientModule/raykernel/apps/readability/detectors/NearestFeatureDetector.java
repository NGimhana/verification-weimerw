package raykernel.apps.readability.detectors;

import java.util.ListIterator;

import raykernel.apps.readability.code.CodeLine;
import raykernel.apps.readability.code.TrainableCodeBlock;
import raykernel.ml.feature.FeatureDetector;
import raykernel.ml.feature.StandardValueFeature;
import raykernel.ml.feature.Trainable;
import raykernel.util.Common;

/**
 * Creates a Nearest-Feautre Decector from a regular Feature Detector
 * 
 * @author rlb7g
 * 
 */
public class NearestFeatureDetector implements FeatureDetector
{

	static final int MAXDIST = 50;
	static int ceil_inc(int i)
	{
		if (i == MAXDIST)
			return i;
		return ++i;
	}

	FeatureDetector detector;

	String name;

	public NearestFeatureDetector(FeatureDetector detector, String name)
	{
		this.detector = detector;
		this.name = name;
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

		//first run the original detector
		detector.runDetector(block);

		ListIterator<CodeLine> iter = block.iterator();

		int distLastComment = MAXDIST;

		//forward
		while (iter.hasNext())
		{
			CodeLine cl = iter.next();

			if (cl.getFeature(detector.featureNames()[0]).value() > 0)
			{
				distLastComment = 0;
			}

			cl.setFeature(new StandardValueFeature(name, distLastComment));

			distLastComment = ceil_inc(distLastComment);
		}

		/*
		
		distLastComment = MAXDIST;
		
		//reverse
		while( iter.hasPrevious() )
		{
			CodeLine cl = iter.previous();
			
			//if this line has a comment
			if( cl.getFeature(detector.featureName()).value() > 0 ) {
				distLastComment = 0;
			}
			
			float current = cl.getFeature(name).value();
			
			if( distLastComment < current )
				cl.setFeature( new StandardValueFeature(name, distLastComment) );
			
			distLastComment = ceil_inc(distLastComment);
		}
		
		*/

	}
}
