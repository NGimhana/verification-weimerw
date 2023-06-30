package raykernel.apps.readability.core;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import raykernel.apps.readability.bugdetectors.FindBugsDetector;
import raykernel.apps.readability.code.CodeFile;
import raykernel.apps.readability.code.Function;
import raykernel.apps.readability.detectors.FunctionExtractor;
import raykernel.apps.readability.detectors.ReadabilityDetectorSuite;
import raykernel.apps.readability.misc.CodeFileLoader;
import raykernel.apps.readability.snippet.Snippet;
import raykernel.apps.readability.snippet.SnippetLoader;
import raykernel.config.GlobalStats;
import raykernel.experiment.Benchmark;
import raykernel.ml.feature.DetectorSuite;
import raykernel.ml.feature.FeatureDetector;
import raykernel.ml.feature.StandardValueFeature;
import raykernel.ml.feature.Trainable;
import raykernel.ml.validate.Analyzer;
import raykernel.ml.validate.CrossVal;
import raykernel.ml.validate.SnippetVal;
import raykernel.ml.validate.Stats;
import raykernel.ml.validate.StraightVal;
import raykernel.ml.validate.WekaVal;
import raykernel.util.FeatureGreaterThanPredicate;
import raykernel.util.Filter;
import raykernel.util.HasFeaturePredicate;
import raykernel.util.HasLineWithFeaturePredicate;
import raykernel.util.Predicate;
import raykernel.util.PredicateNOT;
import raykernel.versioncontrol.logbugs.LogBugDetector;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.Logistic;
import weka.classifiers.functions.RBFNetwork;
import weka.classifiers.functions.SimpleLogistic;
import weka.classifiers.misc.HyperPipes;
import weka.classifiers.trees.J48;

public class Experimenter
{

	static float cutoff = (float) 0.5;

	static DetectorSuite ds = ReadabilityDetectorSuite.getDefaultSuite();
	static FeatureDetector score_detector;

	private static float avgCrossFmeasue(CrossVal cval, int iterations)
	{
		Analyzer ana = new Analyzer();

		float total = 0;

		for (int i = 0; i < iterations; i++)
		{
			cval.run();
			float thisrun = ana.getAvgBestFmeasure(cval.getOrderedResults());

			//io.Out.println("-----");

			//ana.printScores( cval.getResults() );

			total += thisrun;
		}

		return total / iterations;
	}

	private static float avgWekaFmeasue(WekaVal wval, int iterations)
	{
		float total = 0;

		for (int i = 0; i < iterations; i++)
		{
			float thisrun = wval.runCrossVal();
			total += thisrun;
		}

		return total / iterations;
	}

	public static void compareAvgValues(Collection<Snippet> snippets, String... features)
	{
		//filer
		raykernel.util.Filter<Snippet> scoredFilter = new Filter<Snippet>(new FeatureGreaterThanPredicate<Snippet>(
				score_detector.featureNames()[0], -1));
		Filter<Snippet> lessReadableFilter = new Filter<Snippet>(new HasFeaturePredicate<Snippet>(score_detector
				.featureNames()[0]));
		Filter<Snippet> moreReadableFilter = new Filter<Snippet>(new PredicateNOT<Snippet>(
				new HasFeaturePredicate<Snippet>(score_detector.featureNames()[0])));

		Collection<Snippet> scoredSnippets = scoredFilter.filter(snippets);
		Collection<Snippet> lessReadableSnippets = lessReadableFilter.filter(scoredSnippets);
		Collection<Snippet> moreReadableSnippets = moreReadableFilter.filter(scoredSnippets);

		//print
		raykernel.io.Out.println(lessReadableSnippets.size() + " lessReadableSnippets");
		raykernel.io.Out.println(moreReadableSnippets.size() + " moreReadableSnippets");

		raykernel.io.Out.println("feature\t\tless\tmore");

		for (String f : features)
		{
			raykernel.io.Out.println(f + "\t" + getAvgFeatureValue(lessReadableSnippets, f) + "\t"
					+ getAvgFeatureValue(moreReadableSnippets, f));
		}
	}

