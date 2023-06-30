package raykernel.ml.feature;

/**
 * A Feature Detector takes a code block and annotates each line with a Feature
 * object, which stores the value of the feature for the linesurely no
 * 
 * @author rlb7g
 * 
 */
public interface FeatureDetector
{

	/**
	 * the kind of feature this detector correspods to
	 * 
	 * @return
	 */
	public String[] featureNames();

	/**
	 * Run the detector
	 * 
	 * @param block
	 */
	public void runDetector(Trainable p);

}
