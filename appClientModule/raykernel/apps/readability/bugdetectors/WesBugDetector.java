package raykernel.apps.readability.bugdetectors;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import raykernel.apps.readability.code.CodeFile;
import raykernel.config.GlobalStats;
import raykernel.ml.feature.FeatureDetector;
import raykernel.ml.feature.StandardValueFeature;
import raykernel.ml.feature.Trainable;
import raykernel.util.Common;

public class WesBugDetector implements FeatureDetector
{

	public static String path_to_jil = "/home/rlb7g/workspace/FeatureBugFinder/bin/jil";

	String name = "jil";

	public String[] featureNames()
	{
		return Common.makeSingletonArray("simple_bug");
	}

	public void runDetector(Trainable block)
	{

		if (!(block instanceof CodeFile))
			throw new IllegalArgumentException();

		CodeFile codefile = (CodeFile) block;

		File f = codefile.getFile();

		try
		{

			String s = path_to_jil + " " + f.getAbsolutePath();

			Process proc = Runtime.getRuntime().exec(s);

			BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));

			String line;

			while ((line = br.readLine()) != null)
			{
				if (line.startsWith("RAYBUSE"))
				{
					String[] line_s = line.split(" ");

					try
					{
						int line_num = Integer.parseInt(line_s[2]);
						codefile.getLine(line_num).setFeature(new StandardValueFeature(name, true));

						GlobalStats.bugCount++;
					}
					catch (java.lang.NumberFormatException nfe)
					{

					}
				}

			}

			br.close();

		}
		catch (IOException e)
		{
		}

	}

}
