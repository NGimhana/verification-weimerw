package raykernel.experiment;

import java.io.File;

import raykernel.util.Runner;

public class BenchmarkRunner
{
	public static void main(String[] args)
	{
		if (args.length > 0)
		{
			run(new Benchmark(args[0]));
		}
		else
		{
			run(Benchmark.loadDefault());
		}
	}

	public static void run(Benchmark benchmark)
	{
		double time = Runner.runCommand("java " + benchmark.getMainClass() + " " + benchmark.getArgString(), new File(
				"C:/Users/buse/workspace/pathfreq/benchbin"));

		raykernel.io.Out.println("Time(s) = " + time);
	}
}