	public static void compareClassifiers(List<Snippet> snippets, Class[] classifiers, String classFeature,
			String[] otherFeatures)
	{
		raykernel.io.Out.println("classifier name\tweka\tstandard");

		for (Class classifier : classifiers)
		{
			//WekaVal wval = new WekaVal( snippets, classifier, classFeature, otherFeatures);
			//CrossVal cval = new CrossVal(snippets, 10, classifier, classFeature, otherFeatures);

			//float weka = avgWekaFmeasue(wval, 10);
			//float std = avgCrossFmeasue(cval, 10);

			float total = 0;
			for (int j = 0; j < 10; j++)
			{
				CrossVal cv_others = new CrossVal(snippets, 10, classifier, classFeature, otherFeatures);
				total += avgCrossFmeasue(cv_others, 10);
			}

			raykernel.io.Out.println(classifier.getSimpleName() + "\t" + (total / 10));
		}
	}

	public static void compareFeatures(List<Snippet> snippets, Class classifier, String classFeature,
			String[] otherFeatures)
	{
		raykernel.io.Out.println("classifier = " + classifier.getSimpleName());

		float total = 0;
		for (int j = 0; j < 10; j++)
		{
			CrossVal cv_others = new CrossVal(snippets, 10, classifier, classFeature, otherFeatures);
			total += avgCrossFmeasue(cv_others, 10);
		}

		raykernel.io.Out.println("all features = " + (total / 10));

		raykernel.io.Out.println("feature name\t\tonly\tleftout");

		//leave others out
		for (String targetFeature : otherFeatures)
		{
			String[] thisFeature = {targetFeature};
			String[] leaveOneOut = new String[otherFeatures.length - 1];

			int i = 0;
			for (String feat : otherFeatures)
			{
				if (feat.equals(targetFeature))
				{
					continue;
				}

				leaveOneOut[i] = feat;
				i++;
			}

			//WekaVal wval_this = new WekaVal( snippets, classifier, classFeature, thisFeature);
			//WekaVal wval_others = new WekaVal( snippets, classifier, classFeature, leaveOneOut);

			//CrossVal cv_this = new CrossVal( snippets, 10, classifier, classFeature, thisFeature);

			int runs = 30;

			float total_o = 0, total_t = 0;
			for (int j = 0; j < runs; j++)
			{
				CrossVal cv_others = new CrossVal(snippets, 10, classifier, classFeature, leaveOneOut);
				CrossVal cv_this = new CrossVal(snippets, 10, classifier, classFeature, thisFeature);
				total_t += avgCrossFmeasue(cv_others, 10);
				total_o += avgCrossFmeasue(cv_this, 10);
			}

			raykernel.io.Out.println(targetFeature + "\t\t" + (total_o / runs) + "\t" + (total_t / runs));
		}
	}

	public static void experimentA(List<Snippet> snippets)
	{
		//try some classifiers
		Class[] classifiers = {NaiveBayes.class,
		//LMT.class,
				RBFNetwork.class, HyperPipes.class,
				//MultilayerPerceptron.class,
				//SMO.class,
				//VFI.class,
				SimpleLogistic.class,
				//KStar.class,
				//ADTree.class,
				Logistic.class,
				//IB1.class,
				J48.class,
		//JRip.class, 
		//NBTree.class
		};

		//compareClassifiers( snippets, classifiers, score_detector.featureNames()[0], ds.featureNames() );

		compareFeatures(snippets, NaiveBayes.class, score_detector.featureNames()[0], ds.featureNames());
	}

