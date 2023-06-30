package raykernel.experiment;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import raykernel.apps.docinf.code.JMethod;
import raykernel.config.Config;
import raykernel.util.TagFile;
import raykernel.util.Tools;

public class Benchmark
{

	public static Benchmark loadDefault()
	{

		return new Benchmark("C:/Users/buse/workspace/pathfreq/bench/simple.txt");

	}
	String descFile;

	TagFile tags;

	public Benchmark(File f)
	{
		tags = new TagFile(f);
		descFile = f.getAbsolutePath();
	}

	public Benchmark(String f)
	{
		tags = new TagFile(new File(f));
		descFile = f;
	}

	public List<String> getArgs()
	{
		List<String> args = tags.getValues("args");
		if (args == null)
			return new LinkedList<String>();

		return args;
	}

	public String getArgString()
	{
		StringBuffer buf = new StringBuffer();

		for (String arg : getArgs())
		{
			buf.append(arg + " ");
		}

		return buf.toString();
	}

	private File getBaseDirectory()
	{
		return Tools.getSubDir(Config.getBenchDirectory(), getName());
	}

	public File getBinDir()
	{
		return Tools.getSubDir(getBaseDirectory(), "bin");
	}

	String getClassName(File f)
	{
		String path = f.getAbsolutePath();

		path = path.substring(getBinDir().getPath().length() + 1, path.lastIndexOf('.'));

		path = path.replace("\\", ".");

		return path;
	}

	public String getClasspath()
	{
		StringBuffer buf = new StringBuffer();

		buf.append(Config.getClassPathDelimiter() + getJars(new File(Config.get("java_lib_dir"))));

		if (getBinDir() != null)
		{
			buf.append(getBinDir() + Config.getClassPathDelimiter());
		}

		if (getLibDir() != null)
		{
			buf.append(Config.getClassPathDelimiter() + getJars(getLibDir()));
		}

		return buf.toString();
	}
	public String getDescFile()
	{
		return this.descFile;
	}

	public String getJars(File dir)
	{

		StringBuffer buf = new StringBuffer();

		if (dir != null && dir.exists())
		{
			for (File f : dir.listFiles())
			{
				if (f.getName().endsWith(".jar"))
				{
					buf.append(f.getAbsolutePath() + Config.getClassPathDelimiter());
				}
			}
		}
		return buf.toString();
	}

	public File getLibDir()
	{
		return Tools.getSubDir(getBaseDirectory(), "lib");
	}

	public String getMainClass()
	{
		return tags.getValue("main");
	}

	public String getName()
	{
		return tags.getValue("name");
	}

	public File getOldDir()
	{
		return Tools.getSubDir(getBaseDirectory(), "old");
	}

	public List<String> getProperties(String tag)
	{
		return tags.getValues(tag);
	}

	public String getProperty(String tag)
	{
		return tags.getValue(tag);
	}

	public File getSrcDir()
	{
		return Tools.getSubDir(getBaseDirectory(), "src");
	}

	public Collection<String> getTargetClasses()
	{
		Collection<String> targets = tags.getValues("targets");

		String filter = null;

		if (tags.getValues("targetfilter") != null)
		{
			filter = tags.getValues("targetfilter").get(0);
		}

		if (targets == null) //need to seach
		{
			List<File> files = raykernel.io.FileFinder.findAll(getBinDir(), "class");

			targets = new LinkedList<String>();
			for (File f : files)
			{
				String name = getClassName(f);

				if (filter == null || name.startsWith(filter))
				{
					targets.add(name);
				}
			}
		}

		return targets;
	}

	public File getWorkingDirectory()
	{
		return Tools.getSubDir(Config.getWorkingDirectory(), getName());
	}

	public boolean hasProperty(String tag)
	{
		return tags.getValues(tag) != null;
	}

	public boolean isMember(JMethod m)
	{

		String pack = m.getPackage();

		//io.Out.println("is " + pack + " in " + getProperties("packages") + "?");

		for (String appPackage : this.getProperties("packages"))
		{
			if (pack.contains(appPackage))
			{
				return true;
			}
		}

		return false;
	}
}
