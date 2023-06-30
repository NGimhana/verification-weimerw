package raykernel.apps.readability.code;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import raykernel.ml.feature.Feature;

public abstract class CodeBlockAdapter implements CodeBlock
{

	public static String featureAnnotation(Feature f)
	{
		return featureAnnotation(f.name(), f.value());
	}

	public static String featureAnnotation(String name, float value)
	{
		return "[" + name + ", " + value + "]";
	}

	//	content as list of lines  
	protected LinkedList<CodeLine> codeLines = new LinkedList<CodeLine>();

	public CodeLine getLine(int num)
	{
		return codeLines.get(num);
	}

	public Iterator<CodeLine> getLines(int start, int end)
	{
		if (start < 0)
		{
			start = 0;
		}

		if (end < 0)
		{
			end = 0;
		}

		if (start > codeLines.size() - 1)
		{
			start = codeLines.size() - 1;
		}

		if (end > codeLines.size() - 1)
		{
			end = codeLines.size() - 1;
		}

		if (end <= start)
			return (new LinkedList<CodeLine>()).iterator();

		return codeLines.subList(start, end).iterator();
	}

	public ListIterator<CodeLine> iterator()
	{
		return codeLines.listIterator();
	}

	public CodeLine lineContaining(int index)
	{
		int total = 0;
		for (CodeLine l : this.codeLines)
		{
			if (total > index) //just passed it
				return l;

			total += l.length() + 1;
		}

		return null;
	}

	/**
	 * Return an iterator over all the lines that overlap the set of chars
	 * between index1 and index2 inclusive.
	 * 
	 * @param index1
	 * @param index2
	 * @return
	 */
	public Iterator<CodeLine> linesContaining(int index1, int index2)
	{
		return listContaining(index1, index2).iterator();
	}

	public Iterator<CodeLine> linesMatching(Pattern p)
	{

		Matcher matcher = p.matcher(this.codeLines.toString());

		LinkedList<CodeLine> matchingLines = new LinkedList<CodeLine>();

		while (matcher.find())
		{
			matchingLines.addAll(listContaining(matcher.start(), matcher.end()));
		}

		return matchingLines.iterator();
	}

	protected LinkedList<CodeLine> listContaining(int index1, int index2)
	{
		if (index1 > index2)
			throw new IllegalArgumentException("Index one must be <= index2!");

		CodeLine first = lineContaining(index1);
		CodeLine last = lineContaining(index2);

		if (first == null || last == null)
			throw new IllegalArgumentException("Indexes appear to be out of range.  1: " + index1 + ", 2: " + index2);

		int lineNum = codeLines.indexOf(first); //codelines could store their index to make this O(1)
		Iterator<CodeLine> iter = codeLines.listIterator(lineNum);
		LinkedList<CodeLine> buffer = new LinkedList<CodeLine>();

		while (true)
		{
			CodeLine next = iter.next();
			buffer.add(next);

			if (next == last)
			{
				break;
			}
		}

		return buffer;
	}

	public int size()
	{
		return this.codeLines.size();
	}

	public String toString()
	{
		StringBuffer str = new StringBuffer();

		for (CodeLine l : this)
		{
			str.append(l.toString() + "\n");
		}

		return str.toString();
	}

}
