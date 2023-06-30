package raykernel.apps.readability.snippet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import raykernel.apps.readability.code.CodeLine;

public class SnippetLoader
{

	public static Vector<Snippet> loadSnippets(File directory)
	{
		Vector<Snippet> snippets = new Vector<Snippet>();

		//read in meta file creating empty snippets for each line
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(directory + "/meta.txt"));

			while (br.ready())
			{
				String s = br.readLine();
				Snippet new_snippet = new Snippet(s);
				snippets.add(new_snippet);
			}
		}
		catch (IOException e)
		{
			raykernel.io.Out.println("error reading snippet meta file: " + directory + "/meta.txt");
		}

		int total = 0;

		//now load the content
		for (Snippet sn : snippets)
		{
			try
			{
				BufferedReader br = new BufferedReader(new FileReader(directory.toString() + "/" + sn.getNumber()
						+ ".jsnp"));

				while (br.ready())
				{
					sn.addLine(new CodeLine(br.readLine()));
					total++;
				}
			}
			catch (IOException e)
			{
				raykernel.io.Out.println("error reading snippet");
			}
		}

		//io.Out.println("avg size = " + (total/100.0));

		return snippets;
	}

}