	public static void experimentB(Collection<Snippet> snippets, Class classiferType, FeatureDetector bug_detector,
			Benchmark bench)
	{
		raykernel.io.Out.println("\n-- Experiment B : Correlate with external measures --");
		raykernel.io.Out.println(bench);
		raykernel.io.Out.println(bug_detector.getClass().getSimpleName());
		raykernel.io.Out.println(classiferType.getSimpleName() + "\n");

		int max_files = 1000;
		int max_functions = 10000;

		//*** run detectors

		Collection<CodeFile> code_files;

		if (bug_detector instanceof FindBugsDetector)
		{
			code_files = CodeFileLoader.getCodeFiles(bench.getSrcDir(), max_files);
			CodeFileLoader.attachAuxVersions(code_files, bench.getBinDir(), "class");
		}
		else
		//new version detector
		{
			code_files = CodeFileLoader.getCodeFiles(bench.getOldDir(), max_files);
			CodeFileLoader.attachAuxVersions(code_files, bench.getSrcDir(), "java");
		}

		raykernel.io.Out.println(code_files.size() + " Code Files loaded.");

		for (CodeFile cf : code_files)
		{
			bug_detector.runDetector(cf);
		}

		//select set of functions with at least 1 fb
		Filter<CodeFile> bugfilefilter = new Filter<CodeFile>(new HasFeaturePredicate<CodeFile>(bug_detector
				.featureNames()[0]));
		Filter<CodeFile> reversefilefilter = new Filter<CodeFile>(new PredicateNOT<CodeFile>(
				new HasFeaturePredicate<CodeFile>(bug_detector.featureNames()[0])));

		Collection<CodeFile> files_with_fb = bugfilefilter.filter(code_files);
		Collection<CodeFile> files_without_fb = reversefilefilter.filter(code_files, files_with_fb.size() * 2);

		raykernel.io.Out.println("finished running bug detector.  Identified " + GlobalStats.bugCount + " bugs.");
		raykernel.io.Out.println("in " + files_with_fb.size() + " files.");

		//combine
		LinkedList<CodeFile> fileSet = new LinkedList<CodeFile>();
		fileSet.addAll(files_with_fb);
		fileSet.addAll(files_without_fb);

		Collection<Function> target_functions = FunctionExtractor.getAllFunctions(fileSet, max_functions);

		raykernel.io.Out.println("extracted " + target_functions.size() + " functions");

		//label functions
		Predicate<Function> funHasBug = new HasLineWithFeaturePredicate<Function>(bug_detector.featureNames()[0]);
		for (Function f : target_functions)
		{
			boolean hasBug = funHasBug.getBoolean(f);
			f.setFeature(new StandardValueFeature(bug_detector.featureNames()[0], hasBug));
		}

		raykernel.io.Out.println("functions labeled");

		//*** Partition data

		//select set of functions with at least 1 fb
		Filter<Function> bugfunfilter = new Filter<Function>(new HasFeaturePredicate<Function>(bug_detector
				.featureNames()[0]));
		Filter<Function> reversefunfilter = new Filter<Function>(new PredicateNOT<Function>(
				new HasFeaturePredicate<Function>(bug_detector.featureNames()[0])));

		Collection<Function> functions_with_fb = bugfunfilter.filter(target_functions);
		Collection<Function> functions_without_fb = reversefunfilter.filter(target_functions, functions_with_fb.size());

		List<Function> all_functions = new LinkedList<Function>();
		all_functions.addAll(functions_with_fb);
		all_functions.addAll(functions_without_fb);

		raykernel.io.Out.println("\n--- Working Set ---");
		raykernel.io.Out.println("functions (bugs>0): " + functions_with_fb.size());
		raykernel.io.Out.println("functions (bugs=0): " + functions_without_fb.size());

		for (Function f : all_functions)
		{
			ds.runAll(f);
		}

		//*** Run Analysis

		raykernel.io.Out.println("\n--- Evaluation Results ---");

		//WekaVal wval = new WekaVal( snippets, classiferType, score_detector.featureNames()[0], ds.featureNames());
		//double w = wval.runStraightVal(all_functions, bug_detector.featureNames()[0]);

		//StraightVal sv_funs = new StraightVal(snippets, all_functions, classiferType, score_detector.featureNames()[0],
		//bug_detector.featureNames()[0], ds.featureNames());

		StraightVal sv_funs = new StraightVal(all_functions, score_detector.featureNames()[0], bug_detector
				.featureNames()[0]);

		sv_funs.run();

		Analyzer ana = new Analyzer();

		float s = ana.getBestFmeasure(sv_funs.getResults());

		//io.Out.println( "weka fmeasure = " + w );
		raykernel.io.Out.println(" std fmeasure = " + s);

		ana.printScoreRatio(sv_funs.getResults());

	}

