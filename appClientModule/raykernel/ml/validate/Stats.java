package raykernel.ml.validate;

import java.util.Collection;

public class Stats
{

	public static float getAverageScore(Collection<LineStat> results)
	{
		float sum = 0;
		float line_count = 0;

		for (LineStat stat : results)
		{
			line_count++;
			sum += stat.getScore();
		}

		return sum / line_count;
	}

	public static float getAvgBugScore(Collection<LineStat> results)
	{
		float sum = 0;
		float num = 0;

		for (LineStat stat : results)
		{
			if (stat.isBug())
			{
				sum += stat.getScore();
				num++;
			}
		}

		return sum / num;
	}

	public static float getAvgNonBugScore(Collection<LineStat> results)
	{
		float sum = 0;
		float num = 0;

		for (LineStat stat : results)
		{
			if (!stat.isBug())
			{
				sum += stat.getScore();
				num++;
			}
		}

		return sum / num;
	}

	public static int getNumBugs(Collection<LineStat> results)
	{
		int bugs = 0;

		for (LineStat stat : results)
		{
			if (stat.isBug())
			{
				bugs++;
			}
		}

		return bugs;
	}

	public static float getPctCorrect(Collection<LineStat> results, float cutoff)
	{
		int correct = 0;
		int total = 0;

		for (LineStat stat : results)
		{
			if (stat.isBug() && stat.getScore() > cutoff)
			{
				correct++;
			}
			else if (!stat.isBug() && stat.getScore() <= cutoff)
			{
				correct++;
			}

			total++;
		}

		return (float) correct / (float) total;
	}

	/**
	 * 
	 * A: number of bug lines above cutoff C: number of non-bug lines above
	 * cutoff
	 * 
	 * precision = A/(A+C)
	 * 
	 */
	public static float getPrecision(Collection<LineStat> results, float cutoff)
	{
		int a = 0;
		int c = 0;

		for (LineStat stat : results)
		{
			if (stat.getScore() > cutoff)
			{
				if (stat.isBug())
				{
					a++;
				}
				else
				{
					c++;
				}
			}
		}

		//io.Out.println("a=" + a + " c=" + c);

		if (a + c == 0)
			return 0;

		return (float) a / (a + c);
	}

	/**
	 * 
	 * A: number of bug lines above cutoff B: number of bug lines below cutoff
	 * 
	 * recall = A/(A+B)
	 * 
	 */
	public static float getRecall(Collection<LineStat> results, float cutoff)
	{
		int a = 0;
		int b = 0;

		for (LineStat stat : results)
		{
			if (stat.isBug())
			{
				if (stat.getScore() > cutoff)
				{
					a++;
				}
				else
				{
					b++;
				}
			}
		}

		//io.Out.println("a=" + a + " b=" + b);

		return (float) a / (a + b);
	}

}
