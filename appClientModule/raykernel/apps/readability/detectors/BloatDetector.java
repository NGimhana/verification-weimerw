package raykernel.apps.readability.detectors;

import raykernel.apps.readability.code.TrainableCodeBlock;
import raykernel.ml.feature.FeatureDetector;
import raykernel.ml.feature.Trainable;
import raykernel.util.Common;

/**
 * Detects features associated with "code bloat"
 * 
 * @author rlb7g
 * 
 */
public class BloatDetector implements FeatureDetector
{

	CharCountDetector ccd;
	CharCountDetector dcd;
	DensityDetector dd;
	IndentDetector id;
	LineLengthDetector lld;

	public BloatDetector()
	{
		id = new IndentDetector();
		lld = new LineLengthDetector();
		dd = new DensityDetector();
		ccd = new CharCountDetector(',');
		dcd = new CharCountDetector('.');
	}

	public String featureName()
	{
		return "bloat";
	}

	public String[] featureNames()
	{
		return Common.makeSingletonArray(featureName());
	}
	public void runDetector(Trainable b)
	{
		TrainableCodeBlock block = (TrainableCodeBlock) b;

		id.runDetector(block);
		lld.runDetector(block);
		dd.runDetector(block);
		ccd.runDetector(block);
		dcd.runDetector(block);

	}
}
