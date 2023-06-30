package raykernel.config;

import raykernel.experiment.Benchmark;
import soot.Scene;

public class SootConfig
{
	public static void setClasspath(Benchmark b)
	{
		//String cp = Scene.v().getSootClassPath() + Config.getClassPathDelimiter() + b.getClasspath();
		String cp = b.getClasspath();

		Scene.v().setSootClassPath(cp);
		raykernel.io.Out.println("set soot classpath to: " + cp);
	}
}
