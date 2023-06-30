package raykernel.experiment;

import java.io.File;

public class BenchmarkOld
{

	File binDir;
	File mainDir;
	private String name = "default";
	File oldDir;

	File srcDir;

	public BenchmarkOld(File dir)
	{
		mainDir = dir;
		srcDir = new File(dir + "/src");
		binDir = new File(dir + "/bin");
		oldDir = new File(dir + "/old");
	}

	public BenchmarkOld(File src, String name)
	{
		srcDir = src;
		this.setName(name);
	}

	public File getBinDir()
	{
		return binDir;
	}

	public File getMainDir()
	{
		return mainDir;
	}

	public String getName()
	{
		return name;
	}

	public File getOldDir()
	{
		return oldDir;
	}

	public File getSrcDir()
	{
		return srcDir;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String toString()
	{
		return "Benchmark: " + mainDir.getName();
	}

}