	public static void experimentC(Collection<Snippet> snippets, Class classiferType, Benchmark bench)
	{
		raykernel.io.Out.println("readability = " + getReadability(snippets, classiferType, bench));
	}

	public static void experimentD(Collection<Snippet> snippets, Class classifier)
	{
		SnippetVal val = new SnippetVal((List<? extends Trainable>) snippets, classifier,
				score_detector.featureNames()[0], ds.featureNames());

		val.run();

		val.printResults();
	}
	/*
		public static void experimentE(Collection<Snippet> snippets, Class classiferType, File benchDir)
		{
			for (Benchmark2 b : getBenchmarks(benchDir))
			{
				io.Out.println(b.getName() + "\t" + getReadability(snippets, classiferType, b));
			}
		}*/

	private static void experimentF(List<Snippet> snippets)
	{

		for (FeatureDetector fd : ReadabilityDetectorSuite.getDefaultSuite())
		{
			System.out.print(fd.featureNames()[0] + "\t");
			for (Snippet s : snippets)
			{
				System.out.print(s.getFeature(fd.featureNames()[0]).value() + "\t");
			}
			raykernel.io.Out.println();
		}
	}

	public static void experimentG(FeatureDetector bug_detector, Benchmark bench, int max_files)
	{
		raykernel.io.Out.println("\n-- Experiment G : How many bugs? --");
		raykernel.io.Out.println(bench);
		raykernel.io.Out.println(bug_detector.getClass().getSimpleName());

		Collection<CodeFile> code_files;

		if (bug_detector instanceof FindBugsDetector)
		{
			code_files = CodeFileLoader.getCodeFiles(bench.getSrcDir(), max_files);
			CodeFileLoader.attachAuxVersions(code_files, bench.getBinDir(), "class");
		}
		else
		//new version detector
		{
			code_files = CodeFileLoader.getCodeFiles(bench.getOldDir(), max_files);
			CodeFileLoader.attachAuxVersions(code_files, bench.getSrcDir(), "java");
		}

		raykernel.io.Out.println(code_files.size() + " Code Files loaded.");

		for (CodeFile cf : code_files)
		{
			bug_detector.runDetector(cf);
		}

		raykernel.io.Out.println("finished running bug detector.  Identified " + GlobalStats.bugCount + " bugs.");
	}

	public static float getAvgFeatureValue(Collection<Snippet> moreReadableSnippets, String featureName)
	{
		float total = 0;

		for (Trainable t : moreReadableSnippets)
		{
			total += t.getFeature(featureName).value();
		}

		return total / moreReadableSnippets.size();
	}
	/*
		public static List<Benchmark2> getBenchmarks(File directory)
		{
			LinkedList<Benchmark2> marks = new LinkedList<Benchmark2>();

			for (File f : directory.listFiles())
			{
				if (f.isDirectory())
				{
					if (f.getName().equals("bin") || f.getName().equals("lib"))
					{
						continue;
					}

					Benchmark2 b = new Benchmark2(f, f.getName());
					marks.add(b);
				}
			}

			return marks;
		}*/

