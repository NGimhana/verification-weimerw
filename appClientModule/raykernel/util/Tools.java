package raykernel.util;

import java.io.File;
import java.util.Date;

public class Tools
{

	public static File getSubDir(File dir, String sub)
	{
		String parent = dir.getPath();

		if (!parent.endsWith("/"))
		{
			parent = parent + "/";
		}

		parent = parent + sub;

		return makeDir(parent);
	}

	public static File makeDir(String s)
	{
		File wd = new File(s);

		if (!wd.exists())
		{
			raykernel.io.Out.println("creating dir: " + wd);
			wd.mkdir();
		}

		return wd;
	}

	public static String makeNameSimple(String name)
	{
		return name.substring(name.lastIndexOf(".") + 1);
	}

	public static void printTime()
	{
		raykernel.io.Out.println("TIMESTAMP: " + new Date());
	}

}
