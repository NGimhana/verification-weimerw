package raykernel.apps.readability.core;

import java.util.Vector;

import raykernel.apps.complexity.Cyclomatic;
import raykernel.apps.docinf.ast.UserDocInstance;
import raykernel.apps.docinf.code.JMethod;
import raykernel.apps.docinf.data.JavaDocDBase;
import raykernel.experiment.Benchmark;
import raykernel.util.Timer;

public class ReadComplexityCorr
{

	public static void main(String[] args)
	{
		run(new Benchmark(args[0]));
	}

	public static void run(Benchmark bench)
	{
		Timer.start();

		Cyclomatic.run(bench);

		Timer.lap("Complexity");

		MethodReadability.run(bench);

		Timer.lap("Readablity");

		JavaDocDBase.addSrcDir(bench.getSrcDir());
		UserDocInstance.recordUserDocs(JavaDocDBase.getInstance().getMethods());

		Vector<Double> readability = new Vector<Double>();
		Vector<Double> complexity = new Vector<Double>();
		Vector<Double> methlength = new Vector<Double>();

		int total = 0;

		for (JMethod jm : Cyclomatic.getMethods())
		{
			if (!bench.isMember(jm))
				continue;

			total++;

			Double read = MethodReadability.getReadability(jm);
			Integer comp = Cyclomatic.getComplexity(jm);
			Integer length = Cyclomatic.getLength(jm);

			if (total > 0 && read != null && comp != null && length != null)
			{
				readability.add(read);
				complexity.add(comp.doubleValue());
				methlength.add(length.doubleValue());

				raykernel.io.Out.println(jm + "\t" + Cyclomatic.getComplexity(jm) + "\t" + Cyclomatic.getLength(jm)
						+ "\t" + MethodReadability.getReadability(jm));
			}
		}

		Timer.lap("Computing correlation");

		raykernel.io.Out.println();

		Timer.printAll();

		raykernel.io.Out.println();
		raykernel.io.Out.println("readability/complexity corr: "
				+ raykernel.stats.Correlation.getPearsonCorrelation(readability, complexity));
		raykernel.io.Out.println("readability/length     corr: "
				+ raykernel.stats.Correlation.getPearsonCorrelation(readability, methlength));
		raykernel.io.Out.println("complexity/length      corr: "
				+ raykernel.stats.Correlation.getPearsonCorrelation(complexity, methlength));

	}
}
