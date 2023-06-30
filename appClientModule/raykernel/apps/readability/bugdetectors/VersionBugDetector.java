package raykernel.apps.readability.bugdetectors;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import raykernel.apps.readability.code.CodeFile;
import raykernel.config.GlobalStats;
import raykernel.ml.feature.FeatureDetector;
import raykernel.ml.feature.StandardValueFeature;
import raykernel.ml.feature.Trainable;
import raykernel.util.Common;

public class VersionBugDetector implements FeatureDetector
{
	String name = "version_bug";

	public String[] featureNames()
	{
		return Common.makeSingletonArray("simple_bug");
	}

	public void runDetector(Trainable block)
	{

		if (!(block instanceof CodeFile))
			throw new IllegalArgumentException();

		CodeFile codefile = (CodeFile) block;

		if (!codefile.hasNewVersion())
			return;

		//diff new and ond version	
		String s = GlobalStats.PATH_DIFF + " \"" + codefile.getFile().getAbsolutePath() + "\" \""
				+ codefile.getNewVersion().getAbsolutePath() + "\"";

		Process proc = null;;

		try
		{
			proc = Runtime.getRuntime().exec(s);
			//proc.waitFor();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (proc == null)
			return;

		BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));

		String line;

		try
		{

			while ((line = br.readLine()) != null)
			{

				if (line.startsWith("-") || line.startsWith("<") || line.startsWith(">"))
				{
					continue;
				}

				int a = line.indexOf('a');

				if (a < 0)
				{
					a = line.indexOf('d');
				}

				if (a < 0)
				{
					a = line.indexOf('c');
				}

				if (a < 0)
				{
					//io.Out.println("diff not working as expected for : " + line);
					continue;
				}

				String nums = line.substring(0, a);

				String[] numarray = nums.split(",");

				try
				{
					for (String element : numarray)
					{
						int line_num = Integer.parseInt(element);
						codefile.getLine(line_num).setFeature(new StandardValueFeature(name, true));

						codefile.setBug(true);

						GlobalStats.bugCount++;

						raykernel.io.Out.println("vb bug " + GlobalStats.bugCount);

						;
						codefile.setFeature(new StandardValueFeature(name, true));
					}
				}
				catch (java.lang.NumberFormatException nfe)
				{
					raykernel.io.Out.println("couldnt figure: " + line);
				}
			}

			br.close();
		}
		catch (Exception e)
		{
			//io.Out.println("error reading files: " + e);
		}

	}

}
