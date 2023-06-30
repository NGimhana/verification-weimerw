package raykernel.apps.readability.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import raykernel.apps.readability.code.CodeLine;
import raykernel.apps.readability.code.Function;
import raykernel.apps.readability.code.TrainableCodeBlock;

public class BasicReadability
{
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	//args:  snippetDir scoreFile

	static TrainableCodeBlock getNextBlock()
	{
		Function f = new Function();

		String next;

		while (true)
		{
			try
			{
				next = br.readLine();
			}
			catch (Exception e)
			{
				break;
			}

			if (next == null || next.equals(raykernel.config.Config.get("break")))
			{
				break;
			}

			f.appendLine(new CodeLine(next));
		}

		return f;

	}

	public static void main(String[] args)
	{
		ReadabilityEvaluator re = new ReadabilityEvaluator();

		while (true)
		{
			TrainableCodeBlock block = getNextBlock();

			if (block == null || block.isEmpty())
			{
				break;
			}
			else
			{
				double readability = re.getReadability(block);
				raykernel.io.Out.println("readability: " + readability + "\n");
			}
		}
	}

}