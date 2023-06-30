package raykernel.apps.readability.core;

import java.io.File;
import java.util.List;

import raykernel.apps.readability.snippet.MassSnippetGenerator;

public class SnippetGenMain
{

	private static String desc = "default";

	private static boolean html = true;
	private static boolean jsnip = true;

	private static int MAXFILES = 200;
	private static int num_snippets = 5;
	private static String output_dir = "c:/snippetout";

	private static File checkDir(String path)
	{
		File dir = new File(path);

		if (!dir.exists() || !dir.isDirectory())
		{
			raykernel.io.Out.println("Invalid directory: " + path);
			System.exit(0);
		}

		return dir;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{

		if (args.length == 0)
		{
			raykernel.io.Out.println("Provide args!");
			return;
		}

		File dir = checkDir(args[0]);
		File outputDir = tryCreateDir(output_dir);

		List<File> javaFiles = raykernel.io.FileFinder.findAll(dir, "java");

		if (javaFiles.size() > MAXFILES)
		{
			javaFiles = javaFiles.subList(0, MAXFILES);
		}

		raykernel.io.Out.println("candidate files = " + javaFiles.size());

		MassSnippetGenerator gen = new MassSnippetGenerator(javaFiles, desc);

		gen.generateSnippets(num_snippets);

		gen.outputMetaData(new File(outputDir.getAbsolutePath() + "/meta.txt"));

		if (jsnip)
		{
			gen.outputSnippets(new File(outputDir.getAbsolutePath()));
		}

		if (html)
		{
			gen.outputSnippetsHTML(new File(outputDir.getAbsolutePath()), 0);
		}

	}

	private static File tryCreateDir(String path)
	{
		File dir = new File(path);
		dir.mkdir();

		if (!dir.exists())
		{
			raykernel.io.Out.println("Couldn't create directory: " + path);
			System.exit(0);
		}

		return dir;
	}

}
