package raykernel.apps.readability.snippet;

import java.util.TreeMap;

import raykernel.apps.readability.code.CodeLine;
import raykernel.apps.readability.code.TrainableCodeBlock;
import raykernel.ml.feature.Trainable;
import de.java2html.Java2Html;

/**
 * Essentially a String with metadata
 * 
 * @author buse
 * 
 */
public class Snippet extends TrainableCodeBlock implements Trainable
{

	static final int HTMLFONTSIZE = 5;
	String benchmark;
	String fileName;

	int number;

	TreeMap<String, Integer> scores = new TreeMap<String, Integer>();

	public Snippet()
	{

	}

	public Snippet(int number)
	{
		this.number = number;
	}

	/**
	 * Create snippet from meta line
	 * 
	 * @param s
	 */
	public Snippet(String s)
	{
		String[] meta_strs = s.split(" ");

		number = Integer.parseInt(meta_strs[0]);
	}

	public Snippet(String string, int number)
	{
		this.number = number;
		String[] lines = string.split("\n");

		for (String line : lines)
		{
			codeLines.add(new CodeLine(line));
		}
	}

	public void addLine(CodeLine l)
	{
		codeLines.add(l);
	}

	public void addScore(String user, int score)
	{
		scores.put(user, score);
	}

	public String getFileName()
	{
		return fileName;
	}

	public int getNumber()
	{
		return number;
	}

	public int getScore(String user)
	{
		return scores.get(user);
	}

	public String getSource()
	{
		return benchmark;
	}

	public void setFileName(String fName)
	{
		fileName = fName;
	}

	public void setNumber(int i)
	{
		number = i;

	}

	public void setSource(String benchmark)
	{
		this.benchmark = benchmark;
	}

	public String toHtmlString(int text_size)
	{
		String tmp = Java2Html.convertToHtml(toString());

		if (text_size > 0)
		{
			tmp = tmp.replace("font color", "font size=" + text_size + " color");
		}

		return "<html> \n" + tmp + "</html> \n";
	}

	public String toMetaString()
	{
		return number + " " + benchmark + " " + fileName;
	}

	public String toString()
	{
		StringBuffer buf = new StringBuffer();

		for (CodeLine l : this)
		{
			buf.append(l + "\n");
		}

		return buf.toString();
	}

	public String codeableString()
	{
		StringBuffer buf = new StringBuffer();

		for (CodeLine l : this)
		{
			if (buf.length() != 0)
				buf.append(" + \n");

			buf.append("\"" + l.toString().replace("\"", "\\\"") + "\\n\"");
		}

		return buf.toString();
	}

}
