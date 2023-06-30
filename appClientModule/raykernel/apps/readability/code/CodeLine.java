package raykernel.apps.readability.code;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.regex.Pattern;

import raykernel.ml.core.TrainableAdapter;

/**
 * A wrapper around a string, allows decorations in the form of Features
 * 
 * @author rlb7g
 * 
 */
public class CodeLine extends TrainableAdapter
{

	String line;

	public CodeLine(String line)
	{
		this.line = line;
	}

	public CodeLine getLine(int num)
	{
		if (num == 0)
			return this;
		return null;
	}

	public Iterator<CodeLine> getLines(int start, int end)
	{
		if (start == 0 && end == 0)
			return iterator();
		return null;
	}

	public ListIterator<CodeLine> iterator()
	{
		LinkedList<CodeLine> l = new LinkedList<CodeLine>();
		l.add(this);
		return l.listIterator();
	}

	public int length()
	{
		return line.length();
	}

	public CodeLine lineContaining(int index)
	{
		throw new UnsupportedOperationException();
	}

	public Iterator<CodeLine> linesContaining(int index1, int index2)
	{
		throw new UnsupportedOperationException();
	}

	public Iterator<CodeLine> linesMatching(Pattern p)
	{
		throw new UnsupportedOperationException();
	}

	public int size()
	{
		return 1;
	}

	public String toString()
	{
		return line;
	}

}
