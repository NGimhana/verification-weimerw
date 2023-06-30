package raykernel.apps.readability.core;

import java.io.File;
import java.util.Collection;
import java.util.List;

import raykernel.apps.readability.code.TrainableCodeBlock;
import raykernel.apps.readability.detectors.ReadabilityDetectorSuite;
import raykernel.apps.readability.snippet.Snippet;
import raykernel.apps.readability.snippet.SnippetLoader;
import raykernel.apps.readability.snippet.SnippetScoreFeatureDetector;
import raykernel.ml.core.Evaluator;
import raykernel.ml.core.Trainer;
import raykernel.ml.feature.DetectorSuite;
import raykernel.ml.feature.FeatureDetector;
import weka.classifiers.Classifier;

public class ReadabilityEvaluator
{
	Classifier classifier = raykernel.config.Config.getDefaultClassifier();

	float cutoff = (float) 0.5;
	DetectorSuite ds = ReadabilityDetectorSuite.getDefaultSuite();

	Evaluator eval;
	SnippetScoreFeatureDetector ssfd;

	public ReadabilityEvaluator()
	{
		raykernel.io.Out.verbose("Software Readability Metric");

		raykernel.io.Out.verbose("building model...");

		File snippet_dir, scoreFile;

		snippet_dir = new File(raykernel.config.Config.get("snippet_dir"));
		scoreFile = new File(raykernel.config.Config.get("score_file"));

		ssfd = new SnippetScoreFeatureDetector(scoreFile, cutoff);

		List<Snippet> snippets = getSnippets(scoreFile, snippet_dir, ssfd, ds);

		train(snippets);

		raykernel.io.Out.verbose(" done.\n");
	}

	public double getReadability(TrainableCodeBlock block)
	{
		ds.runAll(block);
		eval.runDetector(block);
		return eval.getScoreFor(block);
	}

	public List<Snippet> getSnippets(File scoreFile, File snippet_dir, FeatureDetector score_detector, DetectorSuite ds)
	{
		List<Snippet> snippets = SnippetLoader.loadSnippets(snippet_dir);

		for (Snippet s : snippets)
		{
			ds.runAll(s);
			score_detector.runDetector(s);
		}

		return snippets;
	}

	void train(Collection<Snippet> snippets)
	{
		Trainer t = new Trainer(snippets, ssfd.featureName(), ds.featureNames());

		//train
		t.trainClassifier(classifier);

		//run evaluator
		eval = new Evaluator(classifier, t.getTrainingSet(), ssfd.featureName(), ds.featureNames());

		//ObjectStore.put("readability_eval", eval);
	}
}
