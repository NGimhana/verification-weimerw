package raykernel.apps.readability.judger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.util.Date;
import java.util.HashMap;

import raykernel.apps.readability.code.FileExtensionFilter;
import raykernel.apps.readability.snippet.Score;
import raykernel.apps.readability.snippet.ScoreLog;

/**
 * The judger gui interacts with this
 * 
 * @author buse
 * 
 */
public class JudgerModel
{

	int currentSnippet = 1;
	GUI gui;
	ScoreLog log;
	boolean needToSave = false;
	HashMap<Integer, String> snippets;

	public JudgerModel(GUI judger)
	{
		this.gui = judger;
	}

	public int currentSnippetNumber()
	{
		return currentSnippet;
	}

	public int exportScores(File file)
	{
		log.writeScores(file);
		needToSave = false;
		return 0;
	}

	public String getCurrentSnippet()
	{
		if (!ready())
			return null;

		return snippets.get(currentSnippet);
	}

	public boolean isDone()
	{
		if (snippets == null)
			return false;

		return snippets.size() > 0 && currentSnippet == snippets.size();
	}

	/**
	 * Used to resume scoreing from previous point
	 * 
	 * @param selectedFile
	 * @return
	 */
	public int loadScores(File selectedFile)
	{

		log = new ScoreLog();

		int ret = log.readScores(selectedFile);
		if (ret < 0)
		{
			gui.dispError("Score file invalid - cannot resume");
			return ret;
		}

		ret = loadSnippets(log.snippetLocation);
		if (ret < 0)
		{
			gui.dispError("Could not load snippets");
			return ret;
		}

		currentSnippet = log.nextExpectedSnippetNumber();

		return ret;
	}

	public int loadSnippets(File directory)
	{
		try
		{
			FileFilter ff = new FileExtensionFilter("Snippet", "snip");

			if (!directory.isDirectory())
			{
				gui.dispError("Snippet directory not found");
				return -1;
			}

			File[] files = directory.listFiles(ff);

			if (files.length < 1)
			{
				gui.dispError("No snippets found!");
				return -1;
			}

			//reset
			snippets = new HashMap<Integer, String>();

			if (log == null || isDone())
			{
				log = new ScoreLog(directory);
				currentSnippet = 1;
			}

			//read in snippets
			for (File file : files)
			{
				BufferedReader br = new BufferedReader(new FileReader(file));
				StringBuffer buff = new StringBuffer();

				while (br.ready())
				{
					buff.append(br.readLine() + "\n");
				}

				int dot = file.getName().indexOf('.');
				int num = Integer.parseInt(file.getName().substring(0, dot));

				snippets.put(num, buff.toString());

			}
		}
		catch (Exception e)
		{
			gui.dispError("Unknown Error loading snippets.");
			e.printStackTrace();

			return -1;
		}

		return 0;

	}

	public boolean needsToSave()
	{
		return needToSave;
	}

	public boolean ready()
	{
		if (snippets == null || snippets.size() == 0)
			return false;
		return true;
	}

	public void scoreCurrent(int vote)
	{

		if (!ready())
			return;

		Date d = new Date();
		Score s = new Score(currentSnippet, "default", vote, d.getTime());

		log.addScore(s);

		if (currentSnippet <= snippets.size())
		{
			currentSnippet++;
		}

		needToSave = true;
	}

	public void setGUI(GUI gui)
	{
		this.gui = gui;
	}

	public int totalSnippets()
	{
		if (snippets == null)
			return 0;

		return snippets.size();
	}

}
