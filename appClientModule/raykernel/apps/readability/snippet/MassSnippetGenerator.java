package raykernel.apps.readability.snippet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import raykernel.apps.readability.code.Function;
import raykernel.apps.readability.detectors.FunctionExtractor;

/**
 * Generate a snippet set, and accociated database Run any detectors you want
 * first to get those included in the dbase
 */
public class MassSnippetGenerator
{

	static final int MAX_FUNS = 1000;
	@SuppressWarnings("unchecked")
	public static void shuffle(List list)
	{
		for (int lastPlace = list.size() - 1; lastPlace > 0; lastPlace--)
		{
			// Choose a random location from among 0,1,...,lastPlace.
			int randLoc = (int) (Math.random() * (lastPlace + 1));
			// Swap items in locations randLoc and lastPlace.
			Object o = list.set(lastPlace, list.get(randLoc));
			list.set(randLoc, o);
		}
	}
	Vector<Function> functions;
	Vector<Snippet> generatedSnippets;
	String name;

	Random rand = new Random();

	public MassSnippetGenerator(List<File> files, String name)
	{
		functions = new Vector<Function>();
		this.name = name;

		for (Function f : FunctionExtractor.getAllFunctions(files, MAX_FUNS))
		{
			functions.add(f);
		}
	}

	public void generateSnippets(int numSnippets)
	{
		generatedSnippets = new Vector<Snippet>();
		SnippetGenerator gen = new SnippetGenerator();

		while (this.generatedSnippets.size() < numSnippets)
		{
			Function f = pickRandomFunction();

			Snippet s = gen.generateRandom(f);

			functions.remove(f);

			if (s == null)
			{
				continue;
			}

			//s.setFileName( f.getFile().getName() );
			s.setSource(name);

			generatedSnippets.add(s);
		}

		shuffle(generatedSnippets);

		//renumber snippets
		for (int i = 0; i < generatedSnippets.size(); i++)
		{
			generatedSnippets.get(i).setNumber((i + 1) * 5 - 4); //start numbering at 1

			raykernel.io.Out.println("----new snippet-----\n" + generatedSnippets.get(i) + "---------");
		}
	}

	public void outputMetaData(File metaFile)
	{
		try
		{
			BufferedWriter br = new BufferedWriter(new FileWriter(metaFile));

			for (Snippet s : generatedSnippets)
			{
				br.write(s.toMetaString() + "\n");
			}

			br.flush();
		}

		catch (IOException e)
		{
			System.err.println("Couldnt write snippet meta file");
			e.printStackTrace();
		}

	}

	public void outputSnippets(File dir)
	{
		dir.mkdir();

		String path = dir.getAbsolutePath() + "\\";

		for (Snippet s : generatedSnippets)
		{
			try
			{
				BufferedWriter br = new BufferedWriter(new FileWriter(path + s.number + ".jsnp"));

				br.write(s.toString());
				br.flush();
			}

			catch (IOException e)
			{
				System.err.println("Couldnt write jsnip file");
				e.printStackTrace();
			}
		}
	}

	public void outputSnippetsHTML(File dir, int text_size)
	{
		dir.mkdir();

		String path = dir.getAbsolutePath() + "\\";

		for (Snippet s : generatedSnippets)
		{
			try
			{
				BufferedWriter br = new BufferedWriter(new FileWriter(path + s.number + ".snip"));

				br.write(s.toHtmlString(text_size));
				br.flush();
			}

			catch (IOException e)
			{
				System.err.println("Couldnt write snippet file");
				e.printStackTrace();
			}
		}
	}

	private Function pickRandomFunction()
	{
		return functions.get(rand.nextInt(functions.size()));
	}

}
