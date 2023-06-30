package raykernel.apps.readability.applet;

import java.util.Collection;
import java.util.List;

import raykernel.apps.readability.code.TrainableCodeBlock;
import raykernel.apps.readability.detectors.ReadabilityDetectorSuite;
import raykernel.apps.readability.snippet.Snippet;
import raykernel.ml.core.Evaluator;
import raykernel.ml.core.Trainer;
import raykernel.ml.feature.DetectorSuite;
import raykernel.ml.feature.FeatureDetector;
import weka.classifiers.Classifier;
import weka.classifiers.functions.Logistic;

public class PortableEvaluator
{

	Classifier classifier = new Logistic();

	float cutoff = (float) 0.5;
	DetectorSuite ds = ReadabilityDetectorSuite.getDefaultSuite();

	Evaluator eval;
	FeatureDetector ssfd;

	public PortableEvaluator()
	{
		ssfd = new PortableSnippetScoreFeatureDetector();

		List<Snippet> snippets = getSnippets(ssfd, ds);

		train(snippets);
	}

	public double getReadability(TrainableCodeBlock block)
	{
		ds.runAll(block);
		eval.runDetector(block);
		return eval.getScoreFor(block);
	}

	public List<Snippet> getSnippets(FeatureDetector score_detector, DetectorSuite ds2)
	{
		List<Snippet> snippets = PortableData.getSnippets();

		for (Snippet s : snippets)
		{
			ds2.runAll(s);
			score_detector.runDetector(s);
		}

		return snippets;
	}

	void train(Collection<Snippet> snippets)
	{
		Trainer t = new Trainer(snippets, ssfd.featureNames()[0], ds.featureNames());

		//train
		t.trainClassifier(classifier);

		//run evaluator
		eval = new Evaluator(classifier, t.getTrainingSet(), ssfd.featureNames()[0], ds.featureNames());
	}
}
