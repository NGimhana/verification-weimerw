package raykernel.apps.readability.snippet;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import raykernel.apps.readability.code.CodeBlock;
import raykernel.apps.readability.code.CodeLine;
import raykernel.apps.readability.code.Function;
import raykernel.apps.readability.code.TrainableCodeBlock;
import raykernel.apps.readability.detectors.BasicCommentDetector;
import raykernel.apps.readability.detectors.FunctionDetector;
import raykernel.ml.feature.Feature;
import raykernel.ml.feature.FeatureDetector;
import raykernel.ml.feature.StandardValueFeature;
import raykernel.ml.feature.Trainable;
import raykernel.util.Common;

public class SnippetGenerator implements FeatureDetector
{

	private static final int MAX_LINES = 12;
	private static final int SIZE = 3;

	String classFeature = null;
	BasicCommentDetector commentDetect = new BasicCommentDetector();
	private final Iterator<CodeLine> emptyIter = (new LinkedList<CodeLine>()).iterator();

	Random rand;

	public SnippetGenerator()
	{
		rand = new Random();
	}

	public String featureName()
	{
		return "Snippetable";
	}

	public String[] featureNames()
	{
		return Common.makeSingletonArray(featureName());
	}

	public Snippet generateFrom(CodeBlock b, int startLine)
	{
		Iterator<CodeLine> iter = growSnippet(b, startLine);
		Snippet s = new Snippet();

		if (iter == emptyIter)
			return null;

		while (iter.hasNext())
		{
			CodeLine l = iter.next();
			s.addLine(l);
		}

		return s;
	}

	/**
	 * Start with a random line then grow downward until we have a sufficient
	 * number of insn statements
	 * 
	 * Grow upward until we reach a an insn line or leave the function
	 * 
	 */
	public Snippet generateRandom(Function candidate)
	{
		commentDetect.runDetector(candidate);

		if (candidate.size() < SIZE)
			return null;

		//try 5 times, return null if we can find any snippets
		for (int i = 0; i < 5; i++)
		{
			Snippet s = generateFrom(candidate, rand.nextInt(candidate.size()));
			if (s != null)
				return s;
		}

		return null;
	}

	/**
	 * Returns -1 if there arent enoguh lines to generate a complete snippet
	 */
	private int growDown(CodeBlock b, int startLine)
	{
		int insns = 0;
		int currNestingDepth = 0;

		for (int currLine = startLine; currLine < b.size(); currLine++)
		{
			CodeLine l = b.getLine(currLine);

			if (isInstructionLine(l))
			{
				insns++;

				if (insns == SIZE)
					return currLine;
			}

			String trimed = l.toString().trim();

			if (trimed.startsWith("}") && trimed.endsWith("{"))
				return -1;

			currNestingDepth += FunctionDetector.netDepthChange(l.toString());

			if (currNestingDepth < 0) //if we left this block
				return -1;
		}

		return -1;
	}

	private Iterator<CodeLine> growSnippet(CodeBlock b, int startLine)
	{
		String start = b.getLine(startLine).toString();

		if (start.contains("{") && start.contains("}"))
			return emptyIter;

		int first = growUp(b, startLine);
		int last = growDown(b, startLine);

		if (first < 0 || last < 0 || last - first < SIZE || last - first > MAX_LINES)
			return emptyIter;

		return b.getLines(first, last + 1);
	}

	private int growUp(CodeBlock b, int startLine)
	{
		if (startLine == 0)
			return startLine;

		for (int currLine = startLine - 1; currLine >= 0; currLine--)
		{
			CodeLine l = b.getLine(currLine);

			if (isInstructionLine(l) || l.toString().contains("}"))
				return currLine + 1; //back up 1 and return
		}

		return 0;
	}

	private boolean isInstructionLine(CodeLine l)
	{
		String s = l.toString().trim();

		if (s.length() < 4)
			return false;

		if (s.startsWith("for") || s.startsWith("/") || s.startsWith("*"))
			return false;

		return s.contains(";");
	}

	public void printSnippetable(TrainableCodeBlock b)
	{
		runDetector(b);

		for (CodeLine l : b)
		{
			Feature sn = l.getFeature(featureName());

			if (sn != null && sn.value() > 0)
			{
				System.out.print("# ");
			}
			else
			{
				System.out.print("  ");
			}

			raykernel.io.Out.println(l);
		}
	}

	/**
	 * Label lines that could be in a snippet
	 */
	public void runDetector(Trainable block)
	{
		TrainableCodeBlock b = (TrainableCodeBlock) block;

		commentDetect.runDetector(b);

		for (CodeLine l : b)
		{
			l.setFeature(new StandardValueFeature(this.featureName(), false));

		}

		for (int i = 0; i < b.size(); i++)
		{
			int first = growUp(b, i);
			int last = growDown(b, i);

			if (first < 0 || last < 0 || last - first < SIZE)
			{
				continue;
			}

			//else label lines
			Iterator<CodeLine> iter = growSnippet(b, i);

			while (iter.hasNext())
			{
				iter.next().setFeature(new StandardValueFeature(this.featureName(), true));
			}
		}
	}

	public void setClassFeature(String cf)
	{
		classFeature = cf;
	}

	/*
	public static void main( String[] args )
	{
		CodeFile f = new CodeFile( new File(args[0]) );
		
		SnippetGenerator gen = new SnippetGenerator( );
		
		gen.printSnippetable(f);
		
	}
	

	public static void main( String[] args )
	{
		CodeFile f = new CodeFile( new File(args[0]) );
		
		SnippetGenerator gen = new SnippetGenerator( 4, 4, 10 );
		
		for( int i=0; i<20; i++ )
		{
			String s = gen.generateRandom(f);
			
			if( s != null )
			{
				io.Out.println("Snippet #" + i + "\n" + s);
			}
		}
	}
	*/

}
