package raykernel.apps.readability.code;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.regex.Pattern;

public interface CodeBlock extends Iterable<CodeLine>
{

	public CodeLine getLine(int num);

	public Iterator<CodeLine> getLines(int start, int end);

	public ListIterator<CodeLine> iterator();

	public CodeLine lineContaining(int index);

	public Iterator<CodeLine> linesContaining(int index1, int index2);

	public Iterator<CodeLine> linesMatching(Pattern p);

	public int size();

}
