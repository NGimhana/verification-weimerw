package raykernel.apps.readability.core;

import java.io.File;
import java.util.List;

import raykernel.apps.readability.code.Function;
import raykernel.apps.readability.detectors.FunctionExtractor;
import raykernel.io.FileFinder;
import raykernel.util.Timer;

public class WholeProgramReadability
{

	public static void main(String[] args)
	{
		run(args[0]);
	}

	public static void run(String bench)
	{
		File src = new File(bench);

		Timer.start();

		//step1 get all the methods
		List<Function> functions = FunctionExtractor.getAllFunctions(FileFinder.findAll(src, "java"), 9999);

		//step2 get readability
		ReadabilityEvaluator re = new ReadabilityEvaluator();

		Timer.lap("Training");

		double sum = 0;

		for (Function f : functions)
		{
			double read = re.getReadability(f);

			raykernel.io.Out.println(f.getClassName() + "." + f.getMethodName() + " : " + read);

			sum += read;
		}

		double mean = sum / (double) functions.size();
		raykernel.io.Out.println("\nAverage: " + mean);

		Timer.lap("Evaluation (" + functions.size() + " methods)");

		Timer.printAll();
	}
}
