package raykernel.apps.readability.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import raykernel.apps.docinf.code.JMethod;
import raykernel.apps.readability.code.Function;
import raykernel.apps.readability.detectors.FunctionExtractor;
import raykernel.experiment.Benchmark;
import raykernel.io.FileFinder;

/**
 * Goal here is to print out method names and readability values
 * 
 * @author buse
 * 
 */
public class MethodReadability
{
	static Map<JMethod, Double> readabilityMap = new HashMap<JMethod, Double>();

	public static Double getReadability(JMethod jm)
	{
		return readabilityMap.get(jm);
	}

	public static void main(String[] args)
	{
		run(new Benchmark(args[0]));
	}

	public static void run(Benchmark bench)
	{
		//step1 get all the methods\
		List<Function> functions = FunctionExtractor.getAllFunctions(FileFinder.findAll(bench.getSrcDir(), "java"),
				9999);

		//step2 get readability
		ReadabilityEvaluator re = new ReadabilityEvaluator();

		for (Function f : functions)
		{
			JMethod jm = new JMethod(f.getClassName(), f.getMethodName());

			readabilityMap.put(jm, re.getReadability(f));

			raykernel.io.Out.println(jm + " " + re.getReadability(f));
		}
	}

}
