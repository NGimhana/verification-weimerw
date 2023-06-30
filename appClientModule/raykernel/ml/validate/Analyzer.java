package raykernel.ml.validate;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import raykernel.ml.feature.Trainable;
import raykernel.stats.KendallsTau;
import raykernel.stats.Sorter;

/**
 * 
 * Methods of or analyssis of the results
 * 
 * @author rlb7g
 * 
 */
public class Analyzer
{

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

	public Analyzer()
	{
	}

	public float getAvgBestFmeasure(Vector<Vector<LineStat>> name)
	{
		float total = 0;
		float count = 0;

		for (Collection<LineStat> runStats : name)
		{
			total += getBestFmeasure(runStats);
			count++;
		}

		return total / count;
	}

	public float getAvgKendallsTau(Vector<Vector<LineStat>> name, String rankFeature, int bins)
	{
		float total = 0;
		float count = 0;

		for (Vector<LineStat> runStats : name)
		{
			total += getTau(runStats, rankFeature, bins);
			count++;
		}

		return total / count;
	}

	public float getBestFmeasure(Collection<LineStat> results)
	{
		//float interval = Stats.getAverageScore( results ) / 10;

		//if( interval < 0.05 )
		float interval = (float) 0.05;

		//float interval = (float) 0.1;

		float best_fmeas = 0;

		for (float i = 0; i < 1; i += interval)
		{
			float p = Stats.getPrecision(results, i);
			float r = Stats.getRecall(results, i);

			//float fmeas = Stats.getPctCorrect(results, i);

			float fmeas = (2 * p * r) / (p + r);

			//io.Out.println( "[cutoff=" + i + "] [prec=" + p + "] [recall=" + r + "] [fmeas = " + fmeas + "]");

			if (fmeas > best_fmeas)
			{
				best_fmeas = fmeas;
			}

			float stop = (float) 0.000001;

			if (fmeas < stop)
			{
				break;
			}

		}

		//io.Out.println("best fmeas = " + best_fmeas + " @ cutoff = " + best_cutoff );

		return best_fmeas;

		//return  Stats.getPctCorrect(results, best_cutoff);
	}

	//will change order of runstats
	public float getTau(Vector<LineStat> runStats, String estFeature, int bins)
	{
		List<Trainable> sortCorrect = new LinkedList<Trainable>();
		List<Trainable> sortByEval = new LinkedList<Trainable>();

		for (LineStat l : runStats)
		{
			sortCorrect.add(l.getTrainable());
			sortByEval.add(l.getTrainable());
		}

		Sorter.sortByFeature(sortCorrect, runStats.get(0).getScoreFeature());
		Sorter.sortByFeature(sortByEval, estFeature);

		raykernel.io.Out.println("sorting by: " + runStats.get(0).getScoreFeature() + " and " + estFeature);

		return (float) KendallsTau.computeDistance(sortCorrect, sortByEval, bins);
	}

	public void printScoreRatio(Collection<LineStat> results)
	{
		float bug = Stats.getAvgBugScore(results);
		float non = Stats.getAvgNonBugScore(results);

		raykernel.io.Out.println("AvgBugScore    = " + bug);
		raykernel.io.Out.println("AvgNonBugScore = " + non);
		raykernel.io.Out.println("Ratio          = " + (bug / non));
	}

	public void printScores(Collection<LineStat> results)
	{
		for (LineStat ls : results)
		{
			raykernel.io.Out.println(ls);
		}
	}

}
