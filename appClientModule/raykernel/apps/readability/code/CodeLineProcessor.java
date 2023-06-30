package raykernel.apps.readability.code;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class CodeLineProcessor
{

	static HashSet<String> keyWords;

	private static final String[] KEYWORDS = {"abstract", "assert", "boolean", "break", "byte", "case", "catch",
			"char", "class", "const", "continue", "default", "do", "double", "else", "enum", "extends", "false",
			"final", "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface",
			"long", "native", "new", "null", "package", "private", "protected", "public", "return", "short", "static",
			"strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "true", "try",
			"void", "volatile", "while"};

	public static List<String> getWords(CodeLine l)
	{
		String[] words = l.toString().split("[\\p{Punct}\\p{Blank}]");

		List<String> ret = new LinkedList<String>();

		for (String word : words)
		{
			if (word.length() < 1)
			{
				continue;
			}

			ret.add(word);
		}

		return ret;
	}

	public static void init()
	{
		if (keyWords != null)
			return;

		keyWords = new HashSet<String>();

		for (String k : KEYWORDS)
		{
			keyWords.add(k);
		}
	}

	public static boolean isKeyWord(String s)
	{
		init();
		return keyWords.contains(s);
	}

	public static boolean isNumber(String s)
	{
		try
		{
			Integer.parseInt(s);
		}
		catch (NumberFormatException e)
		{
			return false;
		}
		return true;
	}

}