	static double getReadability(Collection<Snippet> snippets, Class classiferType, Benchmark bench)
	{
		int max_files = 1000;
		int max_functions = 10000;

		//*** run detectors

		GlobalStats.startTimer();

		Collection<CodeFile> code_files = CodeFileLoader.getCodeFiles(bench.getSrcDir(), max_files);

		//io.Out.println(code_files.size() + " Code Files loaded.");

		Collection<Function> functions = FunctionExtractor.getAllFunctions(code_files, max_functions);

		//io.Out.println(functions.size() + " Functions Extracted.");

		for (Function f : functions)
		{
			ds.runAll(f);
		}

		//StraightVal sv_funs = new StraightVal(snippets, functions, classiferType, score_detector.featureNames()[0],
		//		score_detector.featureNames()[0], ds.featureNames());

		//StraightVal sv_funs = new StraightVal(snippets, functions, classiferType, score_detector.featureNames()[0],
		//		score_detector.featureNames()[0], ds.featureNames());

		StraightVal sv_funs = new StraightVal((List<? extends Trainable>) snippets, score_detector.featureNames()[0],
				ds.featureNames()[0]);

		sv_funs.run();

		//io.Out.println( "time = " + GlobalStats.endTimer() );

		return 1.0 - Stats.getAverageScore(sv_funs.getResults());
	}

	public static List<Snippet> getSnippets(File scoreFile, File snippet_dir, FeatureDetector score_detector,
			DetectorSuite ds)
	{
		List<Snippet> snippets = SnippetLoader.loadSnippets(snippet_dir);
		//List<Snippet> classified = new LinkedList<Snippet>();

		int zero_class = 0, one_class = 0, no_class = 0;

		for (Snippet s : snippets)
		{
			ds.runAll(s);
			score_detector.runDetector(s);

			if (s.getFeature(score_detector.featureNames()[0]) == null)
			{
				no_class++;
				continue;
			}

			if (s.getFeature(score_detector.featureNames()[0]).value() == 0)
			{
				zero_class++;
			}
			else if (s.getFeature(score_detector.featureNames()[0]).value() == 1)
			{
				one_class++;
			}

			//classified.add(s);
		}

		raykernel.io.Out.println("# Snippets:\t" + snippets.size());
		raykernel.io.Out.println("#  class 0:\t" + zero_class);
		raykernel.io.Out.println("#  class 1:\t" + one_class);
		raykernel.io.Out.println("# no class:\t" + no_class);

		//return classified;
		return snippets;
	}

	public static void logBugExperiment(Collection<Snippet> snippets, Class classiferType, Benchmark bench)
	{
		raykernel.io.Out.println("\n-- Log Bug Experiment --");
		raykernel.io.Out.println(bench);
		raykernel.io.Out.println(classiferType.getSimpleName() + "\n");

		//int max_files = 1000;
		//int max_functions = 10000;

		//*** run detectors

		LogBugDetector bug_detector = new LogBugDetector();

		Collection<Function> target_functions = bug_detector.loadFunctions(new File(bench.getMainClass()));

		raykernel.io.Out.println("extracted " + target_functions.size() + " functions");

		for (Function f : target_functions)
		{
			bug_detector.runDetector(f);
		}

		raykernel.io.Out.println("functions labeled");

		//*** Partition data

		//select set of functions with at least 1 fb
		Filter<Function> bugfunfilter = new Filter<Function>(new HasFeaturePredicate<Function>(bug_detector
				.featureNames()[0]));
		Filter<Function> reversefunfilter = new Filter<Function>(new PredicateNOT<Function>(
				new HasFeaturePredicate<Function>(bug_detector.featureNames()[0])));

		Collection<Function> functions_with_fb = bugfunfilter.filter(target_functions);
		Collection<Function> functions_without_fb = reversefunfilter.filter(target_functions, functions_with_fb.size());

		List<Function> all_functions = new LinkedList<Function>();
		all_functions.addAll(functions_with_fb);
		all_functions.addAll(functions_without_fb);

		raykernel.io.Out.println("\n--- Working Set ---");
		raykernel.io.Out.println("functions (bugs>0): " + functions_with_fb.size());
		raykernel.io.Out.println("functions (bugs=0): " + functions_without_fb.size());

		for (Function f : all_functions)
		{
			ds.runAll(f);
		}

		//*** Run Analysis

		raykernel.io.Out.println("\n--- Evaluation Results ---");

		//WekaVal wval = new WekaVal( snippets, classiferType, score_detector.featureNames()[0], ds.featureNames());
		//double w = wval.runStraightVal(all_functions, bug_detector.featureNames()[0]);

		//StraightVal sv_funs = new StraightVal(snippets, all_functions, classiferType, score_detector.featureNames()[0],
		//		bug_detector.featureNames()[0], ds.featureNames());

		StraightVal sv_funs = new StraightVal(all_functions, score_detector.featureNames()[0], bug_detector
				.featureNames()[0]);

		sv_funs.run();

		Analyzer ana = new Analyzer();

		float s = ana.getBestFmeasure(sv_funs.getResults());

		//io.Out.println( "weka fmeasure = " + w );
		raykernel.io.Out.println(" std fmeasure = " + s);

		ana.printScoreRatio(sv_funs.getResults());

	}

