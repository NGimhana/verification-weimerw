package raykernel.apps.readability.snippet;

public class Score
{

	float score;
	int snippetNumber;
	long time;
	String user;

	public Score(int snippetNumber, String user, int score, long time)
	{
		this.snippetNumber = snippetNumber;
		this.user = user;
		this.score = score;
		this.time = time;
	}

	public Score(String s)
	{
		String[] tokens = s.split("\\p{Blank}");

		this.snippetNumber = Integer.parseInt(tokens[0]);
		this.score = Float.parseFloat(tokens[1]);

		if (tokens.length == 4)
		{
			this.user = tokens[2];
			this.time = Long.parseLong(tokens[3]);
		}
	}

	public float getScore()
	{
		return score;
	}

	public int getSnippetNumber()
	{
		return snippetNumber;
	}

	public long getTime()
	{
		return time;
	}

	public String getUser()
	{
		return user;
	}

	public void setScore(float score)
	{
		this.score = score;
	}

	public void setScore(int score)
	{
		this.score = score;
	}

	public void setSnippetNumber(int snippetNumber)
	{
		this.snippetNumber = snippetNumber;
	}

	public void setTime(long time)
	{
		this.time = time;
	}

	public void setUser(String user)
	{
		this.user = user;
	}

	public String toString()
	{
		return snippetNumber + " " + user + " " + score + " " + time;
	}

}
