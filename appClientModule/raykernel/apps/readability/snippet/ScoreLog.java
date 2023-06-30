package raykernel.apps.readability.snippet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class ScoreLog
{

	Vector<Score> scores = new Vector<Score>();
	public File snippetLocation;

	public ScoreLog()
	{

	}

	public ScoreLog(File directory)
	{
		// TODO Auto-generated constructor stub
	}

	public void addScore(Score s)
	{
		// TODO Auto-generated method stub

	}

	public Score getScore(int SnippetNumber)
	{
		return scores.get(SnippetNumber);
	}

	public int nextExpectedSnippetNumber()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public int readScores(File file)
	{
		scores = new Vector<Score>();
		scores.setSize(101);

		try
		{
			BufferedReader br = new BufferedReader(new FileReader(file));

			while (br.ready())
			{
				String next = br.readLine();
				if (next.trim().length() == 0)
				{
					continue;
				}

				Score s = new Score(next);

				scores.add(s.getSnippetNumber(), s);
			}
		}

		catch (IOException e)
		{
			System.err.println("Couldnt read score file: " + file);
			return -1;
		}

		return 0;
	}

	public int writeScores(File file)
	{
		try
		{
			BufferedWriter br = new BufferedWriter(new FileWriter(file));

			for (Score s : scores)
			{
				br.write(s + "\n");
			}

			br.flush();
		}

		catch (IOException e)
		{
			System.err.println("Couldnt write score file");
			return -1;
		}

		return 0;
	}

}