	public static void main(String[] args)
	{
		raykernel.io.Out.println("\n--- FeatureBased Bug Detection ---");

		//get files
		//File snippet_dir = new File("C:/snip");
		//File snippet_dir = new File("data/snippets");

		Benchmark bench = new Benchmark(new File(args[0]));
		//File scoreFile = new File("C:/Users/buse/Desktop/research/svn/readability/data/scorefiles/final.txt");
		//File scoreFile = new File("data/scorefiles/final.txt");

		//*** load and train

		//score_detector = new RandomScoreDetector( 12 );

		//score_detector = new SnippetScoreFeatureDetector(scoreFile, cutoff);

		//List<Snippet> snippets = getSnippets(scoreFile, snippet_dir, score_detector, ds);

		//io.Out.println("\n--- Run ---");

		//calcSnippetable( data_dir );

		//compareAvgValues( snippets, DetectorSuite.getDefaultSuite().featureNames() );

		//experimentA( snippets );

		//experimentB(snippets, NaiveBayes.class, new FindBugsDetector(), bench);

		//experimentB(snippets, NaiveBayes.class, new VersionBugDetector(), bench);
		//
		//experimentC(snippets, NaiveBayes.class, bench);

		//experimentE( snippets, NaiveBayes.class, new File( args[0] ) );

		//one snippet at a time
		//experimentD( snippets, NaiveBayes.class );

		//experimentF(snippets);

		experimentG(new FindBugsDetector(), bench, 1000);

		//logBugExperiment(snippets, NaiveBayes.class, bench);

	}

	/*
	private static void calcSnippetable(File data_dir) {
		
		int max_files = 200;
		int max_functions = 1200;
		
		Vector<CodeFile> code_files = CodeFileLoader.getFilesFromDir( data_dir, max_files );
		
		SnippetGenerator sg = new SnippetGenerator();
		FeatureDetector avgSnippetable = new AvgLineValueDetector( sg.featureNames()[0] );
		
		for( CodeFile f : code_files )
		{
			sg.runDetector(f);
		}
		
		List<Function> functions = FunctionExtractor.getAllFunctions(code_files, max_functions);
	
		float total = 0;
		float count = 0;
		for( Function f : functions )
		{
			avgSnippetable.runDetector(f);
			
			float coverage = f.getFeature(avgSnippetable.featureNames()[0]).value();
			total += coverage;
			
			//if( coverage > 0 )
			count++;
			 
			io.Out.println("coverage = " + coverage);
		}
		
		io.Out.println("averageSnippetable = " + total/count);
	}
	*/

}
